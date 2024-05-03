package com.example.chapter07_client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter07_client.util.ToastUtil;

public class SendMmsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityResultLauncher<Intent> mResultLauncher;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;
    private ImageView iv_appendix;
    private  Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_mms);
        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);

        iv_appendix = findViewById(R.id.iv_appendix);
        iv_appendix.setOnClickListener(this);
        findViewById(R.id.btn_send_mms).setOnClickListener(this);
        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
               if(result.getResultCode() == RESULT_OK)
               {
                   Intent intent = result.getData();
                   picUri = intent.getData();
                   if(picUri != null){
                       iv_appendix.setImageURI(picUri);
                   }
               }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_appendix) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            mResultLauncher.launch(intent);
        } else if (v.getId() == R.id.btn_send_mms) {
            sendMms(et_phone.getText().toString(), et_title.getText().toString(),et_message.getText().toString());

        }
    }

    private void sendMms(String phone, String title, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("address",phone);
        intent.putExtra("subject",title);
        intent.putExtra("sms_body",message);
        intent.putExtra(Intent.EXTRA_STREAM,picUri);
        intent.setType("image/*");
        startActivity(intent);
        ToastUtil.show(this,"ダイアログの中にメッセージを選択してください");
    }
}










