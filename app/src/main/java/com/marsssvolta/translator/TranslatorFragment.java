package com.marsssvolta.translator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TranslatorFragment extends Fragment {

    private Spinner mSpinnerLanguageFrom;
    private Spinner mSpinnerLanguageTo;
    private ImageView mSwapTranslate;
    private ImageView mClearText;
    private EditText mTextInput;
    private TextView mTextTranslated;
    private String[] mCountries;

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
}
