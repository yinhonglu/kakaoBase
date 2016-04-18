package com.kakao.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.kakao.demo.R;
import com.kakao.demo.apinterface.TestService;
import com.kakao.demo.bean.NewsList;
import com.kakao.network.manager.RetrofitManager;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author jyb jyb_96@sina.com
 * @version V1.0
 * @Description:请添加描述
 * @date 16-4-15 19:40
 * @copyright www.tops001.com
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TestService testService = RetrofitManager.builder().retrofit().create(TestService.class);
        testService.getLatestNews();
        Call<NewsList> call = testService.getLatestNews();
        call.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                Logger.d("MainActivity:" +  "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    NewsList result = response.body();
                    Logger.d("MainActivity:" + "response = " + new Gson().toJson(result));

                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    //Handle errors

                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {

            }


        });
    }
}
