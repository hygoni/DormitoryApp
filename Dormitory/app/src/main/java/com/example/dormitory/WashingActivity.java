package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class WashingActivity extends AppCompatActivity implements View.OnClickListener{

    int washerCount = 0;
    int workingWasherCount = 0;
    int dryerCount =0;
    int workingDryerCount =0;

    TextView usingWasherTextView;
    TextView usingDryerTextView;
    Button washerButton;
    Button dryerButton;

    JSONArray nowBuildingWasherArray = new JSONArray();
    JSONArray nowBuildingDryerArray = new JSONArray();

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing);

        washerButton = findViewById(R.id.washerBtn);
        dryerButton  = findViewById(R.id.dryerBtn);
        usingDryerTextView = findViewById(R.id.usingDryer);
        usingWasherTextView = findViewById(R.id.usingWasher);

        washerButton.setOnClickListener(this);
        dryerButton.setOnClickListener(this);

        queue = Volley.newRequestQueue(WashingActivity.this);
        JsonArrayRequest washerArrayRequest = new JsonArrayRequest(Request.Method.GET, "washing", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject washer = response.getJSONObject(i);
                        if(washer.getString("building_number")=="intent에 담긴 동 정보"){
                            nowBuildingWasherArray.put(washer);
                            washerCount++;
                            if(washer.getString("work")=="true"){
                                workingWasherCount++;
                            }
                        }
                    }
                    usingWasherTextView.setText("현재 작동 중인 세탁기는"+Integer.toString(workingWasherCount)+" 대 입니다.");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast t = Toast.makeText(WashingActivity.this,"송수신 문제",Toast.LENGTH_SHORT);
                t.show();
            }
        });

        queue.add(washerArrayRequest);
        JsonArrayRequest dryerArrayRequest = new JsonArrayRequest(Request.Method.GET, "drying", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject dryer = response.getJSONObject(i);
                        if(dryer.getString("building_number")=="intent에 담긴 동 정보"){
                            nowBuildingDryerArray.put(dryer);
                            dryerCount++;
                            if(dryer.getString("work")=="true"){
                                workingDryerCount++;
                            }
                        }
                    }
                    usingDryerTextView.setText("현재 작동 중인 세탁기는"+Integer.toString(workingDryerCount)+" 대 입니다.");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast t = Toast.makeText(WashingActivity.this,"송수신 문제",Toast.LENGTH_SHORT);
                t.show();
            }
        });
        queue.add(dryerArrayRequest);
    }
    @Override
    public void onClick(View v){
        if(v == washerButton){
            Intent intent=new Intent(this, WasherActivity.class);

            String washerInfo = nowBuildingWasherArray.toString();
            intent.putExtra("washerInfo",washerInfo);

            startActivity(intent);
        } else if(v == dryerButton){
            Intent intent=new Intent(this, DryerActivity.class);

            String dryerInfo = nowBuildingDryerArray.toString();
            intent.putExtra("dryerInfo",dryerInfo);

            startActivity(intent);
        }
    }
}
