package com.example.covidhelper;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.firestore.FirebaseFirestore;

public class ModifyUser extends AppCompatActivity {
    private Button update_btn;
    private EditText full_name, phone, email, address;

    private FirebaseAuth my_auth;
    private FirebaseDatabase db;

    String name, phone_number, mail, addr, id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        full_name = findViewById(R.id.fullName);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        update_btn = findViewById(R.id.update);

        my_auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            name = bundle.getString("name");
            phone_number = bundle.getString("phone");
            addr = bundle.getString("address");
            mail = bundle.getString("email");
            id = bundle.getString("id");

            full_name.setText(name);
            phone.setText(phone_number);
            email.setText(mail);
            address.setText(addr);

            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUser();
                }
            });
        }

    }

    private void updateUser() {
        final String f_name = full_name.getText().toString().trim();
        final String mail = email.getText().toString().trim();
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

        if (add.isEmpty()) {
            address.setError("Address is required");
            address.requestFocus();
            return;
        }

        User user = new User(
                f_name,
                mail,
                phone_number,
                add
        );

        db.getReference("Users").child(id)
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(ModifyUser.this,
                                    "Updated Successful",
                                    Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(ModifyUser.this,
                                    "Something went wrong",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ModifyUser.this,
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
