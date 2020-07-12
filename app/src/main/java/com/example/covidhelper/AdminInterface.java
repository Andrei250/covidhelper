package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.covidhelper.ui.adminSettings.AdminSettingsFragment;
import com.example.covidhelper.ui.createVulPerson.CreateVulPersonFragment;
import com.example.covidhelper.ui.homeAdmin.HomeFragmentAdmin;
import com.example.covidhelper.ui.notifications.NotificationsFragment;
import com.example.covidhelper.ui.showVulPerson.ShowVulPersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminInterface extends AppCompatActivity {
    private FirebaseAuth my_auth;
    private DrawerLayout my_drawer_layout;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration app_bar_configuration;

    private RecyclerView recycler_view;
    private List<User> items;
    private List<String> Uid;
    private FirebaseDatabase reference;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_admin);

        reference = FirebaseDatabase.getInstance();
        my_auth = FirebaseAuth.getInstance();

        FirebaseUser user = my_auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        my_drawer_layout = findViewById(R.id.admin_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar_admin_test);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, my_drawer_layout,
                R.string.open_admin_nav,
                R.string.close_admin_nav);

        my_drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottom_nav_view = findViewById(R.id.bottom_navigation_admin);
        bottom_nav_view.setOnNavigationItemSelectedListener(bottom_nav_listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new HomeFragmentAdmin(), "Home").commit();

        app_bar_configuration = new AppBarConfiguration.Builder(R.id.home,
                R.id.navigation_notifications_admin,
                R.id.add_user).build();

        NavigationView left_nav_view = findViewById(R.id.admin_left_nav_view);
        left_nav_view.setNavigationItemSelectedListener(left_nav_listener);
    }

    private NavigationView.OnNavigationItemSelectedListener left_nav_listener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected_fragment = null;
                    Integer fragment_id = 0;

                    switch (item.getItemId()) {
                        case R.id.admin_nav_vulnerable:
                         //   selected_fragment = new ShowVulPersonFragment();
                            fragment_id = 2;
                            break;
                        case R.id.admin_nav_volunteer:
                            fragment_id = 3;
                            break;
                        case R.id.admin_nav_store:
                            fragment_id = 4;
                            break;
                        case R.id.admin_nav_settings:
                            selected_fragment = new AdminSettingsFragment();
                            break;
                        case R.id.admin_nav_logout:
                            fragment_id = 5;
                            break;
                    }

                    if (fragment_id == 2) {
                        Intent intent = new Intent(getApplicationContext(), DisplayUsersActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        return true;
                    } else if (fragment_id == 4) {
                        Intent intent = new Intent(getApplicationContext(), DisplayStoresActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        return true;
                    } else if (fragment_id == 3) {
                        Intent intent = new Intent(getApplicationContext(), DisplayVolunteersActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        return true;
                    } else if (fragment_id == 5) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return true;
                    }

                    if (selected_fragment == null) throw new AssertionError();
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selected_fragment).commit();

                    return true;
                }
            };

    private BottomNavigationView.OnNavigationItemSelectedListener bottom_nav_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected_fragment = null;
                    String fragment_id = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selected_fragment = new HomeFragmentAdmin(); // needs to be changed
                            fragment_id = "Home";
                            break;
                        case R.id.navigation_notifications_admin:
                            selected_fragment = new NotificationsFragment();
                            fragment_id = "Notifications";
                            break;
                        case R.id.add_user:
                            selected_fragment = new CreateVulPersonFragment();
                            fragment_id = "createVulPerson";
                            break;
                    }

                    if (selected_fragment == null) throw new AssertionError();
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selected_fragment, fragment_id).commit();

                    return true;
                }
            };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation_bar, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, app_bar_configuration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}