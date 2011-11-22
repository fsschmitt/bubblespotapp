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



public class ListShops extends Activity{
	//private ListView list;
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<Loja> lojas;
	private ProgressDialog dialog;
	private GridView gridview;
	private Bundle b;
	private String text;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shops);
	        Bundle c = this.getIntent().getExtras();
	        this.text = c.getString("text");
	        
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, ListShops.this);
	        
	        dialog = ProgressDialog.show(this, "", "Loading...",true);
	        b = new Bundle();
	        lojas = new ArrayList<Loja>();
	        nomes = new ArrayList<String>();
	        bImages = new ArrayList<Bitmap>();
	        gridview = (GridView) findViewById(R.id.gridView1);
	        gridview.setNumColumns(2);
	        gridview.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Intent intent = new Intent(v.getContext(), ShopDetail.class);
	                Loja loja = lojas.get(position);
	                b.putString("lojaNome", loja.getNome());
	                b.putInt("lojaPiso", loja.getPiso());
	                b.putInt("lojaNumero", loja.getNumero());
	                b.putString("lojaTelefone", loja.getTelefone());
	                b.putString("lojaDetalhes", loja.getDetalhes());
	                b.putString("lojaImagem", loja.getImagem());
	                b.putString("lojaTags", loja.getTags());
	                b.putString("lojaShopping", loja.getShopping());
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
						JSONObject loja = jo.getJSONObject(i);
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
						bImages.add(b);
						nomes.add(Nome);
						Loja s = new Loja(Nome, piso, numero, Telefone, Detalhes, Imagem, tags, shoppingId);
						lojas.add(s);
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
					gridview.setAdapter(new ImageAdapter(ListShops.this, bImages));
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
