package com.zino.translatermobilizationmvp.screens.change_language.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;



public class LangsResponse {
    @SerializedName("langs")
    private HashMap<String, String> langs;

    public HashMap<String, String> getLangs() {
        return langs;
    }

    public void setLangs(HashMap<String, String> langs) {
        this.langs = langs;
    }
}
