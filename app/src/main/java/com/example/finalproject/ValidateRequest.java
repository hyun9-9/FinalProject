package com.example.finalproject;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://3.39.213.10/api/account/check";
    private Map<String, String> map;

    public ValidateRequest(String UserEmail, Response.Listener<String> listener){


        super(Request.Method.GET, URL+"?email="+UserEmail, listener,
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err", String.valueOf(error));
            }
            });
        map = new HashMap<>();
        map.put("email", UserEmail);
        //Log.d("Success", "String.valueOf(success)");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return map;
    }
}
