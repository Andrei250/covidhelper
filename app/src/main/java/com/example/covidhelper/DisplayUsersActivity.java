package com.example.covidhelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayUsersActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private ItemAdapter adapter;
    private List<User> items;
    private DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recycler_view = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                                                            RecyclerView.VERTICAL,
                                                            false);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);

        items = new ArrayList<>();
        DisplayUser();
    }

    private void DisplayUser() {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot  snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    items.add(user);
                }

                adapter = new ItemAdapter(getApplicationContext(), items);
                recycler_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }
}
