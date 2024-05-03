package com.example.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter06.entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserDBHelper extends SQLiteOpenHelper {
  private  static  final String DB_NAME = "user.db";

  private  static  final  String TABLE_NAME = "user_info";

  private static  final  int DB_VERSION = 2;

  private static UserDBHelper mHelper = null;

  private static SQLiteDatabase mRDB = null;
  private static SQLiteDatabase mWDB = null;



    public UserDBHelper( Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static  UserDBHelper getInstance(Context context) {
        if(mHelper == null) {
            mHelper = new UserDBHelper(context);
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
                " name varchar not null," +
                " age integer not null," +
                " height long not null," +
                " weight float not null," +
                " married integer not null);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN phone VARCHAR; ";
        db.execSQL(sql);
        sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN password VARCHAR ;";
        db.execSQL(sql);

    }

    public  long insert(User user){
        ContentValues values = new ContentValues();
        values.put("name",user.name);
        values.put("age",user.age);
        values.put("height",user.height);
        values.put("weight",user.weight);
        values.put("married",user.married);

       return mWDB.insert(TABLE_NAME,null,values);
    }


    public long deleteByName(String name){
        //ありとあらゆるデータを削除する
//        mWDB.delete(TABLE_NAME,"1=1",null);

        //該当するデータしか削除しません
        return  mWDB.delete(TABLE_NAME,"name=?",new String[]{name});
    }


    public  long update(User user) {
        ContentValues values = new ContentValues();
        values.put("name",user.name);
        values.put("age",user.age);
        values.put("height",user.height);
        values.put("weight",user.weight);
        values.put("married",user.married);
        return  mWDB.update(TABLE_NAME,values,"name=?",new String[]{user.name});
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

}
