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

import com.bubblespot.Loja;
import com.bubblespot.R;

public class ListAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Loja> shops;
	private ArrayList<Loja> shopsDisplay;
	private Context c;
	private ArrayList<String> nomes;

	public ListAdapter(Context c,ArrayList<String> nomes,ArrayList<Loja> shops) {
		this.shops = shops;
		this.c = c;
		this.nomes = nomes;
		this.shopsDisplay = shops;
	}

	public int getCount() {
		return shopsDisplay.size();
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
		TextView shopping = (TextView) v.findViewById(R.id.shopping_nome);
		
		if(shopsDisplay.get(position).isPrimeira()){
				shopping.setText(shopsDisplay.get(position).getShopping());
				shopping.setVisibility(View.VISIBLE);
		}
		else
			shopping.setVisibility(View.GONE);
		
		TextView nome = (TextView) v.findViewById(R.id.loja_nome);
		TextView areas = (TextView) v.findViewById(R.id.areas_negocio);

		nome.setVisibility(View.VISIBLE);
		areas.setVisibility(View.VISIBLE);
		nome.setText(shopsDisplay.get(position).getNome());
		areas.setText(shopsDisplay.get(position).getTags());
		
		return v;
	}
	
	@Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shopsDisplay = (ArrayList<Loja>) results.values;
                ListAdapter.this.notifyDataSetChanged();
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
				String shopping = null;
				for(Loja l : shops){
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