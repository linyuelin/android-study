package com.example.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.database.UserDBHelper;
import com.example.chapter06.entity.User;
import com.example.chapter06.util.ToastUtil;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView et_name;
    private TextView et_age;
    private TextView et_height;
    private TextView et_weight;
    private CheckBox ck_married;
    private UserDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite_helper);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);


        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = UserDBHelper.getInstance(this);
        mHelper.openWriteLink();
        mHelper.openReadLink();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        User user = null;

        if(v.getId() == R.id.btn_insert){
           user = new User(name,Integer.parseInt(age),Long.parseLong(height),Float.parseFloat(weight),ck_married.isChecked());
            if(mHelper.insert(user) > 0){
                ToastUtil.show(this,"追加成功");
            }

        } else if (v.getId() == R.id.btn_delete ) {
            if(mHelper.deleteByName(name) > 0) {
                ToastUtil.show(this,"削除成功");
            }
        } else if (v.getId() == R.id.btn_update) {
            user = new User(name,Integer.parseInt(age),Long.parseLong(height),Float.parseFloat(weight),ck_married.isChecked());
            if(mHelper.update(user) > 0) {
                ToastUtil.show(this,"アップデート成功");
            }
        } else if (v.getId() == R.id.btn_select) {
//            List<User> list = mHelper.queryAll();
            List<User> list = mHelper.queryByName(name);
            for(User u : list) {
                Log.d("ning",u.toString());
            }
        }

    }
}