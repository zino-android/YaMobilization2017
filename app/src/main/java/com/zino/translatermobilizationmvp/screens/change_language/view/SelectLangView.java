package com.zino.translatermobilizationmvp.screens.change_language.view;

import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;

import java.util.List;



public interface SelectLangView {

    void showLangs(List<Language> languages);

    void showError();

}
