package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener{

    RequestQueue queue;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ListView listView = findViewById(R.id.custom_list_view);

        fab = findViewById(R.id.writing_fab);
        fab.setOnClickListener(this);

        DBTest helper = new DBTest(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_test",null);

        ArrayList<PostVO> datas = new ArrayList<>();

       while(cursor.moveToNext()){
            PostVO vo = new PostVO();
            vo.title = cursor.getString(1);
            vo.time = cursor.getString(2);
            vo.name = cursor.getString(3);
            datas.add(vo);
        }
        db.close();

        PostAdapter adapter = new PostAdapter(this,R.layout.custom_item,datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStop(){
        super.onStop();
        //??refresh
    }

    @Override
    public void onClick(View v){
        if(v == fab){
            Intent intent = new Intent(DeliveryActivity.this,WritingActivity.class);
            startActivity(intent);
        }
    }
}
