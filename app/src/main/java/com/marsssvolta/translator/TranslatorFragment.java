package com.marsssvolta.translator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import rx.functions.Func1;

public class TranslatorFragment extends Fragment {

    private static final String TAG = "TranslatorFragment";

    private Spinner mSpinnerLanguageFrom;
    private Spinner mSpinnerLanguageTo;
    private ImageView mSwapTranslate;
    private ImageView mClearText;
    private EditText mTextInput;
    private TextView mTextTranslated;
    private String[] mCountries;
    private WordsViewModel mWordsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
                .get(WordsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translator, container, false);

        mSpinnerLanguageFrom = view.findViewById(R.id.spinner_language_from);
        mSpinnerLanguageTo = view.findViewById(R.id.spinner_language_to);
        mSwapTranslate = view.findViewById(R.id.swap_translate);
        mClearText = view.findViewById(R.id.clear_text);
        mTextInput = view.findViewById(R.id.text_input);
        mTextTranslated = view.findViewById(R.id.text_translated);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setSpinners();
        swapButtonListener();
        clearButtonListener();
        textChangedListener();
    }

    //Установка спиннеров
    public void setSpinners() {
        List<String> categories = new ArrayList<>();

        mCountries = getResources().getStringArray(R.array.countries);
        Collections.addAll(categories, mCountries);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerLanguageFrom.setAdapter(arrayAdapter);
        mSpinnerLanguageFrom.setSelection(3);
        mSpinnerLanguageTo.setAdapter(arrayAdapter);
        mSpinnerLanguageTo.setSelection(60);
    }

    //Изменение направления перевода
    public void swapButtonListener() {
        mSwapTranslate.setOnClickListener(v -> {
            int sourceLng = mSpinnerLanguageFrom.getSelectedItemPosition();
            int targetLng = mSpinnerLanguageTo.getSelectedItemPosition();

            mSpinnerLanguageFrom.setSelection(targetLng);
            mSpinnerLanguageTo.setSelection(sourceLng);
        });
    }

    //Очистка текста
    public void clearButtonListener() {
        mClearText.setOnClickListener(v -> {
            mTextInput.setText("");
            mTextTranslated.setText("");
        });
    }

    //Перевод текста
    public void translate(String text) {
        String language1 = String.valueOf(mSpinnerLanguageFrom.getSelectedItem());
        String language2 = String.valueOf(mSpinnerLanguageTo.getSelectedItem());

        mWordsViewModel.translate(text, langCode(language1), langCode(language2)).observe(
                this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable final String text) {
                        Log.i(TAG, "onChanged: " + text);
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextTranslated.setText(text);
                            }
                        });
                    }
                });
    }

    //Получение кода языка
    public String langCode(String selectedLang) {
        String code = null;
        String[] сodes = getResources().getStringArray(R.array.codes);

        for (int i = 0; i < mCountries.length; i++) {
            if (selectedLang.equals(mCountries[i])) {
                code = сodes[i];
            }
        }

        return code;
    }

    //Слушатель ввода
    public void textChangedListener() {
        RxTextView.textChanges(mTextInput).
                filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() > 0;
                    }
                }).
                debounce(750, TimeUnit.MILLISECONDS).
                subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        TranslatorFragment.this.translate(charSequence.toString().trim());
                    }
                });
    }
}
