package com.marsssvolta.translator;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HistoryFragment extends Fragment {

    private WordsViewModel mWordsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        HistoryListAdapter historyListAdapter = new HistoryListAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(historyListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mWordsViewModel = ViewModelProviders.of(this).get(WordsViewModel.class);
        // Установка списка
        mWordsViewModel.getAllHistoryWords().observe(this, new Observer<List<HistoryWords>>() {
            @Override
            public void onChanged(@Nullable List<HistoryWords> historyWords) {
                historyListAdapter.setHistoryWords(historyWords);
            }
        });

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
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.dialog_delete_all_notes)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mWordsViewModel.deleteAll();
                        Snackbar.make(getActivity().findViewById(R.id.fragmentCoordinatorLayout),
                                R.string.toast_delete_notes, Snackbar.LENGTH_LONG).show();
                    }
                }).setNegativeButton(R.string.cancel, null).show();
    }
}
