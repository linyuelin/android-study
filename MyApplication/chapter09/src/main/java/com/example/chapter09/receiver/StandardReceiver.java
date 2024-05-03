package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StandardReceiver extends BroadcastReceiver {

    public  static  final  String  STANDARD_ACTION = "com.example.chapter09.standard";

    @Override
    public void onReceive(Context context, Intent intent) {
         if(intent != null && intent.getAction().equals(STANDARD_ACTION)){
             Log.d("ning","メッセージをゲットした");
         }
    }
}
