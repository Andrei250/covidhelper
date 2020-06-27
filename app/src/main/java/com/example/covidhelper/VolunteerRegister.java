package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerRegister extends AppCompatActivity {

    private EditText input_email;
    private EditText input_name;
    private EditText input_phone_num;
    private EditText input_password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_register);

        // Get Firebase Auth Instance
        auth = FirebaseAuth.getInstance();

        Button btn_register = findViewById(R.id.idRegisterButton);

        input_name = findViewById(R.id.idVolunteerName);
        input_phone_num = findViewById(R.id.idPhoneNumber);
        input_password = findViewById(R.id.idPassword);
        input_email = findViewById(R.id.idEmail);

        // When clicked registers the volunteer
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerVolunteer();
            }
        });
    }

    private void registerVolunteer () {
        // get text from input fields
        final String name = input_name.getText().toString().trim();
        final String phone_number = input_phone_num.getText().toString().trim();
        final String password = input_password.getText().toString().trim();
        final String email = input_email.getText().toString().trim();

        // check if there are wrong inputs
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter volunteer's name!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone_number)) {
            Toast.makeText(getApplicationContext(), "Enter a phone number!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter an email address!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter a password!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Your password is too short!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // adds volunteer and volunteer's details into the db
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Volunteer volunteer = new Volunteer (name, phone_number, email);

                            FirebaseDatabase.getInstance().getReference("Volunteers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(volunteer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(VolunteerRegister.this,
                                                "Registration Successful",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(VolunteerRegister.this,
                                                task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(VolunteerRegister.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                         }
                });
    }
}