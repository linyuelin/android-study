package com.example.chapter05;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditFocusActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private TextView et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_focus);
        et_phone = findViewById(R.id.et_phone);
        TextView et_password = findViewById(R.id.et_password);
        et_password.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            String phone = et_phone.getText().toString();
            if(TextUtils.isEmpty(phone) ||  phone.length() < 11){
                et_phone.requestFocus();
                Toast.makeText(this,"11桁の電話番号を入力してください",Toast.LENGTH_SHORT).show();
            }
        }
    }
}