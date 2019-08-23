package fwk;

import android.os.AsyncTask;
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

import static java.lang.Thread.sleep;

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

    private String str_id, str_sql;
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

    private boolean b_call;
    private JSONArray jsa_result;

    private JSONArray setSyncListener(final String mode) {
//        jsa_result = null;
        jsa_result = new JSONArray();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                b_call = true;
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                b_call = false;
            }
        }.execute();

//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                    b_call = true;
//                    Call<ResponseBody> call = getRetInf(mode);
//                    return call.execute().body().toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String s) {
//                b_call = false;
//                try {
//                    jsa_result = new JSONArray(s);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.execute();

        // 리턴 대기
        while (b_call) {
            try {
                sleep(100);
            } catch (InterruptedException e) {}
        }
        return jsa_result;
    }
    private Call<ResponseBody> getRetInf(final String mode) {
        Call<ResponseBody> call = null;

        switch (mode) {
            case "RunSql":
                call = rtfif.RunSql(str_id, str_sql);
                break;
            case "ExecSql":
                call =  rtfif.ExecSql(str_id, str_sql);
                break;
        }

        return call;
    }

    /*-------------------------SQL 실행 관련 기능(Start)--------------------------*/
    //-1. RunSql(String sID, String sSql) => select 구문
    protected JSONArray RunSql(String sid, String sql) {
        str_id = sid;  // sql 실행구분자
        str_sql = sql;
        JSONArray jsa = null;

        if (sid.length() > 0) {
            this.setAsyncListener(rtfif.RunSql(str_id, str_sql));
        } else {
            jsa = this.setSyncListener("RunSql");
        }
        return jsa;
    }
    protected JSONArray ExecSql(String sid, String sql) {
        str_id = sid;  // sql 실행구분자
        str_sql = sql;
        JSONArray jsa = null;

        if (sid.length() > 0) {
            this.setAsyncListener(rtfif.ExecSql(str_id, str_sql));
        } else {
            jsa = this.setSyncListener("ExecSql");
        }
        return jsa;
    }
    //--> SqlResult(String sID, String result) => 콜백 함수 Overloading 필수
    /*-------------------------SQL 실행 관련 기능(End)----------------------------*/

}
