package com.gnos.androidpda5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gnos.androidpda5.databinding.ActivityUserBinding;

import org.json.JSONArray;

import fwk.GnosActivity;

public class UserActivity extends GnosActivity {
    private Button btnSave;
    private TextView tv_id, tv_pwd, tv_emp, tv_dept;

    ActivityUserBinding layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user);

        layout = DataBindingUtil.setContentView(this, R.layout.activity_user);

        // 액션바 설정
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.menu_user));
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 저장버튼 클릭
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                return true;
            case R.id.menu_add:
                break;
            case R.id.menu_check:
                onSave();
                break;
            case R.id.menu_delete:
                onDelete();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected int onPreSaveCheck(String sflag) {
        String sid = layout.userId.getText().toString().trim();
        if (sid.length() == 0) {
            Toast.makeText(this, "아이디를 지정하세요!", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (sflag == "D") {   // 삭제인 경우 아이디만 체크하고 리턴
            return 1;
        }
        String spwd = layout.userPwd.getText().toString().trim();
        if (spwd.length() == 0) {
            Toast.makeText(this, "패스워드를 지정하세요!", Toast.LENGTH_SHORT).show();
            return -1;
        }
        String sname = layout.userName.getText().toString().trim();
        if (sname.length() == 0) {
            Toast.makeText(this, "사원명을 지정하세요!", Toast.LENGTH_SHORT).show();
            return -1;
        }
        String sdept = layout.userDept.getText().toString().trim();
        if (sdept.length() == 0) {
            Toast.makeText(this, "부서명을 지정하세요!", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }

    protected void onSave() {
        if (onPreSaveCheck("I") == -1) {
            return;
        }

        String sid = layout.userId.getText().toString().trim();
        String spwd = layout.userPwd.getText().toString().trim();
        String sname = layout.userName.getText().toString().trim();
        String sdept = layout.userDept.getText().toString().trim();

//        String sSql = "select 1 from login_t where l_userid='"+sid+"'";
        String sSql = "select 1 from login_t where l_userid='TEST'";
        JSONArray result = RunSql("", sSql);
        if (result.length() > 0) {
            Toast.makeText(this, "이미 등록된 사용자입니다!", Toast.LENGTH_SHORT).show();
        } else {
            String sIns = "INSERT INTO LOGIN_T\n" +
                    "(L_USERID, L_PASSWORD, L_DEPT, L_EMPNO, L_COMMENT, L_SAUPJ,\n" +
                    " L_GUBUN, L_SABU, ORDER_YN, ADMIN_YN, CRT_DATE, CRT_TIME, CRT_USER)\n" +
                    "VALUES\n" +
                    "('"+sid+"', '"+spwd+"', '"+sdept+"', '"+sname+"', 'Y', '10',\n" +
                    " '1', '1', 'P', 'W', TO_CHAR(SYSDATE,'YYYYMMDD'), TO_CHAR(SYSDATE,'HH24MISS'), 'ADMIN')";

            ExecSql("Save", sIns);
        }
    }

    protected void onDelete() {

    }

    @Override
    protected void SqlResult(String sID, JSONArray result) {
        super.SqlResult(sID, result);

        switch (sID) {
            case "Save" :
                Toast.makeText(this, "사용자 등록 완료!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
