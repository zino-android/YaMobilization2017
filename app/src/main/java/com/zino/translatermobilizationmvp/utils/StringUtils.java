package com.zino.translatermobilizationmvp.utils;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.DictionaryResponse;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Example;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Meaning;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Synonym;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.Translation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class StringUtils {

    public static SpannableString getTranscriptionSpan(DictionaryResponse dictionaryResponse, int color) {
        String word = dictionaryResponse.getPartsOfSpeech().get(0).getText();
        String trans = dictionaryResponse.getPartsOfSpeech().get(0).getTs();
        String s = word;
        if (!TextUtils.isEmpty(trans)) {
            s += " [" + trans + "]";
        }

        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new ForegroundColorSpan(color), word.length(), s.length(), 0);
        return ss1;
    }

    public static String getExamplesString(Translation translations) {
        StringBuilder example = new StringBuilder();

        if (translations.getExamples() != null && translations.getExamples().size() != 0) {
            Iterator<Example> exampleIterator = translations.getExamples().iterator();
            while (exampleIterator.hasNext()) {
                Example e = exampleIterator.next();
                example.append(e.getText());
                if (e.getTranslationExamples() != null && e.getTranslationExamples().size() != 0) {
                    example.append(" — ");
                    example.append(e.getTranslationExamples().get(0).getText());
                }
                if (exampleIterator.hasNext()) {
                    example.append("\n");
                }
            }
        }
        return example.toString();
    }

    public static String getSynonymString(Translation translations) {
        StringBuilder syn = new StringBuilder();

        if (translations.getMeanings() != null && translations.getMeanings().size() != 0) {
            Iterator<Meaning> meaningIterator = translations.getMeanings().iterator();
            while (meaningIterator.hasNext()) {
                Meaning mean = meaningIterator.next();
                syn.append(mean.getText());
                if (meaningIterator.hasNext()) {
                    syn.append(", ");
                }

            }
        }
        if (!TextUtils.isEmpty(syn)) {


            syn.insert(0, "(");
            syn.append(")");

        }
        return syn.toString();
    }

    public static SpannableString getTranslationsString(Translation translations, int color) {
        Map<Integer, Integer> map = new HashMap<>();

        StringBuilder gg = new StringBuilder(translations.getText());
        if (!TextUtils.isEmpty(translations.getGen())) {
            gg.append(" ");
            int n = gg.length();
            gg.append(translations.getGen());
            map.put(n, gg.length());
        }

        if (translations.getSynonyms() != null && translations.getSynonyms().size() != 0) {
            for (Synonym synonym : translations.getSynonyms()) {
                gg.append(", ");
                gg.append(synonym.getText());
                if (!TextUtils.isEmpty(synonym.getGen())) {
                    gg.append(" ");
                    int n = gg.length();
                    gg.append(synonym.getGen());
                    map.put(n, gg.length());
                }
            }
        }

        SpannableString genString = new SpannableString(gg);
        for (int i : map.keySet()) {
            genString.setSpan(new ForegroundColorSpan(color), i, map.get(i), 0);
            genString.setSpan(new StyleSpan(Typeface.ITALIC), i, map.get(i), 0);
        }

        return genString;

    }

}
