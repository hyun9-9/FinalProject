package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecipeActivity extends AppCompatActivity {
    ImageView recipeimage;
    TextView recipename,Tmainingredient,Tsubingredient,Tseasoning;
    TextView recipetime;
    TextView recipedifficulty;
    TextView recipecapacity;
    Bitmap bitmap;

    LinearLayout recipeLayout,recipeview;
    List<String> main_ingredient;
    List<String> sub_ingredient;
    List<String> seasoning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);



        Intent get_intent = getIntent();
        String recipe_id = get_intent.getStringExtra("id");
        String recipe_image = get_intent.getStringExtra("recipeimage");
        String recipe_name = get_intent.getStringExtra("recipename");
        String recipe_time = get_intent.getStringExtra("recipetime");
        String recipe_difficulty = get_intent.getStringExtra("recipedifficulty");
        String access_token = get_intent.getStringExtra("access_token");
        String recipe_capacity = get_intent.getStringExtra("recipecapacity");
        main_ingredient = new ArrayList<>();
        sub_ingredient = new ArrayList<>();
        seasoning = new ArrayList<>();

        //recipeLayout=(LinearLayout)findViewById(R.id.recipexml);

        //mInflater.inflate(R.layout.recipeview,mRootLinear,true);

        //recipeLayout.addView(recipeview);
        recipeimage = findViewById(R.id.recipeimage);
        recipename = findViewById(R.id.recipename);
        recipetime = findViewById(R.id.recipetime);
        recipedifficulty = findViewById(R.id.recipedifficulty);
        recipecapacity = findViewById(R.id.capacity);
        Tmainingredient = findViewById(R.id.main_ingredient);
        Tsubingredient = findViewById(R.id.sub_ingredient);
        Tseasoning = findViewById(R.id.seasoning);
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(recipe_image);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        };
        mThread.start();
        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            recipename.setText(recipe_name);
            recipetime.setText("소요 시간 : " + recipe_time);
            recipedifficulty.setText("난이도 : " +recipe_difficulty);
            recipecapacity.setText(recipe_capacity);
            recipeimage.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       jsonRequest(access_token,recipe_id);
    }
    private void jsonRequest(String access_token,String recipe_id){
        main_ingredient.clear();
        sub_ingredient.clear();
        seasoning.clear();
        Tmainingredient.setText("  주재료 : ");
        Tsubingredient.setText("  부재료 : ");
        Tseasoning.setText("  양념 : ");
        Response.Listener<String> recipeListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Log.e("Test123", jsonObject.toString());
                    JSONArray data_json = jsonObject.getJSONArray("data");
                    //Log.e("Test123",data_json.toString());
                    for (int i = 0; i < data_json.length(); i++) {
                        JSONObject jsonObject1 = data_json.getJSONObject(i);
                        // jsonObject1=data_json.getJSONObject(i);
                        //Log.e("Test123",jsonObject1.toString());
                        if (jsonObject1.getString("type").equals("주재료")) {
                            main_ingredient.add(jsonObject1.getString("name") + " : " + jsonObject1.getString("capacity"));

                        } else if (jsonObject1.getString("type").equals("부재료")) {
                            sub_ingredient.add(jsonObject1.getString("name") + " : " + jsonObject1.getString("capacity"));

                        } else if (jsonObject1.getString("type").equals("양념")) {
                            seasoning.add(jsonObject1.getString("name") + " : " + jsonObject1.getString("capacity"));

                        }
                        //Log.e("name",data_json.getString(i));
                    }



                    Response.Listener<String> recipeListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {


                                JSONObject jsonObject = new JSONObject(response);

                                //Log.e("Test123", jsonObject.toString());
                                JSONArray data_json = jsonObject.getJSONArray("data");
                                //Log.e("Test123",data_json.toString());
                                for(int i=0;i<main_ingredient.size();i++){
                                    if(i>0&&main_ingredient.size()>i) {
                                        Tmainingredient.append(", ");
                                    }
                                    Tmainingredient.append(main_ingredient.get(i));
                                }
                                for(int i=0;i<sub_ingredient.size();i++){
                                    if(i>0&&sub_ingredient.size()>i) {
                                        Tsubingredient.append(", ");
                                    }
                                    Tsubingredient.append(sub_ingredient.get(i));
                                }
                                for(int i=0;i<seasoning.size();i++){
                                    if(i>0&&seasoning.size()>i) {
                                        Tseasoning.append(", ");
                                    }
                                    Tseasoning.append(seasoning.get(i));
                                }


                                for (int i = 0; i < data_json.length(); i++) {
                                    JSONObject jsonObject1 = data_json.getJSONObject(i);
                                    // jsonObject1=data_json.getJSONObject(i);
                                    //Log.e("Test123",jsonObject1.toString());




                                    final String im=jsonObject1.getString("image_link");
                                    Log.e("image_link",im);
                                    if(im.equals("null")){
                                        LayoutInflater mInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                                        LinearLayout mRootLinear=(LinearLayout)findViewById(R.id.linear_root);
                                        LinearLayout mLinearLayout=(LinearLayout)mInflater.inflate(R.layout.recipeviewtext,mRootLinear,false) ;
                                        TextView textView = mLinearLayout.findViewById(R.id.name);
                                        textView.setText(jsonObject1.getString("description"));
                                        mRootLinear.addView(mLinearLayout);
                                    }
                                    else{
                                        Thread mThread = new Thread() {
                                            @Override
                                            public void run() {
                                                try {
                                                    URL url = new URL(im);
                                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                    conn.setDoInput(true); // 서버로 부터 응답 수신
                                                    conn.connect();

                                                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                                                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                                                } catch (MalformedURLException e) {
                                                    e.printStackTrace();

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }


                                        };
                                        mThread.start();
                                        try {
                                            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
                                            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
                                            mThread.join();

                                            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
                                            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
                                            LayoutInflater mInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                                            LinearLayout mRootLinear=(LinearLayout)findViewById(R.id.linear_root);
                                            LinearLayout mLinearLayout=(LinearLayout)mInflater.inflate(R.layout.recipeview,mRootLinear,false) ;
                                            TextView textView = mLinearLayout.findViewById(R.id.name);
                                            ImageView imageView=mLinearLayout.findViewById(R.id.Image);
                                            textView.setText(jsonObject1.getString("description"));
                                            imageView.setImageBitmap(bitmap);
                                            mRootLinear.addView(mLinearLayout);
                                        } catch (InterruptedException e) {

                                            e.printStackTrace();
                                        }

                                    }



                                    //Log.e("name",data_json.getString(i));
                                }
                                LayoutInflater mInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                                LinearLayout mRootLinear=(LinearLayout)findViewById(R.id.linear_root);
                                LinearLayout mLinearLayout=(LinearLayout)mInflater.inflate(R.layout.recipeviewtext,mRootLinear,false) ;
                                TextView textView = mLinearLayout.findViewById(R.id.name);
                                textView.setText("");
                                mRootLinear.addView(mLinearLayout);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("err", "test json err");

                                jsonRequest(access_token,recipe_id);
                            }
                        }
                    };
                    RecipeproRequest recipeproqueue = new RecipeproRequest(access_token, recipe_id, recipeListener);
                    RequestQueue recipequeue = Volley.newRequestQueue(RecipeActivity.this);
                    recipequeue.add(recipeproqueue);

                    //Log.d("token",access_token);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("err", "Searchtag json err");

                    jsonRequest(access_token,recipe_id);
                }
            }
        };


        RecipeRequest recipeRequest = new RecipeRequest(access_token, recipe_id, recipeListener);
        RequestQueue recipequeue = Volley.newRequestQueue(RecipeActivity.this);
        recipequeue.add(recipeRequest);

    }
}