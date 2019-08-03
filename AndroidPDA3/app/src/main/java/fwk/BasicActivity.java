package fwk;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BasicActivity extends AppCompatActivity {
    Retrofit retrofit;
    DataService dataService;
    String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrofit HTTP 통신 설정
        retrofit = new Retrofit.Builder().baseUrl(dataService.BASE_URL).build();
        dataService = retrofit.create(DataService.class);
    }

    protected void RunSql(String sID, String sSql) {

        str_id = sID;  // sql 실행구분자

        Call<ResponseBody> call = dataService.RunSql(sID, sSql);
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
                        Log.d("[RunSql]성공1", result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 서버와 연결은 되었으나 오류 발생
                    Log.d("[RunSql]성공2", "오류 발생");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("[RunSql]실패", t.toString()); //서버와 연결 실패
            }
        });

    }

    protected void SqlResult(String sID, JSONArray jResult) {

        if (jResult == null) {
            Toast.makeText(this, "서비스 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

}
