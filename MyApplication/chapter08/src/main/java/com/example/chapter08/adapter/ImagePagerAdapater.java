package com.example.chapter08.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter08.ViewPageActivity;
import com.example.chapter08.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapater extends PagerAdapter {

    private final Context mContext;
    private final ArrayList<GoodsInfo> mGoodsList;

    private List<ImageView> mViewList = new ArrayList<>();


    public ImagePagerAdapater(Context mContext, ArrayList<GoodsInfo> mGoodsList) {
       this.mContext  = mContext;
       this.mGoodsList = mGoodsList;
        for (GoodsInfo info : mGoodsList) {
            ImageView view = new ImageView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            view.setImageResource(info.pic);
            mViewList.add(view);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView item = mViewList.get(position);
         container.addView(item);
        return  item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView(mViewList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }
}
