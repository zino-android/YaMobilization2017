package com.zino.translatermobilizationmvp.screens.history.favorites.presenter;

import com.zino.translatermobilizationmvp.screens.history.favorites.model.FavoritesModel;
import com.zino.translatermobilizationmvp.screens.history.favorites.model.FavoritesModelImp;
import com.zino.translatermobilizationmvp.screens.history.favorites.view.FavoritesView;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;


public class FavoritesPresenterImp implements FavoritesPresenter {

    private FavoritesView view;
    private FavoritesModel model;

    public FavoritesPresenterImp(FavoritesView view) {
        this.view = view;
        this.model = new FavoritesModelImp();
    }

    @Override
    public void getFavorites() {
        List<Word> words = model.getFavorites();

        if (words != null && !words.isEmpty()) {
            if (view != null) {
                view.showFavorites(words);
            }
        } else {
            if (view != null) {
                view.showNoFavorites();
            }
        }
    }

    @Override
    public void addOrRemoveFavoritesClicked(Word word) {
        model.addOrRemoveFavorites(word);
        getFavorites();
    }

    @Override
    public void getFindedFavorites(String text) {
        List<Word> words = model.getFindedFavorites(text);
        if (view != null) {
            view.showFavorites(words);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        model.onDestroy();
    }
}
