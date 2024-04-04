package com.example.chapter05.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtil {

    public static void hideOneInputMethod(Activity act , View V){
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(V.getWindowToken(),0);
    }
}
