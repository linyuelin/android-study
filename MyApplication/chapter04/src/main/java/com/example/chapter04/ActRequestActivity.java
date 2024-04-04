package com.example.chapter04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter04.utils.DateUtil;

public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private String mRequest = "やっほー";
    private ActivityResultLauncher<Intent> register;
    private TextView tv_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_request);
        TextView tv_request = findViewById(R.id.tv_request);
        tv_request.setText(mRequest);

        findViewById(R.id.btn_request).setOnClickListener(this);

        tv_response = findViewById(R.id.tv_response);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
               if(o != null) {
                   Intent intent = o.getData();
                   if(intent != null && o.getResultCode() == Activity.RESULT_OK) {
                       Bundle bundle = intent.getExtras();

                       String response_time = bundle.getString("response_time");
                       String response_content = bundle.getString("response_content");
                       String format = String.format(response_time + "に" + response_content + "受け取りました");
                       tv_response.setText(format);
                   }
               }
            }

        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ActResponseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("request_time", DateUtil.getNowTime());
        bundle.putString("request_content" , mRequest);
        intent.putExtras(bundle);
        startActivity(intent);
        register.launch(intent);
    }
}