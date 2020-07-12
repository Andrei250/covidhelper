package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopUpdatePhoneNumber extends AppCompatActivity {

    private static final String TAG = "ShopUpdatePhoneNumber";
    private TextView current_phone_number;
    private EditText phone_number;
    private FirebaseAuth my_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_phone_number);

        my_auth = FirebaseAuth.getInstance();
        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");

        Button save = findViewById(R.id.idSaveNameBtn);
        Button cancel = findViewById(R.id.idCancelNameBtn);
        Toolbar toolbar = findViewById(R.id.idToolBarShopUDName);
        current_phone_number = findViewById(R.id.idCurrentPhoneNumber);
        phone_number = (EditText) findViewById(R.id.idEditTextPhoneNumber);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change phone number");

        useName(current_user_id, reference);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_phone_number = phone_number.getText().toString();
                changeName(new_phone_number, current_user_id, reference);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel_intent = new Intent(getApplicationContext(), ShopUpdateDetails.class);
                startActivity(cancel_intent);
            }
        });
    }

    private void useName(String current_user_id, DatabaseReference reference) {
        reference.child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String shop_phone_number = snapshot.child("phoneNumber").getValue().toString();
                current_phone_number.setText(shop_phone_number);
                phone_number.setText(shop_phone_number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }

    private void changeName(String new_phone_number, String current_user_id, DatabaseReference reference) {
        if (new_phone_number.isEmpty()) {
            Toast.makeText(ShopUpdatePhoneNumber.this,
                    "PHONE NUMBER CANNOT BE EMPTY!", Toast.LENGTH_LONG).show();
        } else {
            reference.child(current_user_id).child("phoneNumber").setValue(new_phone_number);
            // TODO add onComplete
        }
    }
}