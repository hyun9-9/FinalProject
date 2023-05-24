package com.example.finalproject;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends StringRequest {
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://3.39.213.10/api/ingredients";
    private Map<String, String> map;
    private HashMap<String, String> headers;
    String tokenEncoded;
    String token;
    public SearchRequest(String Token, Response.Listener<String> listener){


        super(Request.Method.GET, URL+"?type=주재료&only_name=1", listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("err", String.valueOf(error));
                    }
                });

        //map = new HashMap<>();
        //map.put("token", Token);
        tokenEncoded= Base64.encodeToString(Token.getBytes(), Base64.NO_WRAP);
        token=Token;
        //Log.d("Success", "String.valueOf(success)");
    }
    //    @Override
//    public String getBodyContentType() {
//        return "application/json; charset=utf-8";
//    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers=new HashMap<>();
        //headers.put("Content-Type","");
        //headers.put("Authorization",String.format("Bearer Token %s",tokenEncoded));
        Log.d("Tokentext",token);
        //headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + token);
        //headers.put("Authorization", token);
        return headers;
    }

//    @Override
//    public Map<String, String> getAuthorization() throws AuthFailureError {
//        HashMap<String, String> headers=new HashMap<>();
//        headers.put("type","bearer");
//        headers.put("token",map.get("token"));
//        return headers;
//    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return map;
    }
}
