package com.example.gankRimon.retrofit;

import com.example.gankRimon.model.Gank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by gaohailong on 2016/5/17.
 */
public interface GankService {
    @GET("api/data/{type}/{count}/{page}")
    //Retrofit 的 RxJava 版 API
    Observable<Gank> getGank(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );
}
