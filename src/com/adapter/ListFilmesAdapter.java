package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bubblespot.R;
import com.bubblespot.Utils;
import com.bubblespot.cinema.Cinema;
import com.bubblespot.cinema.Filme;

public class ListFilmesAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Filme> filmes;
	private ArrayList<Filme> filmesDisplay;
	private Context c;
	private ArrayList<String> nomes;

	public ListFilmesAdapter(Context c,ArrayList<String> nomes,ArrayList<Filme> filmes) {
		this.filmes = filmes;
		this.c = c;
		this.nomes = nomes;
		this.filmesDisplay = filmes;
	}

	public int getCount() {
		return filmesDisplay.size();
	}

	public String getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.shoprow, null);
		} 
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), Cinema.class);
				Filme filme = filmesDisplay.get(position);
				Bundle b = new Bundle();
				b.putString("text", Utils.link_shopping+filme.getIdShopping()+Utils.link_filme+filme.getId()+Utils.link_format);
				intent.putExtras(b);
				c.startActivity(intent);
			}
		});
		
		TextView shopping = (TextView) v.findViewById(R.id.sp_shopping_nome);
		if(filmesDisplay.get(position).isPrimeira()){
			shopping.setText(filmesDisplay.get(position).getShopping());
			shopping.setTypeface(Utils.tf);
			shopping.setVisibility(View.VISIBLE);
		}
		else
			shopping.setVisibility(View.GONE);

		TextView nome = (TextView) v.findViewById(R.id.sp_loja_nome);
		nome.setTypeface(Utils.tf);
		TextView areas = (TextView) v.findViewById(R.id.sp_areas_negocio);
		areas.setTypeface(Utils.tf);

		nome.setVisibility(View.VISIBLE);
		areas.setVisibility(View.VISIBLE);
		nome.setText(filmesDisplay.get(position).getNome());
		areas.setText(filmesDisplay.get(position).getHorarios());

		return v;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				filmesDisplay = (ArrayList<Filme>) results.values;
				ListFilmesAdapter.this.notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				ArrayList<Filme> filteredResults = getFilteredResults(constraint);

				FilterResults results = new FilterResults();
				results.values = filteredResults;

				return results;
			}

			private ArrayList<Filme> getFilteredResults(CharSequence filterText) {
				ArrayList<String> filtered = new ArrayList<String>();
				ArrayList<Filme> filteredShops = new ArrayList<Filme>();
				for(String s : nomes)
				{
					if(s.toLowerCase().contains(filterText.toString().toLowerCase()))
						filtered.add(s);
				}
				String shopping = null;
				for(Filme l : filmes){
					if(filtered.contains(l.getNome())){
						if(shopping == null){
							l.setPrimeira(true);
							shopping = l.getShopping();
						}else if(l.getShopping().equalsIgnoreCase(shopping)){
							l.setPrimeira(false);
						}
						else{
							l.setPrimeira(true);
							shopping = l.getShopping();
						}
						filteredShops.add(l);
					}
				}
				return filteredShops;
			}
		};
	}
}