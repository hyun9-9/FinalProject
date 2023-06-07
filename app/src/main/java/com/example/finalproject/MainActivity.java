package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String access_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent get_intent = getIntent();
        String UserEmail = get_intent.getStringExtra("UserEmail");
        String User_mobile = get_intent.getStringExtra("User_mobile");
        String UserName = get_intent.getStringExtra("UserName");
        String refresh_token=get_intent.getStringExtra("refresh_token");
        access_token=get_intent.getStringExtra("access_token");

        if(UserEmail ==null) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
        }else {

            jsonRequest(UserEmail,User_mobile,UserName,refresh_token,access_token);

        }

    }

    public void jsonRequest(String UserEmail,String User_mobile,String UserName,String refresh_token,String access_token){
        List <String> id=new ArrayList<>();
        List <String> name=new ArrayList<>();
        List <String> introduction=new ArrayList<>();
        List <String> time=new ArrayList<>();
        List <String> calorie=new ArrayList<>();
        List <String> capacity=new ArrayList<>();
        List <String> difficulty=new ArrayList<>();
        List <String> image_link=new ArrayList<>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    Log.e("Test123",jsonObject.toString());
                    JSONArray data_json=jsonObject.getJSONArray("data");
                    for(int i=0;i<data_json.length();i++){
                        JSONObject jsonObject1=data_json.getJSONObject(i);
                        id.add(jsonObject1.getString("id"));
                        name.add(jsonObject1.getString("name"));
                        introduction.add(jsonObject1.getString("introduction"));
                        time.add(jsonObject1.getString("time"));
                        calorie.add(jsonObject1.getString("calorie"));
                        capacity.add(jsonObject1.getString("capacity"));
                        difficulty.add(jsonObject1.getString("difficulty"));
                        image_link.add(jsonObject1.getString("image_link"));

                    }
                    ProductRepository dao = ProductRepository.getInstance();
                    for(int i=0;i<id.size();i++){

                        Product newProduct = new Product();
                        newProduct.setProductId(id.get(i));
                        newProduct.setPname(name.get(i));
                        newProduct.setIntroduction(introduction.get(i));
                        newProduct.settime(time.get(i));
                        newProduct.setcalorie(calorie.get(i));
                        newProduct.setcapacity(capacity.get(i));
                        newProduct.setdifficulty(difficulty.get(i));
                        newProduct.setimage_link(image_link.get(i));
                        dao.addProduct(newProduct);

                    }
                    jsonRequestSearch(UserEmail,User_mobile,UserName,refresh_token,access_token);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("err","json err");
                    jsonRequest(UserEmail,User_mobile,UserName,refresh_token,access_token);
                }
            }
        };
        HomeRequest homeRequest = new HomeRequest(access_token, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(homeRequest);

    }
    private void jsonRequestSearch(String UserEmail,String User_mobile,String UserName,String refresh_token,String access_token){
        Response.Listener<String> searchResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject( response );

                    Log.e("Test123",jsonObject.toString());
                    JSONArray data_json=jsonObject.getJSONArray("data");
                    Log.e("Test123",data_json.toString());
                    for(int i=0;i<data_json.length();i++){
                        // jsonObject1=data_json.getJSONObject(i);
                        //Log.e("Test123",jsonObject1.toString());
                        SearchRepository s_dao = SearchRepository.getInstance();
                        s_dao.addProduct(data_json.getString(i));

                        //Log.e("name",data_json.getString(i));
                    }
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra( "UserEmail", UserEmail );
                    intent.putExtra( "User_mobile", User_mobile );
                    intent.putExtra( "UserName", UserName );
                    intent.putExtra( "access_token", access_token );
                    intent.putExtra( "refresh_token", refresh_token );
                    //Log.d("token",access_token);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity( intent );
                    //settingList();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("err","Search json err");
                    jsonRequestSearch(UserEmail,User_mobile,UserName,refresh_token,access_token);
                }
            }
        };

        SearchRequest searchRequest = new SearchRequest( access_token, searchResponseListener );
        RequestQueue searchqueue = Volley.newRequestQueue( MainActivity.this );
        searchqueue.add( searchRequest );

    }

    @Override
    protected void onResume(){
        super.onResume();
        //Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        restoreState();
    }
    protected void restoreState(){
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref!=null)&&(pref.contains("access_token"))){
            access_token=pref.getString("access_token","");
//            Response.Listener<String> searchResponseListener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                }
//            };
//
//            SearchRequest searchRequest = new SearchRequest( access_token, searchResponseListener );
//            RequestQueue searchqueue = Volley.newRequestQueue( MainActivity.this );
//            searchqueue.add( searchRequest );

        }
    }
}