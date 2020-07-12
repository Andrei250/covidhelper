package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SeeUserDetails extends AppCompatActivity {
    private TextView full_name, phone, email, address;
    String name, phone_number, mail, addr, id;
    private FirebaseAuth my_auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_user_info);

        my_auth = FirebaseAuth.getInstance();
        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        full_name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            name = bundle.getString("name");
            phone_number = bundle.getString("phone");
            addr = bundle.getString("address");
            mail = bundle.getString("email");

            full_name.setText(name);
            phone.setText(phone_number);
            email.setText(mail);
            address.setText(addr);
        }
    }
}
