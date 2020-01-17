package com.example.dormitory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    long initTime;    //종료할 때 두 번 입력받는 기능을 구현하기 위해 시간을 측정하기 위한 변수
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    TextView userView;
    //버튼 선언
    ImageButton cafeteriaBtn;
    ImageButton deliveryBtn;
    ImageButton washingBtn;
    ImageButton busBtn;
    ImageButton dormBtn;
    ImageButton noticeBtn;

    String token;
    RequestQueue queue;

    String userName;
    int buildingNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        //버튼 지정 ,setOnClickListener 까지 설정
        cafeteriaBtn = findViewById(R.id.cafeteria_btn);
        deliveryBtn = findViewById(R.id.delivery_btn);
        washingBtn = findViewById(R.id.washing_btn);
        busBtn = findViewById(R.id.bus_btn);
        dormBtn = findViewById(R.id.dorm_btn);
        noticeBtn = findViewById(R.id.notice_btn);
        userView = findViewById(R.id.userTextView);

        cafeteriaBtn.setOnClickListener(this);
        deliveryBtn.setOnClickListener(this);
        washingBtn.setOnClickListener(this);
        busBtn.setOnClickListener(this);
        dormBtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);

        token = intent.getStringExtra("token");

        queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "http://cnuant.iptime.org:8000/getUser", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    userName = response.getString("uid");
                    buildingNumber = response.getInt("building_number");
                    userView.setText(userName);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("송수신 오류");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-AUTH-TOKEN", token);
                return headers;
            }
        };

        queue.add(jsonRequest);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer=findViewById(R.id.main_drawer);
        toggle=new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.main_drawer_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.menu_drawer_home){
                    showToast("NavigationDrawer... home...");
                }else if(id==R.id.menu_drawer_message){
                    showToast("NavigationDrawer... message...");
                }else if(id==R.id.menu_drawer_add){
                    showToast("NavigationDrawer... add...");
                }
                return false;
            }
        });
    }

    private void showToast(String message){
        Toast toast=Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View v){
        if(v==noticeBtn){
            Intent intent=new Intent(this, NoticeActivity.class);
            startActivity(intent);
        }else if(v==dormBtn){
            Intent intent=new Intent(this, DormActivity.class);
            startActivity(intent);
        }
        else if(v==busBtn){
            Intent intent=new Intent(this, BusActivity.class);
            startActivity(intent);
        }
        else if(v==cafeteriaBtn){
            Intent intent=new Intent(this, CafeteriaActivity.class);
            startActivity(intent);
        }else if(v==washingBtn){
            Intent intent=new Intent(this, WashingActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("building_number",Integer.toString(buildingNumber));
            startActivity(intent);
        }else if(v==deliveryBtn){
            Intent intent=new Intent(this, DeliveryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                showToast("종료하려면 한 번 더 누르세요.");
                initTime = System.currentTimeMillis();
            }
            else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
