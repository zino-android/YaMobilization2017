package com.zino.translatermobilizationmvp.screens.translate.presenter;

import android.text.SpannableString;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.DictionaryResponse;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Translation;

/**
 * Created by zino on 9.4.17.
 */

public interface TranslatePresenter {

    void lookup(String text);

    void translate(String text);

    void onDestroy();

    void clearButtonClicked();

    SpannableString getTranscriptionSpan(DictionaryResponse dictionaryResponse);

    String getExamplesString(Translation translations);

    String getSynonymString(Translation translations);

    SpannableString getTranslationsString(Translation translations);

    void micButtonClicked();

    void vocalButtonClick(String text);

    void vocalTranslateButtonClicked(String text);

    void createAndStartRecognizer();

    void getSavedSelectLanguage();

    void swapLanguages();

    boolean isWordInFavorites(Word word);

    void addOrRemoveToFavoritesClicked(String word);

    void getLastWord();

    void getWordById(long id);
}
