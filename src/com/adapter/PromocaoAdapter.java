package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubblespot.R;
import com.bubblespot.Utils;
import com.bubblespot.promocoes.PromoDetail;
import com.bubblespot.promocoes.Promocao;

public class PromocaoAdapter extends BaseAdapter implements Filterable {
	private ArrayList<Promocao> promocoes;
	private ArrayList<Promocao> promocoesDisplay;
	private ArrayList<String> nomes;
	private Context c;

	public PromocaoAdapter(Context c, ArrayList<Bitmap> bImages, ArrayList<Promocao> promocoes, ArrayList<String> nomes) {
		this.promocoes = promocoes;
		this.promocoesDisplay = promocoes;
		this.c = c;
		this.nomes=nomes;
	}

	public int getCount() {
		return promocoesDisplay.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView nome;
		TextView desc;
		ImageView imageView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.shoppingrow, null);
		} 
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle b = new Bundle();
				Intent intent = new Intent(v.getContext(), PromoDetail.class);
				Promocao promo = promocoesDisplay.get(position);
				b.putInt("idLoja", promo.getLoja_id());
				b.putInt("id", promo.getId());
				b.putInt("idShopping", promo.getShopping_id());
				b.putString("nomeLoja", promo.getLoja_nome());
				b.putString("desconto", promo.getDesconto());
				b.putString("produto", promo.getProduto());
				b.putString("detalhes", promo.getDetalhes());
				b.putString("precoFinal", promo.getPreco_final());
				b.putString("precoInicial", promo.getPreco_inicial());
				b.putString("dataFinal", promo.getData_final());
				b.putString("imagem", promo.getImagem_url());
				b.putString("shopping", promo.getShopping_nome());
				Bitmap image = promo.getbImage();
				if(image != null){
					b.putByteArray("promoImageByte", Utils.encodeBitmap(image));
				}
				else
					b.putByteArray("promoImageByte", null);
				intent.putExtras(b);
				c.startActivity(intent);
			}
		});
		
		nome = (TextView) v.findViewById(R.id.sn_nome);
		nome.setText(promocoesDisplay.get(position).getProduto());
		nome.setTypeface(Utils.tf);
		
		TextView loja = (TextView) v.findViewById(R.id.sp_loja_nome);
		if(promocoesDisplay.get(position).isPrimeira_l()){
			loja.setText(promocoesDisplay.get(position).getLoja_nome());
			loja.setTypeface(Utils.tf);
			loja.setVisibility(View.VISIBLE);
		}
		else
			loja.setVisibility(View.GONE);

		desc = (TextView) v.findViewById(R.id.distancia);
		desc.setTypeface(Utils.tf);
		String desconto = promocoesDisplay.get(position).getDesconto();
		String[] temp = new String[2];
		if(desconto==null)
			desc.setVisibility(View.GONE);
		else{
			temp = desconto.split("\\.");
			if (temp[1].equals("0"))
				desconto=temp[0];
			else if (temp[1].length()==1)
				desconto=desconto.concat("0");
			desc.setText("Desconto de "+desconto+" %");
		}
		imageView = (ImageView) v.findViewById(R.id.sn_shopping);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(3, 3, 3, 3);
		imageView.setAdjustViewBounds(true);

		imageView.setImageBitmap(promocoesDisplay.get(position).getbImage());

		return v;
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				promocoesDisplay = (ArrayList<Promocao>) results.values;
				PromocaoAdapter.this.notifyDataSetChanged();
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
				ArrayList<Promocao> filteredPromos = new ArrayList<Promocao>();
				for(String s : nomes)
				{
					if(s.toLowerCase().contains(filterText.toString().toLowerCase()))
						filtered.add(s);
				}
				String loja = null;
				for(Promocao p : promocoes){
					if(filtered.contains(p.getProduto())){
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
						filteredPromos.add(p);
					}
				}
				return filteredPromos;
			}
		};
	}
}
