package com.bubblespot;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingAdapter extends BaseAdapter {
	private ArrayList<Shopping> shoppings;
	private ArrayList<Bitmap> bImages;
	private Context c;

	ShoppingAdapter(Context c, ArrayList<Bitmap> bImages, ArrayList<Shopping> shoppings) {
		this.bImages = bImages;
		this.shoppings = shoppings;
		this.c = c;
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
		View v = convertView;
		TextView nome;
		TextView dist;
		ImageView imageView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.shoppingrow, null);
		} 
		nome = (TextView) v.findViewById(R.id.sn_nome);
		nome.setText(shoppings.get(position).getNome());

		dist = (TextView) v.findViewById(R.id.distancia);
		dist.setText(shoppings.get(position).getDist()+"km");
		imageView = (ImageView) v.findViewById(R.id.sn_shopping);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(3, 3, 3, 3);
		imageView.setAdjustViewBounds(true);
		 
	    imageView.setImageBitmap(bImages.get(position));

		return v;
	}
}