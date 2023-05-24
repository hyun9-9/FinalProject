package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class errorActivity extends AppCompatActivity {
ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Intent get_intent = getIntent();
        String UserEmail = get_intent.getStringExtra("UserEmail");
        String User_mobile = get_intent.getStringExtra("User_mobile");
        String UserName = get_intent.getStringExtra("UserName");
        String refresh_token=get_intent.getStringExtra("refresh_token");
        String access_token=get_intent.getStringExtra("access_token");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    Log.e("Test123",jsonObject.toString());
                    JSONArray data_json=jsonObject.getJSONArray("data");
                    for(int i=0;i<data_json.length();i++){
                        JSONObject jsonObject1=data_json.getJSONObject(i);

                        ProductRepository dao = ProductRepository.getInstance();
                        Product newProduct = new Product();
                        newProduct.setProductId(jsonObject1.getString("id"));
                        newProduct.setPname(jsonObject1.getString("name"));
                        newProduct.setIntroduction(jsonObject1.getString("introduction"));
                        newProduct.settime(jsonObject1.getString("time"));
                        newProduct.setcalorie(jsonObject1.getString("calorie"));
                        newProduct.setcapacity(jsonObject1.getString("capacity"));
                        newProduct.setdifficulty(jsonObject1.getString("difficulty"));
                        newProduct.setimage_link(jsonObject1.getString("image_link"));


                        dao.addProduct(newProduct);
                        Log.e("name",jsonObject1.getString("id"));
                    }

                    Intent intent = new Intent(errorActivity.this, HomeActivity.class);
                    intent.putExtra( "UserEmail", UserEmail );
                    intent.putExtra( "User_mobile", User_mobile );
                    intent.putExtra( "UserName", UserName );
                    intent.putExtra( "access_token", access_token );
                    intent.putExtra( "refresh_token", refresh_token );
                    //Log.d("token",access_token);
                    startActivity( intent );
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("err","json err");
                    Intent intent = new Intent(errorActivity.this, errorActivity.class);
                    intent.putExtra( "UserEmail", UserEmail );
                    intent.putExtra( "User_mobile", User_mobile );
                    intent.putExtra( "UserName", UserName );
                    intent.putExtra( "access_token", access_token );
                    intent.putExtra( "refresh_token", refresh_token );
                    startActivity( intent );
                    finish();
                }
            }
        };
        HomeRequest homeRequest = new HomeRequest(access_token, responseListener);
        RequestQueue queue = Volley.newRequestQueue(errorActivity.this);
        queue.add(homeRequest);
        imageButton =findViewById(R.id.replay);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}