package com.gnos.androidpda5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import fwk.GnosActivity;

public class LoginActivity extends GnosActivity {

    private EditText et_id;
    private EditText et_pwd;
    private Button btn_login;
    private String str_id, str_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        str_id = et_id.getText().toString();
        str_pwd = et_pwd.getText().toString();

                // 로그인 check
                CheckUser();
            }
        });

    }

    private void CheckUser() {
        String sSql = " select l_userid, " +
                " l_password, " +
                " l_dept, " +
                " l_empno, " +
                " l_saupj " +
                " from login_t " +
                " where l_userid = '" + str_id + "'" +
                " and l_password = '" + str_pwd + "' ";

        RunSql("User", sSql);
    }

    private void OpenMain(JSONArray result) {
        if (result.length() == 0) {
            Toast.makeText(this, "등록되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
//            return;
        }

        // 메인 화면으로 이동
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        Intent intent = new Intent(LoginActivity.this, UserActivity2.class);
        intent.putExtra("userID", str_id);
        intent.putExtra("userPassword", str_pwd);
        startActivity(intent);  // 액티비티 이동
    }

    @Override
    protected void SqlResult(String sID, JSONArray result) {
        super.SqlResult(sID, result);

        switch (sID) {
            case "User" :
                OpenMain(result);
                break;
        }
    }

}
