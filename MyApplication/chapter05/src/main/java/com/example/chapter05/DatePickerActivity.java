package com.example.chapter05;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private DatePicker dp_date;
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_date_picker);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_date).setOnClickListener(this);
        tv_date = findViewById(R.id.tv_date);
        dp_date = findViewById(R.id.dp_date);

    }

    @Override
    public void onClick(View v) {
    if(v.getId() == R.id.btn_ok) {
        String desc = String.format("現在は%d年%d月%d日", dp_date.getYear(), dp_date.getMonth() + 1, dp_date.getDayOfMonth());
        tv_date.setText(desc);
    } else if (v.getId() == R.id.btn_date) {
//        Calendar calendar= Calendar.getInstance();
//        calendar.get(Calendar.YEAR);
//        calendar.get(Calendar.MONTH);
//        calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, this, 2090, 5, 11);
        dialog.show();
    }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc = String.format("現在は%d年%d月%d日", year, month + 1, dayOfMonth);
        tv_date.setText(desc);
    }
}