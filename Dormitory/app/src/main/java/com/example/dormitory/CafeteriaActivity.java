package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Calendar;

public class CafeteriaActivity extends AppCompatActivity {
    Calendar cal = Calendar.getInstance();
    int currentMonth = cal.get(Calendar.MONTH)+1;
    int currentDate = cal.get(Calendar.DATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria);
    }
}
