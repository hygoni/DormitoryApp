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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.Locale;

public class WasherActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Button> btnArray = new ArrayList<>();

    JSONArray jsonArray ;

    Button testBtn ;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer);
        //버튼을 여러개 만들어 놓고 43

        actionBar = getSupportActionBar();
        actionBar.setTitle("세탁기 현황");

        Intent intent= getIntent();
        String jsonArrayList = intent.getStringExtra("washerInfo");
        try{
            jsonArray = new JSONArray(jsonArrayList);
            for(int i=0;i<jsonArray.length();i++){
            JSONObject washer = jsonArray.getJSONObject(i);
                if(washer.getString("washer_number")==Integer.toString(i)){
                    if(washer.getString("work")=="true"){

                    }else if(washer.getString("work")=="false"){

                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        testBtn = findViewById(R.id.washer1_turnOnBtn);
        testBtn.setOnClickListener(this);

    }

    //클릭 후 서버에 종료시간과 세탁기 번호 보낼거임
    @Override
    public void onClick(View v){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,1);
        //calendar.add(Calendar.MINUTE,43);
        Date currentDateTime = calendar.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 a EE요일 hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
        Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
        diaryNotification(calendar);
    }

    void diaryNotification(Calendar calendar)
    {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }
}
