package com.zino.translatermobilizationmvp.screens.history.history.model;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;

/**
 * Created by zino on 22.4.17.
 */

public interface HistoryModel {

    List<Word> getHistory();

    void addOrRemoveFavorites(Word word);

    List<Word> getFindedHistory(String text);

    void onDestroy();
}
