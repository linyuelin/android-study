package com.example.chapter05;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlertDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alert_dialog);

        findViewById(R.id.btn_alert).setOnClickListener(this);
        tv_alert = findViewById(R.id.tv_alert);

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("お客様");
        builder.setMessage("APPを削除する");

        builder.setPositiveButton("削除",(dialog, which) -> {
            tv_alert.setText("残念です");
        });

        builder.setNegativeButton("考えさせて",(dialog, which) -> {
            tv_alert.setText("ご容赦ありがとう");
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}