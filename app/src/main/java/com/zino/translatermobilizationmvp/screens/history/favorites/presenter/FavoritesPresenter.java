package com.zino.translatermobilizationmvp.screens.history.favorites.presenter;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;


public interface FavoritesPresenter {

    void getFavorites();

    void addOrRemoveFavoritesClicked(Word word);

    void getFindedFavorites(String text);

    void onDestroy();

}
