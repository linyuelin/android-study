package com.example.chapter04;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MetaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meta_data);

        TextView tv_meta = findViewById(R.id.tv_meta);
        PackageManager pm = getPackageManager();
        try {
            ActivityInfo info = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;
            String weather = bundle.getString("weather");
            tv_meta.setText(weather);

        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}