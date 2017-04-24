package com.zino.translatermobilizationmvp.screens.translate.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class TranslateResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("lang")
    private String lang;
    @SerializedName("text")
    private List<String> texts;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }
}
