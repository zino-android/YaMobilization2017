package com.zino.translatermobilizationmvp.screens.translate.presenter;

import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.Language;
import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.SelectedLanguage;
import com.zino.translatermobilizationmvp.screens.translate.model.TranslateModel;
import com.zino.translatermobilizationmvp.screens.translate.model.TranslateModelImp;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.TranslateResponse;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.DictionaryResponse;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.ErrorBody;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.PartOfSpeech;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Translation;
import com.zino.translatermobilizationmvp.screens.translate.view.TranslateView;
import com.zino.translatermobilizationmvp.utils.StringUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;



public class TranslatePresenterImp implements TranslatePresenter,
        TranslateModel.TranslateListener,
        TranslateModel.LookupListener,
        VocalizerListener, RecognizerListener {


    private TranslateView view;
    private TranslateModel translateModel;

    private Vocalizer vocalizer;
    private Recognizer recognizer;

    private String firstLanguageValue;
    private String secondLanguageValue;
    private String firstLanguageName;
    private String secondLanguageName;

    private DictionaryResponse dictionary;
    private String translate;
    private String inputWord;



    public TranslatePresenterImp(TranslateView view) {
        this.view = view;
        this.translateModel = new TranslateModelImp();
    }

    @Override
    public void lookup(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (view != null) {
                view.showProgress();
                view.showTextIsNotEmpty();
            }
            translateModel.lookup(text, firstLanguageValue, secondLanguageValue, this);
        } else {
            if (view != null) {
                view.showTextIsEmpty();
            }
        }
    }



    @Override
    public void translate(String text) {
        if (!TextUtils.isEmpty(text)) {
            inputWord = text;
            if (view != null) {
                view.showProgress();
                view.showTextIsNotEmpty();

                Word word = new Word();
                word.setWord(text);
                word.setFirstLanguageValue(firstLanguageValue);
                word.setSecondLanguageValue(secondLanguageValue);
                word.setFirstLanguageName(firstLanguageName);
                word.setSecondLanguageName(secondLanguageName);
                word.setDictionaryResponse(dictionary);
                word.setTranslate(translate);

                view.setBookmarkButtonImage(isWordInFavorites(word));
            }
            translateModel.translate(text, firstLanguageValue, secondLanguageValue, this);
        } else {
            if (view != null) {
                view.showTextIsEmpty();
            }
        }
    }

    @Override
    public void clearButtonClicked() {
        if (view != null) {
            view.clearTranslation();
            view.showTextIsEmpty();
        }
    }


    @Override
    public void showTranslate(TranslateResponse translateResponse) {
        if (translateResponse.getCode() == 200) {
            if (view != null) {
                view.hideProgress();
                translate = translateResponse.getTexts().get(0);
                view.showTranslate(translate);
            }
        } else {
            showErrorTranslate();
        }
    }

    @Override
    public void showErrorTranslate() {
        translate = null;
        if (view != null) {
            view.showError();
            view.hideProgress();
        }
    }

    @Override
    public void showLookup(DictionaryResponse dictionaryResponse) {
        if (dictionaryResponse != null && dictionaryResponse.getPartsOfSpeech() != null && dictionaryResponse.getPartsOfSpeech().size() != 0) {

            dictionary = dictionaryResponse;
            if (view != null) {
                view.removeDictionaryViews();

                view.addTranscriptionTextView(getTranscriptionSpan(dictionaryResponse));

                for (PartOfSpeech pos : dictionaryResponse.getPartsOfSpeech()) {

                    view.addPosLayout(pos);

                }
            }
        } else {
            if (view != null) {
                view.removeDictionaryViews();
            }
            dictionary = null;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                addWordToHistory(inputWord);
            }
        }, 500);

    }

    @Override
    public void showErrorLookup(Throwable throwable) {
        throwable.printStackTrace();
        dictionary = null;

        if (throwable instanceof HttpException) {
            try {
                ErrorBody error = new Gson().fromJson(((HttpException) throwable).response().errorBody().string(), ErrorBody.class);
                if (error.getCode() == 501) {
                    // Словарь не поддерживает текущий язык
                    if (view != null) {
                        view.removeDictionaryViews();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SpannableString getTranscriptionSpan(DictionaryResponse dictionaryResponse) {
        return StringUtils.getTranscriptionSpan(dictionaryResponse, view.getColor(R.color.colorGray));
    }

    @Override
    public String getExamplesString(Translation translations) {
        return StringUtils.getExamplesString(translations);
    }

    @Override
    public String getSynonymString(Translation translations) {
        return StringUtils.getSynonymString(translations);
    }

    @Override
    public SpannableString getTranslationsString(Translation translations) {
        return StringUtils.getTranslationsString(translations, view.getColor(R.color.translate_edit_text_default_gray));
    }


    @Override
    public void onRecordingBegin(Recognizer recognizer) {

    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {

    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        if (view != null) {
            view.stopMicAnimation();
        }

    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {

    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {

    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {

    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        String recognitionText = recognition.getBestResultText().trim();
        if (view != null) {
            view.setRecognitionText(recognitionText);
        }
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {

    }

    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {

    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {

    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {

    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {
        if (view != null) {
            view.hideVocalLoading();
            view.hideVocalTranslateLoading();
        }
    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, Error error) {

    }


    @Override
    public void micButtonClicked() {
        if (isRecognizerAvailable()) {
            createAndStartRecognizer();
            if (view != null) {
                view.startMicAnimation();
            }
        }
    }

    @Override
    public void vocalButtonClick(String text) {
        if (isVocalizerAvailableForFirstLanguage()) {
            resetVocalizer();

            String language = null;
            switch (firstLanguageValue) {
                case "ru":
                    language = Recognizer.Language.RUSSIAN;
                    break;
                case "uk":
                    language = Recognizer.Language.UKRAINIAN;
                    break;
                case "tr":
                    language = Recognizer.Language.TURKISH;
                    break;
                case "en":
                    language = Recognizer.Language.ENGLISH;
                    break;
                default:
                    language = null;
                    break;


            }
            if (!TextUtils.isEmpty(language)) {

                vocalizer = Vocalizer.createVocalizer(language, text, true, Vocalizer.Voice.JANE);
                vocalizer.setListener(this);
                vocalizer.start();
                if (view != null) {
                    view.showVocalLoading();
                }
            }
        }
    }


    @Override
    public void vocalTranslateButtonClicked(String text) {
        if (isVocalizerAvailableForSecondLanguage()) {

            resetVocalizer();

            String language = null;
            switch (secondLanguageValue) {
                case "ru":
                    language = Recognizer.Language.RUSSIAN;
                    break;
                case "uk":
                    language = Recognizer.Language.UKRAINIAN;
                    break;
                case "tr":
                    language = Recognizer.Language.TURKISH;
                    break;
                case "en":
                    language = Recognizer.Language.ENGLISH;
                    break;
                default:
                    language = null;
                    break;


            }
            if (!TextUtils.isEmpty(language)) {
                vocalizer = Vocalizer.createVocalizer(language, text, true, Vocalizer.Voice.JANE);
                vocalizer.setListener(this);
                vocalizer.start();
                if (view != null) {
                    view.showVocalTranslateLoading();
                }
            }
        }
    }

    private void resetVocalizer() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
        if (view != null) {
            view.hideVocalLoading();
            view.hideVocalTranslateLoading();
        }
    }

    private void resetRecognizer() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer = null;
        }
    }


    public void createAndStartRecognizer() {

        view.requestPermission();
        resetRecognizer();

        String language = null;
        switch (firstLanguageValue) {
            case "ru":
                language = Recognizer.Language.RUSSIAN;
                break;
            case "uk":
                language = Recognizer.Language.UKRAINIAN;
                break;
            case "tr":
                language = Recognizer.Language.TURKISH;
                break;
            case "en":
                language = Recognizer.Language.ENGLISH;
                break;
            default:
                language = null;
                break;


        }
        if (!TextUtils.isEmpty(language)) {
            recognizer = Recognizer.create(language, Recognizer.Model.NOTES, this);

            recognizer.start();
        }
    }

    @Override
    public void getSavedSelectLanguage() {
        SelectedLanguage selectedLanguage = translateModel.getSavedSelectedLanguage();
        firstLanguageValue = selectedLanguage.getFirstLanguage();
        secondLanguageValue = selectedLanguage.getSecondLanguage();
        firstLanguageName = selectedLanguage.getFirstLanguageName();
        secondLanguageName = selectedLanguage.getSecondLanguageName();
        if (view != null) {
            view.setFirstLanguage(firstLanguageName);
            view.setSecondLanguage(secondLanguageName);
            view.setVocalizerAvailableForFirstLanguage(isVocalizerAvailableForFirstLanguage());
            view.setVocalizerAvailableForSecondLanguage(isVocalizerAvailableForSecondLanguage());
            view.setRecognizerAvailable(isRecognizerAvailable());
        }
    }

    @Override
    public void swapLanguages() {
        SelectedLanguage selectedLanguage = translateModel.getSavedSelectedLanguage();
        translateModel.saveFirstLanguage(new Language(selectedLanguage.getSecondLanguageName(), selectedLanguage.getSecondLanguage()));
        translateModel.saveSecondLanguage(new Language(selectedLanguage.getFirstLanguageName(), selectedLanguage.getFirstLanguage()));
        getSavedSelectLanguage();
    }

    @Override
    public boolean isWordInFavorites(Word word) {
        return translateModel.isWordInFavorites(word);
    }

    private boolean isVocalizerAvailableForFirstLanguage() {
        return firstLanguageValue.equals("ru") || firstLanguageValue.equals("uk") || firstLanguageValue.equals("en") || firstLanguageValue.equals("tr");
    }

    private boolean isVocalizerAvailableForSecondLanguage() {
        return secondLanguageValue.equals("ru") || secondLanguageValue.equals("uk") || secondLanguageValue.equals("en") || secondLanguageValue.equals("tr");
    }

    private boolean isRecognizerAvailable() {
        return firstLanguageValue.equals("ru") || firstLanguageValue.equals("uk") || firstLanguageValue.equals("en") || firstLanguageValue.equals("tr");
    }

    @Override
    public void addOrRemoveToFavoritesClicked(String text) {
        Word word = new Word();
        word.setWord(text);
        word.setFirstLanguageValue(firstLanguageValue);
        word.setSecondLanguageValue(secondLanguageValue);
        word.setFirstLanguageName(firstLanguageName);
        word.setSecondLanguageName(secondLanguageName);
        word.setDictionaryResponse(dictionary);
        word.setTranslate(translate);
        translateModel.addWordToFavorites(word);
        if (view != null) {
            view.setBookmarkButtonImage(isWordInFavorites(word));
        }

    }

    private void addWordToHistory(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Word word = new Word();
        word.setWord(text);
        word.setFirstLanguageValue(firstLanguageValue);
        word.setSecondLanguageValue(secondLanguageValue);
        word.setFirstLanguageName(firstLanguageName);
        word.setSecondLanguageName(secondLanguageName);
        word.setDictionaryResponse(dictionary);
        word.setTranslate(translate);
        translateModel.addWordToHistory(word);
    }

    @Override
    public void getLastWord() {
        Word lastWord = translateModel.getLastWord();
        if (lastWord == null) {
            return;
        }
        if (view != null) {
            view.showTranslate(lastWord.getTranslate());
            view.setTextToTranslateEditText(lastWord.getWord());
            showLookup(lastWord.getDictionaryResponse());
        }
    }

    @Override
    public void getWordById(long id) {
        Word word = translateModel.getWordById(id);
        if (word == null) {
            return;
        }
        firstLanguageValue = word.getFirstLanguageValue();
        secondLanguageValue = word.getSecondLanguageValue();

        translateModel.saveFirstLanguage(new Language(word.getFirstLanguageName(), word.getFirstLanguageValue()));
        translateModel.saveSecondLanguage(new Language(word.getSecondLanguageName(), word.getSecondLanguageValue()));
        getSavedSelectLanguage();
        if (view != null) {
            view.showTranslate(word.getTranslate());
            view.setTextToTranslateEditText(word.getWord());
            showLookup(word.getDictionaryResponse());
        }

    }


    @Override
    public void onDestroy() {
        view = null;
        translateModel.onDestroy();
        resetRecognizer();
        resetVocalizer();
    }
}
