package com.example.covidhelper.ui.showVulPerson;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelper.DisplayUsersActivity;
import com.example.covidhelper.ItemAdapter;
import com.example.covidhelper.ListUsersAdapter;
import com.example.covidhelper.R;
import com.example.covidhelper.User;
import com.example.covidhelper.ui.homeAdmin.HomeViewModelAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowVulPersonFragment extends Fragment {
    private RecyclerView rev;
    private View v;
    private DatabaseReference reference;
    private List<User> items;
    private List<String> Uid;
    private ListUsersAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_display, container, false);
        this.v = root;
        rev = v.findViewById(R.id.recycler_view_admin);
        rev.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ListUsersAdapter(this.getContext(), items, Uid);
        rev.setAdapter(adapter);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<>();
        Uid = new ArrayList<>();
        DisplayUser(new FirebaseCallback() {
            @Override
            public void onCallBack(List list) {
                Log.d("pers", items.toString());
                Log.d("uids", Uid.toString());
            }
        });

        User user1 = new User("da", "da", "da", "da", "da");
        User user2 = new User("da", "da", "da", "da", "da");
        User user3 = new User("da", "da", "da", "da", "da");
        items.add(user1);
        items.add(user2);
        items.add(user3);
        Uid.add("da");
        Uid.add("da");
        Uid.add("da");


    }

    private void DisplayUser(final FirebaseCallback firebase_callback) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("un user", "si aci");
                items.clear();
                Uid.clear();
                for (DataSnapshot  snapshot : dataSnapshot.getChildren()) {
                    Log.d("un user", "intra aci");
                    User user = snapshot.getValue(User.class);
                    Uid.add(snapshot.getKey());
                    items.add(user);
                }

                firebase_callback.onCallBack(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    public interface FirebaseCallback {
        void onCallBack(List list);
    }

}
