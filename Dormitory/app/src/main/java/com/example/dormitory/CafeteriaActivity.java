package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;

public class CafeteriaActivity extends AppCompatActivity implements View.OnClickListener{
    Calendar cal = Calendar.getInstance();
    int  day = cal.get(Calendar.DAY_OF_WEEK);

    public  String[] dateArray=new String[7];
    public  String[][] breakfastArray=new String[7][2];
    public  String[][] lunchArray=new String[7][2];
    public  String[] dinnerArray=new String[7];

    String todayOfWeek = dayOfWeek();

    String date;
    String breakfastA;
    String breakfastC;
    String lunchA;
    String lunchB;
    String dinner;

    TextView todayMenu;
    TextView breakfastA_View;
    TextView breakfastC_View;
    TextView lunchA_View;
    TextView lunchB_View;
    TextView dinner_View;

    AlertDialog dialog;

    Button evaluationBtn1;
    Button evaluationBtn2;
    Button evaluationBtn3;
    Button evaluationBtn4;
    Button evaluationBtn5;

    ActionBar actionBar;

    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria);

        actionBar  = getSupportActionBar();
        actionBar.setTitle("기숙사 식당");

        todayMenu= findViewById(R.id.todayMenu);
        breakfastA_View = findViewById(R.id.BreakfastA);
        breakfastC_View = findViewById(R.id.BreakfastC);
        lunchA_View = findViewById(R.id.lunchA);
        lunchB_View = findViewById(R.id.lunchB);
        dinner_View = findViewById(R.id.Dinner);

        evaluationBtn1 = findViewById(R.id.cafeteria_evaluation_btn1);
        evaluationBtn2 = findViewById(R.id.cafeteria_evaluation_btn2);
        evaluationBtn3 = findViewById(R.id.cafeteria_evaluation_btn3);
        evaluationBtn4 = findViewById(R.id.cafeteria_evaluation_btn4);
        evaluationBtn5 = findViewById(R.id.cafeteria_evaluation_btn5);

        handler = new Handler();
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                //SubThread이므로 UI작업시 Exception
                try{
                    Document doc = Jsoup.connect("http://dorm.cnu.ac.kr/html/kr/sub03/sub03_0304.html").get();
                    Elements contents=doc.select("tbody tr td");
                    int dayCount=0;
                    int get=3;
                    while(dayCount<=6){
                        dateArray[dayCount]=contents.get(get).ownText();
                        get++;
                        getBreakfast(contents,get,dayCount);
                        get++;
                        getLunch(contents,get,dayCount);
                        get++;
                        getDinner(contents,get,dayCount);
                        get++;
                        dayCount++;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        //Handler - UI 작업 가능!!
                        try {
                            JSONObject menuData = innerPut((day+5)%7);

                            date = menuData.getString("date");
                            breakfastA = menuData.getString("BreakfastA");
                            breakfastC = menuData.getString("BreakfastC");
                            lunchA = menuData.getString("LunchA");
                            dinner = menuData.getString("Dinner");

                            lunchB = null;
                            if(!(todayOfWeek=="Sunday"||todayOfWeek=="Saturday")){
                                lunchB = menuData.getString("LunchB");
                            }
                            todayMenu.setText(date+" 의 메뉴");

                            breakfastA_View.setText("메인 A"+breakfastA.substring(0,breakfastA.indexOf("]")+2)+"\n\n"+parser(breakfastA.substring(breakfastA.indexOf("]")+2)));
                            breakfastC_View.setText("메인 C"+breakfastC.substring(0,breakfastC.indexOf("]")+2)+"\n\n"+parser(breakfastC.substring(breakfastC.indexOf("]")+2)));
                            lunchA_View.setText("메인 A"+lunchA.substring(0,lunchA.indexOf("]")+2)+"\n\n"+parser(lunchA.substring(lunchA.indexOf("]")+2)));
                            if(!(todayOfWeek=="Sunday"||todayOfWeek=="Saturday")){
                                lunchB_View.setText("메인 B"+lunchB.substring(0,lunchB.indexOf("]")+2)+"\n\n"+parser(lunchB.substring(lunchB.indexOf("]")+2)));
                            }
                            dinner_View.setText("메인 A"+dinner.substring(0,dinner.indexOf("]")+2)+"\n\n"+parser(dinner.substring(dinner.indexOf("]")+2)));
                            if(todayOfWeek=="Sunday"||todayOfWeek=="Saturday"){
                                lunchB_View.setVisibility(View.INVISIBLE);
                                evaluationBtn4.setVisibility(View.INVISIBLE);
                                lunchB_View.setHeight(0);
                                evaluationBtn4.setHeight(0);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        t.start();

        breakfastA_View.setOnClickListener(this);
        breakfastC_View.setOnClickListener(this);
        lunchA_View.setOnClickListener(this);
        lunchB_View.setOnClickListener(this);
        dinner_View.setOnClickListener(this);

        evaluationBtn1.setOnClickListener(this);
        evaluationBtn2.setOnClickListener(this);
        evaluationBtn3.setOnClickListener(this);
        evaluationBtn4.setOnClickListener(this);
        evaluationBtn5.setOnClickListener(this);
    }

    private String dayOfWeek(){
        switch(day){
            case 1: return "Sunday";
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
        }
        return null;
    }

    private String parser(String str){
        StringBuilder temp = new StringBuilder();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i) == ' '){
                temp.append('\n');
            }
            else{
                temp.append(str.charAt(i));
            }
        }
        return temp.toString();
    }

    @Override
    public void onClick(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(CafeteriaActivity.this);
        if(v==breakfastA_View){
            dialog = builder.setMessage("매진 되었습니다.").setPositiveButton("확인",null).create();
            dialog.show();
        }else if(v==breakfastC_View){
            dialog = builder.setMessage("매진 되었습니다.").setPositiveButton("확인",null).create();
            dialog.show();
        }else if(v==lunchA_View){
            dialog = builder.setMessage("580/700.\n700끼중 580끼 남았습니다.").setPositiveButton("확인",null).create();
            dialog.show();
        } else if(v==lunchB_View){
            dialog = builder.setMessage("420/800.\n800끼중 420끼 남았습니다.").setPositiveButton("확인",null).create();
            dialog.show();
        } else if(v==dinner_View){
            dialog = builder.setMessage("1000/1000.\n1000끼중 1000끼 남았습니다.").setPositiveButton("확인",null).create();
            dialog.show();
        } else{
            Intent intent = new Intent(CafeteriaActivity.this, EvaluationActivity.class);
            String menuType="";
            String temp= "";
            if(v==evaluationBtn1){
                temp = breakfastA_View.getText().toString().substring(0,breakfastA.indexOf("[")+3);
                menuType = date+" 아침 A"+temp +" 식단";
            } else if(v==evaluationBtn2){
                temp = breakfastC_View.getText().toString().substring(0,breakfastC.indexOf("[")+3);
                menuType = date+" 아침 C"+ temp +" 식단";
            } else if(v==evaluationBtn3){
                temp = lunchA_View.getText().toString().substring(0,lunchA.indexOf("[")+3);
                menuType = date+" 점심 A"+ temp +" 식단";
            } else if(v==evaluationBtn4){
                temp = lunchB_View.getText().toString().substring(0,lunchB.indexOf("[")+4);
                menuType = date+" 점심 B"+ temp +" 식단";
            } else if(v==evaluationBtn5){
                temp = dinner_View.getText().toString().substring(0,dinner.indexOf("[")+3);
                menuType = date+" 저녁 A"+ temp +" 식단";
            }
            intent.putExtra("menuType",menuType);
            CafeteriaActivity.this.startActivity(intent);
        }
    }

    public void getBreakfast(Elements contents, int get, int day){
        String str=contents.get(get).ownText();
        String[] word=str.split("메인A|메인 A|메인B|메인 B|메인 C|메인C");
        breakfastArray[day][0]=word[1];
        breakfastArray[day][1]=word[2];
    }
    public void getLunch(Elements contents, int get, int day){
        Element ele=contents.get(get);
        String str=ele.text();
        String[] word=str.split("메인A|메인 A|메인B|메인 B|메인 C|메인C");
        lunchArray[day][0]=word[1];
        lunchArray[day][1]=word[2];
    }
    public void getDinner(Elements contents, int get, int day){
        String str=contents.get(get).ownText();
        String[] word=str.split("메인A|메인 A|메인B|메인 B|메인 C|메인C");
        dinnerArray[day]=word[1];
    }
    JSONObject innerPut(int i){
        JSONObject inner=new JSONObject();
        try{
            inner.put("date",dateArray[i]);
            inner.put("BreakfastA",breakfastArray[i][0]);
            inner.put("BreakfastC",breakfastArray[i][1]);
            inner.put("LunchA",lunchArray[i][0]);
            inner.put("LunchB",lunchArray[i][1]);
            inner.put("Dinner",dinnerArray[i]);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return inner;
    }
}
