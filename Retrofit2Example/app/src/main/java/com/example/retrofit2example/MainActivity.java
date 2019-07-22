package com.example.retrofit2example;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mRepoText;
    private Button mGetButton;
    private Button mPostButton;

    private Retrofit retrofit;
    private GitHubService gitHubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepoText = (TextView) findViewById(R.id.repoText);
        mGetButton = (Button) findViewById(R.id.getButton);
        mPostButton = (Button) findViewById(R.id.postButton);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubService = retrofit.create(GitHubService.class);

        mGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatas();
            }
        });

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDatas(new User("11", "22"));
            }
        });

        //
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void postDatas(User user) {
        Call<User> call = gitHubService.postRepos("manin21st", user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "change url appropriately", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    String str = "response code: " + response.code() + "\n ID: " + response.body().login + "\n URL: " + response.body().html_url;
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Not Response", t.getLocalizedMessage());
            }
        });
    }

    public void getDatas() {
        Call<User> call = gitHubService.getRepos("manin21st");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String str = "response code: " + response.code() + "\n ID: " + response.body().login + "\n URL: " + response.body().html_url;
                    mRepoText.setText(str);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Not Response", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
