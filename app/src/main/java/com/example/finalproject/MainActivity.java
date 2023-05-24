package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent get_intent = getIntent();
        String UserEmail = get_intent.getStringExtra("UserEmail");
        String User_mobile = get_intent.getStringExtra("User_mobile");
        String UserName = get_intent.getStringExtra("UserName");
        String refresh_token=get_intent.getStringExtra("refresh_token");
        String access_token=get_intent.getStringExtra("access_token");
        if(UserEmail ==null) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra( "UserEmail", UserEmail );
            intent.putExtra( "User_mobile", User_mobile );
            intent.putExtra( "UserName", UserName );
            intent.putExtra( "access_token", access_token );
            intent.putExtra( "refresh_token", refresh_token );
            //Log.d("token",access_token);
            startActivity( intent );
        }

    }
}