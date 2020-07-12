package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpanWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText input_email;
    private EditText input_password;
    private FirebaseAuth auth;
    private TextView register;
    private ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = findViewById(R.id.idLogInBtn);

        input_email = findViewById(R.id.idLogInEmail);
        input_password = findViewById(R.id.idLogInPassword);
        register = findViewById(R.id.idLoginRegister);
        prog = findViewById(R.id.progressLogIn);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String UID = user.getUid();
            if (chooseInterface(UID)) {
                finish();
            }
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShopOrVolunteer.class));
            }
        });
    }

    private void login () {
        final String email = input_email.getText().toString().trim();
        final String password = input_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter your email address!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter your password!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        prog.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    if (user == null) {
                        Toast.makeText(getApplicationContext(), "Failed to log in",
                                Toast.LENGTH_SHORT).show();
                        startActivity(getIntent());
                    }

                    String UID = user.getUid();

                    if (chooseInterface(UID)) {
                        finish();
                        return;
                    }

                    prog.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Failed to log in",
                            Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());

                }
                else {
                    prog.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Something wrong happened!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean chooseInterface(String UID) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Admins").child(UID);

        if (ref != null) {
            Toast.makeText(getApplicationContext(), "You are logged into your account!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AdminInterface.class));
            finish();
            prog.setVisibility(View.INVISIBLE);
            return true;
        }

        ref  = FirebaseDatabase.getInstance().getReference("Volunteers").child(UID);

        if (ref != null) {
            Toast.makeText(getApplicationContext(), "You are logged into your account!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), VolunteerInterface.class));
            finish();
            prog.setVisibility(View.INVISIBLE);
            return true;
        }

        ref  = FirebaseDatabase.getInstance().getReference("Users").child(UID);

        if (ref != null) {
            Toast.makeText(getApplicationContext(), "You are logged into your account!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), VulnerablePersonInterface.class));
            finish();
            prog.setVisibility(View.INVISIBLE);
            return true;
        }

        ref  = FirebaseDatabase.getInstance().getReference("Stores").child(UID);

        if (ref != null) {
            Toast.makeText(getApplicationContext(), "You are logged into your account!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomeShopHome.class));
            finish();
            prog.setVisibility(View.INVISIBLE);
            return true;
        }

        return false;
    }

}