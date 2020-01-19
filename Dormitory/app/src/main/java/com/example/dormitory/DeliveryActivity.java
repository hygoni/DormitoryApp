package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener{

    RequestQueue queue;
    FloatingActionButton fab;
    String token;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        final ListView listView = findViewById(R.id.custom_list_view);
        actionBar = getSupportActionBar();
        actionBar.setTitle("배달음식 게시판");

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        fab = findViewById(R.id.writing_fab);
        fab.setOnClickListener(this);
        final ArrayList<PostVO> datas = new ArrayList<>();
        final ArrayList<JSONObject> jsonArray = new ArrayList<>();

        queue = Volley.newRequestQueue(DeliveryActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://cnuant.iptime.org:8000/boards", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i=0;i<response.length();i++){
                        jsonArray.add((JSONObject)response.get(i));
                    }
                    for(int i=jsonArray.size()-1;i>=0;i--){
                        PostVO vo = new PostVO();
                        vo.article_id = jsonArray.get(i).getInt("id");
                        vo.title = jsonArray.get(i).getString("subject");
                        vo.name = jsonArray.get(i).getString("author");
                        vo.content = jsonArray.get(i).getString("content");
                        vo.category = jsonArray.get(i).getString("category");
                        vo.token = token;
                        long time = jsonArray.get(i).getLong("created_at")*1000;
                        Calendar cal = Calendar.getInstance();
                        long nowTime = cal.getTimeInMillis();
                        Date date = new Date(time);
                        Date nowDate = new Date(nowTime);
                        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd");
                        if(nowTime - time < 3600000 &&timeFormat.format(date).equals(timeFormat.format(nowDate))){
                            vo.time = Long.toString((nowTime - time)/60000)+"분 전";
                        }else if(nowTime - time < 86400000 &&timeFormat.format(date).equals(timeFormat.format(nowDate))){
                            SimpleDateFormat timeFormat2 = new SimpleDateFormat("HH:mm");
                            vo.time =  timeFormat2.format(date);
                        }else{
                            vo.time =  timeFormat.format(date);
                        }
                        datas.add(vo);
                    }
                    PostAdapter adapter = new PostAdapter(DeliveryActivity.this,R.layout.custom_item,datas);
                    listView.setAdapter(adapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast t = Toast.makeText(DeliveryActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
                t.show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-AUTH-TOKEN", token);
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View v){
        if(v == fab){
            Intent intent = new Intent(DeliveryActivity.this,WritingActivity.class);
            intent.putExtra("token", token);
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
