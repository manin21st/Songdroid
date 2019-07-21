package com.example.androidpda;

import android.app.ProgressDialog;
import android.app.Service;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DataService extends AsyncTask<String, Void, String> {

    private static String TAG = "DataService";

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";

    ArrayList<HashMap<String, String>> arrayList;
    ListView listView;

    ProgressDialog progressDialog;
    String errorString = null;
    String mJsonString;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        progressDialog = ProgressDialog.show(MainActivity.this,
//                "Please Wait", null, true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        progressDialog.dismiss();
//        mTextViewResult.setText(result);
//        Log.d(TAG, "response  - " + result);

        if (s == null) {
//            mTextViewResult.setText(errorString);
        }
        else {
            mJsonString = s;
            showResult();
        }

    }

    @Override
    protected String doInBackground(String... strings) {

        String serverURL = strings[0];

        try {
            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "response code - " + responseStatusCode);

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else {
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();

            return sb.toString().trim();

        } catch (Exception e) {

            Log.d(TAG, "InsertData: Error ", e);
            errorString = e.toString();

        }

        return null;
    }

    private void showResult() {

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i=0; i<jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_ADDRESS);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_ADDRESS, address);

                arrayList.add(hashMap);
            }

//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, mArrayList, R.layout.item_list,
//                    new String[]{TAG_ID,TAG_NAME, TAG_ADDRESS},
//                    new int[]{R.id.textView_list_id, R.id.textView_list_name, R.id.textView_list_address}
//            );
//
//            listView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult: ", e);

        }
    }

}
