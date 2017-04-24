package com.zino.translatermobilizationmvp.screens.translate.model;

import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.SelectedLanguage;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.TranslateResponse;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.DictionaryResponse;

/**
 * Created by zino on 9.4.17.
 */

public interface TranslateModel {


    interface TranslateListener {

        void showTranslate(TranslateResponse translateResponse);

        void showErrorTranslate();


    }

    interface LookupListener {

        void showLookup(DictionaryResponse dictionaryResponse);

        void showErrorLookup(Throwable throwable);


    }

    void translate(String text, String from, String to, TranslateListener listener);

    void lookup(String text, String from, String to, LookupListener listener);

    SelectedLanguage getSavedSelectedLanguage();

    void saveFirstLanguage(Language firstLanguage);

    void saveSecondLanguage(Language secondLanguage);

    boolean isWordInFavorites(Word word);

    void addWordToFavorites(Word word);

    void addWordToHistory(Word word);

    Word getLastWord();

    Word getWordById(long id);


    void onDestroy();

}
