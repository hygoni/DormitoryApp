package com.example.dormitory;

import android.view.View;
import android.widget.TextView;

public class CommentHolder {
    TextView comment_name;
    TextView comment_time;
    TextView comment_content;

    public CommentHolder(View root){
        comment_name = root.findViewById(R.id.comment_name);
        comment_time = root.findViewById(R.id.comment_time);
        comment_content = root.findViewById(R.id.comment_content);
    }
}
