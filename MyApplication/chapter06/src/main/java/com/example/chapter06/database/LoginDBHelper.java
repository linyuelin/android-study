package com.example.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter06.entity.LoginInfo;
import com.example.chapter06.entity.User;

import java.util.ArrayList;
import java.util.List;


public class LoginDBHelper extends SQLiteOpenHelper {
  private  static  final String DB_NAME = "login.db";

  private  static  final  String TABLE_NAME = "login_info";

  private static  final  int DB_VERSION = 1;

  private static LoginDBHelper mHelper = null;

  private static SQLiteDatabase mRDB = null;
  private static SQLiteDatabase mWDB = null;



    public LoginDBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static LoginDBHelper getInstance(Context context) {
        if(mHelper == null) {
            mHelper = new LoginDBHelper(context);
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
        String sql = "CREATE  table if not exists " + TABLE_NAME + "(" +
                " _id integer primary key AUTOINCREMENT NOT NULL," +
                " phone varchar not null," +
                " password varchar not null," +
                " remember integer not null);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void save(LoginInfo info){
     // 存在したら、まず削除して、追加する
      try {
          mWDB.beginTransaction();
          delete(info);
          insert(info);
          mWDB.setTransactionSuccessful();
      }catch (Exception e){
         e.printStackTrace();
      }finally {
       mWDB.endTransaction();
      }
    }

    public  long delete(LoginInfo info){
     return mWDB.delete(TABLE_NAME,"phone=?",new String[]{info.phone});
    }

    public  long insert(LoginInfo info){
        ContentValues values = new ContentValues();
        values.put("phone",info.phone);
        values.put("password",info.password);
        values.put("remember",info.remember);

       return mWDB.insert(TABLE_NAME,null,values);
    }


    public long deleteByName(String name){
        //ありとあらゆるデータを削除する
//        mWDB.delete(TABLE_NAME,"1=1",null);

        //該当するデータしか削除しません
        return  mWDB.delete(TABLE_NAME,"name=?",new String[]{name});
    }


    public List<User> queryAll() {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            User user = new User();
            user.id= cursor.getInt(0);
             user.name= cursor.getString(1);
             user.age =cursor.getInt(2);
             user.height = cursor.getLong(3);
             user.weight = cursor.getFloat(4);

           user.married = (cursor.getInt(5) == 0) ? false: true;
           list.add(user);

        }
        return list;
    }

    public List<User> queryByName(String name) {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME, null, "name=?", new String[]{name}, null, null, null);

        while(cursor.moveToNext()){
            User user = new User();
            user.id= cursor.getInt(0);
            user.name= cursor.getString(1);
            user.age =cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);

            user.married = (cursor.getInt(5) == 0) ? false: true;
            list.add(user);

        }
        return list;
    }


    public LoginInfo queryTop() {
        LoginInfo info = null;
        String sql = "select * from " + TABLE_NAME + " where remember = 1 order by _id desc limit 1";

        Cursor cursor = mRDB.rawQuery(sql, null);

        if(cursor.moveToNext()){
            info = new LoginInfo();
            info.id= cursor.getInt(0);
            info.phone= cursor.getString(1);
            info.password= cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;

        }
        return info;
    }


    public LoginInfo queryByPhone(String phone) {
        LoginInfo info = null;
        String sql = "select * from " + TABLE_NAME ;

        Cursor cursor = mRDB.query(TABLE_NAME , null , "phone =? and remember = 1",new String[]{phone},null,null,null );

        if(cursor.moveToNext()){
            info = new LoginInfo();
            info.id= cursor.getInt(0);
            info.phone= cursor.getString(1);
            info.password= cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;

        }
        return info;
    }

}
