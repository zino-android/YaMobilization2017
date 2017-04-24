package com.zino.translatermobilizationmvp.api;


import com.zino.translatermobilizationmvp.screens.change_language.model.pojo.LangsResponse;
import com.zino.translatermobilizationmvp.screens.translate.model.pojo.TranslateResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;



public interface ApiTranslate {

    @FormUrlEncoded
    @POST("translate")
    Observable<TranslateResponse> translate(@Field("key") String key, @Field("text") String text, @Field("lang") String lang);


    @GET("getLangs")
    Observable<LangsResponse> getLangs(@Query("key") String key, @Query("ui") String ui);

}
