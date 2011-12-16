package com.bubblespot.promocoes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.adapter.PromocaoAdapter;
import com.bubblespot.Header;
import com.bubblespot.R;
import com.bubblespot.Search;
import com.bubblespot.Utils;

public class ListPromo extends Activity{
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<String> images;
	private ArrayList<Promocao> promos;
	private ProgressDialog dialog;
	private ListView listview;
	private String text;
	private EditText filterText;
	private PromocaoAdapter adapter;
	private Boolean loading;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allshops);
		Bundle c = this.getIntent().getExtras();
		this.text = c.getString("text");

		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, ListPromo.this);

		filterText = (EditText) findViewById(R.id.filter_box);
		filterText.addTextChangedListener(filterTextWatcher);
		filterText.setHint("Filtrar Promoções");
		loading=true;
		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		new Bundle();
		promos = new ArrayList<Promocao>();
		nomes = new ArrayList<String>();
		bImages = new ArrayList<Bitmap>();
		images = new ArrayList<String>();
		listview = (ListView) findViewById(R.id.listView1);
		new RetrievePromos().execute();
	}

	class RetrievePromos extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {

			String uri = Utils.link + text;
			URL url = null;
			try {
				url = new URL(uri);				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			String line = null;
			JSONArray jo = null;
			try {
				line = Utils.getJSONLine(url);
				jo = new JSONArray(line);
				for (int i = 0; i < jo.length(); i++) {
					JSONObject promo = jo.getJSONObject(i);
					int id = promo.getInt("id");
					String desconto = promo.getString("desconto");
					String dataf = promo.getString("dataf");
					String detalhes = promo.getString("detalhes");
					String imagem = promo.getString("imagem");
					String precof = promo.getString("precof");
					String precoi = promo.getString("precoi");
					String produto = promo.getString("produto");
					String lojaNome = promo.getString("loja_nome");
					String shoppingNome = promo.getString("shopping_nome");
					int shoppingId = promo.getInt("shopping_id");
					int idLoja = promo.getInt("loja_id");

					Promocao p = new Promocao(id,dataf,desconto,detalhes,imagem,idLoja, lojaNome,shoppingId,shoppingNome,precoi,precof,produto);
					promos.add(p);
				}
				ordenar(promos);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		private void ordenar(ArrayList<Promocao> promos) {
			Collections.sort(promos, new Comparator<Object>(){
				public int compare(Object obj1, Object obj2) {
					Promocao p1 = (Promocao) obj1;
					Promocao p2 = (Promocao) obj2;
					int deptComp = p1.getShopping_nome().compareTo(p2.getShopping_nome());
					return ((deptComp == 0) ? (p1.getLoja_nome().compareTo(p2.getLoja_nome())==0 ? p1.getProduto().compareTo(p2.getProduto()) : 
						p1.getLoja_nome().compareTo(p2.getLoja_nome()))
						: deptComp);
				}
			});

			String shopping = null;
			String loja = null;
			for (Promocao p : promos){
				nomes.add(p.getProduto());
				images.add(p.getImagem_url());
				if (shopping==null){
					shopping=p.getShopping_nome();
					p.setPrimeira_s(true);
				}
				else if(p.getShopping_nome().compareTo(shopping)!=0){
					shopping=p.getShopping_nome();
					p.setPrimeira_s(true);
					loja = null;
				}
				if (loja==null){
					loja=p.getLoja_nome();
					p.setPrimeira_l(true);
				}
				else if(p.getLoja_nome().compareTo(loja)!=0){
					loja=p.getLoja_nome();
					p.setPrimeira_l(true);
				}
			}
		}

		// Called once the background activity has completed
		@Override
		protected void onPostExecute(String result) { //
			if(nomes != null && !nomes.isEmpty()){
				for(int i = 0; i<images.size();i++)
					bImages.add(BitmapFactory.decodeResource(Utils.res, R.drawable.loading_images));
				adapter = new PromocaoAdapter(ListPromo.this, bImages, promos, nomes);
				listview.setAdapter(adapter);
				new RetrieveImages().execute();
			}
			else{
				dialog.dismiss();
				setResult(RESULT_FIRST_USER);
				finish();
			}
		}
	}

	class RetrieveImages extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			try{
				Bitmap image = Utils.loadImageFromNetwork(images.get(0));
				promos.get(promos.size()-images.size()).setbImage(image);
				bImages.set(promos.size()-images.size(),image);
			}
			catch(Exception e){
				Log.e("Erro ao baixar as imagens.", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(loading){
				dialog.dismiss();
				loading = false;
			}
			images.remove(0);
			adapter.notifyDataSetChanged();
			if(images.size()>0)
				new RetrieveImages().execute();
		}
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			adapter.getFilter().filter(s);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		filterText.removeTextChangedListener(filterTextWatcher);
	}	
}
