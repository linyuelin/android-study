package com.example.chapter06.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chapter06.dao.BookDao;
import com.example.chapter06.entity.BookInfo;


@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase extends RoomDatabase {

    public  abstract BookDao bookDao();
}
