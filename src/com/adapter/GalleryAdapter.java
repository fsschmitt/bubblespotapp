package com.adapter;

import java.util.ArrayList;

import com.bubblespot.Promocao;
import com.bubblespot.R;
import com.bubblespot.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Promocao> promos;

	public GalleryAdapter(Context c, ArrayList<Promocao> promos) {
		mContext = c;
		this.promos = promos;
	}

	public int getCount() {
		return promos.size();
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
		String precoFinal=promos.get(position).getPreco_final();
		String[] temp = new String[2];
		if(precoFinal ==null)
			precoFinal = promos.get(position).getDesconto();
		temp = precoFinal.split("\\.");
		if (temp[1].equals("0"))
			precoFinal=temp[0];
		else if (temp[1].length()==1)
			precoFinal=precoFinal.concat("0");
		
		if(promos.get(position).getPreco_final()==null)
			precoFinal=precoFinal.concat(" %");
		else
			precoFinal=precoFinal.concat(" €");
		text.setText(precoFinal);
		text.setTypeface(Utils.tf);

		imageView.setPadding(10, 0, 10, 0);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setAdjustViewBounds(true);
		imageView.setImageBitmap(promos.get(position).getbImage());
		return v;
	}
}
