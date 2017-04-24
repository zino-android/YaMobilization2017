package com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class Synonym extends RealmObject {

    @SerializedName("text")
    private String text;
    @SerializedName("pos")
    private String pos;
    @SerializedName("gen")
    private String gen;
    @SerializedName("num")
    private String num;

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

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
