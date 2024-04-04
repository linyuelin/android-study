package com.example.chapter05;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private TimePicker tp_time;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_time_picker);
        tp_time = findViewById(R.id.tp_time);
        tp_time.setIs24HourView(true);
        tv_time = findViewById(R.id.tv_time);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_time).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok){
            String format = String.format("今は %d 時 %d 分です", tp_time.getHour(), tp_time.getMinute());
            tv_time.setText(format);
        } else if (v.getId() == R.id.btn_time) {
            Calendar instance = Calendar.getInstance();

            TimePickerDialog dialog = new TimePickerDialog(this, this, instance.get(Calendar.HOUR_OF_DAY),
                    instance.get(Calendar.MINUTE),
                    true);
            dialog.show();
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String format = String.format("今は %d 時 %d 分です", hourOfDay, minute);
        tv_time.setText(format);
    }
}