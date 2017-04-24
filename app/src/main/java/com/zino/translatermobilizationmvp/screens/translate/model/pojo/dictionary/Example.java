package com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Example extends RealmObject {

    @SerializedName("text")
    private String text;
    @SerializedName("tr")
    private RealmList<TranslationExample> translationExamples;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RealmList<TranslationExample> getTranslationExamples() {
        return translationExamples;
    }

    public void setTranslationExamples(RealmList<TranslationExample> translationExamples) {
        this.translationExamples = translationExamples;
    }
}
