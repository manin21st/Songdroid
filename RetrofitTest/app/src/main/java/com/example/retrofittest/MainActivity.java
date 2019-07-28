package com.example.retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> postCommentStr = apiService.getPostCommentStr("1");
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


//        Call<ResponseBody> comment = apiService.getComment(1);
//        comment.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        String result = response.body().string();
//                        Log.v("Retrofit 성공1", result);
//                        try {
//                            JSONArray jsonArray = new JSONArray(result);
//                            int postId, id;
//                            String name, mail, body;
//                            for (int i=0;i<jsonArray.length();i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                postId = jsonObject.getInt("postId");
//                                id = jsonObject.getInt("id");
//                                name = jsonObject.getString("name");
//                                mail = jsonObject.getString("email");
//                                body = jsonObject.getString("body");
//                                Log.v("Retrofit 성공2", jsonObject.toString());
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
////                        Log.v("Retrofit 성공1", response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.v("Retrofit 성공3", "오류 발생");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.v("Retrofit 실패", t.toString()); //서버와 연결 실패
//            }
//        });
    }
}
