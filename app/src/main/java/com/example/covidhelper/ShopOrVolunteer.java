package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopOrVolunteer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_or_volunteer);

        Button vendor_btn = findViewById(R.id.idIAmVendorBtn);
        Button volunteer_btn = findViewById(R.id.idIamVolunteerBtn);
        TextView already_acc = findViewById(R.id.idVOrVAlreadyAcc);

        // if Button is pressed then open ShopRegister activity
        vendor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopRegister.class);
                startActivity(intent);
            }
        });

        // if Button is pressed then open VolunteerRegister activity
        volunteer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VolunteerRegister.class);
                startActivity(intent);
            }
        });

        // if TextView is pressed then open Login activity
        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}