package com.example.chapter06;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import com.example.chapter06.database.LoginDBHelper;
import com.example.chapter06.entity.LoginInfo;
import com.example.chapter06.util.ViewUtil;

import java.util.Random;

public class LoginSQLiteActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener,View.OnFocusChangeListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;

    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private  String mPassword = "111111";
    private String verifyCode;
    private SharedPreferences preferences;
    private LoginDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_main);
        RadioGroup rg_login = findViewById(R.id.rg_login);
        tv_password = findViewById(R.id.tv_password);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        btn_login = findViewById(R.id.btn_login);


        //rg_loginにリスナーを設定する
        rg_login.setOnCheckedChangeListener(this);

        //et_phoneとet_passwordにリスナーを設定する
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone , 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));

        //btn_forgetにリスナーをセットする
        btn_forget.setOnClickListener(this);

        btn_login.setOnClickListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent data = o.getData();
                if(data != null && o.getResultCode() == Activity.RESULT_OK){
                    //パスワードを更新する
                    mPassword = data.getStringExtra("new_password");
                }
            }
        });

    }



    private void reload() {
        LoginInfo info = mHelper.queryTop();
        if(info != null && info.remember) {
            et_phone.setText(info.phone);
            et_password.setText(info.password);
            ck_remember.setChecked(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper = LoginDBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
        reload();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rb_password){
            //パスワードでログインする

            tv_password.setText(getString(R.string.login_password));
            et_password.setHint(getString(R.string.input_password));
            btn_forget.setText(getString(R.string.forget_password));
            ck_remember.setVisibility(View.VISIBLE);

        }else  {
            //認証コードでログインする
            tv_password.setText(getString(R.string.verifycode));
            et_password.setHint(getString(R.string.inpout_verifycode));
            btn_forget.setText(getString(R.string.get_verifycode));
            ck_remember.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();

        if(phone.length() < 11 ){
            Toast.makeText(this,"正しい番号を入力してください",Toast.LENGTH_SHORT).show();
            return;
        }


        if(v.getId() == R.id.btn_forget) {

            //パスワードをリセットする画面に遷移する
            if(rb_password.isChecked()){
                //入力した電話番号も一緒に
                Intent intent = new Intent(this, LoginForgetActivity.class);
                intent.putExtra("phone",phone);
                register.launch(intent);

            } else if (rb_verifycode.isChecked()) {
                // 認証コードを作成
                verifyCode = String.format("%06d", new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("認証コードを忘れないようにしてください");
                builder.setMessage("電話番号:" + phone + ",この度の認証コードは:" + verifyCode +",認証コードを入力してください");
                builder.setPositiveButton("はい",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        } else if (v.getId() == R.id.btn_login) {
            //パスワードで検証する
            if(rb_password.isChecked()){
                if(!mPassword.equals(et_password.getText().toString())){
                    Toast.makeText(this,"正しいパスワードを入力してください",Toast.LENGTH_SHORT).show();
                    return;
                }
                //ログイン成功
                loginSuccess();
            } else if (rb_verifycode.isChecked()) {
                if(!verifyCode.equals(et_password.getText().toString())){
                    Toast.makeText(this,"正しい認証コードを入力してください",Toast.LENGTH_SHORT).show();
                    return;
                }

                //サクセス
                loginSuccess();
            }
        }


    }

    private void loginSuccess() {
        String format = String.format("%sユーザーはログインにサクセスし、ホームページに戻ります",et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ログイン成功");
        builder.setMessage(format);
        builder.setPositiveButton("閉じる", (dialog, which) -> {
            //閉じる
            finish();
        });
        builder.setNegativeButton("もうちょっと見ます",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        LoginInfo info = new LoginInfo();
        info.phone = et_phone.getText().toString();
        info.password = et_password.getText().toString();
        info.remember = ck_remember.isChecked();
        mHelper.save(info);

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
       if(v.getId() == R.id.et_password && hasFocus){
        LoginInfo info =  mHelper.queryByPhone(et_phone.getText().toString());
        if(info != null) {
            et_password.setText(info.password);
            ck_remember.setChecked(info.remember);

        }else {
            et_password.setText("");
            ck_remember.setChecked(false);
        }
       }
    }


    // モニター、　指定された長さに達したら、キーボードが自動的に隠れる。
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        public HideTextWatcher(EditText v, int maxLength) {
            this.mView = v;
            this.mMaxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length() == mMaxLength) {
                ViewUtil.hideOneInputMethod(LoginSQLiteActivity.this, mView);
            }
        }
    }
}