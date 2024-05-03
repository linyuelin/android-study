package com.example.chapter06;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private String mPhone;
    private String verifyCode;
    private EditText et_password_first;
    private EditText et_password_second;
    private EditText et_verifycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget);

        mPhone = getIntent().getStringExtra("phone");
        et_password_first = findViewById(R.id.et_password_first);
        et_password_second = findViewById(R.id.et_password_second);
        et_verifycode = findViewById(R.id.et_verifycode);


        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
             //コード取得ボタンがタッチされたら
        if(v.getId() == R.id.btn_verifycode){
            // 認証コードを作成
            verifyCode = String.format("%06d", new Random().nextInt(999999));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("認証コードを忘れないようにしてください");
            builder.setMessage("電話番号:" + mPhone + ",この度の認証コードは:" + verifyCode +",認証コードを入力してください");
            builder.setPositiveButton("はい",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            //確認ボタン
        } else if (v.getId() == R.id.btn_confirm) {
            String password_first= et_password_first.getText().toString();
            String password_second = et_password_second.getText().toString();
            if(password_first.length() < 6){
                Toast.makeText(this,"正しいパスワードを入力してください",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!password_first.equals(password_second)){
                Toast.makeText(this,"二回一致してません",Toast.LENGTH_SHORT).show();
                return;
            }

            if(!verifyCode.equals(et_verifycode.getText().toString())){
                Toast.makeText(this,"正しい認証コードを入力してください",Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this,"パスワード変更しました",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.putExtra("new_password",password_first);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}