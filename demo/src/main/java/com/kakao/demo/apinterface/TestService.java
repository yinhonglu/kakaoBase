package com.kakao.demo.apinterface;

import com.kakao.demo.bean.NewsList;
import com.kakao.network.manager.RetrofitManager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author jyb jyb_96@sina.com
 * @version V1.0
 * @Description:请添加描述
 * @date 16-4-15 19:58
 * @copyright www.tops001.com
 */
public interface TestService {

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("stories/latest")
    Call<NewsList> getLatestNews();
}
