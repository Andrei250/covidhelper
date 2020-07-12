package com.example.covidhelper;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ShopUpdateSchedule extends AppCompatActivity {

    private static final int NUMBER_OF_DAYS = 7;
    public static final String TAG = "ShopUpdateSchedule";

    private HashMap<String, Schedule> schedule = new HashMap<>();
    private TimePicker picker_opening;
    private TimePicker picker_closing;
    private TextView text_oc;
    private TextView text_update;
    private TextView text_select;
    boolean is_open;
    boolean first_time = true;
    private String day;

    private FirebaseAuth auth;
    private DatabaseReference database;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_schedule);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        text_oc = findViewById(R.id.idShopTextOC);
        text_update = findViewById(R.id.idShopTextUpdateSchedule);
        picker_opening = findViewById(R.id.idShopTimeOpening);
        picker_closing = findViewById(R.id.idShopTimeClosing);
        text_select= findViewById(R.id.idShopTextSelect);
        Button save_btn = findViewById(R.id.idShopSaveBtn);
        Button cancel_btn = findViewById(R.id.idShopCancelBtn);

        picker_opening.setVisibility(View.INVISIBLE);
        picker_closing.setVisibility(View.INVISIBLE);
        text_oc.setVisibility(View.INVISIBLE);
        text_update.setVisibility(View.VISIBLE);
        text_select.setVisibility(View.INVISIBLE);

        picker_opening.setIs24HourView(true);
        picker_closing.setIs24HourView(true);
        picker_opening.setEnabled(false);
        picker_closing.setEnabled(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        picker_opening.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                System.out.println(picker_opening.getHour() + "   " + picker_opening.getMinute());
                try {
                    schedule.get(day).setOpening_hour(hourOfDay);
                    schedule.get(day).setOpening_minutes(minute);
                }
                catch (NullPointerException e) {
                    System.out.println("NullPointerException Caught");
                }
            }
        });

        picker_closing.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                System.out.println(picker_closing.getHour() + "   " + picker_closing.getMinute());

                try {
                    schedule.get(day).setClosing_hour(hourOfDay);
                    schedule.get(day).setClosing_minutes(minute);
                }
                catch (NullPointerException e) {
                    System.out.println("NullPointerException Caught");
                }
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO has to select time as well! time picker is only enabled if the user pressed opening/closing
                if (schedule.size() != NUMBER_OF_DAYS) {
                    Toast.makeText(ShopUpdateSchedule.this,
                            "PLEASE SET SCHEDULE FOR ALL DAYS!", Toast.LENGTH_LONG).show();
                } else {
                    String current_user_id = auth.getCurrentUser().getUid();
                    database.child("Stores").child(current_user_id).child("schedule").setValue(schedule);
                }
            }
        });
    }

    public void onRadioButtonClickedDays(View view) {
        if (first_time) {
            changeVisDay();
            first_time = false;
        }
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.idShopMon:
                if (checked) {
                    day = "Monday";
                    initializeHashMap();
                }
                break;
            case R.id.idShopTue:
                if (checked) {
                    day = "Tuesday";
                    initializeHashMap();
                }
                break;
            case R.id.idShopWed:
                if (checked) {
                    day = "Wednesday";
                    initializeHashMap();
                }
                break;
            case R.id.idShopThu:
                if (checked) {
                    day = "Thursday";
                    initializeHashMap();
                }
                break;
            case R.id.idShopFri:
                if (checked) {
                    day = "Friday";
                    initializeHashMap();
                }
                break;
            case R.id.idShopSat:
                if (checked) {
                    day = "Saturday";
                    initializeHashMap();
                }
                break;
            case R.id.idShopSun:
                if (checked) {
                    day = "Sunday";
                    initializeHashMap();
                }
                break;
        }
    }

    public void onRadioButtonClickedOC(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.idShopOpening:
                if (checked) {
                    changeVisOpening();
                    is_open = true;
                    changeVisOpening();
                }
                break;
            case R.id.idShopClosing:
                if (checked) {
                    is_open = false;
                    changeVisClosing();
                }
                break;
        }
    }

    private void changeVisDay () {
        text_oc.setVisibility(View.VISIBLE);
        text_update.setVisibility(View.INVISIBLE);
        picker_opening.setVisibility(View.VISIBLE);
        picker_closing.setVisibility(View.INVISIBLE);
        text_select.setVisibility(View.INVISIBLE);
    }

    private void changeVisOpening () {
        picker_opening.setEnabled(true);
        picker_closing.setEnabled(true);
        picker_opening.setVisibility(View.VISIBLE);
        picker_closing.setVisibility(View.INVISIBLE);
        text_select.setVisibility(View.VISIBLE);
        text_oc.setVisibility(View.INVISIBLE);
    }

    private void changeVisClosing () {
        picker_opening.setEnabled(true);
        picker_closing.setEnabled(true);
        picker_opening.setVisibility(View.INVISIBLE);
        picker_closing.setVisibility(View.VISIBLE);
        text_select.setVisibility(View.VISIBLE);
        text_oc.setVisibility(View.INVISIBLE);
    }

    private void initializeHashMap () {
        if (!schedule.containsKey(day)) {
            Schedule time = new Schedule(picker_opening.getCurrentHour(), picker_opening.getCurrentMinute(),
                    picker_closing.getCurrentHour(), picker_closing.getCurrentMinute());
            schedule.put(day, time);
        }
    }
}