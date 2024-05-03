package com.example.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter06.entity.CartInfo;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.entity.LoginInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDBHelper extends SQLiteOpenHelper {


    //データーベース名
    private  static  final String DB_NAME = "shopping.db";

    //商品情報
    private  static  final  String TABLE_GOODS_INFO = "goods_info";
    //カート情報
    private  static  final  String TABLE_CART_INFO = "cart_info";

    private static  final  int DB_VERSION = 1;

    private static ShoppingDBHelper mHelper = null;

    private static SQLiteDatabase mRDB = null;
    private static SQLiteDatabase mWDB = null;



    public ShoppingDBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static ShoppingDBHelper getInstance(Context context) {
        if(mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    //読み取り接続を開く
    public  SQLiteDatabase openReadLink(){
        if(mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }
    //書き込み接続を開く
    public  SQLiteDatabase openWriteLink(){
        if(mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    //接続を閉じる
    public  void closeLink(){
        if(mRDB != null && mRDB.isOpen()){
            mRDB.close();
            mRDB = null;
        }

        if(mWDB != null && mWDB.isOpen()){
            mWDB.close();
            mWDB = null;
        }


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //商品情報テーブル
        String sql = "CREATE  table if not exists " + TABLE_GOODS_INFO +
                " (_id integer primary key AUTOINCREMENT NOT NULL," +
                " name varchar not null," +
                " description varchar not null," +
                " price FLOAT not null," +
                " pic_path varchar not null);";

        db.execSQL(sql);

        //カート情報テーブル
        sql = "CREATE  table if not exists " + TABLE_CART_INFO +
                " (_id integer primary key AUTOINCREMENT NOT NULL," +
                " goods_id integer not null," +
                " count integer not null);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertGoodsInfos(List<GoodsInfo> list){

        try{
            mWDB.beginTransaction();
            for(GoodsInfo info :list){
                ContentValues values = new ContentValues();
                values.put("name",info.name);
                values.put("description",info.description);
                values.put("price",info.price);
                values.put("pic_path",info.picPath);
                mWDB.insert(TABLE_GOODS_INFO,null,values);
            }
            mWDB.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mWDB.endTransaction();
        }
    }

    public List<GoodsInfo> queryAllGoodInfo(){
        String sql = "select * from " + TABLE_GOODS_INFO;
        List<GoodsInfo> list = new ArrayList();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price =  cursor.getFloat(3);
            info.picPath = cursor.getString(4);
            list.add(info);
        }
        cursor.close();
        return  list;
    }

    //商品をカートに入れる
     public void insertCartInfo( int goodsId){
        //まず、セレクトし、なければ、入れる
       CartInfo cartInfo =  queryCartInfoByGoodsId(goodsId);
       ContentValues values = new ContentValues();
       values.put("goods_id",goodsId);
       if(cartInfo == null) {
           values.put("count",1);
           mWDB.insert(TABLE_CART_INFO,null,values);

       }else {
           values.put("_id",cartInfo.id);
           values.put("count",++cartInfo.count);
           mWDB.update(TABLE_CART_INFO,values,"_id=?",new String[]{String.valueOf(cartInfo.id)});
       }
     }

    private CartInfo queryCartInfoByGoodsId(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, "goods_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        CartInfo info = null;
        if(cursor.moveToNext()) {
            info  =  new CartInfo();
           info.id = cursor.getInt(0);
           info.goodsId = cursor.getInt(1);
           info.count =  cursor.getInt(2);
        }
        return info;
    }


    //カートにある商品を計算
    public int countCartInfo() {
         int count = 0;
         String sql = "select sum(count) from " + TABLE_CART_INFO;
        Cursor cursor = mRDB.rawQuery(sql, null);
        if(cursor.moveToNext()) {
           count = cursor.getInt(0);
        }
        return count;
    }

    public List<CartInfo> queryAllCartInfo() {
      List<CartInfo> list =   new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, null, null, null, null, null);
         while (cursor.moveToNext()){
             CartInfo info  =  new CartInfo();
             info.id = cursor.getInt(0);
             info.goodsId = cursor.getInt(1);
             info.count =  cursor.getInt(2);
             list.add(info);
         }
        return list;
    }

    //商品IDによって、商品情報をクエリする
    public GoodsInfo queryGoodsInfoById(int goodsId) {
        GoodsInfo info = null;
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, " _id= ? ", new String[]{String.valueOf(goodsId)}, null, null, null);
        if(cursor.moveToNext()){
            info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price =  cursor.getFloat(3);
            info.picPath = cursor.getString(4);

        }
       return info;
    }

    //商品Idにっよって削除
    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_INFO,"goods_id=?",new String[]{String.valueOf(goodsId)});
    }


    //全てを削除する
    public  void deleteAllCartInfo(){
        mWDB.delete(TABLE_CART_INFO,"1 = 1",null);
    }
}














