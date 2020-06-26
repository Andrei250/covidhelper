package com.example.covidhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.PhoneAuthProvider;

public class ShopRegister extends AppCompatActivity {

    private double latitude;
    private double longitude;

    private EditText input_email;
    private EditText input_shop_name;
    private EditText input_phone_num;
    private EditText input_password;
    private Button btn_register;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private FirebaseAuth auth;
//    private EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_register);

        auth = FirebaseAuth.getInstance();

        btn_register = (Button) findViewById(R.id.idRegisterButtonTry);

        input_shop_name = (EditText) findViewById(R.id.idShopName);
        input_phone_num = (EditText) findViewById(R.id.idPhoneNumberTry);
        input_password = (EditText) findViewById(R.id.idPassword);
        input_email = (EditText) findViewById(R.id.idEmail);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop_name = input_shop_name.getText().toString().trim();
                String phone_number = input_phone_num.getText().toString().trim();
                String password = input_password.getText().toString().trim();
                String email = input_email.getText().toString().trim();


                if (TextUtils.isEmpty(shop_name)) {
                    Toast.makeText(getApplicationContext(), "Enter Shop Name!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone_number)) {
                    Toast.makeText(getApplicationContext(), "Enter a phone number!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter a password!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone_number)) {
                    Toast.makeText(getApplicationContext(), "Enter a phone number!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Enter Shop Name1",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(ShopRegister.this,
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(ShopRegister.this, "createUserWithEmail:onComplete:"
                                        + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    Toast.makeText(ShopRegister.this, "Authentication failed. :("
                                            + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity (new Intent(ShopRegister.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}