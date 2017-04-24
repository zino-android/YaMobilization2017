package com.zino.translatermobilizationmvp.screens.change_language.presenter;

import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;

import java.util.List;

/**
 * Created by zino on 11.4.17.
 */

public interface SelectLangPresenter {

    void loadLangs();

    int getSelectedItem(List<Language> languages, boolean isFirstLanguage);

    void onSelectLanguage(Language language, boolean isFirstLanguage);


    void onDestroy();

}
