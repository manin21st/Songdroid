package com.gnos.androidpda5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import fwk.GnosFragment;
import fwk.GnosRecycler;

public class UserFragment extends GnosFragment {
    String[] mapTag;

    private RecyclerView recyclerView;
    private GnosRecycler gnosRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("[UserFragment]", "onCreateView...................");
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        onLoad(view);

        // 액션바 설정
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(getString(R.string.menu_user));
        setHasOptionsMenu(true);  // 프래그먼트 onCreateOptionsMenu 호출

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("[UserFragment]", "onCreateOptionsMenu...................");
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_edit:
                onAppend();
                break;
            case R.id.menu_delete:
                break;
            case R.id.menu_search:
                onRetrieve();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onLoad(View view) {
        mapTag = new String[] {"L_USERID", "L_PASSWORD", "EMPNAME", "DEPTNAME"};
        int[] rid = new int[] {R.id.id, R.id.pwd, R.id.name, R.id.dept};

        // RecyclerView 연결
        recyclerView = view.findViewById(R.id.list_view);
        gnosRecycler = new GnosRecycler(getContext(), recyclerView, R.layout.fragment_user_list, rid, mapTag);
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
    protected void SqlResult(String sID, JSONArray result) {
        super.SqlResult(sID, result);

        switch (sID) {
            case "User" :
                gnosRecycler.setListUp(result);
                break;
        }
    }

    protected void onAppend() {

        // 액티비티로 이동
        Intent intent = new Intent(getActivity(), UserActivity.class);
//        intent.putExtra("userID", str_id);
//        intent.putExtra("userPassword", str_pwd);
        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
//        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

//        // 프래그먼트로 이동
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, new UserFragmentEdit());
//        transaction.addToBackStack(null);
//        transaction.commit();

//        HashMap<String, String> row = new HashMap<>();
//
//        row.put(mapTag[0], "TEST");
//        row.put(mapTag[1], "12345");
//        row.put(mapTag[2], "테스트");
//        row.put(mapTag[3], "품질팀");
//
//        gnosRecycler.addRow(row);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("[UserFragment]", "onSaveInstanceState...................");
        super.onSaveInstanceState(outState);

//        outState.putStringArrayList("key", jsonArray);
//        outState.putParcelable("key", (Parcelable) jsonArray);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("[UserFragment]", "onViewStateRestored...................");
        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//            savedInstanceState.getParcelable("key");
//        }
//        if (jsonArray != null) {
//            showList(jsonArray);
//        }
    }
}