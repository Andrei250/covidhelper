package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayUsersActivity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private ItemAdapter adapter;
    private List<User> items;
    private List<String> Uid;
    private DatabaseReference reference;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        recycler_view = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                                                            RecyclerView.VERTICAL,
                                                            false);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);

        items = new ArrayList<>();
        Uid = new ArrayList<>();

        db = FirebaseDatabase.getInstance();
        DisplayUser();
    }

    private void DisplayUser() {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                Uid.clear();
                for (DataSnapshot  snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    Uid.add(snapshot.getKey());
                    items.add(user);
                }

                adapter = new ItemAdapter(DisplayUsersActivity.this, getApplicationContext(), items, Uid);
                recycler_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating the menu
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu_search_user, menu);
        //SearchView
        MenuItem item = menu.findItem(R.id.action_search_user);
        SearchView search_view = (SearchView) item.getActionView();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when we press search button
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called as and when we type even a single letter
                return false;
            }
        });
        return true;
    }

    private void searchData(String query) {
        db.getReference("Users").orderByChild("email").equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //when is succeedded
                items.clear();
                Uid.clear();
                for (DataSnapshot  snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    Uid.add(snapshot.getKey());
                    items.add(user);
                }

                adapter = new ItemAdapter(DisplayUsersActivity.this, getApplicationContext(), items, Uid);
                recycler_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //when it fails
                Toast.makeText(DisplayUsersActivity.this, "Error, can't found the user", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle other menu item clicks here
        if (item.getItemId() == R.id.action_add_person) {
            //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DisplayUsersActivity.this, AdminPanelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
