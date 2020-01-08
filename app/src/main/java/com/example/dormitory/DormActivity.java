package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class DormActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1;
    Button btn2;

    FragmentManager manager;
    OneFragment oneFragment;
    TwoFragment twoFragment;
    TempFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm);

        btn1=findViewById(R.id.dorm_btn1);
        btn2=findViewById(R.id.dorm_btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        manager=getSupportFragmentManager();
        oneFragment=new OneFragment();
        twoFragment=new TwoFragment();
        tempFragment = new TempFragment();
        FragmentTransaction tf=manager.beginTransaction();
        tf.addToBackStack(null);
        tf.add(R.id.main_container, tempFragment);
        tf.commit();
    }

    @Override
    public void onClick(View v) {
        if(v==btn1){
            if(!oneFragment.isVisible()){
                FragmentTransaction tf=manager.beginTransaction();
                tf.addToBackStack(null);
                tf.replace(R.id.main_container, oneFragment);
                tf.commit();
            }
        }else if(v==btn2){
            if(!twoFragment.isVisible()){
                FragmentTransaction tf=manager.beginTransaction();
                tf.addToBackStack(null);
                tf.replace(R.id.main_container, twoFragment);
                tf.commit();
            }
        }
    }
}
