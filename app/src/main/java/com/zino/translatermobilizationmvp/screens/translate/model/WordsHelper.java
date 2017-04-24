package com.zino.translatermobilizationmvp.screens.translate.model;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


public class WordsHelper {

    private final static String WORD_FIELD_NAME = "word";
    private final static String ID_FIELD_NAME = "id";
    private final static String IN_HISTORY_FIELD_NAME = "inHistory";
    private final static String IN_FAVORITES_FIELD_NAME = "inFavorites";
    private final static String TRANSLATE_FIELD_NAME = "translate";

    private Realm realm;

    public WordsHelper() {
        realm = Realm.getDefaultInstance();
    }

    public void addOrRemoveWord(Word word) {

        if (isWordInFavorites(word)) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {

                    RealmQuery<Word> query = bgRealm.where(Word.class);

                    RealmResults<Word> result = query.equalTo(WORD_FIELD_NAME, word.getWord()).findAll();
                    result.load();

                    Word w = result.first();
                    w.setInFavorites(false);
                }
            });
        } else {
            if (isWordInHistory(word)) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {

                        RealmQuery<Word> query = bgRealm.where(Word.class);

                        RealmResults<Word> result = query.equalTo(WORD_FIELD_NAME, word.getWord()).findAll();
                        result.load();

                        Word w = result.first();
                        w.setInFavorites(true);
                    }
                });
            } else {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {

                        word.setInFavorites(true);
                        word.setId(getNextKey());
                        bgRealm.copyToRealmOrUpdate(word);
                    }
                });
            }
        }



    }

    public void addWordToHistory(Word word) {

        if (!realm.isClosed()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    if (isWordInHistory(word)) {
                        RealmQuery<Word> query = bgRealm.where(Word.class);

                        RealmResults<Word> result = query.equalTo(WORD_FIELD_NAME, word.getWord()).findAll();
                        result.load();
                        word.setInFavorites(result.first().isInFavorites());
                        result.deleteAllFromRealm();
                    }

                    word.setId(getNextKey());
                    word.setInHistory(true);

                    bgRealm.copyToRealmOrUpdate(word);
                }
            });
        }

    }

    public boolean isWordInHistory(Word word) {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(WORD_FIELD_NAME, word.getWord()).equalTo(IN_HISTORY_FIELD_NAME, true).findAll();

        return !result.isEmpty();
    }




    public boolean isWordInFavorites(Word word) {

        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.beginGroup().equalTo(WORD_FIELD_NAME, word.getWord()).equalTo(IN_FAVORITES_FIELD_NAME, true).endGroup().findAll();

        return !result.isEmpty();
    }

    public List<Word> getFavorites() {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(IN_FAVORITES_FIELD_NAME, true).findAll();
        result.load();

        return result.sort(ID_FIELD_NAME, Sort.DESCENDING);
    }


    public List<Word> getHistory() {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(IN_HISTORY_FIELD_NAME, true).findAll();
        result.load();

        return  result.sort(ID_FIELD_NAME, Sort.DESCENDING);

    }




    //Получаем следующий id
    public int getNextKey()
    {
        try {
            return realm.where(Word.class).max(ID_FIELD_NAME).intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public Word getLastWord() {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.findAll();
        result.load();

        result = result.sort(ID_FIELD_NAME, Sort.DESCENDING);

        if (result.isEmpty()) {
            return null;
        } else {
            return result.first();
        }


    }

    public Word getWordById(long id) {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(ID_FIELD_NAME, id).findAll();
        result.load();

        if (result.isEmpty()) {
            return null;
        } else {
            return result.first();
        }
    }

    public void deleteAllFromHistory() {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(IN_HISTORY_FIELD_NAME, true).equalTo(IN_FAVORITES_FIELD_NAME, false).findAll();
        result.load();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                result.deleteAllFromRealm();
            }
        });

        RealmQuery<Word> queryFav = realm.where(Word.class);

        RealmResults<Word> resultFav = queryFav.equalTo(IN_FAVORITES_FIELD_NAME, true).findAll();
        resultFav.load();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                for (Word word : resultFav) {
                    word.setInHistory(false);
                }
            }
        });

    }

    public List<Word> getFindedHistory(String text) {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(IN_HISTORY_FIELD_NAME, true)
                .beginGroup().contains(WORD_FIELD_NAME, text, Case.INSENSITIVE).or()
                .contains(TRANSLATE_FIELD_NAME, text, Case.INSENSITIVE).endGroup().findAll();

        result.load();

        return  result.sort(ID_FIELD_NAME, Sort.DESCENDING);
    }

    public List<Word> getFindedFavorites(String text) {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(IN_FAVORITES_FIELD_NAME, true).beginGroup()
                .contains(WORD_FIELD_NAME, text, Case.INSENSITIVE).or()
                .contains(TRANSLATE_FIELD_NAME, text, Case.INSENSITIVE).endGroup().findAll();

        result.load();

        return  result.sort(ID_FIELD_NAME, Sort.DESCENDING);
    }

    public void deleteAllFromFavorites() {
        RealmQuery<Word> query = realm.where(Word.class);

        RealmResults<Word> result = query.equalTo(IN_FAVORITES_FIELD_NAME, true).findAll();
        result.load();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                result.deleteAllFromRealm();
            }
        });

    }

    public void onDestroy() {
        realm.close();
    }
}
