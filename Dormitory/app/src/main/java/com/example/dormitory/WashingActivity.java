package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WashingActivity extends AppCompatActivity implements View.OnClickListener{

    TextView usingWasherTextView;
    TextView usingDryerTextView;
    Button washerButton;
    Button dryerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing);
        washerButton = findViewById(R.id.washerBtn);
        dryerButton  = findViewById(R.id.dryerBtn);


        washerButton.setOnClickListener(this);
        dryerButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v == washerButton){
            Intent intent=new Intent(this, WasherActivity.class);
            //몇동인지 정보가 넘어가야할듯
            startActivity(intent);
        } else if(v == dryerButton){
            Intent intent=new Intent(this, DryerActivity.class);
            //몇동인지 정보가 넘어가야할듯
            startActivity(intent);
        }
    }
}
