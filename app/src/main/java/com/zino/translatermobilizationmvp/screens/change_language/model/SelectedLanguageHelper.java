package com.zino.translatermobilizationmvp.screens.change_language.model;

import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.SelectedLanguage;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;



public class SelectedLanguageHelper {

    private Realm realm;

    public SelectedLanguageHelper() {
        realm = Realm.getDefaultInstance();
    }

    public SelectedLanguage getSelectedLanguage() {
        RealmResults<SelectedLanguage> result;

        RealmQuery<SelectedLanguage> query = realm.where(SelectedLanguage.class);

        result = query.equalTo("id", 1).findAll();
        result.load();

        if (result.isEmpty()) {
            saveFirstLanguage(new Language("Английский", "en"));
            saveSecondLanguage(new Language("Русский", "ru"));
        }

        return realm.copyFromRealm(result.get(0));
    }

    public void saveFirstLanguage(Language firstLanguage) {

        RealmQuery<SelectedLanguage> query = realm.where(SelectedLanguage.class);

        RealmResults<SelectedLanguage> result = query.equalTo("id", 1).findAll();
        result.load();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (result.isEmpty()) {
                    SelectedLanguage selectedLanguage = realm.createObject(SelectedLanguage.class);
                    selectedLanguage.setFirstLanguage(firstLanguage.getValue());
                    selectedLanguage.setFirstLanguageName(firstLanguage.getName());
                    selectedLanguage.setSecondLanguage("ru");
                    selectedLanguage.setSecondLanguageName("Русский");
                } else {
                    SelectedLanguage selectedLanguage = result.get(0);

                    selectedLanguage.setFirstLanguage(firstLanguage.getValue());
                    selectedLanguage.setFirstLanguageName(firstLanguage.getName());

                    bgRealm.insertOrUpdate(selectedLanguage);
                }

            }
        });
    }

    public void saveSecondLanguage(Language secondLanguage) {
        RealmQuery<SelectedLanguage> query = realm.where(SelectedLanguage.class);

        RealmResults<SelectedLanguage> result = query.equalTo("id", 1).findAll();
        result.load();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (result.isEmpty()) {
                    SelectedLanguage selectedLanguage = realm.createObject(SelectedLanguage.class);
                    selectedLanguage.setSecondLanguage(secondLanguage.getValue());
                    selectedLanguage.setSecondLanguageName(secondLanguage.getName());
                    selectedLanguage.setFirstLanguage("en");
                    selectedLanguage.setFirstLanguage("Английский");
                } else {
                    SelectedLanguage selectedLanguage = result.get(0);

                    selectedLanguage.setSecondLanguage(secondLanguage.getValue());
                    selectedLanguage.setSecondLanguageName(secondLanguage.getName());

                    bgRealm.insertOrUpdate(selectedLanguage);
                }
            }
        });
    }

    public void onDestroy() {
        realm.close();
    }

}
