package com.example.chapter07_server.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class UserDBHelper extends SQLiteOpenHelper {
  private  static  final String DB_NAME = "user.db";

  public   static  final  String TABLE_NAME = "user_info";

  private static  final  int DB_VERSION = 1;

  private static UserDBHelper mHelper = null;

  private static SQLiteDatabase mRDB = null;
  private static SQLiteDatabase mWDB = null;



    public UserDBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static  UserDBHelper getInstance(Context context) {
        if(mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
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

    }


}
