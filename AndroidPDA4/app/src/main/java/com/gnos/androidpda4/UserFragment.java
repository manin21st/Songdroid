package com.gnos.androidpda4;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;

import java.util.HashMap;

import fwk.GnosFragment;
import fwk.GnosListview;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends GnosFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 여기 아래부터 사용자정의 필드, 메소드 추가 영역
     */
    String[] mapTag;

    private RecyclerView recyclerView;
    private GnosListview gnosListview;
    private Button btnRe, btnAp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        HashMap<String, String> row = new HashMap<>();

        row.put(mapTag[0], "TEST");
        row.put(mapTag[1], "12345");
        row.put(mapTag[2], "테스트");
        row.put(mapTag[3], "품질팀");

        gnosListview.addRow(row);
    }

    protected void onLoad() {
        mapTag = new String[] {"L_USERID", "L_PASSWORD", "EMPNAME", "DEPTNAME"};
        int[] rid = new int[] {R.id.id, R.id.pwd, R.id.name, R.id.dept};

        // RecyclerView 연결
        recyclerView = (RecyclerView) getView().findViewById(R.id.list_view);
        gnosListview = new GnosListview(getActivity(), recyclerView, R.layout.activity_user_list, rid, mapTag);
    }

    protected void showList(JSONArray jsa) {
        gnosListview.setRows(GetHashMap(jsa));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
//        View v = inflater.inflate(R.layout.fragment_user, container, false);

//        onLoad();
//
//        // 조회버튼 클릭
//        btnRe = getView().findViewById(R.id.btn_retrieve);
//        btnRe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onRetrieve();
//            }
//        });
//
//        // 추가버튼 클릭
//        btnAp = getView().findViewById(R.id.btn_append);
//        btnAp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onAppend();
//            }
//        });

//        return v;
    }


    /**
     * 여기 위까지 사용자정의 필드, 메소드 추가 영역
     */


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
