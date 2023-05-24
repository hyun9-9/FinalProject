package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    Fragment defaultFrag;
    BottomNavigationView nav_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent get_intent = getIntent();
        String UserEmail = get_intent.getStringExtra("UserEmail");
        String User_mobile = get_intent.getStringExtra("User_mobile");
        String UserName = get_intent.getStringExtra("UserName");
        String refresh_token=get_intent.getStringExtra("refresh_token");
        String access_token=get_intent.getStringExtra("access_token");


        Bundle bundle = new Bundle();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();



        nav_menu = findViewById(R.id.bottom_nav);
        defaultFrag = new HomeFragment();
        nav_menu.setSelectedItemId(R.id.home);

        bundle.putString("access_token",access_token);
        HomeFragment homeFragment=new HomeFragment();
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
                        HomeFragment homeFragment=new HomeFragment();
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
                        SearchFragment searchFragment=new SearchFragment();
                        searchFragment.setArguments(bundle);
                        transaction.replace(R.id.mainFrame, searchFragment).commit();
//                        bundle.putSerializable("list", settingList.getlist());
//                        SearchFragment searchFragment=new SearchFragment();
//                        searchFragment.setArguments(bundle);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new SearchFragment()).commit();
                        return true;
                    }
                    case R.id.profile: {

                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ProfileFragment()).commit();
                        return true;
                    }

                }

                return false;
            }
        });

    }
    public void replaceFragment(Fragment fragment) {
        nav_menu.setSelectedItemId(R.id.search);
    }


}