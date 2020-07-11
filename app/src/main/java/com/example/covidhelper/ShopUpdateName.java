package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopUpdateName extends AppCompatActivity {

    private static final String TAG = "ShopUpdateName";
    private TextView current_name;
    private EditText name;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_name);

        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");

        Button save = findViewById(R.id.idSaveNameBtn);
        Button cancel = findViewById(R.id.idCancelNameBtn);
        Toolbar toolbar = findViewById(R.id.idToolBarShopUDName);
        current_name = findViewById(R.id.idCurrentName);
        name = (EditText) findViewById(R.id.idEditTextNewName);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change name");

        useName(current_user_id, reference);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = name.getText().toString();
                changeName(new_name, current_user_id, reference);
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
                String shop_name = snapshot.child("name").getValue().toString();
                current_name.setText(shop_name);
                name.setText(shop_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        });
    }

    private void changeName(String new_name, String current_user_id, DatabaseReference reference) {
        if (new_name.isEmpty()) {
            Toast.makeText(ShopUpdateName.this,
                    "NAME CANNOT BE EMPTY!", Toast.LENGTH_LONG).show();
        } else {
            reference.child(current_user_id).child("name").setValue(new_name);
            // TODO  add  onComplete
        }
    }
}