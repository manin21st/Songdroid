package com.gnos.recyclerview3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemModel> itemModel = new ArrayList<ItemModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView 연결
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);

        // LayoutManager 생성 및 지정
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        for (int i=1; i<100; i++) {
            addItemList("품번"+i, "품명"+i, "규격"+i);
        }

        // Adapter 생성 및 지정
        adapter = new ItemAdapter(itemModel);
        recyclerView.setAdapter(adapter);
    }

    private void addItemList(String itnbr, String itdsc, String ispec) {
        ItemModel item = new ItemModel();

        item.setItnbr(itnbr);
        item.setItdsc(itdsc);
        item.setIspec(ispec);

        itemModel.add(item);
    }
}
