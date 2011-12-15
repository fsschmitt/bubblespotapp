package com.bubblespot.cinema;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.adapter.ListFilmesAdapter;
import com.bubblespot.Header;
import com.bubblespot.R;
import com.bubblespot.Search;
import com.bubblespot.Utils;

public class ListAllCinema extends Activity{
	private ArrayList<Filme> filmes;
	private ArrayList<String> nomes;
	private ProgressDialog dialog;
	private ListView listview;
	private String text;
	private ListFilmesAdapter adapter;
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
		Search.pesquisa(this, ListAllCinema.this);

		filterText = (EditText) findViewById(R.id.filter_box);
		filterText.addTextChangedListener(filterTextWatcher);
		filterText.setHint("Filtrar filmes");

		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		filmes = new ArrayList<Filme>();
		nomes = new ArrayList<String>();
		listview = (ListView) findViewById(R.id.listView1);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(v.getContext(), Cinema.class);
				Filme filme = filmes.get(position);
				Bundle b = new Bundle();
				b.putString("text", "shoppings/"+filme.getIdShopping()+"/filmes/"+filme.getId()+".json");
				intent.putExtras(b);
				startActivity(intent);
			}
		});

		new RetrieveFilmes().execute();
	}

	class RetrieveFilmes extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {

			String uri = "http://bubblespot.heroku.com/" + text;
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
					JSONObject filme = jo.getJSONObject(i);
					int id = filme.getInt("id");
					String nome = filme.getString("nome");
					String imagem_url = filme.getString("imagem");
					String sala = filme.getString("sala");
					String detalhes = filme.getString("detalhes");
					String horarios = filme.getString("horarios");
					String trailer = filme.getString("trailer");
					String shoppingNome = filme.getString("shopping_nome");
					int shoppingId = filme.getInt("shopping_id");
					filmes.add(new Filme(id,shoppingId,shoppingNome,nome,detalhes,sala,horarios,imagem_url,trailer));
				}
				ordenar(filmes);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		private void ordenar(ArrayList<Filme> filmes) {
			Collections.sort(filmes, new Comparator<Object>(){
				public int compare(Object obj1, Object obj2) {
					Filme l1 = (Filme) obj1;
					Filme l2 = (Filme) obj2;
					int deptComp = l1.getShopping().compareTo(l2.getShopping());
					return ((deptComp == 0) ? l1.getNome().compareTo(l2.getNome())
							: deptComp);
				}
			});

			String shopping = null;
			for (Filme l : filmes){
				nomes.add(l.getNome());
				if (shopping==null){
					shopping=l.getShopping();
					l.setPrimeira(true);
				}
				else if(l.getShopping().compareTo(shopping)!=0){
					shopping=l.getShopping();
					l.setPrimeira(true);
				}
			}
		}

		// Called once the background activity has completed
		@Override
		protected void onPostExecute(String result) { //
			if(filmes != null && !filmes.isEmpty()){
				adapter =  new ListFilmesAdapter(mContext,nomes,filmes);
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
