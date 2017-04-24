package com.zino.translatermobilizationmvp.api;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiInstance {

    private static ApiTranslate apiTranslate;
    private static ApiDictionary apiDictionary;


    public static ApiTranslate getApiTranslate() {
        if (apiTranslate == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiTranslate = retrofit.create(ApiTranslate.class);
        }

        return apiTranslate;
    }

    public static ApiDictionary getApiDictionary() {
        if (apiDictionary == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://dictionary.yandex.net/api/v1/dicservice.json/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiDictionary = retrofit.create(ApiDictionary.class);
        }

        return apiDictionary;
    }

}
