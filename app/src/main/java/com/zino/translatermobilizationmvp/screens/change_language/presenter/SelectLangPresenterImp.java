package com.zino.translatermobilizationmvp.screens.change_language.presenter;

import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.model.SelectLangModel;
import com.zino.translatermobilizationmvp.screens.change_language.model.SelectLangModelImp;
import com.zino.translatermobilizationmvp.screens.change_language.view.SelectLangView;

import java.util.List;


public class SelectLangPresenterImp implements SelectLangPresenter, SelectLangModel.LoadLangsListener {

    private SelectLangView view;
    private SelectLangModel model;

    public SelectLangPresenterImp(SelectLangView view) {
        this.view = view;
        this.model = new SelectLangModelImp();

    }

    @Override
    public void loadLangs() {
        model.getLangs(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        model.onDestroy();
    }

    @Override
    public void showLanguages(List<Language> languages) {
        if (view != null) {
            view.showLangs(languages);
        }
    }

    @Override
    public int getSelectedItem(List<Language> languages, boolean isFirstLanguage) {
        String valueSelected = model.getSelectedLanguage(isFirstLanguage);
        int selected = -1;
        for (int i = 0; i < languages.size(); i++) {
            if (languages.get(i).getValue().equals(valueSelected)) {
                selected = i;
                break;
            }
        }
        return selected;
    }

    @Override
    public void onSelectLanguage(Language language, boolean isFirstLanguage) {
        if (isFirstLanguage) {
            model.saveFirstLanguage(language);
        } else {
            model.saveSecondLanguage(language);
        }
    }


    @Override
    public void showErrorLanguages() {
        if (view != null) {
            view.showError();
        }
    }
}
