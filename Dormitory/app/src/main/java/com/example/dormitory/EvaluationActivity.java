package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    ActionBar actionBar;
    AlertDialog dialog;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        actionBar = getSupportActionBar();
        actionBar.setTitle("메뉴평가");

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
                if(editText.getText().toString().equals("")){
                    dialog = builder.setMessage("빈칸을 채워주세요. ").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }else {
                    dialog = builder.setMessage("제출이 완료되었습니다.\n소중한 의견 감사합니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).create();
                    dialog.show();
                }
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
