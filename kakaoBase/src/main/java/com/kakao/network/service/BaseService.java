package com.kakao.network.service;

import com.kakao.bean.Test;
import com.kakao.network.manager.RetrofitManager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author jyb jyb_96@sina.com
 * @version V1.0
 * @Description:
 * @date 16-4-15 18:54
 * @copyright www.tops001.com
 */
public interface BaseService {

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("stories/latest")
    Call<Test> getLatestNews();


}
