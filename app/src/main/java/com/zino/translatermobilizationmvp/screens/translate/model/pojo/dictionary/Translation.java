package com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Translation extends RealmObject {

    @SerializedName("text")
    private String text;
    @SerializedName("pos")
    private String pos;
    @SerializedName("gen")
    private String gen;
    @SerializedName("syn")
    private RealmList<Synonym> synonyms;
    @SerializedName("mean")
    private RealmList<Meaning> meanings;
    @SerializedName("ex")
    private RealmList<Example> examples;

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

    public RealmList<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(RealmList<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public RealmList<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(RealmList<Meaning> meanings) {
        this.meanings = meanings;
    }

    public RealmList<Example> getExamples() {
        return examples;
    }

    public void setExamples(RealmList<Example> examples) {
        this.examples = examples;
    }
}
