package com.song.androidpda2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import fwk.RetrofitConnection;
import fwk.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        // Retrofit 통신
//        RetrofitInterface retrofitInterface = RetrofitInterface.retrofit.create(RetrofitInterface.class);
//        Call<String> call = retrofitInterface.get_data();
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<JSONArray> call = retrofitConnection.server.get_data();

        call.enqueue(new Callback<JSONArray>() {
            @Override
            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                if (response.isSuccessful()) {
                    // 성공적으로 서버에서 데이터 불러옴
                    Log.d("Retrofit 성공1", response.body().toString());

                } else {
                    // 서버와 연결은 되었으나 오류 발생
                    Log.d("Retrofit 성공2", "오류 발생");

                }
            }

            @Override
            public void onFailure(Call<JSONArray> call, Throwable t) {
                Log.d("Retrofit 실패", t.toString()); //서버와 연결 실패
            }
        });

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
