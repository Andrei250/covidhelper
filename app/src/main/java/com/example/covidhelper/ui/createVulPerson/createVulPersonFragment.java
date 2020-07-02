package com.example.covidhelper.ui.createVulPerson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidhelper.R;
import com.example.covidhelper.ui.homeAdmin.HomeViewModelAdmin;

public class createVulPersonFragment extends Fragment {
    private createVulPersonModel create_vul_person_model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        create_vul_person_model =
                new ViewModelProvider(this).get(createVulPersonModel.class);
        View root = inflater.inflate(R.layout.activity_admin_panel, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        create_vul_person_model.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
