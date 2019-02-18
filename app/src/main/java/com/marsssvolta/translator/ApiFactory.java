package com.marsssvolta.translator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Основной сетевой код
public class ApiFactory {

    String mTranslatedText;

    public String translate(String text, String langFrom, String langTo) {

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
            mTranslatedText = responseTranslate.body().getText().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mTranslatedText;
    }
}
