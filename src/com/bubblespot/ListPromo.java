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



public class ListPromo extends Activity{
	//private ListView list;
	private int idLoja;
	private int idShopping;
	private String nomeLoja;
	private String nomeShopping;
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<Promocao> promos;
	private ProgressDialog dialog;
	private GridView gridview;
	private Bundle b;
	private String text;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shops);
	        Bundle c = this.getIntent().getExtras();
	        this.text = c.getString("text");
	        idLoja = c.getInt("idLoja");
	        idShopping = c.getInt("idShopping");
	        nomeLoja = c.getString("nomeLoja");
	        nomeShopping = c.getString("nomeShopping");
	        
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, ListPromo.this);
	        
	        dialog = ProgressDialog.show(this, "", "Loading...",true);
	        b = new Bundle();
	        promos = new ArrayList<Promocao>();
	        nomes = new ArrayList<String>();
	        bImages = new ArrayList<Bitmap>();
	        gridview = (GridView) findViewById(R.id.gridView1);
	        gridview.setNumColumns(1);
	        gridview.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Intent intent = new Intent(v.getContext(), PromoDetail.class);
	                Promocao promo = promos.get(position);
	                b.putInt("lojaID", promo.getId());
	                b.putString("nomeLoja", nomeLoja);
	                b.putInt("idShopping", idShopping);
	                b.putString("desconto", promo.getDesconto());
	                b.putString("shopping", nomeShopping);
	                intent.putExtras(b);
					startActivity(intent);
	        	}
	        });

	        new RetrieveLojas().execute();
	        }
	 
	 class RetrieveLojas extends AsyncTask<String, Integer, String> {

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
						JSONObject promo = jo.getJSONObject(i);
						int id = promo.getInt("id");
						String desconto = promo.getString("desconto");
						String dataf = promo.getString("dataf");
						String detalhes = promo.getString("detalhes");
						String imagem = promo.getString("imagem");
						String precof = promo.getString("precof");
						String precoi = promo.getString("precoi");
						String produto = promo.getString("produto");
						
						bImages.add(Utils.loadImageFromNetwork("http://placehold.it/200x150"));
						nomes.add(produto);
						Promocao p = new Promocao(id,dataf,desconto,detalhes,imagem,idLoja,precoi,precof,produto);
						promos.add(p);
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
					gridview.setAdapter(new PromocaoAdapter(ListPromo.this, bImages, promos));
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
