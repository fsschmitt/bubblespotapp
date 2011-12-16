package com.adapter;

import java.util.ArrayList;

import com.bubblespot.lojas.Loja;
import com.bubblespot.lojas.ShopDetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter implements Filterable {
	private Context c;
	private ArrayList<Loja> lojas;
	private ArrayList<Loja> lojasdisplay;
	private ArrayList<String> nomes;

	public ImageAdapter(Context c, ArrayList<Loja> lojas,  ArrayList<String> nomes) {
		this.c = c;
		this.lojas = lojas;
		this.lojasdisplay = lojas;
		this.nomes = nomes;
	}

	public int getCount() {
		return lojasdisplay.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(c);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 10, 0, 10);
			imageView.setAdjustViewBounds(true);

		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageBitmap(lojasdisplay.get(position).getbImage());
		
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ShopDetail.class);
				Bundle b = new Bundle();
				Loja loja = lojasdisplay.get(position);
				b.putInt("lojaID", loja.getId());
				b.putString("lojaNome", loja.getNome());
				b.putInt("lojaPiso", loja.getPiso());
				b.putInt("lojaNumero", loja.getNumero());
				b.putString("lojaTelefone", loja.getTelefone());
				b.putString("lojaDetalhes", loja.getDetalhes());
				b.putString("lojaImagem", loja.getImagem());
				b.putString("lojaTags", loja.getTags());
				b.putString("lojaShopping", loja.getShopping());
				b.putInt("idShopping", loja.getIdShopping());
				intent.putExtras(b);
				c.startActivity(intent);
			}
		});
		return imageView;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				lojasdisplay = (ArrayList<Loja>) results.values;
				ImageAdapter.this.notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				ArrayList<Loja> filteredResults = getFilteredResults(constraint);

				FilterResults results = new FilterResults();
				results.values = filteredResults;

				return results;
			}

			private ArrayList<Loja> getFilteredResults(CharSequence filterText) {
				ArrayList<String> filtered = new ArrayList<String>();
				ArrayList<Loja> filteredShops = new ArrayList<Loja>();
				for(String s : nomes)
				{
					if(s.toLowerCase().contains(filterText.toString().toLowerCase()))
						filtered.add(s);
				}
				for(int i = 0; i< nomes.size(); i++)
				{
					if(filtered.contains(nomes.get(i))){
						filteredShops.add(lojas.get(i));
					}
				}
				return filteredShops;
			}
		};
	}
}
