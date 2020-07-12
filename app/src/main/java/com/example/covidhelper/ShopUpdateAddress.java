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

public class ShopUpdateAddress extends AppCompatActivity {

    private static final String TAG = "ShopUpdateEmail";
    private TextView current_address;
    private EditText address;
    private FirebaseAuth my_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_address);

        my_auth = FirebaseAuth.getInstance();
        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");

        Button save = findViewById(R.id.idSaveNameBtnA);
        Button cancel = findViewById(R.id.idCancelNameBtnA);
        Toolbar toolbar = findViewById(R.id.idToolBarShopUpdateAddress);
        current_address= findViewById(R.id.idShopCurrentAddress);
        address = (EditText) findViewById(R.id.idEditTextShopAddress);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change address");

        useName(current_user_id, reference);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_address = address.getText().toString().trim();
                changeName(new_address, current_user_id, reference);
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
                String shop_address = snapshot.child("address").getValue().toString().trim();
                current_address.setText(shop_address);
                address.setText(shop_address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }

    private void changeName(String new_address, String current_user_id, DatabaseReference reference) {
        if (new_address.isEmpty()) {
            Toast.makeText(ShopUpdateAddress.this,
                    "ADDRESS CANNOT BE EMPTY!", Toast.LENGTH_LONG).show();
        } else {
            reference.child(current_user_id).child("address").setValue(new_address);
            // TODO add onComplete
        }
    }
}