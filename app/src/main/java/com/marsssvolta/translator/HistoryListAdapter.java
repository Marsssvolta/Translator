package com.marsssvolta.translator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>{

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private HistoryWords mHistoryWords;
        private final TextView mSourceTextView;
        private final TextView mTranslatedTextView;

        private HistoryViewHolder(View itemView) {
            super(itemView);
            mSourceTextView = itemView.findViewById(R.id.list_item_word_source);
            mTranslatedTextView = itemView.findViewById(R.id.list_item_word_translated);
        }

        void bindHistoryWords(HistoryWords historyWords) {
            mHistoryWords = historyWords;
            mSourceTextView.setText(mHistoryWords.getSource());
            mTranslatedTextView.setText(mHistoryWords.getTranslated());
        }
    }

    private final LayoutInflater mInflater;
    private List<HistoryWords> mHistoryWords = Collections.emptyList();

    HistoryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryWords historyWords = mHistoryWords.get(position);
        holder.bindHistoryWords(historyWords);
    }

    void setHistoryWords(List<HistoryWords> historyWords) {
        mHistoryWords = historyWords;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHistoryWords.size();
    }
}
