package com.marsssvolta.translator;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class WordsViewModel extends AndroidViewModel {

    private WordsRepository mRepository;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordsRepository();
    }

    public LiveData<String> translate(String text, String langFrom, String langTo) {
        return mRepository.translate(text, langFrom, langTo);
    }
}
