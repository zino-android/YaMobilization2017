package com.zino.translatermobilizationmvp.screens.history.favorites.model;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;

/**
 * Created by zino on 22.4.17.
 */

public interface FavoritesModel {

    List<Word> getFavorites();

    void addOrRemoveFavorites(Word word);

    List<Word> getFindedFavorites(String text);

    void onDestroy();
}
