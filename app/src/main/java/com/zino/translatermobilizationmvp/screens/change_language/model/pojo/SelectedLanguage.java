package com.zino.translatermobilizationmvp.screens.change_language.model.pojo;

import io.realm.RealmObject;


public class SelectedLanguage extends RealmObject {

    private int id = 1;

    private String firstLanguage;

    private String secondLanguage;

    private String firstLanguageName;

    private String secondLanguageName;

    public SelectedLanguage() {

    }

    public String getFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public String getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.secondLanguage = secondLanguage;
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
}
