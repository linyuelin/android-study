package com.example.chapter06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_database;
    private String mDatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database);
        tv_database = findViewById(R.id.tv_database);
        findViewById(R.id.btn_database_create).setOnClickListener(this);
        findViewById(R.id.btn_database_delete).setOnClickListener(this);

        mDatabaseName = getFilesDir() + "/test.db";
    }

    @Override
    public void onClick(View v) {

        String desc = null;
         if(v.getId() == R.id.btn_database_create){
             SQLiteDatabase db = openOrCreateDatabase(mDatabaseName, Context.MODE_PRIVATE, null);
             desc = String.format("データーベース%sは%sを作成した", db.getPath(), (db != null) ? "完成" : "失敗");
             tv_database.setText(desc);
         }else if (v.getId() == R.id.btn_database_delete){
            boolean result = deleteDatabase(mDatabaseName);
            desc = String.format("データーベース%sは%sを削除した", mDatabaseName, result ? "完成" : "失敗");
             tv_database.setText(desc);
        }
    }
}