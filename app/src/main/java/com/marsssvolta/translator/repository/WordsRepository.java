package com.marsssvolta.translator.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.marsssvolta.translator.remote.APIHelper;
import com.marsssvolta.translator.model.HistoryWords;
import com.marsssvolta.translator.db.HistoryWordsDao;
import com.marsssvolta.translator.db.HistoryWordsRoomDatabase;
import com.marsssvolta.translator.model.TranslatedText;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordsRepository {

    private HistoryWordsDao mHistoryWordsDao;
    private LiveData<List<HistoryWords>> mAllHistoryWords;

    public WordsRepository(Application application) {
        HistoryWordsRoomDatabase db = HistoryWordsRoomDatabase.getDatabase(application);
        mHistoryWordsDao = db.historyWordsDao();
        mAllHistoryWords = mHistoryWordsDao.getHistoryWords();
    }

    // Установка истории
    public LiveData<List<HistoryWords>> getAllHistoryWords() {
        return mAllHistoryWords;
    }

    // Добавление в историю
    public void insert(HistoryWords historyWords) {
        new insertAsyncTask(mHistoryWordsDao).execute(historyWords);
    }

    private static class insertAsyncTask extends AsyncTask<HistoryWords, Void, Void> {

        private HistoryWordsDao mAsyncTaskDao;

        insertAsyncTask(HistoryWordsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final HistoryWords... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // Очистка истории
    public void deleteAll(){
        new DeleteAllAsyncTask(mHistoryWordsDao).execute();
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private HistoryWordsDao mAsyncTaskDao;

        DeleteAllAsyncTask(HistoryWordsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    // Перевод
    public MutableLiveData<String> translate(String text, String langFrom, String langTo) {

        final MutableLiveData<String> translateResponseLiveData = new MutableLiveData<>();

        String apiKey = "trnsl.1.1.20181017T060237Z.da104919343eec52.3" +
                "b53694d0cf917ef528b3a939d42ec1a9af43545";

        Retrofit query = new Retrofit
                .Builder()
                .baseUrl("https://translate.yandex.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIHelper apiHelper = query.create(APIHelper.class);
        Call<TranslatedText> call = apiHelper
                .getTranslation(apiKey, text, langFrom +
                        "-" + langTo);
        try {
            Response<TranslatedText> responseTranslate = call.execute();
            translateResponseLiveData.postValue(Objects.requireNonNull(responseTranslate.body())
                    .getText().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return translateResponseLiveData;
    }
}
