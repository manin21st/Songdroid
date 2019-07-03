package com.example.imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_test;
    private Button btn_test;
    private ImageView iv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_test = findViewById(R.id.et_test);
        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });

        iv_test = findViewById(R.id.iv_test);
        iv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "송드로이드 잘생김!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
