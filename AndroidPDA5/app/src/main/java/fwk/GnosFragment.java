package fwk;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;

public class GnosFragment extends Fragment {
    private GnosRetrofit gnosRetrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
