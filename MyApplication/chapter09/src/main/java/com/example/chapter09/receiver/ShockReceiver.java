package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class ShockReceiver extends BroadcastReceiver {

    public static  final  String SHOCK_ACTION = "com.example.chapter09.shock";
    @Override
    public void onReceive(Context context, Intent intent) {
       if(intent != null && intent.getAction().equals(SHOCK_ACTION)){
           Log.d("ning","振動");
           Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
           vb.vibrate(500);
       }
    }
}