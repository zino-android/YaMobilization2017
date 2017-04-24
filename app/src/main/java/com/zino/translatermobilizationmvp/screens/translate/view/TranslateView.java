package com.zino.translatermobilizationmvp.screens.translate.view;

import android.text.SpannableString;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.PartOfSpeech;

/**
 * Created by zino on 9.4.17.
 */

public interface TranslateView {

    void showProgress();

    void hideProgress();

    void clearTranslation();

    void showTranslate(String translate);

    void showError();

    void showTextIsEmpty();

    void showTextIsNotEmpty();

    void removeDictionaryViews();

    int getColor(int colorId);

    void addTranscriptionTextView(SpannableString text);

    void addPosLayout(PartOfSpeech pos);

    void startMicAnimation();

    void stopMicAnimation();

    void showVocalLoading();

    void hideVocalLoading();

    void showVocalTranslateLoading();

    void hideVocalTranslateLoading();

    void requestPermission();

    void setFirstLanguage(String text);

    void setSecondLanguage(String text);

    void setVocalizerAvailableForSecondLanguage(boolean available);

    void setVocalizerAvailableForFirstLanguage(boolean available);

    void setRecognizerAvailable(boolean available);

    void setBookmarkButtonImage(boolean inFavorites);

    void setTextToTranslateEditText(String text);

    void setRecognitionText(String text);



}
