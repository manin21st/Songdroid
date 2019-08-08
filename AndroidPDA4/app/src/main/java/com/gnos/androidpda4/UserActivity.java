package com.gnos.androidpda4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fwk.GnosActivity;

public class UserActivity extends GnosActivity {
    private static final String TAG_1 = "L_USERID";
    private static final String TAG_2 = "L_PASSWORD";
    private static final String TAG_3 = "EMPNAME";
    private static final String TAG_4 = "DEPTNAME";

    Button button1;
    ListView list;
    ArrayList<HashMap<String, String>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        list = findViewById(R.id.list_view);
        mapList = new ArrayList<>();

        // 조회버튼 클릭
        button1 = findViewById(R.id.btn_search);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetrieve();
            }
        });
    }

    protected void onRetrieve() {
        String sSql = " SELECT a.l_userid," +
                " a.l_password," +
                " b.empname," +
                " c.deptname" +
                "  FROM login_t a," +
                "  p1_master b," +
                "  p0_dept c" +
                " WHERE a.l_empno = b.empno(+)" +
                "   AND a.l_dept = c.deptcode(+) ";

        Log.d("UserActivity:", "............0");

        RunSql("User", sSql);
    }
    @Override
    protected void SqlResult(String sID, JSONArray jResult) {
        super.SqlResult(sID, jResult);

        Log.d("UserActivity:", "............1");

        switch (sID) {
            case "User" :
                showList(jResult);
                break;
        }
    }

    protected void showList(JSONArray jsa) {
        Log.d("UserActivity:", "............2");
        try {
            for (int i=0;i<jsa.length();i++) {
                JSONObject jso = jsa.getJSONObject(i);
                HashMap<String, String> hmap = new HashMap<>();

                hmap.put(TAG_1, jso.getString(TAG_1));
                hmap.put(TAG_2, jso.getString(TAG_2));
                hmap.put(TAG_3, jso.getString(TAG_3));
                hmap.put(TAG_4, jso.getString(TAG_4));

                mapList.add(hmap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    this, mapList, R.layout.activity_user_list,
                    new String[] {TAG_1, TAG_2, TAG_3, TAG_4},
                    new int[] {R.id.id, R.id.pwd, R.id.name, R.id.dept}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
