package com.example.chapter08;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.chapter08.adapter.LaunchSimpleAdapter;

public class LaunchSimpleActivity extends AppCompatActivity {

    private int[] lanuchImageArray = {R.drawable.guide_bg1,
    R.drawable.guide_bg2,R.drawable.guide_bg3,R.drawable.guide_bg4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch_simple);

        ViewPager vp_launch = findViewById(R.id.vp_launch);

        LaunchSimpleAdapter adapter = new LaunchSimpleAdapter(this, lanuchImageArray);
         vp_launch.setAdapter(adapter);
    }
}