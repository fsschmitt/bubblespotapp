package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubblespot.Promocao;
import com.bubblespot.R;

public class PromocaoAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Promocao> promocoes;
	private ArrayList<Promocao> promocoesDisplay;
	private ArrayList<Bitmap> bImages;
	private ArrayList<Bitmap> bImagesdisplay;
	private ArrayList<String> nomes;
	private Context c;

	public PromocaoAdapter(Context c, ArrayList<Bitmap> bImages, ArrayList<Promocao> promocoes, ArrayList<String> nomes) {
		this.bImages = bImages;
		this.bImagesdisplay = bImages;
		this.promocoes = promocoes;
		this.promocoesDisplay = promocoes;
		this.c = c;
		this.nomes=nomes;
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
		nome.setText(promocoesDisplay.get(position).getProduto());

		dist = (TextView) v.findViewById(R.id.distancia);
		String desconto = promocoesDisplay.get(position).getDesconto();
		String[] temp = new String[2];
		if(desconto==null)
			dist.setVisibility(View.GONE);
		else{
			temp = desconto.split("\\.");
			if (temp[1].equals("0"))
				desconto=temp[0];
			else if (temp[1].length()==1)
				desconto=desconto.concat("0");
			dist.setText("Desconto de "+desconto+" %");
		}
		imageView = (ImageView) v.findViewById(R.id.sn_shopping);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(3, 3, 3, 3);
		imageView.setAdjustViewBounds(true);

		imageView.setImageBitmap(bImagesdisplay.get(position));

		return v;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				bImagesdisplay = (ArrayList<Bitmap>) results.values;
				PromocaoAdapter.this.notifyDataSetChanged();
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
						promocoesDisplay.add(promocoes.get(i));
					}
				}
				return filteredShops;
			}

		};
	}
}
