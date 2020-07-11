package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopUpdateDetails extends AppCompatActivity {

    private static String TAG = "ShopUpdateDetails";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_details);

        ConstraintLayout name = findViewById(R.id.constraintLayout1SD);
        ConstraintLayout phone = findViewById(R.id.constraintLayout2SD);
        ConstraintLayout email = findViewById(R.id.constraintLayout3SD);
        ConstraintLayout address = findViewById(R.id.constraintLayout4SD);

        Toolbar toolbar = findViewById(R.id.idToolBarShopUD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showUserName();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_name = new Intent(getApplicationContext(), ShopUpdateName.class);
                startActivity(intent_name);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_phone = new Intent(getApplicationContext(), ShopUpdatePhoneNumber.class);
                startActivity(intent_phone);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_email = new Intent(getApplicationContext(), ShopUpdateEmail.class);
                startActivity(intent_email);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_address = new Intent(getApplicationContext(), ShopUpdateAddress.class);
                startActivity(intent_address);
            }
        });

    }

    private void showUserName () {
        String current_user_id = auth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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