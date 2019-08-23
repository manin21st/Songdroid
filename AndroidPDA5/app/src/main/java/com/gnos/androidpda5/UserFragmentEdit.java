package com.gnos.androidpda5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import org.json.JSONArray;

import java.util.HashMap;

import fwk.GnosFragment;

public class UserFragmentEdit extends GnosFragment {
    String[] mapTag;

    private Button btnSave, btnDel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_user_edit, container, false);

        // 액션바 설정
        ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
        ab.setTitle(getString(R.string.menu_user));
        ab.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        onLoad(rootView);

        // 저장버튼 클릭
        btnSave = rootView.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
            }
        });

        // 삭제버튼 클릭
        btnDel = rootView.findViewById(R.id.btn_delete);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("[UserFragmentEdit]", "onOptionsItemSelected...................");

        if (id == android.R.id.home) {
//            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onLoad(ViewGroup parent) {
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
    protected void SqlResult(String sID, JSONArray result) {
        super.SqlResult(sID, result);

        switch (sID) {
            case "User" :
                break;
        }
    }

    protected void onSave() {
        HashMap<String, String> row = new HashMap<>();

        row.put(mapTag[0], "TEST");
        row.put(mapTag[1], "12345");
        row.put(mapTag[2], "테스트");
        row.put(mapTag[3], "품질팀");
    }
    protected void onDelete() {
    }
}