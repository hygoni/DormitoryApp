package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WritingActivity extends AppCompatActivity  implements View.OnClickListener{
    EditText titleView;
    EditText contentView;
    Button addBtn;

    RequestQueue queue;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        titleView = findViewById(R.id.add_title);
        contentView = findViewById(R.id.add_content);
        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        String title = titleView.getText().toString();
        String content = contentView.getText().toString();
        if(title.equals("")||content.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
            dialog = builder.setMessage("빈 칸 없이 입력해주세요.").setNegativeButton("확인", null).create();
            dialog.show();
            return;
        }
        JSONObject requestJsonObject = new JSONObject();
        try{
            requestJsonObject.put("title",title);
            requestJsonObject.put("content",content);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        queue= Volley.newRequestQueue(WritingActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "URL", requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    boolean success = response.getBoolean("success");
                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                        dialog = builder.setMessage("글쓰기에 성공했습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
                        dialog.show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                        dialog = builder.setMessage("글쓰기에 실패했습니다.").setNegativeButton("재시도",null).create();
                        dialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast t = Toast.makeText(WritingActivity.this,"송수신 문제",Toast.LENGTH_SHORT);
                t.show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}