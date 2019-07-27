package com.song.androidpda2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

//    private final EditText et_id;
//    private EditText et_pwd;
//    private Button btn_login;
//    private String str_id, str_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        final EditText et_id = (EditText) findViewById(R.id.et_id);
        final EditText et_pwd = (EditText) findViewById(R.id.et_pwd);
        final Button btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str_id = et_id.getText().toString();
                final String str_pwd = et_pwd.getText().toString();

                // 로그인 쿼리 실행 check
                // Code here..

                // 메인 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userID", str_id);
                intent.putExtra("userPassword", str_pwd);
                LoginActivity.this.startActivity(intent);  // 액티비티 이동
            }
        });
    }
}
