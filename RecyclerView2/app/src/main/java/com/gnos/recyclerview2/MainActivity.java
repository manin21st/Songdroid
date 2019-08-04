package com.gnos.recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView = null;
    RecyclerImageTextAdapter mAdapter = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler1);

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Image RecyclterView --------------------------------------------------------------------
        // 아이템 추가.
        addItem(ContextCompat.getDrawable(this, R.drawable.ic_assign_foreground),
                "Box", "Account Box Black 36dp");
        // 두 번째 아이템 추가.
        addItem(ContextCompat.getDrawable(this, R.drawable.ic_circle_foreground),
                "Circle", "Account Circle Black 36dp");
        // 세 번째 아이템 추가.
        addItem(ContextCompat.getDrawable(this, R.drawable.ic_assign_foreground),
                "Android", "Account Android Black 36dp");
//        mAdapter.notifyDataSetChanged();

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerImageTextAdapter(mList);
        recyclerView.setAdapter(mAdapter);

    }

    private void addItem(Drawable icon, String title, String desc) {
        RecyclerItem item = new RecyclerItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        mList.add(item);
    }
}
