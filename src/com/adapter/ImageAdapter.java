package com.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.graphics.Bitmap;

public class ImageAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<Bitmap> bImages;

    public ImageAdapter(Context c, ArrayList<Bitmap> bImages) {
        mContext = c;
        this.bImages = bImages;
    }

    public int getCount() {
        return bImages.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 5, 0, 5);
            imageView.setAdjustViewBounds(true);
            
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(bImages.get(position));
        	
        return imageView;
    }
}
