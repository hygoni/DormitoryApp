package com.example.dormitory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LeavingActivity extends AppCompatActivity implements View.OnClickListener{

    int reqWidth;
    int reqHeight;

    ImageButton bedBtn;
    ImageButton deskBtn;
    ImageButton bathroomBtn;
    ImageButton closetBtn;
    ImageButton refrigeratorBtn;

    Button submissionBtn;

    File filePath;

    ImageView bedImage;
    ImageView deskImage;
    ImageView bathroomImage;
    ImageView closetImage;
    ImageView refrigeratorImage;

    AlertDialog dialog;

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving);

        actionBar = getSupportActionBar();
        actionBar.setTitle("퇴사서류 제출");

        bedBtn = findViewById(R.id.bed_camera_btn);
        deskBtn = findViewById(R.id.desk_camera_btn);
        bathroomBtn = findViewById(R.id.bathroom_camera_btn);
        closetBtn = findViewById(R.id.closet_camera_btn);
        refrigeratorBtn = findViewById(R.id.refrigerator_camera_btn);
        submissionBtn = findViewById(R.id.submissionBtn);

        bedImage = findViewById(R.id.bed_imageView);
        deskImage = findViewById(R.id.desk_imageVIew);
        bathroomImage = findViewById(R.id.bathroom_imageVIew);
        closetImage = findViewById(R.id.closet_imageVIew);
        refrigeratorImage = findViewById(R.id.refrigerator_imageVIew);

        bedBtn.setOnClickListener(this);
        deskBtn.setOnClickListener(this);
        bathroomBtn.setOnClickListener(this);
        closetBtn.setOnClickListener(this);
        refrigeratorBtn.setOnClickListener(this);
        submissionBtn.setOnClickListener(this);

        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);
    }

    @Override
    public void onClick(View v){
        if(v == submissionBtn){
            AlertDialog.Builder builder = new AlertDialog.Builder(LeavingActivity.this);
            dialog = builder.setMessage("제출이 완료되었습니다.\n잠시만 기다려주세요.").setPositiveButton("확인",null).create();
            dialog.show();
        }else{
            int requestCode = 0;
            if(v == bedBtn){
                requestCode = 10;
            }else if(v == deskBtn){
                requestCode = 20;
            }else if(v == bathroomBtn){
                requestCode = 30;
            }else if(v == closetBtn){
                requestCode = 40;
            }else if(v == refrigeratorBtn){
                requestCode = 50;
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                try{
                    String dirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp";
                    File dir=new File(dirPath);
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                    filePath=File.createTempFile("IMG", ".jpg", dir);
                    if(!filePath.exists()){
                        filePath.createNewFile();
                    }
                    Uri phoneURI= FileProvider.getUriForFile(LeavingActivity.this, BuildConfig.APPLICATION_ID+
                            ".provider", filePath);
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, phoneURI);
                    startActivityForResult(intent,requestCode);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(!(requestCode==100) && resultCode==RESULT_OK){
            if(filePath != null){
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inJustDecodeBounds=true;
                try{
                    InputStream in=new FileInputStream(filePath);
                    BitmapFactory.decodeStream(in, null, options);
                    in.close();
                    in=null;
                }catch (Exception e){
                    e.printStackTrace();
                }
                final int height=options.outHeight;
                final int width=options.outWidth;
                int inSampleSize=1;
                if(height>reqHeight || width>reqWidth){
                    final int heightRatio=Math.round((float)height/(float)reqHeight);
                    final int widthRatio=Math.round((float)width/(float)reqWidth);

                    inSampleSize=heightRatio<widthRatio ? heightRatio : widthRatio;
                }
                BitmapFactory.Options imgOptions=new BitmapFactory.Options();
                imgOptions.inSampleSize=inSampleSize;
                Bitmap bitmap=BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);
                if(requestCode==10) {
                    bedImage.setImageBitmap(bitmap);
                }else if(requestCode==20) {
                    deskImage.setImageBitmap(bitmap);
                }else if(requestCode==30) {
                    bathroomImage.setImageBitmap(bitmap);
                }else if(requestCode==40) {
                    closetImage.setImageBitmap(bitmap);
                }else if(requestCode==50) {
                    refrigeratorImage.setImageBitmap(bitmap);
                }
            }
        }
    }
}
