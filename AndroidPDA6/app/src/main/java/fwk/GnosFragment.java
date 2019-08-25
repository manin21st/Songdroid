package fwk;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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
                sqlResult(sid, result);
            }
        };
        gnosRetrofit.setCallbackFunc(callback);
    }

    /*------------------------- 1. SQL 실행 관련 기능(Start) --------------------------*/
    protected JSONArray runSql(String sid, String sql) {
        return gnosRetrofit.runSql(sid, sql);
    }
    protected JSONArray execSql(String sid, String sql) {
        return gnosRetrofit.execSql(sid, sql);
    }
    // SQL 구문 실행 후 콜백 메소드
    protected void sqlResult(String sid, JSONArray result) {
    }
    /*------------------------- 1. SQL 실행 관련 기능(End) ----------------------------*/


    /*------------------------- 2. 이벤트 Listener 관련 기능(Start) -------------------*/
    private int get_id;

    // 2-1. Click 이벤트
    class ClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            caseEventHandler("Click", get_id, "");
        }
    }
    // 2-2. Edit 이벤트
    class EditHandler implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            caseEventHandler("TextChanged", get_id, editable.toString());
//            Log.d("[GnosActivity]", "afterTextChanged...................");
        }
    }
    // 2-3. 이벤트 등록
    protected void addEventHandler(String tag, View view) {
        get_id = view.getId();

        switch (tag) {
            case "Click":
                view.setOnClickListener(new ClickHandler());
                break;
            case "TextChanged":
                EditText editText = (EditText) view;
                editText.addTextChangedListener(new EditHandler());
                break;
        }
    }
    // 2-4. 이벤트 처리기
    protected void caseEventHandler(String tag, int id, String data) {
        switch (tag) {
            case "Click":
                break;
            case "TextChanged":
                break;
        }
    }
    /*------------------------- 2. 이벤트 Listener 관련 기능(End) ---------------------*/
}
