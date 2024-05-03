package com.example.chapter06;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.CartInfo;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    private LinearLayout ll_cart;
    private ShoppingDBHelper mDBHelper;
    private List<CartInfo> mCartList;

    //キャッシュのためのマップ
    private Map<Integer ,GoodsInfo> mGoodsMap = new HashMap<>();
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("カート");
        ll_cart = findViewById(R.id.ll_cart);
        tv_total_price = findViewById(R.id.tv_total_price);

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        mDBHelper = ShoppingDBHelper.getInstance(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);

        ll_empty = findViewById(R.id.ll_empty);
        ll_content = findViewById(R.id.ll_content);


    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }

    private void showCart() {
        //全ての子ビューを抜く
       ll_cart.removeAllViews();

       //カートの中の商品をクエリする
        mCartList = mDBHelper.queryAllCartInfo();

        if(mCartList.size() == 0) {
            return;
        }

        for (CartInfo info: mCartList
             ) {
           GoodsInfo goods = mDBHelper.queryGoodsInfoById(info.goodsId);
           mGoodsMap.put(info.goodsId,goods);

           // item_cart.xml のレイアウトを取得
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            TextView tv_count = view.findViewById(R.id.tv_count);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_tv_sum = view.findViewById(R.id.tv_sum);

            iv_thumb.setImageURI(Uri.parse(goods.picPath));
            tv_name.setText(goods.name);
            tv_desc.setText(goods.description);
            tv_count.setText(String.valueOf(info.count));
            tv_price.setText(String.valueOf((int) goods.price));
            //合計金額
            tv_tv_sum.setText(String.valueOf((int) (info.count * goods.price)));

            //長押し
            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setMessage(goods.name +"を削除します？");
                builder.setPositiveButton("はい",(dialog, which) -> {
                    ll_cart.removeView(v);
                    deleteGoods(info);
                });

                builder.setNegativeButton("いいえ",null);
                builder.create().show();


                return true;
            });


            // 商品を押したら、該当する商品詳細に移す
            view.setOnClickListener(v -> {
                //TODO debug
                Intent intent = new Intent(ShoppingCartActivity.this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id",goods.id);   //元々は info.id
                startActivity(intent);
            });

            ll_cart.addView(view);

        }
        refreshTotalPrice();
    }

    private void deleteGoods(CartInfo info) {
      MyApplication.getInstance().goodsCount -= info.count;
      //データーベースも
      mDBHelper.deleteCartInfoByGoodsId(info.goodsId);
      //カート一覧も
        CartInfo removed = null;
        for (CartInfo cartInfo: mCartList
             ) {
            if(cartInfo.goodsId == info.goodsId) {
                removed = cartInfo;
                break;
            }
        }
        mCartList.remove(removed);

        //最新商品数を表示する
        showCount();
        ToastUtil.show(this,mGoodsMap.get(info.goodsId).name+"をカートから削除されました");
        mGoodsMap.remove(info.goodsId);
        //総額を刷新する
        refreshTotalPrice();;


    }
     // カートマークの商品総数を表示する
    private void showCount() {
     tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

     //なければ、"空空如也" を表示する
        if(MyApplication.getInstance().goodsCount == 0) {
           ll_empty.setVisibility(View.VISIBLE);
           ll_content.setVisibility(View.GONE);
           ll_cart.removeAllViews();
        }else {
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }
    }

    //商品の総額を計算
    private void refreshTotalPrice() {
        int totalPrice  = 0;
        for (CartInfo info: mCartList
             ) {

            GoodsInfo goods = mGoodsMap.get(info.goodsId);

            totalPrice += goods.price * info.count;
        }

        tv_total_price.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.iv_back) {
            finish();

            //売り場ページへ
        } else if (v.getId() == R.id.btn_shopping_channel) {
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            //全てをデリート
        }else if (v.getId() == R.id.btn_clear) {
            mDBHelper.deleteAllCartInfo();
            MyApplication.getInstance().goodsCount = 0;
            showCount();
            ToastUtil.show(this,"カートを空にしました");


            //会計ボタンがタップされたら
        }else if (v.getId() == R.id.btn_settle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("会計する");
            builder.setMessage("申し訳ございません、支払い機能はまだ開通してないので、現金でお願いします");
            builder.setPositiveButton("はい",null);
            builder.create().show();
        }

    }
}