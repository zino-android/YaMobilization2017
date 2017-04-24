package com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;



public class TranslationExample extends RealmObject {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
