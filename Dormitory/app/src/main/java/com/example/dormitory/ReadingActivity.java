package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReadingActivity extends AppCompatActivity {
    String title;
    String time;
    String name;
    String content;
    String category;
    String token;
    int article_id;

    TextView titleView;
    TextView timeView;
    TextView nameView;
    TextView contentView;
    EditText commentEditText;
    ActionBar actionBar;
    Button commentWriteButton;
    AlertDialog dialog;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        actionBar = getSupportActionBar();
        actionBar.setTitle("글 읽기");
        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        time = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        content = intent.getStringExtra("content");
        category = intent.getStringExtra("category");
        article_id = intent.getIntExtra("article_id",0);
        token = intent.getStringExtra("token");

        commentWriteButton = findViewById(R.id.CommentWriteBtn);
        titleView = findViewById(R.id.ReadingTitle);
        timeView = findViewById(R.id.ReadingTime);
        nameView = findViewById(R.id.ReadingName);
        contentView = findViewById(R.id.ReadingContent);
        commentEditText = findViewById(R.id.CommentWrite);

        queue = Volley.newRequestQueue(ReadingActivity.this);
        final ArrayList<CommentVO> datas = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://cnuant.iptime.org:8000/readComment", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    titleView.setText("["+category+"] "+title);
                    timeView.setText(time);
                    nameView.setText("작성자: "+name);
                    contentView.setText(content);
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject = (JSONObject)response.get(i);
                        if(jsonObject.getInt("article_id")==article_id){
                            CommentVO vo = new CommentVO();
                            vo.content = (String) jsonObject.get("content");
                            vo.name = (String) jsonObject.get("author");
                            vo.token = token;

                            long time = jsonObject.getLong("created_at")*1000;
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
                    }
                    ListView listView = findViewById(R.id.custom_list3_view);
                    CommentAdapter adapter = new CommentAdapter(ReadingActivity.this ,R.layout.custom_item3, datas);
                    listView.setAdapter(adapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.getMessage());
                Toast t = Toast.makeText(ReadingActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
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
        queue.add(jsonArrayRequest);

        commentWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue = Volley.newRequestQueue(ReadingActivity.this);
                JSONObject jsonObject = new JSONObject();
                String str = commentEditText.getText().toString();
                if(str.equals("")){
                    Toast t = Toast.makeText(ReadingActivity.this,"댓글을 달아주세요.",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                try{
                    jsonObject.put("article_id",article_id);
                    jsonObject.put("content",str);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://cnuant.iptime.org:8000/writeComment", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getBoolean("success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReadingActivity.this);
                                dialog = builder.setMessage("댓글이 입력되었습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                }).create();
                                dialog.show();
                            }else{
                                Toast t = Toast.makeText(ReadingActivity.this,"재시도",Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onErrorResponse: "+error.getMessage());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-AUTH-TOKEN", token);
                        return headers;
                    }
                };
                queue.add(jsonObjectRequest);
            }
        });
    }
}
