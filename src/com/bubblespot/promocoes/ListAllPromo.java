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
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.adapter.ListPromosAdapter;
import com.bubblespot.Header;
import com.bubblespot.R;
import com.bubblespot.Search;
import com.bubblespot.Utils;

public class ListAllPromo extends Activity{
	private ArrayList<Promocao> promocoes;
	private ArrayList<String> nomes;
	private ProgressDialog dialog;
	private ListView listview;
	private String text;
	private ListPromosAdapter adapter;
	private EditText filterText;
	private Context mContext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allshops);
		mContext = this;
		Bundle c = this.getIntent().getExtras();
		this.text = c.getString("text");
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, ListAllPromo.this);

		filterText = (EditText) findViewById(R.id.filter_box);
		filterText.addTextChangedListener(filterTextWatcher);
		filterText.setHint("Filtrar Promoções");

		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		new Bundle();
		promocoes = new ArrayList<Promocao>();
		nomes = new ArrayList<String>();
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

					nomes.add(produto);
					Promocao p = new Promocao(id,dataf,desconto,detalhes,imagem,idLoja, lojaNome,shoppingId,shoppingNome,precoi,precof,produto);
					promocoes.add(p);
				}
				ordenar(promocoes);
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
			if(promocoes != null && !promocoes.isEmpty()){
				adapter =  new ListPromosAdapter(mContext,nomes,promocoes);
				listview.setAdapter(adapter);
				listview.setTextFilterEnabled(true);
				dialog.dismiss();
			}
			else{
				dialog.dismiss();
				setResult(RESULT_FIRST_USER);
				finish();
			}
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