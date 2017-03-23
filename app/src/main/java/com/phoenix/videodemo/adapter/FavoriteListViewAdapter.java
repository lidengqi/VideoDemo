package com.phoenix.videodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.phoenix.videodemo.R;

/**
 * Created by lenovo on 2017/3/22.
 */

public class FavoriteListViewAdapter extends BaseAdapter {

    private Context mContext;
    private int[] favIds = new int[] {R.mipmap.hongrenzhutu1, R.mipmap.hongrenzhutu2, R.mipmap.hongrenzhutu1,
            R.mipmap.hongrenzhutu2, R.mipmap.hongrenzhutu1, R.mipmap.hongrenzhutu2};
    private int[] headIds = new int[] {R.mipmap.hongrenzuoshang, R.mipmap.hongrenzuoshang2, R.mipmap.hongrenzuoshang,
            R.mipmap.hongrenzuoshang2, R.mipmap.hongrenzuoshang, R.mipmap.hongrenzuoshang2};

    public FavoriteListViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return favIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.favorite_item, null);
            holder.ivFav = (ImageView) convertView.findViewById(R.id.iv_favorite);
            holder.ivHead = (ImageView) convertView.findViewById(R.id.iv_fav_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivFav.setImageResource(favIds[position]);
        holder.ivHead.setImageResource(headIds[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView ivFav;
        ImageView ivHead;
    }
}
