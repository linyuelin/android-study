package com.example.chapter07_client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.chapter07_client.util.PermissionUtil;
import com.example.chapter07_client.util.ToastUtil;


public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {

    private  static  final  String[] PERMISSIONS_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS

    };

    private  static  final  String[] PERMISSIONS_SMS = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS

    };

    private static  final int REQUEST_CODE_CONTACTS = 1;
    private static  final int REQUEST_CODE_SMS = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE_CONTACTS:
                if(PermissionUtil.checkGrant(grantResults)){
                    Log.d("ning","連絡先のアクセス許可を取得しました");
                }else {
                    ToastUtil.show(this,"連絡先のアクセス許可を取得できませんでした");
                    jumpToSettings();
                }
                break;

            case REQUEST_CODE_SMS:
                if(PermissionUtil.checkGrant(grantResults)){
                    Log.d("ning","メッセージの送受信権限を取得しました");
                }else {
                    ToastUtil.show(this,"メッセージの送受信権限を取得できませんでした");
                    jumpToSettings();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_contact){
            PermissionUtil.checkPermission(this,PERMISSIONS_CONTACTS,REQUEST_CODE_CONTACTS);
        } else if (v.getId() == R.id.btn_sms) {
            PermissionUtil.checkPermission(this,PERMISSIONS_SMS,REQUEST_CODE_SMS);

        }

    }

    public void jumpToSettings(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package",getPackageName(),null));
       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}