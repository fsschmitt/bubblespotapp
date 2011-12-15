package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bubblespot.R;
import com.bubblespot.Utils;
import com.bubblespot.evento.Evento;

public class ListEventosAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Evento> eventos;
	private ArrayList<Evento> eventosDisplay;
	private Context c;
	private ArrayList<String> nomes;

	public ListEventosAdapter(Context c,ArrayList<String> nomes,ArrayList<Evento> eventos) {
		this.eventos = eventos;
		this.c = c;
		this.nomes = nomes;
		this.eventosDisplay = eventos;
	}

	public int getCount() {
		return eventosDisplay.size();
	}

	public String getItem(int position) {
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
		TextView shopping = (TextView) v.findViewById(R.id.sp_shopping_nome);
		if(eventosDisplay.get(position).isPrimeira()){
			shopping.setText(eventosDisplay.get(position).getShopping());
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
		nome.setText(eventosDisplay.get(position).getNome());
		String dataDisplay = eventosDisplay.get(position).getData();
		areas.setText(dataDisplay.substring(8, 10)+"-" + dataDisplay.substring(5, 7) + "-" + dataDisplay.substring(0, 4) + " " + dataDisplay.substring(11, 16));

		return v;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				eventosDisplay = (ArrayList<Evento>) results.values;
				ListEventosAdapter.this.notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				ArrayList<Evento> filteredResults = getFilteredResults(constraint);

				FilterResults results = new FilterResults();
				results.values = filteredResults;

				return results;
			}

			private ArrayList<Evento> getFilteredResults(CharSequence filterText) {
				ArrayList<String> filtered = new ArrayList<String>();
				ArrayList<Evento> filteredShops = new ArrayList<Evento>();
				for(String s : nomes)
				{
					if(s.toLowerCase().contains(filterText.toString().toLowerCase()))
						filtered.add(s);
				}
				String shopping = null;
				for(Evento l : eventos){
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