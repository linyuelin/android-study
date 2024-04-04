package com.example.chapter03;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TextColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_text_color);
        TextView tv_code_system= findViewById(R.id.tv_code_system);
        tv_code_system.setTextColor(Color.GREEN);
        TextView tv_code_eight= findViewById(R.id.tv_code_eight);
        tv_code_eight.setTextColor(0xff00ff00);

        TextView tv_code_six= findViewById(R.id.tv_code_six);
        tv_code_six.setTextColor(0x00ff00);

        TextView tv_code_background= findViewById(R.id.tv_code_background);
        tv_code_background.setBackgroundColor(Color.GREEN);
    }
}