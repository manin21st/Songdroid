package com.song.androidpda2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fwk.BasicClass;
import fwk.DataService;
import fwk.RetrofitConnection;
import fwk.RetrofitInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Retrofit retrofit;
    DataService dataService;

    private EditText et_id;
    private EditText et_pwd;
    private Button btn_login;
    private String str_id, str_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

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

        // Retrofit HTTP 통신 설정
        retrofit = new Retrofit.Builder().baseUrl(dataService.BASE_URL).build();
        dataService = retrofit.create(DataService.class);

        Call<ResponseBody> call = dataService.RunSql("1", sSql);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("Retrofit 성공1", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Retrofit 실패", t.toString()); //서버와 연결 실패
            }
        });
//        RunSql("User", sSql);
    }

    private void OpenMain(JSONArray jResult) {
        if (jResult.length() == 0) {
            Toast.makeText(this, "등록되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

//        String sUserid, sPassword, sDeptcode, sEmpno, sSaupj;
//
//        JSONObject jsonObject = jResult.getJSONObject(0);
//        sUserid = jsonObject.getString("L_USERID");


        // 메인 화면으로 이동
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userID", str_id);
        intent.putExtra("userPassword", str_pwd);
        LoginActivity.this.startActivity(intent);  // 액티비티 이동
    }

//    @Override
//    protected void SqlResult(String sID, JSONArray jResult) {
//        super.SqlResult(sID, jResult);
//
//        switch (sID) {
//            case "User" :
//                OpenMain(jResult);
//                break;
//        }
//    }

}
