package com.example.slidingpuzzle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MyCustomGridViewAdapter extends BaseAdapter {
    private Context ctx;
    private int[] imgs;

    public MyCustomGridViewAdapter(Context ctx, int[] imgs) {
        this.ctx = ctx;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img = new ImageView(ctx);
        img.setImageResource(imgs[position]);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        return img;
    }
}
