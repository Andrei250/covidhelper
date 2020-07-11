package com.example.covidhelper;

import android.os.Bundle;
import android.view.MenuItem;

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

import com.example.covidhelper.ui.needHelpVulPerson.NeedHelpVulPersonFragment;
import com.example.covidhelper.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class VolunteerInterface extends AppCompatActivity {
    private DrawerLayout my_drawer_layout;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration app_bar_configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav_volunteer);

        my_drawer_layout = findViewById(R.id.volunteer_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar_volunteer_test);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, my_drawer_layout,
                R.string.open_admin_nav,
                R.string.close_admin_nav);

        my_drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottom_nav_view = findViewById(R.id.bottom_navigation_volunteer);
        bottom_nav_view.setOnNavigationItemSelectedListener(bottom_nav_listener);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new NeedHelpVulPersonFragment(), "GetCommand").commit();

        app_bar_configuration = new AppBarConfiguration.Builder(R.id.home,
                R.id.navigation_notifications_volunteer,
                R.id.offer_help).build();

        NavigationView left_nav_view = findViewById(R.id.volunteer_left_nav_view);
        left_nav_view.setNavigationItemSelectedListener(left_nav_listener);

    }

    private NavigationView.OnNavigationItemSelectedListener left_nav_listener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected_fragment = null;
                    Integer fragment_id = 0;

                    switch (item.getItemId()) {
                        case R.id.update_info:
                            selected_fragment = new NeedHelpVulPersonFragment(); // needs to be changed
                            break;
                        case R.id.volunteer_nav_settings:
                            selected_fragment = new NeedHelpVulPersonFragment(); // needs to be changed
                            break;
                        case R.id.logout:
                            selected_fragment = new NeedHelpVulPersonFragment(); // needs to be changed
                            break;
                    }

//                    if (fragment_id == 2) {
//                        Intent intent = new Intent(getApplicationContext(), DisplayUsersActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(intent);
//                        return true;
//                    } else if (fragment_id == 4) {
//                        Intent intent = new Intent(getApplicationContext(), DisplayStoresActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(intent);
//                        return true;
//                    } else if (fragment_id == 3) {
//                        Intent intent = new Intent(getApplicationContext(), DisplayVolunteersActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(intent);
//                        return true;
//                    }

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
                            selected_fragment = new NeedHelpVulPersonFragment(); // needs to be changed
                            fragment_id = "INeedHelp";
                            break;
                        case R.id.navigation_notifications_volunteer:
                            selected_fragment = new NotificationsFragment();
                            fragment_id = "Notifications";
                            break;
                        case R.id.offer_help:
                            selected_fragment = new NotificationsFragment();
                            fragment_id = "ReportPerson";
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


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, app_bar_configuration)
                || super.onSupportNavigateUp();
    }

}
