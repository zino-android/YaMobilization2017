package com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;



public class DictionaryResponse extends RealmObject {

    @SerializedName("def")
    private RealmList<PartOfSpeech> partsOfSpeech;

    @SerializedName("code")
    private int code;

    public RealmList<PartOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(RealmList<PartOfSpeech> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
