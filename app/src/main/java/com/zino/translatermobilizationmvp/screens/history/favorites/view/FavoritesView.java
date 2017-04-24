package com.zino.translatermobilizationmvp.screens.history.favorites.view;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;

/**
 * Created by zino on 22.4.17.
 */

public interface FavoritesView {

    void showFavorites(List<Word> favorites);

    void showNoFavorites();

}
