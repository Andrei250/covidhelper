package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminPanelActivity extends AppCompatActivity {
    //activity's elements
    private Button insert_btn;
    private EditText full_name, phone, email, password, address;

    //auth

    private FirebaseAuth my_auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        my_auth = FirebaseAuth.getInstance();
        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        full_name = findViewById(R.id.fullName);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        insert_btn = findViewById(R.id.insert);

        my_auth = FirebaseAuth.getInstance();

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String f_name = full_name.getText().toString().trim();
        final String mail = email.getText().toString().trim();
        final String passw = password.getText().toString().trim();
        final String phone_number = phone.getText().toString().trim();
        final String add = address.getText().toString().trim();

        if (f_name.isEmpty()) {
            full_name.setError("Full name is required");
            full_name.requestFocus();
            return;
        }

        if (mail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (phone_number.isEmpty()) {
            phone.setError("Phone number is required");
            phone.requestFocus();
            return;
        }

        if (passw.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
      
        if (add.isEmpty()) {
            address.setError("Address is required");
            address.requestFocus();
            return;
        }

        my_auth.createUserWithEmailAndPassword(mail, passw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //is the request was successfully completed
                            User user = new User(
                                    f_name,
                                    mail,
                                    phone_number,
                                    add,
                                    "0"
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminPanelActivity.this,
                                                "Registration Successful",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(AdminPanelActivity.this,
                                                task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            Intent intent = new Intent(AdminPanelActivity.this, DisplayUsersActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        } else {
                            Toast.makeText(AdminPanelActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
