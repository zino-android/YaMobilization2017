package com.zino.translatermobilizationmvp.screens.translate.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zino.translatermobilizationmvp.R;
import com.zino.translatermobilizationmvp.screens.change_language.view.SelectLangActivity;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.Word;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.PartOfSpeech;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Translation;
import com.zino.translatermobilizationmvp.screens.translate.presenter.TranslatePresenter;
import com.zino.translatermobilizationmvp.screens.translate.presenter.TranslatePresenterImp;
import com.zino.translatermobilizationmvp.utils.UtilsDrawable;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.speechkit.SpeechKit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class TranslateFragment extends Fragment implements TranslateView {

    private final static String ID_KEY = "id";

    private static final int REQUEST_PERMISSION_CODE = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.translate_edit_text)
    EditText translateEditText;
    @BindView(R.id.translate_text_view)
    TextView translateTextView;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.clear_image_view)
    ImageButton clearImageButton;
    @BindView(R.id.microphone_view)
    MicrophoneView microphoneView;
    @BindView(R.id.left_language_text_view)
    TextView firstLanguageTextView;
    @BindView(R.id.right_language_text_view)
    TextView secondLanguageTextView;
    @BindView(R.id.mic_image_view)
    ImageButton micImageButton;
    @BindView(R.id.vocal_image_view)
    ImageButton firstLanguageVocalizeImageButton;
    @BindView(R.id.loading_progress_bar)
    ProgressBar loadingProgressBar;
    @BindView(R.id.vocal_progress_bar)
    ProgressBar vocalProgressBar;
    @BindView(R.id.vocal_translate_progress_bar)
    ProgressBar vocalTranslateProgressBar;
    @BindView(R.id.vocal_translate_image_view)
    ImageButton vocalTranslateImageButton;
    @BindView(R.id.bookmark_image_view)
    ImageButton bookmarkImageButton;
    @BindView(R.id.share_image_view)
    ImageButton shareImageButton;
    @BindView(R.id.full_screen_image_view)
    ImageButton fullScreenImageButton;
    @BindView(R.id.buttons_relative_layout)
    RelativeLayout buttonsRelativeLayout;

    private TranslatePresenter presenter;

    private boolean isFromHistory = false;
    private long id;

    public static TranslateFragment newInstance(Word word) {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        args.putLong(ID_KEY, word.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public TranslateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechKit.getInstance().configure(getActivity().getApplicationContext(), getResources().getString(R.string.speech_sdk_api_key));

        if (getArguments() != null) {
            id = getArguments().getLong(ID_KEY);
            isFromHistory = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getSavedSelectLanguage();
        if (!isFromHistory) {
            presenter.getLastWord();
        } else {
            presenter.getWordById(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new TranslatePresenterImp(this);

        RxTextView.textChangeEvents(translateEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(e -> {
                    String text = e.text().toString().trim();
                    presenter.translate(text);
                    presenter.lookup(text);
                    isFromHistory = false;
                });


        clearImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                getContext(),
                getResources().getDrawable(R.drawable.close),
                R.color.iconsGray
        ));

        shareImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                getContext(),
                getResources().getDrawable(R.drawable.share_variant),
                R.color.iconsGray
        ));

        fullScreenImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                getContext(),
                getResources().getDrawable(R.drawable.crop_free),
                R.color.iconsGray
        ));

        bookmarkImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                getContext(),
                getResources().getDrawable(R.drawable.bookmark),
                R.color.iconsGray));

    }

    @Override
    public void setTextToTranslateEditText(String text) {
        translateEditText.setText(text);
    }

    @Override
    public void setBookmarkButtonImage(boolean inFavorites) {

        if (inFavorites) {
            bookmarkImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.bookmark),
                    R.color.colorPrimary
                    ));
        } else {
            bookmarkImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.bookmark),
                    R.color.iconsGray
            ));
        }
    }

    @Override
    public void showProgress() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void clearTranslation() {
        translateEditText.setText("");
        translateTextView.setText("");
        clearImageButton.setVisibility(View.GONE);
        linearLayout.removeAllViews();
    }

    @Override
    public void showTranslate(String translate) {
        translateTextView.setText(translate);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getResources().getString(R.string.error_text), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTextIsEmpty() {
        clearImageButton.setVisibility(View.GONE);
        translateTextView.setText("");

        firstLanguageVocalizeImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                getContext(),
                getResources().getDrawable(R.drawable.volume_off),
                R.color.iconsGray
        ));
        buttonsRelativeLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTextIsNotEmpty() {
        clearImageButton.setVisibility(View.VISIBLE);
        buttonsRelativeLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.clear_image_view)
    void onClearButtonClick() {
        presenter.clearButtonClicked();
    }

    @Override
    public void removeDictionaryViews() {
        linearLayout.removeAllViews();
    }



    @Override
    public int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    @Override
    public void addTranscriptionTextView(SpannableString text) {
        TextView transcriptionTextView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.transcription_layout, null);
        transcriptionTextView.setText(text);
        linearLayout.addView(transcriptionTextView);
    }

    @Override
    public void addPosLayout(PartOfSpeech pos) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.part_of_speech, null);
        TextView posTextView = (TextView) view.findViewById(R.id.part_of_speech_text_view);
        posTextView.setText(pos.getPos());

        LinearLayout lin = (LinearLayout) view.findViewById(R.id.pos_linear_layout);

        Iterator<Translation> iterator = pos.getTranslations().iterator();

        int number = 1;

        while (iterator.hasNext()) {
            View meaningView = getActivity().getLayoutInflater().inflate(R.layout.meaning_layout, null);
            TextView numberTextView = (TextView) meaningView.findViewById(R.id.number_text_view);
            numberTextView.setText(String.valueOf(number));
            number++;
            TextView translTextView = (TextView) meaningView.findViewById(R.id.transl_text_view);
            Translation translations = iterator.next();

            translTextView.setText(presenter.getTranslationsString(translations));

            TextView synonymTextView = (TextView) meaningView.findViewById(R.id.synonym_text_view);

            String syn = presenter.getSynonymString(translations);
            if (!TextUtils.isEmpty(syn)) {
                synonymTextView.setText(syn);
            } else {
                synonymTextView.setVisibility(View.GONE);
            }

            TextView exampleTextView = (TextView) meaningView.findViewById(R.id.example_text_view);

            String example = presenter.getExamplesString(translations);

            if (!TextUtils.isEmpty(example)) {
                exampleTextView.setText(example);
            } else {
                exampleTextView.setVisibility(View.GONE);
            }

            lin.addView(meaningView);

        }

        linearLayout.addView(view);
    }

    @OnClick(R.id.share_image_view)
    void onShareButtonClick() {
        String text = translateTextView.getText().toString().trim();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @OnClick(R.id.mic_image_view)
    void onMicButtonClick() {
       presenter.micButtonClicked();
    }

    @Override
    public void startMicAnimation() {
        microphoneView.startAnimation();
    }

    @Override
    public void stopMicAnimation() {
        microphoneView.stopAnimation();
    }


    @OnClick(R.id.vocal_image_view)
    void onVocalButtonClick() {
        String text = translateEditText.getText().toString().trim();
        presenter.vocalButtonClick(text);
    }


    @Override
    public void showVocalLoading() {
        vocalProgressBar.setVisibility(View.VISIBLE);
        firstLanguageVocalizeImageButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideVocalLoading() {
        vocalProgressBar.setVisibility(View.INVISIBLE);
        firstLanguageVocalizeImageButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.vocal_translate_image_view)
    void onVocalTranslateButtonClick() {
        String text = translateTextView.getText().toString().trim();
        presenter.vocalTranslateButtonClicked(text);
    }

    @Override
    public void showVocalTranslateLoading() {
        vocalTranslateProgressBar.setVisibility(View.VISIBLE);
        vocalTranslateImageButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideVocalTranslateLoading() {

        vocalTranslateImageButton.setVisibility(View.VISIBLE);
        vocalTranslateProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void requestPermission() {
        final Context context = getActivity().getApplicationContext();
        if (context == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(context, RECORD_AUDIO) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        }
    }

    @OnClick(R.id.left_language_text_view)
    void onLeftLanguageTextViewClicked() {
        Intent intent = new Intent(getActivity(), SelectLangActivity.class);
        intent.putExtra(SelectLangActivity.TITLE_KEY, getResources().getString(R.string.text_language));
        intent.putExtra(SelectLangActivity.IS_FIRST_LANGUAGE_KEY, true);
        startActivity(intent);
    }

    //
    @OnClick(R.id.right_language_text_view)
    void onRightLanguageTextViewClicked() {
        Intent intent = new Intent(getActivity(), SelectLangActivity.class);
        intent.putExtra(SelectLangActivity.TITLE_KEY, getResources().getString(R.string.translate_language));
        intent.putExtra(SelectLangActivity.IS_FIRST_LANGUAGE_KEY, false);
        startActivity(intent);
    }

    @Override
    public void setFirstLanguage(String text) {
        firstLanguageTextView.setText(text);
    }

    @Override
    public void setSecondLanguage(String text) {
        secondLanguageTextView.setText(text);
    }

    @OnClick(R.id.change_image_view)
    void onSwapButtonClick() {
        presenter.swapLanguages();
        String text = translateTextView.getText().toString().trim();
        translateEditText.setText(text);
        presenter.translate(text);
        presenter.lookup(text);
    }



    @Override
    public void setVocalizerAvailableForSecondLanguage(boolean available) {
        if (available) {
            vocalTranslateImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.volume_high),
                    R.color.iconsGray
                    ));
        } else {
            vocalTranslateImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.volume_off),
                    R.color.iconsGray
            ));
        }
    }

    @Override
    public void setVocalizerAvailableForFirstLanguage(boolean available) {
        if (available) {
            firstLanguageVocalizeImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.volume_high),
                    R.color.iconsGray
            ));
        } else {
            firstLanguageVocalizeImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.volume_off),
                    R.color.iconsGray
            ));
        }
    }


    @Override
    public void setRecognizerAvailable(boolean available) {
        if (available) {
            micImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.microphone),
                    R.color.iconsGray
            ));
        } else {
            micImageButton.setImageDrawable(UtilsDrawable.getTintDrawable(
                    getContext(),
                    getResources().getDrawable(R.drawable.microphone_off),
                    R.color.iconsGray
            ));
        }
    }

    @OnClick(R.id.bookmark_image_view)
    void onBookmarkButtonClicked() {
        presenter.addOrRemoveToFavoritesClicked(translateEditText.getText().toString().trim());
    }

    @OnClick(R.id.full_screen_image_view)
    void onFullScreenButtonClick() {
        Intent intent = new Intent(getActivity(), FullScreenActivity.class);
        intent.putExtra(FullScreenActivity.TEXT_KEY, translateTextView.getText().toString());
        startActivity(intent);
    }

    @Override
    public void setRecognitionText(String text) {
        StringBuilder currentText = new StringBuilder(translateEditText.getText());
        currentText.append(text);
        translateEditText.setText(currentText);
    }

    @OnClick(R.id.yandex_translate_text_view)
    void onYandexTranslateClicked() {
        String url = "http://translate.yandex.ru/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.yandex_dictionary_text_view)
    void onYandexDictionaryClicked() {
        String url = "https://tech.yandex.ru/dictionary/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar = null;
        translateEditText = null;
        translateTextView = null;
        linearLayout = null;
        clearImageButton = null;
        microphoneView = null;
        firstLanguageTextView = null;
        secondLanguageTextView = null;
        micImageButton = null;
        firstLanguageVocalizeImageButton = null;
        loadingProgressBar = null;
        vocalProgressBar = null;
        vocalTranslateProgressBar = null;
        vocalTranslateImageButton = null;
        bookmarkImageButton = null;
        shareImageButton = null;
        fullScreenImageButton = null;
        buttonsRelativeLayout = null;

        presenter.onDestroy();
    }
}
