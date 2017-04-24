package com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class PartOfSpeech extends RealmObject {

    @SerializedName("text")
    private String text;
    @SerializedName("pos")
    private String pos;
    @SerializedName("ts")
    private String ts;
    @SerializedName("tr")
    private RealmList<Translation> translations;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public RealmList<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(RealmList<Translation> translations) {
        this.translations = translations;
    }
}
