package com.zino.translatermobilizationmvp.screens.translate.model;

import android.util.Log;

import com.zino.translatermobilizationmvp.api.ApiInstance;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.SelectedLanguage;
import com.zino.translatermobilizationmvp.screens.change_language.model.SelectedLanguageHelper;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class TranslateModelImp implements TranslateModel {



    //SHORT_POS = 0x0002 - отображать названия частей речи в краткой форме;
    private final static int SHORT_POS_FLAG = 0x0002;
    //MORPHO = 0x0004 - включает поиск по форме слова;
    private final static int MORPHO_FLAG = 0x0004;


    private Subscription translateSubscription;
    private Subscription dictionarySubscription;
    private SelectedLanguageHelper helper;
    private WordsHelper wordsHelper;


    public TranslateModelImp() {
        helper = new SelectedLanguageHelper();
        wordsHelper = new WordsHelper();
    }


    @Override
    public void translate(String text, String from, String to, TranslateListener listener) {
        String fromTo = from + "-" + to;

        translateSubscription = ApiInstance.getApiTranslate()
                .translate("trnsl.1.1.20170315T092256Z.f58b16a070de6f3f.ed8cdf87417b1aefa42af52675ef3b73e5b6856c", text, fromTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> listener.showTranslate(response), throwable -> listener.showErrorTranslate());
    }

    @Override
    public void lookup(String text, String from, String to, LookupListener listener) {
        String fromTo = from + "-" + to;

        dictionarySubscription = ApiInstance.getApiDictionary()
                .lookup("dict.1.1.20170315T100532Z.20b8c11295de38a6.b7bee8438c083ff52ccf86f01bae9ae54255432a", text, fromTo, "ru", SHORT_POS_FLAG | MORPHO_FLAG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dictionaryResponse -> listener.showLookup(dictionaryResponse),
                        throwable -> listener.showErrorLookup(throwable));

    }

    @Override
    public SelectedLanguage getSavedSelectedLanguage() {
        return helper.getSelectedLanguage();
    }

    @Override
    public void saveFirstLanguage(Language firstLanguage) {
        helper.saveFirstLanguage(firstLanguage);
    }

    @Override
    public void saveSecondLanguage(Language secondLanguage) {
        helper.saveSecondLanguage(secondLanguage);
    }


    @Override
    public boolean isWordInFavorites(Word word) {
        return wordsHelper.isWordInFavorites(word);
    }

    @Override
    public void addWordToHistory(Word word) {
        wordsHelper.addWordToHistory(word);
    }

    @Override
    public void addWordToFavorites(Word word) {
        wordsHelper.addOrRemoveWord(word);
    }

    @Override
    public Word getLastWord() {
        return wordsHelper.getLastWord();
    }

    @Override
    public Word getWordById(long id) {
        return wordsHelper.getWordById(id);
    }

    @Override
    public void onDestroy() {
        if (translateSubscription != null) {
            translateSubscription.unsubscribe();
        }
        if (dictionarySubscription != null) {
            dictionarySubscription.unsubscribe();
        }

        helper.onDestroy();
        wordsHelper.onDestroy();
    }
}
