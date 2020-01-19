package com.example.dormitory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CommentAdapter extends ArrayAdapter<CommentVO> {
    Context context;
    int resId;
    ArrayList<CommentVO> datas;
    LayoutInflater inflater;
    String token;
    public CommentAdapter(Context context, int resId, ArrayList<CommentVO> datas){
        super(context,resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }
    @Override
    public int getCount(){
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resId, null);
            CommentHolder holder=new CommentHolder(convertView);
            convertView.setTag(holder);
        }
        CommentHolder holder = (CommentHolder)convertView.getTag();
        final CommentVO vo = datas.get(position);
        token = vo.token;

        final TextView commentNameView = holder.comment_name;
        final TextView commentContentView = holder.comment_content;
        final TextView commentTimeView = holder.comment_time;

        commentNameView.setText(vo.name);
        commentContentView.setText(vo.content);
        commentTimeView.setText(vo.time);

        return convertView;
    }
}
