package fwk;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gnos.androidpda4.R;

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


    /*-------------------------JSON data 처리 기능(Start)--------------------------*/
    protected ArrayList<HashMap<String, String>> GetList(JSONArray jsa) {
        ArrayList<HashMap<String, String>> hmapList = new ArrayList<>();

        try {
            for (int i=0;i<jsa.length();i++) {
                JSONObject jso = jsa.getJSONObject(i);

                HashMap<String, String> hmap = new HashMap<>();

                Iterator keys = jso.keys();
                while (keys.hasNext()) {
                    String skey = (String) keys.next();
                    hmap.put(skey, jso.getString(skey));
                }

                hmapList.add(hmap);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hmapList;
    }
    /*-------------------------JSON data 처리 기능(End)----------------------------*/



    /*-------------------------SQL 실행 관련 기능(Start)--------------------------*/
    //-1. RunSql(String sID, String sSql) => select 구문
    //-2. SqlResult(String sID, JSONArray jsa) => select 결과값 콜백 리턴
    protected void RunSql(String sid, String sql) {

        str_id = sid;  // sql 실행구분자

        Call<ResponseBody> call = datasvc.RunSql(sid, sql);
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
    protected void SqlResult(String sid, JSONArray jsa) {

        if (jsa == null) {
            Toast.makeText(this, "서비스 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            Log.d("[SqlResult]", "jResult == null"); //서버와 연결 실패
        } else {

        }
    }
    /*-------------------------SQL 실행 관련 기능(End)----------------------------*/

}
