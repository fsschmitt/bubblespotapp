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
import com.bubblespot.promocoes.Promocao;

public class ListPromosAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Promocao> promocoes;
	private ArrayList<Promocao> promocoesDisplay;
	private Context c;
	private ArrayList<String> nomes;

	public ListPromosAdapter(Context c,ArrayList<String> nomes,ArrayList<Promocao> eventos) {
		this.promocoes = eventos;
		this.c = c;
		this.nomes = nomes;
		this.promocoesDisplay = eventos;
	}

	public int getCount() {
		return promocoesDisplay.size();
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
			v = vi.inflate(R.layout.promorow, null);
		} 
		TextView shopping = (TextView) v.findViewById(R.id.sp_shopping_nome);
		if(promocoesDisplay.get(position).isPrimeira_s()){
			shopping.setText(promocoesDisplay.get(position).getShopping_nome());
			shopping.setTypeface(Utils.tf);
			shopping.setVisibility(View.VISIBLE);
			
		}
		else
			shopping.setVisibility(View.GONE);
		
		TextView loja = (TextView) v.findViewById(R.id.sp_loja_nome);
		if(promocoesDisplay.get(position).isPrimeira_l()){
			loja.setText(promocoesDisplay.get(position).getLoja_nome());
			loja.setTypeface(Utils.tf);
			loja.setVisibility(View.VISIBLE);
			
		}
		else
			loja.setVisibility(View.GONE);

		TextView nome = (TextView) v.findViewById(R.id.sp_promo_nome);
		nome.setTypeface(Utils.tf);
		TextView areas = (TextView) v.findViewById(R.id.sp_areas_negocio);
		areas.setTypeface(Utils.tf);

		nome.setVisibility(View.VISIBLE);
		areas.setVisibility(View.VISIBLE);
		nome.setText(promocoesDisplay.get(position).getProduto());
		
		String desconto = promocoesDisplay.get(position).getDesconto();
		String[] temp = new String[2];
		if(desconto==null)
			areas.setVisibility(View.GONE);
		else{
			temp = desconto.split("\\.");
			if (temp[1].equals("0"))
				desconto=temp[0];
			else if (temp[1].length()==1)
				desconto=desconto.concat("0");
			areas.setText("Desconto de "+desconto+" %");
		}
		
		return v;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				promocoesDisplay = (ArrayList<Promocao>) results.values;
				ListPromosAdapter.this.notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				ArrayList<Promocao> filteredResults = getFilteredResults(constraint);

				FilterResults results = new FilterResults();
				results.values = filteredResults;

				return results;
			}

			private ArrayList<Promocao> getFilteredResults(CharSequence filterText) {
				ArrayList<String> filtered = new ArrayList<String>();
				ArrayList<Promocao> filteredShops = new ArrayList<Promocao>();
				for(String s : nomes)
				{
					if(s.toLowerCase().contains(filterText.toString().toLowerCase()))
						filtered.add(s);
				}
				String shopping = null;
				String loja = null;
				for(Promocao p : promocoes){
					if(filtered.contains(p.getProduto())){
						if(shopping == null){
							p.setPrimeira_s(true);
							shopping = p.getShopping_nome();
						}else if(p.getShopping_nome().equalsIgnoreCase(shopping)){
							p.setPrimeira_s(false);
						}
						else{
							p.setPrimeira_s(true);
							shopping = p.getShopping_nome();
							loja=null;
						}
						if(loja == null){
							p.setPrimeira_l(true);
							loja = p.getLoja_nome();
						}else if(p.getLoja_nome().equalsIgnoreCase(loja)){
							p.setPrimeira_l(false);
						}
						else{
							p.setPrimeira_l(true);
							loja = p.getLoja_nome();
						}
						filteredShops.add(p);
					}
				}
				return filteredShops;
			}

		};
	}
}