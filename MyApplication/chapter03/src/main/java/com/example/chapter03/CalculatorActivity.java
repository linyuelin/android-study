package com.example.chapter03;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;
    //ファストオペランド
    private String firstNum = "";

    //演算子
    private  String operator = "";

    //セカンドオペランド
    private String secondNum = "";

    //精算結果
    private String result = "";

    //表示するテキスト
    private String showText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tv_result = findViewById(R.id.tv_result);

        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_square).setOnClickListener(this);
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        String inputText;

        if(v.getId() == R.id.btn_square) {
            inputText = "√";
        }else {
            inputText = ((TextView) v).getText().toString();
        }


        if (v.getId() == R.id.btn_clear) {
            // クリア
            clear();
        } else if (v.getId() == R.id.btn_cancel) {
            // キャンセル
        } else if (v.getId() == R.id.btn_plus || v.getId() == R.id.btn_minus || v.getId() == R.id.btn_multiply || v.getId() == R.id.btn_divide) {
            operator = inputText;
            refreshText(showText + operator);
            // たす・ひく・かける・わる
        } else if (v.getId() == R.id.btn_equal) {
            // イコール
            double calculate_result = calculateFour();
            refreshOperate(String.valueOf(calculate_result));
            refreshText(showText + "=" + result);
        } else if (v.getId() == R.id.btn_square) {
            // 平方
            double sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
            refreshOperate(String.valueOf(sqrt_result));
            refreshText(showText + "√=" + result);
        } else if (v.getId() == R.id.btn_reciprocal) {
            // 逆数
            double reciprocal_result = 1.0 / Double.parseDouble(firstNum);
            refreshOperate(String.valueOf(reciprocal_result));
            refreshText(showText + "/=" + result);
        } else {
             if(result.length() > 0 && operator.equals("")) {
                 clear();
             }

            // デフォルト
            if (operator.equals("")) {
                firstNum = firstNum + inputText;
            } else {
                secondNum = secondNum + inputText;
            }

           if(showText.equals("0") && !inputText.equals(".")) {
               refreshText(inputText);
           }else {
               refreshText(showText + inputText);
           }

        }



    }

    private double calculateFour() {
        switch (operator) {
            case "+":
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "-":
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "×":
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            default:
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);


        }
    }

    private void clear() {
        refreshOperate("");
        refreshText("");
    }

    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    private void refreshText(String text) {

        showText = text ;
        tv_result.setText(showText);
    }

}









