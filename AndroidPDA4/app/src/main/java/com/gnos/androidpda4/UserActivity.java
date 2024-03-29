package com.gnos.androidpda4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import fwk.GnosActivity;

public class UserActivity extends GnosActivity {
    String[] mapTag;
    ArrayList<HashMap<String, String>> mapList;

    private Button btnRe, btnAp;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        list = findViewById(R.id.list_view);

        // 조회버튼 클릭
        btnRe = findViewById(R.id.btn_retrieve);
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetrieve();
            }
        });

        // 추가버튼 클릭
        btnAp = findViewById(R.id.btn_append);
        btnAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAppend();
            }
        });
    }

    protected void onRetrieve() {
        String sSql = " SELECT a.l_userid,\n" +
                "\ta.l_password,\n" +
                "\tb.empname,\n" +
                "\tc.deptname\n" +
                "  FROM login_t a,\n" +
                "\t\tp1_master b,\n" +
                "\t\tp0_dept c\n" +
                " WHERE a.l_empno = b.empno(+)\n" +
                "   AND a.l_dept = c.deptcode(+)";

        RunSql("User", sSql);
    }
    @Override
    protected void SqlResult(String sID, JSONArray jsa) {
        super.SqlResult(sID, jsa);

        switch (sID) {
            case "User" :
                showList(jsa);
                break;
        }
    }

    protected void onAppend() {
    }

    protected void showList(JSONArray jsa) {
        mapList = GetList(jsa);
        mapTag = new String[] {"L_USERID", "L_PASSWORD", "EMPNAME", "DEPTNAME"};
        int[] rid = new int[] {R.id.id, R.id.pwd, R.id.name, R.id.dept};

        ListAdapter adapter = new SimpleAdapter(this, mapList, R.layout.activity_user_list, mapTag, rid);
        list.setAdapter(adapter);
    }
}
