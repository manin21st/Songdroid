package fwk;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

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

import static java.lang.Thread.sleep;

public class GnosRetrofit {
    /*---------------------------- 1. Interface 정의(Start) -----------------------------*/
    // 1-1. Retrofit 인터페이스
    private static final String BASE_URL = "http://118.38.159.9/android_pda/";
    private static final String TEST_URL = "http://jsonplaceholder.typicode.com/";

    interface RetrofitInterface {
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
    // 1-2. 콜백 함수 인터페이스
    interface CallbackFunc {
        void sqlCallbackFunc(String sid, JSONArray result);
    }
    public void setCallbackFunc (CallbackFunc callback) {
        this.callback = callback;
    }
    /*---------------------------- 1. Interface 정의(End) -----------------------------*/

    private String str_id, str_sql;
    private CallbackFunc callback;

    public GnosRetrofit() {
    }

    /*------------------------- 3. SQL 실행 관련 기능(Start) --------------------------*/
    // 3-1. RunSql(String sID, String sSql) => select 구문
    protected JSONArray runSql(String sid, String sql) {
        str_id = sid;  // sql 실행구분자
        str_sql = sql;

        JSONArray jsa = new JSONArray();
        if (sid.length() > 0) {
            this.setAsyncCall("RunSql");
        }
        return jsa;
    }
    // 3-2. ExecSql(String sID, String sSql) => insert, update, delete 구문
    protected JSONArray execSql(String sid, String sql) {
        str_id = sid;  // sql 실행구분자
        str_sql = sql;

        JSONArray jsa = new JSONArray();
        if (sid.length() > 0) {
            this.setAsyncCall("ExecSql");
        }
        return jsa;
    }
    //--> SqlResult(String sID, String result) => 콜백 함수 Overloading 필수
    /*------------------------- 3. SQL 실행 관련 기능(End) ----------------------------*/


    /*------------------------- 2. Retrofit 통신 관련 기능(Start) --------------------------*/
    // 2-1. Retrofit HTTP 통신 설정
    private Call<ResponseBody> getRetrofitInterface(final String mode) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<ResponseBody> call = null;
        switch (mode) {
            case "RunSql":
                call = retrofitInterface.RunSql(str_id, str_sql);
                break;
            case "ExecSql":
                call = retrofitInterface.ExecSql(str_id, str_sql);
                break;
            default:
        }
        return call;
    }
    // 2-2. 비동기 호출 방식
    private void setAsyncCall(final String mode) {
        Call<ResponseBody> call = getRetrofitInterface(mode);

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
}
