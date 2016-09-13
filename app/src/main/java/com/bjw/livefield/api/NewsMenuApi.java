package com.bjw.livefield.api;

import com.bjw.livefield.domain.NewsMenu;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public interface NewsMenuApi {
    @GET("{fileName}.json")
    Observable<NewsMenu> getNewsMenu(@Path("fileName")String fileName);
}
