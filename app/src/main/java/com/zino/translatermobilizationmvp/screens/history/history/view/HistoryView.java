package com.zino.translatermobilizationmvp.screens.history.history.view;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;

/**
 * Created by zino on 22.4.17.
 */

public interface HistoryView {

    void showHistory(List<Word> history);

    void showNoHistory();

}
