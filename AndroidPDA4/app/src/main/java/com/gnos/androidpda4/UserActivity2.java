package com.gnos.androidpda4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.util.HashMap;

import fwk.GnosActivity2;
import fwk.GnosListview;

public class UserActivity2 extends GnosActivity2 {
    String[] mapTag;

    private RecyclerView recyclerView;
    private GnosListview gnosListview;
    private Button btnRe, btnAp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        onLoad();

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
        // 메인 화면으로 이동
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Intent intent = new Intent(UserActivity2.this, TestActivity.class);
        startActivity(intent);  // 액티비티 이동
    }

    protected void onLoad() {
        mapTag = new String[] {"L_USERID", "L_PASSWORD", "EMPNAME", "DEPTNAME"};
        int[] rid = new int[] {R.id.id, R.id.pwd, R.id.name, R.id.dept};

        // RecyclerView 연결
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        gnosListview = new GnosListview(this, recyclerView, R.layout.activity_user_list, rid, mapTag);
    }

    protected void showList(JSONArray jsa) {
        gnosListview.setRows(GetHashMap(jsa));
    }
}
