package com.zino.translatermobilizationmvp.screens.translate.model.pojo;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.DictionaryResponse;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;


public class Word extends RealmObject {

    @PrimaryKey
    @Index
    private long id;

    private String word;

    private String translate;

    private String firstLanguageValue;

    private String firstLanguageName;

    private String secondLanguageValue;

    private String secondLanguageName;

    private boolean inFavorites;

    private boolean inHistory;

    private DictionaryResponse dictionaryResponse;

    public Word() {

    }

    public boolean isInHistory() {
        return inHistory;
    }

    public void setInHistory(boolean inHistory) {
        this.inHistory = inHistory;
    }

    public String getSecondLanguageName() {
        return secondLanguageName;
    }

    public void setSecondLanguageName(String secondLanguageName) {
        this.secondLanguageName = secondLanguageName;
    }

    public String getFirstLanguageName() {
        return firstLanguageName;
    }

    public void setFirstLanguageName(String firstLanguageName) {
        this.firstLanguageName = firstLanguageName;
    }

    public boolean isInFavorites() {
        return inFavorites;
    }

    public void setInFavorites(boolean inFavorites) {
        this.inFavorites = inFavorites;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getFirstLanguageValue() {
        return firstLanguageValue;
    }

    public void setFirstLanguageValue(String firstLanguageValue) {
        this.firstLanguageValue = firstLanguageValue;
    }

    public String getSecondLanguageValue() {
        return secondLanguageValue;
    }

    public void setSecondLanguageValue(String secondLanguageValue) {
        this.secondLanguageValue = secondLanguageValue;
    }

    public DictionaryResponse getDictionaryResponse() {
        return dictionaryResponse;
    }

    public void setDictionaryResponse(DictionaryResponse dictionaryResponse) {
        this.dictionaryResponse = dictionaryResponse;
    }
}
