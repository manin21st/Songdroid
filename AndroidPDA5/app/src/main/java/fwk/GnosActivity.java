package fwk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

public class GnosActivity extends AppCompatActivity {
    private GnosRetrofit gnosRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gnosRetrofit = new GnosRetrofit();
        GnosRetrofit.CallbackFunc callback = new GnosRetrofit.CallbackFunc() {
            @Override
            public void sqlCallbackFunc(String sid, JSONArray result) {
                SqlResult(sid, result);
            }
        };
        gnosRetrofit.setCallbackFunc(callback);
    }
    protected JSONArray RunSql(String sid, String sql) {
        return gnosRetrofit.RunSql(sid, sql);
    }
    protected JSONArray ExecSql(String sid, String sql) {
        return gnosRetrofit.ExecSql(sid, sql);
    }
    // SQL 구문 실행 후 콜백 메소드
    protected void SqlResult(String sid, JSONArray result) {
    }
}
