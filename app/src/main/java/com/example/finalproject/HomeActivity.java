package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    Fragment defaultFrag;
    BottomNavigationView nav_menu;
    String access_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent get_intent = getIntent();
        String UserEmail = get_intent.getStringExtra("UserEmail");
        String User_mobile = get_intent.getStringExtra("User_mobile");
        String UserName = get_intent.getStringExtra("UserName");
        String refresh_token=get_intent.getStringExtra("refresh_token");
        access_token=get_intent.getStringExtra("access_token");


        Bundle bundle = new Bundle();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();



        nav_menu = findViewById(R.id.bottom_nav);
        defaultFrag = new HomeFragment1();
        nav_menu.setSelectedItemId(R.id.home);

        bundle.putString("access_token",access_token);
        HomeFragment1 homeFragment=new HomeFragment1();
        homeFragment.setArguments(bundle);
        transaction.replace(R.id.mainFrame, homeFragment).commitAllowingStateLoss();

        //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,defaultFrag).commitAllowingStateLoss();
        nav_menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //리스너 적용하면 아이콘 선택시 색상이 변하지 않기 때문에 수동으로 적용
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.home: {
                        Bundle bundle = new Bundle();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        bundle.putString("access_token",access_token);
                        HomeFragment1 homeFragment=new HomeFragment1();
                        homeFragment.setArguments(bundle);
                        transaction.replace(R.id.mainFrame, homeFragment).commit();

                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new HomeFragment()).commit();
                        return true;
                    }
                    case R.id.search: {
                        Bundle bundle = new Bundle();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        bundle.putString("access_token",access_token);
                        SearchFragment1 searchFragment=new SearchFragment1();
                        searchFragment.setArguments(bundle);
                        transaction.replace(R.id.mainFrame, searchFragment).commit();
//                        bundle.putSerializable("list", settingList.getlist());
//                        SearchFragment searchFragment=new SearchFragment();
//                        searchFragment.setArguments(bundle);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new SearchFragment()).commit();
                        return true;
                    }
                    case R.id.profile: {
                        Bundle bundle = new Bundle();
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        bundle.putString("access_token",access_token);
                        bundle.putString("UserEmail",UserEmail);
                        bundle.putString("User_mobile",User_mobile);
                        bundle.putString("UserName",UserName);
                        ProfileFragment profileFragment=new ProfileFragment();
                        profileFragment.setArguments(bundle);
                        transaction.replace(R.id.mainFrame, profileFragment).commit();

                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ProfileFragment()).commit();
                        return true;
                    }

                }

                return false;
            }
        });

    }
    @Override
    protected void onPause(){
        super.onPause();
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        saveState();
    }
    protected void saveState(){
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor =pref.edit();
        editor.putString("access_token",access_token);
        editor.commit();
    }


}