package com.example.chapter03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter03.utils.DateUtil;

public class ButtonClickActivity extends AppCompatActivity {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_button_click);
        Button btn_click_single = findViewById(R.id.btn_click_single);
        tv_result = findViewById(R.id.tv_result);
        btn_click_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = String.format("%s クリックした: %s", DateUtil.getNowTime(), ((Button) v).getText());
                tv_result.setText(desc);
            }
        });

    }
}