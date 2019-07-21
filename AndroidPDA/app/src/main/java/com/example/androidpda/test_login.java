package com.example.androidpda;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class test_login extends StringRequest {
    final static private String URL = "http://118.38.159.9/android_pda/pda_login.php";
    private Map<String, String> parameters;

    public test_login(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
