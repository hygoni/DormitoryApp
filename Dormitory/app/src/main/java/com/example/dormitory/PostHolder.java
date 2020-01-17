package com.example.dormitory;

import android.view.View;
import android.widget.TextView;

public class PostHolder {
    public TextView titleView;
    public TextView timeView;
    public TextView shiftView;
    public TextView nameView;

    public PostHolder(View root){
        titleView = root.findViewById(R.id.post_title);
        timeView = root.findViewById(R.id.post_time);
        shiftView = root.findViewById(R.id.post_shift);
        nameView = root.findViewById(R.id.post_name);
    }
}
