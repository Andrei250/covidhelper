package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ShopOrVolunteer extends AppCompatActivity {
    private FirebaseAuth my_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_or_volunteer);

        my_auth = FirebaseAuth.getInstance();
        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        Button vendor_btn = findViewById(R.id.idIAmVendorBtn);
        Button volunteer_btn = findViewById(R.id.idIamVolunteerBtn);
        TextView already_acc = findViewById(R.id.idVOrVAlreadyAcc);

        // if Button is pressed then open ShopRegister activity
        vendor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopRegister.class);
                startActivity(intent);
                finish();
            }
        });

        // if Button is pressed then open VolunteerRegister activity
        volunteer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VolunteerRegister.class);
                startActivity(intent);
                finish();
            }
        });

        // if TextView is pressed then open Login activity
        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}