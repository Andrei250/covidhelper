package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomeShopHome extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_sh);

        Toolbar toolbar = findViewById(R.id.idToolBarSH);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.idDrawerSH);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_close, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed()  {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return super.onCreateView(inflater, name, context, attrs);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return super.onCreateView(name, context, attrs);
//    }
}