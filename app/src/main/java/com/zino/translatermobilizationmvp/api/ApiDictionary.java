package com.zino.translatermobilizationmvp.api;


import com.zino.translatermobilizationmvp.screens.translate.model.pojo.dictionary.DictionaryResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface ApiDictionary {

    @FormUrlEncoded
    @POST("lookup")
    Observable<DictionaryResponse> lookup(@Field("key") String key, @Field("text") String text,
                                          @Field("lang") String lang,
                                          @Field("ui") String ui,
                                          @Field("flags") int flags);

}
