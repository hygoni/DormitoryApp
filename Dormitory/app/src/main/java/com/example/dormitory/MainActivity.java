package com.example.dormitory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    long initTime;    //종료할 때 두 번 입력받는 기능을 구현하기 위해 시간을 측정하기 위한 변수
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    //버튼 선언
    ImageButton cafeteriaBtn;
    ImageButton deliveryBtn;
    ImageButton washingBtn;
    ImageButton busBtn;
    ImageButton dormBtn;
    ImageButton noticeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 지정 ,setOnClickListener 까지 설정
        cafeteriaBtn = findViewById(R.id.cafeteria_btn);
        deliveryBtn = findViewById(R.id.delivery_btn);
        washingBtn = findViewById(R.id.washing_btn);
        busBtn = findViewById(R.id.bus_btn);
        dormBtn = findViewById(R.id.dorm_btn);
        noticeBtn = findViewById(R.id.notice_btn);

        cafeteriaBtn.setOnClickListener(this);
        deliveryBtn.setOnClickListener(this);
        washingBtn.setOnClickListener(this);
        busBtn.setOnClickListener(this);
        dormBtn.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);

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
            //몇동인지 정보가 넘어가야할듯
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
