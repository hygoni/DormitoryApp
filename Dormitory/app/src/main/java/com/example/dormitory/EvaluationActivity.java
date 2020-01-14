package com.example.dormitory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class EvaluationActivity extends AppCompatActivity {

    private RatingBar ratingbar;
    TextView evaluationMenu;

    String menuType;
    String contents;

    EditText editText;

    AlertDialog dialog;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        Intent intent = getIntent();


        evaluationMenu= findViewById(R.id.evaluationMenu);
        ratingbar = findViewById(R.id.ratingBar);
        editText = findViewById(R.id.menuEvaluationContents);
        submitBtn = findViewById(R.id.evaluationSubmitBtn);

        menuType = intent.getExtras().getString("menuType");
        evaluationMenu.setText(menuType);

        ratingbar.setOnRatingBarChangeListener(new Listener());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder builder = new AlertDialog.Builder(EvaluationActivity.this);
            @Override
            public void onClick(View v) {
                dialog = builder.setMessage("제출이 완료되었습니다.\n소중한 의견 감사합니다.").setPositiveButton("확인",null).create();
                dialog.show();
            }
        });
    }

    class Listener implements RatingBar.OnRatingBarChangeListener
    {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        }
    }
}