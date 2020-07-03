package com.example.covidhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidhelper.ui.createVulPerson.createVulPersonFragment;
import com.example.covidhelper.ui.homeAdmin.HomeFragmentAdmin;
import com.example.covidhelper.ui.notifications.NotificationsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

public class BottomNavigation extends AppCompatActivity {
    private FirebaseAuth my_auth;
    private DrawerLayout my_drawer_layout;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration app_bar_configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_admin);

        my_drawer_layout = findViewById(R.id.admin_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar_admin_test);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, my_drawer_layout,
                R.string.open_admin_nav,
                R.string.close_admin_nav);

        my_drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView nav_view = findViewById(R.id.bottom_navigation_admin);
        nav_view.setOnNavigationItemSelectedListener(nav_listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new HomeFragmentAdmin(), "Home").commit();

        app_bar_configuration = new AppBarConfiguration.Builder(R.id.navigation_left,
                R.id.navigation_notifications_admin,
                R.id.add_user).build();



        my_auth = FirebaseAuth.getInstance();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected_fragment = null;
                    String fragment_id = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_left:
                            selected_fragment = new HomeFragmentAdmin(); // needs to be changed
                            fragment_id = "Home";
                            break;
                        case R.id.navigation_notifications_admin:
                            selected_fragment = new NotificationsFragment();
                            fragment_id = "Notifications";
                            break;
                        case R.id.add_user:
                            selected_fragment = new createVulPersonFragment();
                            fragment_id = "createVulPerson";
                            break;
                    }

//                    assert fragment_id != null;
//                    if (fragment_id.equals("createVulPerson")) {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
//                                selected_fragment, fragment_id).commit();
//
//
//                    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, app_bar_configuration)
                || super.onSupportNavigateUp();
    }


}