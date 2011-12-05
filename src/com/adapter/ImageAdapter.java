package com.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.graphics.Bitmap;

public class ImageAdapter extends BaseAdapter implements Filterable {
	private Context c;
	private ArrayList<Bitmap> bImages;
	private ArrayList<Bitmap> bImagesdisplay;
	private ArrayList<String> nomes;

	public ImageAdapter(Context c, ArrayList<Bitmap> bImages, ArrayList<String> nomes) {
		this.c = c;
		this.nomes = nomes;
		this.bImages = bImages;
		this.bImagesdisplay = bImages;
	}

	public int getCount() {
		return bImagesdisplay.size();
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
			imageView = new ImageView(c);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 10, 0, 10);
			imageView.setAdjustViewBounds(true);

		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageBitmap(bImagesdisplay.get(position));

		return imageView;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				bImagesdisplay = (ArrayList<Bitmap>) results.values;
				ImageAdapter.this.notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				ArrayList<Bitmap> filteredResults = getFilteredResults(constraint);

				FilterResults results = new FilterResults();
				results.values = filteredResults;

				return results;
			}

			private ArrayList<Bitmap> getFilteredResults(CharSequence filterText) {
				ArrayList<String> filtered = new ArrayList<String>();
				ArrayList<Bitmap> filteredShops = new ArrayList<Bitmap>();
				for(String s : nomes)
				{
					if(s.toLowerCase().contains(filterText.toString().toLowerCase()))
						filtered.add(s);
				}
				for(int i = 0; i< nomes.size(); i++)
				{
					if(filtered.contains(nomes.get(i))){
						filteredShops.add(bImages.get(i));
					}
				}
				return filteredShops;
			}
		};
	}
}
