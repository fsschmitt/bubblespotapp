package com.bubblespot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapter.ListEventosAdapter;

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

public class ListAllCultural extends Activity{
	private ArrayList<Evento> eventos;
	private ArrayList<String> nomes;
	private ProgressDialog dialog;
	private ListView listview;
	private String text;
	private ListEventosAdapter adapter;
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
		Search.pesquisa(this, ListAllCultural.this);

		filterText = (EditText) findViewById(R.id.filter_box);
		filterText.addTextChangedListener(filterTextWatcher);
		filterText.setHint("Filtrar eventos");

		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		eventos = new ArrayList<Evento>();
		nomes = new ArrayList<String>();
		listview = (ListView) findViewById(R.id.listView1);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(v.getContext(), Cultural.class);
				Evento evento = eventos.get(position);
				Bundle b = new Bundle();
				b.putString("text", "shoppings/"+evento.getIdShopping()+"/eventos/"+evento.getId()+".json");
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
					JSONObject evento = jo.getJSONObject(i);
					int idEvento = evento.getInt("id");
					int idShopping = evento.getInt("shopping_id");
					String imagem = evento.getString("imagem");
					String data = evento.getString("data");
					String detalhes = evento.getString("detalhes");
					String nome = evento.getString("nome");
					String local = evento.getString("local");
					String nomeShopping = evento.getString("shopping_nome");
					eventos.add(new Evento(idEvento,idShopping,nomeShopping,nome,data,local,detalhes,imagem));
				}
				ordenar(eventos);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		private void ordenar(ArrayList<Evento> eventos) {
			Collections.sort(eventos, new Comparator<Object>(){
				public int compare(Object obj1, Object obj2) {
					Evento l1 = (Evento) obj1;
					Evento l2 = (Evento) obj2;
					int deptComp = l1.getShopping().compareTo(l2.getShopping());
					return ((deptComp == 0) ? l1.getNome().compareTo(l2.getNome())
							: deptComp);
				}
			});

			String shopping = null;
			for (Evento l : eventos){
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
			if(eventos != null && !eventos.isEmpty()){
				adapter =  new ListEventosAdapter(mContext,nomes,eventos);
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
