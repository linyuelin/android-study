package com.example.chapter06;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShareWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();
    }

     private void reload(){
       String name = preferences.getString("name",null);
       if(name != null) {
           et_name.setText(name);
       }

       int age = preferences.getInt("age", 0);
       if(age != 0) {
           et_age.setText(String.valueOf(age));
       }

       Float height = preferences.getFloat("height", 0f);
       if(height != 0f) {
           et_height.setText(String.valueOf(height));
       }

         Float weight = preferences.getFloat("weight", 0f);
         if(weight != 0f) {
             et_weight.setText(String.valueOf(weight));
         }

         boolean married = preferences.getBoolean("married", false);
         ck_married.setChecked(married);

     }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("name",name);
        edit.putInt("age",Integer.parseInt(age));
        edit.putFloat("height",Float.parseFloat(height));
        edit.putFloat("weight",Float.parseFloat(weight));
        edit.putBoolean("married",ck_married.isChecked());
        edit.commit();
    }
}