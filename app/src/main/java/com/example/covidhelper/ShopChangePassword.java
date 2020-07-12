package com.example.covidhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopChangePassword extends AppCompatActivity {

    private static final String TAG = "ShopUpdateEmail";
    private EditText current_pass;
    private EditText new_pass;
    private EditText new_pass_conf;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_change_password);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String current_user_id = user.getUid();
        final String email = user.getEmail();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Stores");

        Button save = findViewById(R.id.idSaveNameBtnP);
        Button cancel = findViewById(R.id.idCancelNameBtnP);
        Toolbar toolbar = findViewById(R.id.idToolBarShopChangePass);
        current_pass= findViewById(R.id.idShopCurPass);
        new_pass = findViewById(R.id.idEditTextNewPass);
        new_pass = findViewById(R.id.idEditTextNewPassConf);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change password");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}