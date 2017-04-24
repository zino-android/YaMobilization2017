package com.zino.translatermobilizationmvp.screens.translate.model.pojo;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;


@RealmClass
public class RealmString extends RealmObject {
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
