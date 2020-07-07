package com.example.covidhelper;

import android.os.Bundle;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ShopUpdateSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_update_schedule);

        TimePicker picker = findViewById(R.id.idShopTimePicker);
        picker.setIs24HourView(true);
    }


}