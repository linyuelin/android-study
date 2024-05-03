package com.example.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.dao.BookDao;
import com.example.chapter06.entity.BookInfo;
import com.example.chapter06.util.ToastUtil;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_author;
    private EditText et_press;
    private EditText et_price;
    private BookDao bookDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_write);

        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_press = findViewById(R.id.et_press);
        et_price = findViewById(R.id.et_price);


        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        bookDao = MyApplication.getInstance().getBookDB().bookDao();


    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();

        if(v.getId() == R.id.btn_save) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setName(name);
            bookInfo.setAuthor(author);
            bookInfo.setPress(press);
            bookInfo.setPrice(Double.parseDouble(price));
            ToastUtil.show(this,"保存済み");
            bookDao.insert(bookInfo);

        } else if (v.getId() == R.id.btn_query){
            List<BookInfo> bookInfos = bookDao.queryAll();
            for (BookInfo b: bookInfos
                 ) {
                Log.d("ning",b.toString());
            }
        } else if (v.getId() == R.id.btn_delete) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setId(1);
            bookDao.delete(bookInfo);

        } else if (v.getId() == R.id.btn_update) {
            BookInfo b3 = new BookInfo();
            BookInfo b4 = bookDao.queryByName(name);
            b3.setName(name);
            b3.setPress(press);
            b3.setAuthor(author);
            b3.setPrice(Double.parseDouble(price));
            ToastUtil.show(this,"アップデート済み");
            int update = bookDao.update(b3);
            System.out.println(update);
        }
    }
}