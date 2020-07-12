package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShopUpdateStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_stock);

        ConstraintLayout add = findViewById(R.id.constraintLayout1SUS);
        ConstraintLayout update = findViewById(R.id.constraintLayout2SUS);

        Toolbar toolbar = findViewById(R.id.idToolBarShopUS);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Update stock");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_name = new Intent(getApplicationContext(), ShopAddProduct.class);
                startActivity(intent_name);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_phone = new Intent(getApplicationContext(), ShopUpdatePhoneNumber.class);
                startActivity(intent_phone);
            }
        });
    }
}