package fwk;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class GnosRetrofit {
    /*----------------------------Interface 정의(Start)-----------------------------*/
    interface CallbackFunc {
        void sqlCallbackFunc(String sid, JSONArray result);
    }
    public void setCallbackFunc (CallbackFunc callback) {
        this.callback = callback;
    }
    interface RetrofitInterface {
        public static final String BASE_URL = "http://118.38.159.9/android_pda/";
        public static final String TEST_URL = "http://jsonplaceholder.typicode.com/";

        // TEST_URL
        @GET("comments")
        Call<ResponseBody> getComment(@Query("postId") int postId);
        // TEST_URL
        @FormUrlEncoded
        @POST("comments")
        Call<ResponseBody> getPostCommentStr(@Field("postId") String postId);

        // BASE_URL
        @FormUrlEncoded
        @POST("RunSql.php")
        Call<ResponseBody> RunSql(@Field("sqlID") String sqlID, @Field("sqlTEXT") String sqlTEXT);
        // BASE_URL
        @FormUrlEncoded
        @POST("ExecSql.php")
        Call<ResponseBody> ExecSql(@Field("sqlID") String sqlID, @Field("sqlTEXT") String sqlTEXT);
    }
    /*----------------------------Interface 정의(End)-----------------------------*/

    private String str_id;
    private RetrofitInterface rtfif;
    private CallbackFunc callback;

    public GnosRetrofit() {
        // Retrofit HTTP 통신 설정
        Retrofit retrofit = new Retrofit.Builder().baseUrl(rtfif.BASE_URL).build();
        rtfif = retrofit.create(RetrofitInterface.class);
    }

    private void setAsyncListener(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 성공적으로 서버에서 데이터 불러옴
                    try {
                        String result = response.body().string();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            callback.sqlCallbackFunc(str_id, jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("[GnosRetrofit]1", result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 서버와 연결은 되었으나 오류 발생
                    Log.d("[GnosRetrofit]2", "오류 발생");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("[GnosRetrofit]3", t.toString()); //서버와 연결 실패
            }
        });
    }

    private JSONArray setSyncListener(Call<ResponseBody> call) {
        JSONArray jsonArray = null;
        try {
            String result = call.execute().body().toString();
            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("[GnosRetrofit]4", result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    /*-------------------------SQL 실행 관련 기능(Start)--------------------------*/
    //-1. RunSql(String sID, String sSql) => select 구문
    protected JSONArray RunSql(String sid, String sql) {
        str_id = sid;  // sql 실행구분자
        JSONArray jsa = null;

        Call<ResponseBody> call = rtfif.RunSql(sid, sql);
        if (sid.length()>0) {
            this.setAsyncListener(call);
        } else {
            jsa = this.setSyncListener(call);
        }
        return jsa;
    }
    protected JSONArray ExecSql(String sid, String sql) {
        str_id = sid;  // sql 실행구분자
        JSONArray jsa = null;

        Call<ResponseBody> call = rtfif.ExecSql(sid, sql);
        if (sid.length()>0) {
            this.setAsyncListener(call);
        } else {
            jsa = this.setSyncListener(call);
        }
        return jsa;
    }
    //--> SqlResult(String sID, String result) => 콜백 함수 Overloading 필수
    /*-------------------------SQL 실행 관련 기능(End)----------------------------*/

}
