package com.example.chapter06;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;


import com.example.chapter06.database.BookDatabase;
import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.util.FileUtil;
import com.example.chapter06.util.SharedUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MyApplication extends Application {

    private static MyApplication mApp;
    // 声明一个公共的信息映射对象，可当作全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();

    // 声明一个书籍数据库对象
    private BookDatabase bookDatabase;

    // 购物车中的商品总数量
    public int goodsCount;

    public static MyApplication getInstance() {
        return mApp;
    }


    //在App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Log.d("ning", "MyApplication onCreate");

        // 构建书籍数据库的实例
        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();

        initGoodsInfo();

    }

    private void initGoodsInfo() {
        //firstというパラメーターがあるかを確認
        boolean isFirst = SharedUtil.getInstance(this).readBoolean("first", true);
        //ダウンロードのパスを取得
         String directory =  getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        if(isFirst) {
            //ネットからダウンロードをシミュレーションする
            ArrayList<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo info: list
                 ) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
               String path =  directory + info.id + ".jpg";
                //商品イメージをストレージカードに保存する
                FileUtil.saveImage(path,bitmap);
                //回収
                bitmap.recycle();
                info.picPath = path;
            }

            //データーベースをオーぺんし、商品情報をインサート
            ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
            dbHelper.openWriteLink();
            dbHelper.insertGoodsInfos(list);
            dbHelper.closeLink();

            SharedUtil.getInstance(this).writeBoolean("first",false);
        }
    }


    //在App终止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("ning", "onTerminate");
    }

    //在配置改变时调用，例如从竖屏变为横屏。
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("ning", "onConfigurationChanged");
    }

    // 获取书籍数据库的实例
    public BookDatabase getBookDB() {
        return bookDatabase;
    }
}
