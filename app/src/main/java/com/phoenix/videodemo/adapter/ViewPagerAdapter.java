package com.phoenix.videodemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class ViewPagerAdapter extends PagerAdapter {
    public List<View> datas;

    public ViewPagerAdapter(List<View> datas){
        this.datas = datas;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parent = (ViewGroup) datas.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(datas.get(position), 0);
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return  datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(datas.get(position));
    }
}