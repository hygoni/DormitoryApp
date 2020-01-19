package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WritingActivity extends AppCompatActivity  implements View.OnClickListener{
    EditText titleView;
    EditText contentView;
    Spinner spinner;
    Button addBtn;
    String token;

    RequestQueue queue;
    AlertDialog dialog;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        actionBar = getSupportActionBar();
        actionBar.setTitle("게시판 글쓰기");
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        spinner = (Spinner) findViewById(R.id.menuCategorySpinner);

        ArrayList<String> items = new ArrayList<>();
        items.add("한식");
        items.add("중식");
        items.add("일식");
        items.add("분식");
        items.add("치킨");
        items.add("피자");
        items.add("패스트푸드");
        items.add("기타");
        items.add("메뉴 카테고리를 결정해주세요.");

        MySpinnerAdapter adapter = new MySpinnerAdapter(this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());

        titleView = findViewById(R.id.add_title);
        contentView = findViewById(R.id.add_content);
        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        String title = titleView.getText().toString();
        String content = contentView.getText().toString();
        String category = spinner.getSelectedItem().toString();

        if(title.equals("")||content.equals("")||category.equals("메뉴 카테고리를 결정해주세요.")){
            AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
            dialog = builder.setMessage("빈 칸 없이 입력해주세요.").setNegativeButton("확인", null).create();
            dialog.show();
            return;
        }
        JSONObject requestJsonObject = new JSONObject();
        try{
            requestJsonObject.put("title",title);
            requestJsonObject.put("content",content);
            requestJsonObject.put("category",category);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        queue= Volley.newRequestQueue(WritingActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://cnuant.iptime.org:8000/writeArticle", requestJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
                    dialog = builder.setMessage("글쓰기에 성공했습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create();
                    dialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast t = Toast.makeText(WritingActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
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
        queue.add(jsonObjectRequest);
    }
}

class MySpinnerAdapter extends ArrayAdapter<String>{
    public MySpinnerAdapter(Context context, int res, List<String> objects){
        super(context,res,objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = super.getView(position,convertView,parent);
        if(position==getCount()){
            ((TextView)v.findViewById(android.R.id.text1)).setText("");
            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
        }
        return v;
    }
    public int getCount(){
        return super.getCount()-1;
    }
}