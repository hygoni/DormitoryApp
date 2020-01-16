package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class DormActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1;
    Button btn2;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm);

        actionBar = getSupportActionBar();
        actionBar.setTitle("입사 및 퇴사");

        btn1 = findViewById(R.id.dorm_btn1);
        btn2 = findViewById(R.id.dorm_btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btn1){
            Intent intent = new Intent(DormActivity.this,EnteringActivity.class);
            DormActivity.this.startActivity(intent);

        }else if(v==btn2){
            Intent intent = new Intent(DormActivity.this,LeavingActivity.class);
            DormActivity.this.startActivity(intent);

        }
    }
}
