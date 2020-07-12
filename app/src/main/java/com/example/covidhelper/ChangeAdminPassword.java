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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeAdminPassword extends AppCompatActivity {
    private EditText old_pass, new_pass, repeat_new_pass;
    private Button submit;
    private FirebaseAuth my_auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        my_auth = FirebaseAuth.getInstance();
        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        old_pass = findViewById(R.id.old_pass);
        new_pass = findViewById(R.id.new_pass);
        repeat_new_pass = findViewById(R.id.repeat_new_pass);
        submit = findViewById(R.id.submit_change_password);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });
    }

    private void changePass() {
        final String old = old_pass.getText().toString().trim();
        final String new_p = new_pass.getText().toString().trim();
        final String repeat = repeat_new_pass.getText().toString().trim();

        if (old.isEmpty()) {
            old_pass.setError("Full name is required");
            old_pass.requestFocus();
            return;
        }

        if (new_p.isEmpty()) {
            new_pass.setError("Email is required");
            new_pass.requestFocus();
            return;
        }

        if (repeat.isEmpty()) {
            repeat_new_pass.setError("Phone number is required");
            repeat_new_pass.requestFocus();
            return;
        }

        if (!repeat.equals(new_pass)) {
            repeat_new_pass.setError("Passwords must be the same");
            repeat_new_pass.requestFocus();
            return;
        }

        final FirebaseUser user = my_auth.getCurrentUser();

        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), old.toString());

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            user.updatePassword(new_p.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ChangeAdminPassword.this,
                                            "Password Changed Successfully",
                                            Toast.LENGTH_LONG).show();
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(ChangeAdminPassword.this, LoginActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangeAdminPassword.this,
                                            "Can't change password",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChangeAdminPassword.this,
                                    "Password is incorrect",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
