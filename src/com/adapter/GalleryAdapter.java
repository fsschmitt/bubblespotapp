package com.adapter;

import java.util.ArrayList;

import com.bubblespot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;

public class GalleryAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Bitmap> bImages;
	private ArrayList<String> nomes;

	public GalleryAdapter(Context c, ArrayList<Bitmap> bImages, ArrayList<String> nomes) {
		mContext = c;
		this.bImages = bImages;
		this.nomes = nomes;
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
		TextView text;
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.gallleryitem, null);
		} 

		imageView = (ImageView) v.findViewById(R.id.galleryImage);
		text = (TextView) v.findViewById(R.id.galleryText);
		text.setText(nomes.get(position));

		imageView.setPadding(10, 0, 10, 0);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setAdjustViewBounds(true);
		imageView.setImageBitmap(bImages.get(position));        
		return v;
	}
}
