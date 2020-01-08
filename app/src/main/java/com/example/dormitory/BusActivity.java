package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class BusActivity extends AppCompatActivity {

    Calendar cal = Calendar.getInstance();
    TextView bus_A_text1;
    TextView bus_A_text2;
    TextView bus_B_text1;
    TextView bus_B_text2;

    ArrayList<TimeTableVO> bus_A= new ArrayList<>();
    ArrayList<TimeTableVO> bus_B= new ArrayList<>();

    final int bus_start_hour = 8;
    final int bus_finish_hour = 18;
    final int A_bus_start_min = 20;
    final int A_bus_finish_min = 0;
    final int B_bus_start_min = 40;
    final int B_bus_finish_min = 15;

    int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    int currentMin = cal.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        bus_A_text1 = (TextView)findViewById(R.id.A_bus_text1);
        bus_A_text2 = (TextView)findViewById(R.id.A_bus_text2);
        bus_B_text1 = (TextView)findViewById(R.id.B_bus_text1);
        bus_B_text2 = (TextView)findViewById(R.id.B_bus_text2);
        Button resetButton = findViewById(R.id.resetBtn);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursorA = db.rawQuery("select * from tb_bus where type='A'",null);
        Cursor cursorB = db.rawQuery("select * from tb_bus where type='B'",null);

        while(cursorA.moveToNext()){
            TimeTableVO vo = new TimeTableVO();
            vo.hour = cursorA.getString(2);
            vo.min = cursorA.getString(3);
            bus_A.add(vo);
        }
        while(cursorB.moveToNext()){
            TimeTableVO vo = new TimeTableVO();
            vo.hour = cursorB.getString(2);
            vo.min = cursorB.getString(3);
            bus_B.add(vo);
        }
        db.close();

        busA_getTime();
        busB_getTime();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour = cal.get(Calendar.HOUR_OF_DAY);
                currentMin = cal.get(Calendar.MINUTE);
                busA_getTime();
                busB_getTime();
            }
        });
    }
    private void busA_getTime(){
        if((bus_start_hour>currentHour||bus_finish_hour<currentHour)||(currentHour==bus_start_hour&&currentMin<A_bus_start_min)||(currentHour==bus_finish_hour&&currentMin>A_bus_finish_min)){
            bus_A_text1.setText("지금은 운행이 되지 않는 시간입니다.");
        }
        else{
            int currentTemp = currentHour*60+currentMin;
            for(int i=0;i<bus_A.size();i++){
                int timeTemp =Integer.parseInt(bus_A.get(i).hour)*60+Integer.parseInt(bus_A.get(i).min);
                if(currentTemp<timeTemp){
                    StringBuilder temp = new StringBuilder(Integer.toString(timeTemp-currentTemp));
                    temp.append(" 분 남았습니다.");
                    bus_A_text1.setText(temp.toString());
                    return;
                }
            }
        }
    }
    private void busB_getTime(){
        if((bus_start_hour>currentHour||bus_finish_hour<currentHour)||(currentHour==bus_start_hour&&currentMin<B_bus_start_min)||(currentHour==bus_finish_hour&&currentMin>B_bus_finish_min)){
            bus_B_text1.setText("지금은 운행이 되지 않는 시간입니다.");
        }
        else{
            int currentTemp = currentHour*60+currentMin;
            for(int i=0;i<bus_B.size();i++){
                int timeTemp =Integer.parseInt(bus_B.get(i).hour)*60+Integer.parseInt(bus_B.get(i).min);
                if(currentTemp<timeTemp){
                    StringBuilder temp = new StringBuilder(Integer.toString(timeTemp-currentTemp));
                    temp.append(" 분 남았습니다.");
                    bus_B_text1.setText(temp.toString());
                    return;
                }
            }
        }
    }
}
