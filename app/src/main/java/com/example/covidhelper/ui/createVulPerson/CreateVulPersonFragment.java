package com.example.covidhelper.ui.createVulPerson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidhelper.R;
import com.example.covidhelper.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateVulPersonFragment extends Fragment {
    private CreateVulPersonModel create_vul_person_model;
    protected Button insert_btn;
    private EditText full_name, phone, email, password, address;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        create_vul_person_model =
                new ViewModelProvider(this).get(CreateVulPersonModel.class);
        View root = inflater.inflate(R.layout.activity_admin_panel, container, false);

        full_name = root.findViewById(R.id.fullName);
        phone = root.findViewById(R.id.phone);
        address = root.findViewById(R.id.address);
        password = root.findViewById(R.id.password);
        email = root.findViewById(R.id.email);
        insert_btn = root.findViewById(R.id.insert);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return root;
    }

    protected void registerUser() {
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

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, passw)
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
                                        Toast.makeText(getActivity(),
                                                "Registration Successful",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(),
                                                task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
