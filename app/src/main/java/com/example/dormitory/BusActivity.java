package com.example.dormitory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class BusActivity extends AppCompatActivity implements View.OnClickListener{

    Calendar cal = Calendar.getInstance();
    TextView bus_A_text1;
    TextView bus_A_text2;
    TextView bus_A_text3;
    TextView bus_A_text4;
    TextView bus_A_text5;
    TextView bus_B_text1;
    TextView bus_B_text2;
    TextView bus_B_text3;
    TextView bus_B_text4;
    TextView bus_B_text5;

    Button resetButton;
    Button image_A_Btn;
    Button image_B_Btn;
    AlertDialog customDialog;

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
    int currentSec = cal.get(Calendar.SECOND);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        bus_A_text1 = findViewById(R.id.A_bus_text1);
        bus_A_text2 = findViewById(R.id.A_bus_text2);
        bus_A_text3 = findViewById(R.id.A_bus_text3);
        bus_A_text4 = findViewById(R.id.A_bus_text4);
        bus_A_text5 = findViewById(R.id.A_bus_text5);

        bus_B_text1 = findViewById(R.id.B_bus_text1);
        bus_B_text2 = findViewById(R.id.B_bus_text2);
        bus_B_text3 = findViewById(R.id.B_bus_text3);
        bus_B_text4 = findViewById(R.id.B_bus_text4);
        bus_B_text5 = findViewById(R.id.B_bus_text5);

        resetButton = findViewById(R.id.resetBtn);
        image_A_Btn = findViewById(R.id.bus_A_Image_Btn);
        image_B_Btn = findViewById(R.id.bus_B_Image_Btn);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursorA = db.rawQuery("select * from tb_bus where type='A'", null);
        Cursor cursorB = db.rawQuery("select * from tb_bus where type='B'", null);

        while (cursorA.moveToNext()) {
            TimeTableVO vo = new TimeTableVO();
            vo.hour = cursorA.getString(2);
            vo.min = cursorA.getString(3);
            bus_A.add(vo);
        }
        while (cursorB.moveToNext()) {
            TimeTableVO vo = new TimeTableVO();
            vo.hour = cursorB.getString(2);
            vo.min = cursorB.getString(3);
            bus_B.add(vo);
        }
        db.close();

        TimerTask timerTask1 = new TimerTask() {    //60초 간격 마다 1분씩 증가해줌
            @Override
            public void run() {
              currentMin++;
              if(currentMin>=60){
                  currentMin = currentMin%60;
                  currentHour++;
              }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask1,60000-currentSec*1000,60000);

        busA_getTime();
        busB_getTime();
        resetButton.setOnClickListener(this);
        image_A_Btn.setOnClickListener(this);
        image_B_Btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v==resetButton){
            busA_getTime();
            busB_getTime();
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view;
            if(v==image_A_Btn){
                 view = inflater.inflate(R.layout.bus_a_image_layout,null);
                 builder.setView(view);
            }
            else if(v == image_B_Btn){
                view = inflater.inflate(R.layout.bus_b_image_layout,null);
                builder.setView(view);
            }
            builder.setPositiveButton("확인",dialogListener);
            customDialog = builder.create();
            customDialog.show();
        }
    }
    private void busA_getTime(){
        if((bus_start_hour>currentHour||bus_finish_hour<currentHour)||(currentHour==bus_start_hour&&currentMin<A_bus_start_min)||(currentHour==bus_finish_hour&&currentMin>A_bus_finish_min)){
            bus_A_text1.setText("지금은 운행이 되지 않는 시간입니다.");
        }
        else{
            int currentTemp = currentHour*60+currentMin;
            for(int i=0;i<bus_A.size();i++){
                int timeTemp =Integer.parseInt(bus_A.get(i).hour)*60+Integer.parseInt(bus_A.get(i).min);
                if(currentTemp<=timeTemp){
                    StringBuilder temp = new StringBuilder(Integer.toString(timeTemp-currentTemp));
                    temp.append(" 분 남았습니다.");
                    bus_A_text1.setText(temp.toString());
                    if(currentTemp==timeTemp){
                        bus_A_text1.setText("곧 도착 할 예정입니다.");
                    }
                    ArrayList<String> save = new ArrayList<>();
                    int max = 3;
                    for(int j=i;j<bus_A.size()&&max>0;j++){
                        save.add(bus_A.get(j).hour+" 시 "+bus_A.get(j).min+" 분");
                        max --;
                    }
                    for(int j=0;j<save.size();j++){
                        if(j==0){
                            bus_A_text3.setText(save.get(0));
                        }else if(j==1){
                            bus_A_text4.setText(save.get(1));
                        }else if(j==2){
                            bus_A_text5.setText(save.get(2));
                        }
                    }
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
                if(currentTemp<=timeTemp) {
                    StringBuilder temp = new StringBuilder(Integer.toString(timeTemp - currentTemp));
                    temp.append(" 분 남았습니다.");
                    bus_B_text1.setText(temp.toString());
                    if (currentTemp == timeTemp) {
                        bus_B_text1.setText("곧 도착 할 예정입니다.");
                    }
                    ArrayList<String> save = new ArrayList<>();
                    int max = 3;
                    for (int j = i; j < bus_B.size() && max > 0; j++) {
                        save.add(bus_B.get(j).hour + " 시 " + bus_B.get(j).min + " 분");
                        max--;
                    }
                    for (int j = 0; j < save.size(); j++) {
                        if (j == 0) {
                            bus_B_text3.setText(save.get(0));
                        } else if (j == 1) {
                            bus_B_text4.setText(save.get(1));
                        } else if (j == 2) {
                            bus_B_text5.setText(save.get(2));
                        }
                    }
                    return;
                }
            }
        }
    }
    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
