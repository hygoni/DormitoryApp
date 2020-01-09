package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
public class LeavingActivity extends AppCompatActivity implements View.OnClickListener{

    Button bedBtn;
    Button deskBtn;

    File filePath;

    int reqWidth;
    int reqHeight;

    ImageView bedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaving);

        bedBtn = findViewById(R.id.bed_camera_btn);
        deskBtn = findViewById(R.id.desk_camera_btn);
        bedImage = findViewById(R.id.bed_imageView);

        bedBtn.setOnClickListener(this);

        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);
    }

    @Override
    public void onClick(View v){
        if(v==bedBtn){
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        }else if(v==deskBtn){
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
                    startActivityForResult(intent, 40);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode==30 && resultCode==RESULT_OK){
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            bedImage.setImageBitmap(bitmap);
        }else if(requestCode==40 && resultCode==RESULT_OK){
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
                bedImage.setImageBitmap(bitmap);
            }
        }
    }
}
