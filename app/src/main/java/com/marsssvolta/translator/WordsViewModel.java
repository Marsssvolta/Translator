package com.marsssvolta.translator;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WordsViewModel extends AndroidViewModel {

    private WordsRepository mRepository;

    private LiveData<List<HistoryWords>> mAllHistoryWords;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordsRepository(application);
        mAllHistoryWords = mRepository.getAllHistoryWords();
    }

    // Установка истории
    public LiveData<List<HistoryWords>> getAllHistoryWords() {
        return mAllHistoryWords;
    }

    // Добавление в историю
    public void insert(HistoryWords historyWords) {
        mRepository.insert(historyWords);
    }

    // Очистка истории
    public void deleteAll() {
        mRepository.deleteAll();
    }

    // Перевод
    public LiveData<String> translate(String text, String langFrom, String langTo) {
        return mRepository.translate(text, langFrom, langTo);
    }
}
