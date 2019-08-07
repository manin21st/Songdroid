package com.gnos.recyclerview3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private RecyclerView recyclerView;
    private ListViewParts listViewParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView 연결
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        listViewParts = new ListViewParts(recyclerView, this);

        // 조회버튼클릭
        button1 = findViewById(R.id.btn_search);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=1; i<100; i++) {
                    listViewParts.addRow("품번"+i, "품명"+i, "규격"+i);
                }
            }
        });
    }

}
