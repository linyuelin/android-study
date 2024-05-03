package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chapter09.BroadOrderActivity;

public class OrderBReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null && intent.getAction().equals(BroadOrderActivity.ORDER_ACTION)){
            Log.d("ning","Bがメッセージを取得した");
        }
    }
}
