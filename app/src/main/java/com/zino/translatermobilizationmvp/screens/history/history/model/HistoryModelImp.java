package com.zino.translatermobilizationmvp.screens.history.history.model;

import com.zino.translatermobilizationmvp.screens.translate.model.WordsHelper;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;




public class HistoryModelImp implements HistoryModel {


    private WordsHelper helper;


    public HistoryModelImp() {
        this.helper = new WordsHelper();
    }


    @Override
    public List<Word> getHistory() {
        return helper.getHistory();
    }

    @Override
    public void addOrRemoveFavorites(Word word) {
        helper.addOrRemoveWord(word);
    }

    @Override
    public List<Word> getFindedHistory(String text) {
        return helper.getFindedHistory(text);
    }

    @Override
    public void onDestroy() {
        helper.onDestroy();
    }
}
