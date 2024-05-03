package com.example.chapter06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.util.ToastUtil;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {

    private ShoppingDBHelper mDBHelper;
    private TextView tv_count;
    private GridLayout gl_Channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_channel);
         TextView tv_title = findViewById(R.id.tv_title);
         tv_title.setText("スマホ売り場");

        tv_count = findViewById(R.id.tv_count);
        gl_Channel = findViewById(R.id.gl_channel);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);


        mDBHelper =  ShoppingDBHelper.getInstance(this);
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();

        showGoods();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showCartInfoTotal();

    }

    //商品の点数をセレクト
    private void showCartInfoTotal() {
        int count = mDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;

        tv_count.setText(String.valueOf(count));
    }

    private void showGoods() {

        //スクリーンの幅を取得
        int screenWidth =  getResources().getDisplayMetrics().widthPixels;
        //リニアレイアウトの幅はスクリーンの二分の一、高さは内容によって自動的に調整されます
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / 2, ViewGroup.LayoutParams.WRAP_CONTENT);

        //全ての商品をクエリ
        List<GoodsInfo> list = mDBHelper.queryAllGoodInfo();

        //子ビューを抜く
        gl_Channel.removeAllViews();

        for (GoodsInfo info: list
             ) {
           //逐一グリッドを設定する
          View view =   LayoutInflater.from(this).inflate(R.layout.item_goods,null);
          ImageView iv_thumb  = view.findViewById(R.id.iv_thumb);
          TextView tv_name = view.findViewById(R.id.tv_name);
          TextView tv_price  = view.findViewById(R.id.tv_price);
          Button btn_add = view.findViewById(R.id.btn_add);
            iv_thumb.setImageURI(Uri.parse(info.picPath));
            tv_name.setText(info.name);
            tv_price.setText(String.valueOf((int)info.price));

            //カートに入れる
            btn_add.setOnClickListener(v -> {
                addToCart(info.id , info.name);
            });

            iv_thumb.setOnClickListener(v -> {
                Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id",info.id);
                startActivity(intent);
            });

          //グリッドレイアウトに入れる
         gl_Channel.addView(view,params);

        }
    }

    private void addToCart(int goodsId, String name) {

        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        mDBHelper.insertCartInfo(goodsId);
        ToastUtil.show(this,name +"をカートに入れました");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }

    @Override
    public void onClick(View v) {

        //バックマークをタップした際
        if(v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.iv_cart) {
            //カート一覧に遷移する
         Intent intent =   new Intent(this,ShoppingCartActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
        }
    }
}