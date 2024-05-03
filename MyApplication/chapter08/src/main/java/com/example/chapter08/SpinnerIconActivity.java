package com.example.chapter08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chapter08.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpinnerIconActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static  final int[] iconArray = {
            R.drawable.shuixing,R.drawable.jinxing,R.drawable.diqiu,
            R.drawable.huoxing,R.drawable.muxing,R.drawable.tuxing
    };

    private  final static String[] starArray = {"水星","金星","地球","火星","木星","土星"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner_icon);

        ArrayList<Map<String,Object>> list = new ArrayList<>();

        for (int i = 0; i < iconArray.length; i++) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("icon",iconArray[i]);
            item.put("name",starArray[i]);
            list.add(item);
        }
        SimpleAdapter starAdapter = new SimpleAdapter(this,list,R.layout.item_simple,
                new String[]{"icon","name"},
                new int[]{R.id.iv_icon,R.id.tv_name});

        Spinner sp_icon = findViewById(R.id.sp_icon);
        sp_icon.setAdapter(starAdapter);
        sp_icon.setSelection(0);
        sp_icon.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this,"選んだのは" + starArray[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}