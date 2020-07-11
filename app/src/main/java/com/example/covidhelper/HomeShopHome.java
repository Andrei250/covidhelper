package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeShopHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth auth;
    public static final String TAG = "HomeShopHome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_sh);

        NavigationView navigationView = findViewById(R.id.idNavViewHSH);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.idToolBarSH);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.idDrawerSH);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_close, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // TODO uncomment/comment to show/hide user's name
        showUserName();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.idShopUpdateSchedule):
                Intent intent_schedule = new Intent(getApplicationContext(), ShopUpdateSchedule.class);
                startActivity(intent_schedule);
                break;
            case (R.id.idShopUpdateStock):
                Intent intent_stock = new Intent(getApplicationContext(), ShopUpdateStock.class);
                startActivity(intent_stock);
                break;
            case (R.id.idShopUpdateDetails):
                Intent intent_details = new Intent(getApplicationContext(), ShopUpdateDetails.class);
                startActivity(intent_details);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void showUserName () {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            getSupportActionBar().setTitle("Jale");
            return;
        }

        String current_user_id = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("name").getValue() == null) {
                    getSupportActionBar().setTitle("Jale");
                    return;
                }

                String shop_name = snapshot.child("name").getValue().toString();
                getSupportActionBar().setTitle(shop_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }
}