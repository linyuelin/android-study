package com.example.chapter04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter04.utils.DateUtil;

public class ActResponseActivity extends AppCompatActivity implements View.OnClickListener {

    private static  final String mReponse = "久しぶり";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_response);
        TextView tv_request = findViewById(R.id.tv_request);
        Bundle bundle = getIntent().getExtras();

        String requestTime = bundle.getString("request_time");
        String requestContent = bundle.getString("request_content");

        String format = String.format(requestTime + "に" + requestContent + "受け取りました");
        tv_request.setText(format);

        findViewById(R.id.btn_response).setOnClickListener(this);

        TextView  tv_response = findViewById(R.id.tv_response);
        tv_response.setText("送り待ち:" + mReponse);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("response_time", DateUtil.getNowTime());
        bundle.putString("response_content",mReponse);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}