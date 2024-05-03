package com.example.chapter07_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_client.util.PermissionUtil;
import com.example.chapter07_client.util.ToastUtil;


public class PermissionHungryActivity extends AppCompatActivity implements View.OnClickListener {

    private  static  final  String[] PERMISSIONS= new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS

    };

    private static  final int REQUEST_CODE_ALL = 1;


    private static  final int REQUEST_CODE_CONTACTS = 2;
    private static  final int REQUEST_CODE_SMS = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);

        PermissionUtil.checkPermission(this,PERMISSIONS,REQUEST_CODE_ALL);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case REQUEST_CODE_ALL:
                if(PermissionUtil.checkGrant(grantResults)){
                    Log.d("ning","全てのアクセス許可を取得しました");
                }else {
                    for (int i = 0; i < grantResults.length; i++) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            //何が取得できなかったか
                            switch (permissions[i]){
                                case Manifest.permission.READ_CONTACTS:
                                case Manifest.permission.WRITE_CONTACTS:
                                    ToastUtil.show(this,"連絡先のアクセス許可を取得できませんでした");
                                    jumpToSettings();
                                    break;

                                case Manifest.permission.READ_SMS:
                                case Manifest.permission.SEND_SMS:
                                    ToastUtil.show(this,"メッセージの送受信権限を取得できませんでした");
                                    jumpToSettings();
                                    break;
                            }
                        }
                    }
                }
                break;

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
            PermissionUtil.checkPermission(this,new String[]{PERMISSIONS[0],PERMISSIONS[1]},REQUEST_CODE_CONTACTS);
        } else if (v.getId() == R.id.btn_sms) {
            PermissionUtil.checkPermission(this,new String[]{PERMISSIONS[2],PERMISSIONS[3]},REQUEST_CODE_SMS);

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