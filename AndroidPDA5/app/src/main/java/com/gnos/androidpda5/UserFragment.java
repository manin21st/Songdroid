package com.gnos.androidpda5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import fwk.GnosFragment;
import fwk.GnosListview;

public class UserFragment extends GnosFragment {
    String[] mapTag;

    private JSONArray jsonArray;
    private RecyclerView recyclerView;
    private GnosListview gnosListview;
    private Button btnRe, btnAp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("[UserFragment]", "onCreate...................");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("[UserFragment]", "onCreateView...................");
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        onLoad(view);

        // 조회버튼 클릭
        btnRe = view.findViewById(R.id.btn_retrieve);
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetrieve();
            }
        });

        // 추가버튼 클릭
        btnAp = view.findViewById(R.id.btn_append);
        btnAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAppend();
            }
        });

        return view;
    }

    protected void onLoad(View view) {
        mapTag = new String[] {"L_USERID", "L_PASSWORD", "EMPNAME", "DEPTNAME"};
        int[] rid = new int[] {R.id.id, R.id.pwd, R.id.name, R.id.dept};

        // RecyclerView 연결
        recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        gnosListview = new GnosListview(getContext(), recyclerView, R.layout.fragment_user_list, rid, mapTag);
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

//        onLoad(getView());
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

        // 프래그먼트 생성
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new UserFragmentEdit());
        transaction.addToBackStack(null);
        transaction.commit();

//        HashMap<String, String> row = new HashMap<>();
//
//        row.put(mapTag[0], "TEST");
//        row.put(mapTag[1], "12345");
//        row.put(mapTag[2], "테스트");
//        row.put(mapTag[3], "품질팀");
//
//        gnosListview.addRow(row);
    }

    protected void showList(JSONArray jsa) {
        jsonArray = jsa;
        gnosListview.setRows(GetHashMap(jsonArray));
    }

    @Override
    public void onPause() {
        Log.d("[UserFragment]", "onPause...................");
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (jsonArray != null) {
            Log.d("[UserFragment]", "onResume...................");
            showList(jsonArray);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d("[UserFragment]", "onDestroyView...................");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("[UserFragment]", "onDestroy...................");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("[UserFragment]", "onSaveInstanceState...................");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("[UserFragment]", "onViewStateRestored...................");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("[UserFragment]", "onStart...................");
        super.onStart();
    }
}