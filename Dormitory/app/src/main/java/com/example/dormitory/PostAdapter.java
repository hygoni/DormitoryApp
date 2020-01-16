package com.example.dormitory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<PostVO> {
    Context context;
    int resId;
    ArrayList<PostVO> datas;

    public PostAdapter(Context context, int resId, ArrayList<PostVO> datas){
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
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resId, null);
            PostHolder holder=new PostHolder(convertView);
            convertView.setTag(holder);
        }
        PostHolder holder = (PostHolder)convertView.getTag();

        final TextView titleView = holder.titleView;
        final TextView timeView = holder.timeView;
        final TextView shiftView = holder.shiftView;
        final TextView nameView = holder.nameView;

        final PostVO vo = datas.get(position);

        titleView.setText(vo.title);
        timeView.setText(vo.time);
        shiftView.setText(" | ");
        nameView.setText(vo.name);

        RelativeLayout postArea = convertView.findViewById(R.id.posting);
        postArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(v.getContext(),titleView.getText().toString(),Toast.LENGTH_SHORT);
                t.show();
            }
        });

        return convertView;
    }
}
