package com.zino.translatermobilizationmvp.screens.change_language.model;

import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;

import java.util.List;

/**
 * Created by zino on 11.4.17.
 */

public interface SelectLangModel {

    interface LoadLangsListener {
        void showLanguages(List<Language> languages);

        void showErrorLanguages();
    }

    void getLangs(LoadLangsListener listener);

    String getSelectedLanguage(boolean isFirstLanguage);

    void saveFirstLanguage(Language language);

    void saveSecondLanguage(Language language);

    void onDestroy();

}
