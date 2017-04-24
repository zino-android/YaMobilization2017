package com.zino.translatermobilizationmvp.screens.history.history.presenter;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;



public interface HistoryPresenter {

    void getHistory();

    void addOrRemoveFavoritesClicked(Word word);

    void getFindedHistory(String text);

    void onDestroy();

}
