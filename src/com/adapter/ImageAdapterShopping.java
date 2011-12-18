package com.adapter;

import java.util.ArrayList;

import com.bubblespot.shoppings.Shopping;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapterShopping extends BaseAdapter {

	private Context mContext;
	private ArrayList<Shopping> shoppings;

	public ImageAdapterShopping(Context c, ArrayList<Shopping> shoppings) {
		mContext = c;
		this.shoppings = shoppings;
	}

	public int getCount() {
		return shoppings.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 10, 0, 10);
			imageView.setAdjustViewBounds(true);

		} else {
			imageView = (ImageView) convertView;
		}
		
		if(shoppings.get(position).getbImage()!=null)
			imageView.setImageBitmap(shoppings.get(position).getbImage());

		return imageView;
	}
}
