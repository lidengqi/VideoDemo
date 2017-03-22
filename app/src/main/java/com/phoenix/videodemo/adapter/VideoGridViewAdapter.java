package com.phoenix.videodemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phoenix.videodemo.R;

/**
 * Created by lenovo on 2017/3/22.
 */

public class VideoGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int[] headIds = new int[] {R.mipmap.shipintouxiang1, R.mipmap.shipintouxiang2, R.mipmap.shipintouxiang3,
            R.mipmap.shipintouxiang4, R.mipmap.shipintouxiang1, R.mipmap.shipintouxiang2, R.mipmap.shipintouxiang3,
            R.mipmap.shipintouxiang4};

    public VideoGridViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return headIds.length;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_item, null);
            holder.ivVideo = (ImageView) convertView.findViewById(R.id.iv_video);
            holder.ivHead = (ImageView) convertView.findViewById(R.id.iv_video_head);
            holder.tvFensi = (TextView) convertView.findViewById(R.id.tv_video_fensi);
            holder.ivFav = (ImageView) convertView.findViewById(R.id.iv_video_fav);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivHead.setImageResource(headIds[position]);
        holder.ivVideo.setImageResource(R.mipmap.shipinzhutu1);
        return convertView;
    }

    class ViewHolder {
        ImageView ivVideo;
        ImageView ivHead;
        TextView tvFensi;
        ImageView ivFav;
    }
}
