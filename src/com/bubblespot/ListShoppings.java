package com.bubblespot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;



public class ListShoppings extends Activity{
	//private ListView list;
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<Shopping> shoppings;
	private ProgressDialog dialog;
	private GridView gridview;
	private Bundle b;
	private String text;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shoppings);
	        Bundle c = this.getIntent().getExtras();
	        this.text = c.getString("text");
	        
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, ListShoppings.this);
	        
	        dialog = ProgressDialog.show(this, "", "Loading...",true);
	        b = new Bundle();
	        shoppings = new ArrayList<Shopping>();
	        nomes = new ArrayList<String>();
	        bImages = new ArrayList<Bitmap>();
	        gridview = (GridView) findViewById(R.id.gridView1);
	        gridview.setNumColumns(1);
	        gridview.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Intent intent = new Intent(v.getContext(), ShoppingDetail.class);
	                Shopping sb = shoppings.get(position);
	                b.putString("shoppingNome", sb.getNome());
	                b.putString("shoppingLocal", sb.getLocalizacao());
	                b.putString("shoppingDescricao", sb.getDescricao());
	                b.putString("shoppingTelefone", sb.getTelefone());
	                b.putString("shoppingLatitude", sb.getLatitude());
	                b.putString("shoppingLongitude", sb.getLongitude());
	                b.putString("shoppingUrl", sb.getImagem_url());
	                b.putString("shoppingEmail", sb.getEmail());
	                intent.putExtras(b);
					startActivity(intent);
	        	}
	        });

	        new RetrieveShoppings().execute();
	        }
	 
	 class RetrieveShoppings extends AsyncTask<String, Integer, String> {

			@Override
			protected String doInBackground(String... arg0) {
				
				nomes.clear();
				bImages.clear();
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
						JSONObject shopping = jo.getJSONObject(i);
						String Nome = shopping.getString("nome");
						String Descricao = shopping.getString("descricao");
						String Imagem = shopping.getString("imagem");
						String Localizacao = shopping.getString("localizacao");
						String Latitude = shopping.getString("latitude");
						String Longitude = shopping.getString("longitude");
						String Telefone = shopping.getString("telefone");
						String Email = shopping.getString("email");
						
						bImages.add(Utils.loadImageFromNetwork(Imagem));
						nomes.add(Nome);
						Shopping s = new Shopping(Nome,Localizacao,Descricao,Telefone,Email,Latitude,Longitude,Imagem);
						shoppings.add(s);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				

				return null;
			}

			// Called once the background activity has completed
			@Override
			protected void onPostExecute(String result) { //
				if(nomes != null && !nomes.isEmpty()){
					gridview.setAdapter(new ImageAdapter(ListShoppings.this, bImages));
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
	 
	 
	
}
