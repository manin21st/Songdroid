package fwk;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class GnosFragment extends Fragment {
    private GnosRetrofit gnosRetrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gnosRetrofit = new GnosRetrofit();
        GnosRetrofit.CallbackFunc callback = new GnosRetrofit.CallbackFunc() {
            @Override
            public void runSqlCallBack(String sid, JSONArray jsa) {
                SqlResult(sid, jsa);
            }
        };
        gnosRetrofit.setCallbackFunc(callback);
    }

    protected ArrayList<HashMap<String, String>> GetHashMap(JSONArray jsa) {
        return gnosRetrofit.GetList(jsa);
    }
    protected void RunSql(String sid, String sql) {
        gnosRetrofit.RunSql(sid, sql);
    }
    // SQL 구문 실행 후 콜백 메소드
    protected void SqlResult(String sid, JSONArray jsa) {
    }
}
