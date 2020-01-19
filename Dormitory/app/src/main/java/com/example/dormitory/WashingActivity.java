package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WashingActivity extends AppCompatActivity implements View.OnClickListener{
    String token;
    String userBuildingNumber;

    TextView userBuildingInfo;

    TextView usingWasherTextView;
    TextView usingDryerTextView;
    Button washerButton;
    Button dryerButton;

    JSONArray nowBuildingWasherArray = new JSONArray();
    JSONArray nowBuildingDryerArray = new JSONArray();

    RequestQueue queue;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washing);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        userBuildingNumber = intent.getStringExtra("building_number");

        actionBar = getSupportActionBar();
        actionBar.setTitle("세탁기/건조기");

        washerButton = findViewById(R.id.washerBtn);
        dryerButton  = findViewById(R.id.dryerBtn);

        usingDryerTextView = findViewById(R.id.usingDryer);
        usingWasherTextView = findViewById(R.id.usingWasher);
        userBuildingInfo = findViewById(R.id.washingUserInfo);

        userBuildingInfo.setText(userBuildingNumber+"동 세탁기/건조기");

        washerButton.setOnClickListener(this);
        dryerButton.setOnClickListener(this);

        queue = Volley.newRequestQueue(WashingActivity.this);
        JsonArrayRequest washerArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://cnuant.iptime.org:8000/getWashers", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int washerCount = 0;
                int workingWasherCount = 0;
                int dryerCount = 0;
                int workingDryerCount = 0;
                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject machine = response.getJSONObject(i);
                        if(machine.getString("display_name").equals("washer")&&machine.getInt("building_number")== Integer.parseInt(userBuildingNumber) ){
                            nowBuildingWasherArray.put(machine);
                            washerCount++;
                            if(machine.getBoolean("is_working")){
                                workingWasherCount++;
                            }
                        }
                        if(machine.getString("display_name").equals("dryer")&&machine.getInt("building_number")== Integer.parseInt(userBuildingNumber)){
                            nowBuildingDryerArray.put(machine);
                            dryerCount++;
                            if(machine.getBoolean("is_working")){
                                workingDryerCount++;
                            }
                        }
                    }
                    usingWasherTextView.setText("총 세탁기의 수는 "+ washerCount+"대 입니다.\n현재 작동 중인 세탁기는 "+workingWasherCount+"대 입니다.");
                    usingDryerTextView.setText("총 건조기의 수는 "+ dryerCount+"대 입니다.\n현재 작동 중인 건조기는 "+workingDryerCount+"대 입니다.");
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-AUTH-TOKEN", token);
                return headers;
            }
        };
        queue.add(washerArrayRequest);
    }

    @Override
    public void onClick(View v){
        if(v == washerButton){
            Intent intent=new Intent(this, WasherActivity.class);

            String washerInfo = nowBuildingWasherArray.toString();
            intent.putExtra("washerInfo",washerInfo);
            intent.putExtra("token",token);
            intent.putExtra("building_number",userBuildingNumber);

            startActivity(intent);
        } else if(v == dryerButton){
            Intent intent=new Intent(this, DryerActivity.class);

            String dryerInfo = nowBuildingDryerArray.toString();
            intent.putExtra("dryerInfo",dryerInfo);
            intent.putExtra("token",token);
            intent.putExtra("building_number",userBuildingNumber);

            startActivity(intent);
        }
    }
    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
