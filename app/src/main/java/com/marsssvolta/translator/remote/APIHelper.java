package com.marsssvolta.translator.remote;

import com.marsssvolta.translator.model.TranslatedText;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

//Интерфейс для запросов
public interface APIHelper {
    @POST("api/v1.5/tr.json/translate")
    Call<TranslatedText> getTranslation(@Query("key") String APIKey,
                                        @Query("text") String textToTranslate,
                                        @Query("lang") String lang);
}
