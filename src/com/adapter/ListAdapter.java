package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bubblespot.Loja;
import com.bubblespot.R;

public class ListAdapter extends BaseAdapter {
	private ArrayList<Loja> shops;
	private Context c;

	public ListAdapter(Context c, ArrayList<Loja> shops) {
		this.shops = shops;
		this.c = c;
	}

	public int getCount() {
		return shops.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new View for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.shoprow, null);
		} 
		TextView shopping = (TextView) v.findViewById(R.id.shopping_nome);
		
		if(shops.get(position).isPrimeira()){
			shopping.setText(shops.get(position).getShopping());
			shopping.setVisibility(View.VISIBLE);
		}
		else
			shopping.setVisibility(View.GONE);
		TextView nome = (TextView) v.findViewById(R.id.loja_nome);
		nome.setText(shops.get(position).getNome());
		TextView areas = (TextView) v.findViewById(R.id.areas_negocio);
		areas.setText(shops.get(position).getTags());
		return v;
	}
}