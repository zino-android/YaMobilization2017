package com.zino.translatermobilizationmvp.screens.history.favorites.model;

import com.zino.translatermobilizationmvp.screens.translate.model.WordsHelper;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;



public class FavoritesModelImp implements FavoritesModel {

    private WordsHelper helper;


    public FavoritesModelImp() {
        this.helper = new WordsHelper();
    }

    @Override
    public List<Word> getFavorites() {
        return helper.getFavorites();
    }

    @Override
    public void addOrRemoveFavorites(Word word) {
        helper.addOrRemoveWord(word);
    }

    @Override
    public List<Word> getFindedFavorites(String text) {
        return helper.getFindedFavorites(text);
    }

    @Override
    public void onDestroy() {
        helper.onDestroy();
    }
}
