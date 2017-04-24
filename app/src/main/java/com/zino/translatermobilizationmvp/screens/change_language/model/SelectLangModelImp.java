package com.zino.translatermobilizationmvp.screens.change_language.model;

import com.zino.translatermobilizationmvp.api.ApiInstance;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.LangsResponse;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.SelectedLanguage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SelectLangModelImp implements SelectLangModel {

    private Subscription languagesSubscription;
    private SelectedLanguageHelper selectedLanguageHelper;


    public SelectLangModelImp() {
        selectedLanguageHelper = new SelectedLanguageHelper();
    }

    @Override
    public void getLangs(LoadLangsListener listener) {
        languagesSubscription = ApiInstance.getApiTranslate()
                .getLangs("trnsl.1.1.20170315T092256Z.f58b16a070de6f3f.ed8cdf87417b1aefa42af52675ef3b73e5b6856c", "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langsResponse -> showTranslate(langsResponse, listener), throwable -> { throwable.printStackTrace();});
    }

    @Override
    public void onDestroy() {
        selectedLanguageHelper.onDestroy();
        if (!languagesSubscription.isUnsubscribed()) {
            languagesSubscription.unsubscribe();
        }
    }

    private void showTranslate(LangsResponse langs, LoadLangsListener listener) {
        ArrayList<Language> langsList = new ArrayList<>();
        HashMap<String, String> map = langs.getLangs();
        for (String key : map.keySet()) {
            langsList.add(new Language(map.get(key), key));
        }
        Collections.sort(langsList);

        listener.showLanguages(langsList);
    }

    @Override
    public String getSelectedLanguage(boolean isFirstLanguage) {
        SelectedLanguage selectedLanguage =  selectedLanguageHelper.getSelectedLanguage();
        if (isFirstLanguage) {
            return selectedLanguage.getFirstLanguage();
        } else {
            return selectedLanguage.getSecondLanguage();
        }
    }

    @Override
    public void saveFirstLanguage(Language firstLanguage) {
        selectedLanguageHelper.saveFirstLanguage(firstLanguage);
    }

    @Override
    public void saveSecondLanguage(Language secondLanguage) {
        selectedLanguageHelper.saveSecondLanguage(secondLanguage);
    }
}
