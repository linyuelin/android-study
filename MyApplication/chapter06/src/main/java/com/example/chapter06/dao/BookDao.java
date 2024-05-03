package com.example.chapter06.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter06.entity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert (BookInfo ... book);
    @Delete
    void delete(BookInfo ... book);

    @Query("delete from BookInfo")
    void deleteAll();
    @Update
    int update(BookInfo ... book);

    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    @Query("SELECT * FROM BookInfo Where name = :name order by id desc limit 1")
    BookInfo queryByName(String name);
}
