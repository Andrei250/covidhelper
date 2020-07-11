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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopUpdateEmail extends AppCompatActivity {

    private static final String TAG = "ShopUpdateEmail";
    private TextView current_email;
    private EditText email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_email);

        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");

        Button save = findViewById(R.id.idSaveNameBtnE);
        Button cancel = findViewById(R.id.idCancelNameBtnE);
        Toolbar toolbar = findViewById(R.id.idToolBarShopUpdateEmail);
        current_email = findViewById(R.id.idShopCurrentEmail);
        email = (EditText) findViewById(R.id.idEditTextShopEmail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change email address");

        useName(current_user_id, reference);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_email = email.getText().toString().trim();
                changeName(new_email, current_user_id, reference);
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
                String shop_email = snapshot.child("email").getValue().toString().trim();
                current_email.setText(shop_email);
                email.setText(shop_email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }

    private void changeName(String new_email, String current_user_id, DatabaseReference reference) {
        if (new_email.isEmpty()) {
            Toast.makeText(ShopUpdateEmail.this,
                    "EMAIL CANNOT BE EMPTY!", Toast.LENGTH_LONG).show();
        } else {
            reference.child(current_user_id).child("email").setValue(new_email);
            // TODO add onComplete
        }
    }
}