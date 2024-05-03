package com.example.chapter07_client;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.chapter07_client.util.ImageInfo;
import com.example.chapter07_client.util.PermissionUtil;
import com.example.chapter07_client.util.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.Manifest;

public class ProviderMmsActivity extends AppCompatActivity {

    private static  final  String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private  static  final  int PERMISSION_REQUEST_CODE = 1;

    private List<ImageInfo> mImageInfoList = new ArrayList<>();
    private GridLayout gl_appendix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_provider_mms);
        gl_appendix = findViewById(R.id.gl_appendix);

        MediaScannerConnection.scanFile(this,new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},null,null);

        if( PermissionUtil.checkPermission(this,PERMISSIONS,PERMISSION_REQUEST_CODE)){
            loadImageList();
            showImageGrid();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE && PermissionUtil.checkGrant(grantResults)){
            loadImageList();
            showImageGrid();
        }
    }

    private void showImageGrid() {
        gl_appendix.removeAllViews();
        for (ImageInfo image :mImageInfoList
             ) {
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int px = utils.dip2px(this, 110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px,px);
            imageView.setLayoutParams(params);
            int padding = utils.dip2px(this, 5);
            imageView.setPadding(padding,padding,padding,padding);
            imageView.setOnClickListener(v -> {

            });
            gl_appendix.addView(imageView);

        }
    }

    @SuppressLint("Range")
    private void loadImageList() {
        String[] columns = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA,

        };

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                "_size < 307200",
                null,
                "_size DESC"

        );
        int count = 0;
        if(cursor !=null) {
            while (cursor.moveToNext() && count < 6){
                ImageInfo imageInfo = new ImageInfo();
               imageInfo.id =  cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                imageInfo.name =  cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                imageInfo.size =  cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                imageInfo.path =  cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
               count++;
               mImageInfoList.add(imageInfo);
            }
        }
    }
}