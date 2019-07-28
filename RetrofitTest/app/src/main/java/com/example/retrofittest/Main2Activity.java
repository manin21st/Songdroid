package com.example.retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main2Activity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL2).build();
        apiService = retrofit.create(ApiService.class);

        String sSql = " select l_userid, " +
                " l_password, " +
                " l_dept, " +
                " l_empno, " +
                " l_saupj " +
                " from login_t " +
                " where l_userid = 'DEMO'" +
                " and l_password = '2018' ";

        Call<ResponseBody> postCommentStr = apiService.RunSql("1", sSql);
        postCommentStr.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.v("Retrofit 성공", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("Retrofit 실패", t.toString()); //서버와 연결 실패
            }
        });
    }
}
