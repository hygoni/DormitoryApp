package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DryerActivity extends AppCompatActivity{

    ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();
    JSONArray jsonArray ;
    ActionBar actionBar;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dryer);

        actionBar = getSupportActionBar();
        actionBar.setTitle("건조기 현황");


        Intent intent= getIntent();
        String jsonArrayList = intent.getStringExtra("dryerInfo");
        token = intent.getStringExtra("token");

        try{
            jsonArray = new JSONArray(jsonArrayList);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject dryer = jsonArray.getJSONObject(i);
                jsonObjectArrayList.add(dryer);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        for(int i = 0; i< jsonObjectArrayList.size(); i++){
            for(int j = i+1; j< jsonObjectArrayList.size(); j++){
                JSONObject temp1 = jsonObjectArrayList.get(i);
                JSONObject temp2 = jsonObjectArrayList.get(j);
                try{
                    if(temp1.getInt("sub_id")>temp2.getInt("sub_id")){
                        jsonObjectArrayList.set(i,temp2);
                        jsonObjectArrayList.set(j,temp1);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        ArrayList<MachineVO> datas = new ArrayList<>();
        for(int i=0;i<jsonObjectArrayList.size();i++){
            MachineVO vo = new MachineVO();
            vo.jsonObject = jsonObjectArrayList.get(i);
            vo.token = token;
            datas.add(vo);
        }
        ListView listView = findViewById(R.id.custom_list2_view);
        MachineAdapter adapter = new MachineAdapter(this ,R.layout.custom_item2, datas);
        listView.setAdapter(adapter);
    }
}
