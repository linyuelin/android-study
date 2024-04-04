package com.example.chapter04;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActReceiveActivity extends AppCompatActivity {

    private TextView tv_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_receive);
        tv_receive = findViewById(R.id.tv_receive);

        Bundle extras = getIntent().getExtras();
        String requestTime = extras.getString("request_time");
        String request_content = extras.getString("request_content" );
        tv_receive.setText("受け取ったデータは:" +requestTime);
    }
}