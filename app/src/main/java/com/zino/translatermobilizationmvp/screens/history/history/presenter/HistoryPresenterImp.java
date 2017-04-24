package com.zino.translatermobilizationmvp.screens.history.history.presenter;

import com.zino.translatermobilizationmvp.screens.history.history.model.HistoryModel;
import com.zino.translatermobilizationmvp.screens.history.history.model.HistoryModelImp;
import com.zino.translatermobilizationmvp.screens.history.history.view.HistoryView;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;


public class HistoryPresenterImp implements HistoryPresenter {

    private HistoryView view;
    private HistoryModel model;

    public HistoryPresenterImp(HistoryView view) {
        this.view = view;
        this.model = new HistoryModelImp();
    }

    @Override
    public void getHistory() {
        List<Word> words = model.getHistory();
        if (words != null && !words.isEmpty()) {
            if (view != null) {
                view.showHistory(words);
            }
        } else {
            if (view != null) {
                view.showNoHistory();
            }
        }
    }


    @Override
    public void addOrRemoveFavoritesClicked(Word word) {
        model.addOrRemoveFavorites(word);
        getHistory();
    }

    @Override
    public void getFindedHistory(String text) {
        List<Word> words = model.getFindedHistory(text);
        if (view != null) {
            view.showHistory(words);
        }

    }

    @Override
    public void onDestroy() {
        view = null;
        model.onDestroy();
    }
}
