package com.marsssvolta.translator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Объект модели
public class TranslatedText {

    @SerializedName("text")
    @Expose
    private List<String> text = null;

    public List<String> getText() {
        return text;
    }
}
