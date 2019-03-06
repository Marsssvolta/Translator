package com.marsssvolta.translator.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.marsssvolta.translator.R;
import com.marsssvolta.translator.viewmodel.WordsViewModel;

import java.util.Objects;

import static android.support.v7.widget.OrientationHelper.VERTICAL;

public class HistoryFragment extends Fragment {

    private WordsViewModel mWordsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        HistoryListAdapter historyListAdapter = new HistoryListAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(historyListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Подчёркивание пунктов списка
        DividerItemDecoration itemDecor = new DividerItemDecoration(Objects
                .requireNonNull(getContext()), VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        mWordsViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);
        // Установка списка
        mWordsViewModel.getAllHistoryWords().observe(this, historyListAdapter::setHistoryWords);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteListDialog();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    // Диалог очистки списка
    public void deleteListDialog() {
        new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                .setMessage(R.string.dialog_delete_all_notes)
                .setPositiveButton(R.string.delete, (dialog, whichButton) -> {
                    mWordsViewModel.deleteAll();
                    Snackbar.make(Objects.requireNonNull(getActivity())
                                    .findViewById(R.id.fragmentCoordinatorLayout),
                            R.string.toast_delete_notes, Snackbar.LENGTH_LONG).show();
                }).setNegativeButton(R.string.cancel, null).show();
    }
}
