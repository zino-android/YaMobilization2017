package com.zino.translatermobilizationmvp.screens.change_language.model.pojo;

import io.realm.RealmObject;



public class Language extends RealmObject implements Comparable<Language>{
    private String name;
    private String value;

    public Language(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Language() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Language language) {
        return this.getName().compareTo(language.getName());
    }
}
