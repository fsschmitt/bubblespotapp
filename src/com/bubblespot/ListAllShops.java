package com.bubblespot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.adapter.ListAdapter;



public class ListAllShops extends Activity{
	private int idShopping;
	private ArrayList<Loja> lojas;
	private ArrayList<String> nomes;
	private ProgressDialog dialog;
	private ListView listview;
	private Bundle b;
	private String text;
	private ListAdapter adapter;
	private EditText filterText;
	private Context mContext;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.allshops);
	        mContext = this;
	        Bundle c = this.getIntent().getExtras();
	        this.text = c.getString("text");
	        this.idShopping = c.getInt("idShopping");
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, ListAllShops.this);
			
			filterText = (EditText) findViewById(R.id.filter_box);
		    filterText.addTextChangedListener(filterTextWatcher);
	        
	        dialog = ProgressDialog.show(this, "", "Loading...",true);
	        b = new Bundle();
	        lojas = new ArrayList<Loja>();
	        nomes = new ArrayList<String>();
	        listview = (ListView) findViewById(R.id.listView1);
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Intent intent = new Intent(v.getContext(), ShopDetail.class);
	                Loja loja = lojas.get(position);
	                b.putInt("lojaID", loja.getId());
	                b.putString("lojaNome", loja.getNome());
	                b.putInt("lojaPiso", loja.getPiso());
	                b.putInt("lojaNumero", loja.getNumero());
	                b.putString("lojaTelefone", loja.getTelefone());
	                b.putString("lojaDetalhes", loja.getDetalhes());
	                b.putString("lojaImagem", loja.getImagem());
	                b.putString("lojaTags", loja.getTags());
	                b.putString("lojaShopping", loja.getShopping());
	                b.putInt("idShopping", idShopping);
	                intent.putExtras(b);
					startActivity(intent);
	        	}
	        });

	        new RetrieveLojas().execute();
	        }
	 
	 class RetrieveLojas extends AsyncTask<String, Integer, String> {

			@Override
			protected String doInBackground(String... arg0) {
				
				String uri = "http://bubblespot.heroku.com/" + text;
				
				URL url = null;
				try {
					url = new URL(uri);				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String line = null;
				JSONArray jo = null;
				try {
					line = getJSONLine(url);
					jo = new JSONArray(line);
					for (int i = 0; i < jo.length(); i++) {
						JSONObject loja = jo.getJSONObject(i);
						int id = loja.getInt("id");
						String Nome = loja.getString("nome");
						int piso = loja.getInt("piso");
						int numero = loja.getInt("numero");
						String Telefone = loja.getString("telefone");
						String Detalhes = loja.getString("detalhes");
						String Imagem = loja.getString("imagem");
						String tags = loja.getString("tags");
						String shoppingId = loja.getString("shopping_nome");
						Bitmap b = Utils.loadImageFromNetwork(Imagem);
						b = Bitmap.createScaledBitmap(b, b.getWidth()*120/b.getHeight(), 120, false);
						Loja s = new Loja(id, Nome, piso, numero, Telefone, Detalhes, Imagem, tags, shoppingId);
						lojas.add(s);
					}
					ordenar(lojas);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}

			private void ordenar(ArrayList<Loja> lojas) {
				Collections.sort(lojas, new Comparator<Object>(){
					public int compare(Object obj1, Object obj2) {
					    Loja l1 = (Loja) obj1;
					    Loja l2 = (Loja) obj2;
					    int deptComp = l1.getShopping().compareTo(l2.getShopping());

					    return ((deptComp == 0) ? l1.getNome().compareTo(l2.getNome())
					        : deptComp);
					  }
					
				});
				
				String shopping = null;
				for (Loja l : lojas){
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
				if(lojas != null && !lojas.isEmpty()){
					adapter =  new ListAdapter(mContext,nomes,lojas);
					//adapter = new ListAdapter(mContext, lojas);
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
	 
	 public static String getJSONLine(URL url) throws IOException {
			BufferedReader in;

			URLConnection tc = url.openConnection();
			tc.setDoInput(true);
			tc.setDoOutput(true);
			in = new BufferedReader(new InputStreamReader(tc.getInputStream()));	
			return in.readLine();
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
