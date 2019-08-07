package fwk;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GnosActivity extends AppCompatActivity {
    Retrofit retrofit;
    GnosInterface datasvc;
    String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrofit HTTP 통신 설정
        retrofit = new Retrofit.Builder().baseUrl(datasvc.BASE_URL).build();
        datasvc = retrofit.create(GnosInterface.class);

    }

    /*-------------------------SQL 실행 관련 기능(Start)--------------------------*/
    //-1. RunSql(String sID, String sSql) => select 구문
    //-2. SqlResult(String sID, JSONArray jResult) => select 결과값 콜백 리턴
    protected void RunSql(String sID, String sSql) {

        str_id = sID;  // sql 실행구분자

        Call<ResponseBody> call = datasvc.RunSql(sID, sSql);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // 성공적으로 서버에서 데이터 불러옴
                    try {
                        String result = response.body().string();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            SqlResult(str_id, jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("[RunSql]onResponse1", result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 서버와 연결은 되었으나 오류 발생
                    Log.d("[RunSql]onResponse2", "오류 발생");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("[RunSql]onFailure", t.toString()); //서버와 연결 실패
            }
        });

    }
    // SQL 구문 실행 후 콜백 메소드
    protected void SqlResult(String sID, JSONArray jResult) {

        if (jResult == null) {
            Toast.makeText(this, "서비스 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            Log.d("[SqlResult]", "jResult == null"); //서버와 연결 실패
        } else {

        }
    }
    /*-------------------------SQL 실행 관련 기능(End)----------------------------*/

}
