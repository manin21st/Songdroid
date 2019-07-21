package com.example.androidpda;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class test_register extends StringRequest {
    final static private String URL = "http://118.38.159.9/android_pda/test_register.php";
    private Map<String, String> parameters;

    public test_register(String userID, String userPassword, String userName, int userAge, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge + "");
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
