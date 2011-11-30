package com.bubblespot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapter.ImageAdapterShopping;
import com.bubblespot.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class ListShoppings extends Activity{
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<Shopping> shoppings;
	private ArrayList<String> images;
	private ProgressDialog dialog;
	private ListView listview;
	private ImageAdapterShopping iAdapter;
	private Bundle b;
	private String text;
	private Boolean loading;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shoppings);
	        Bundle c = this.getIntent().getExtras();
	        this.text = c.getString("text");
	        
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, ListShoppings.this);
	        
			dialog = ProgressDialog.show(this, "", "A Carregar...",true);
	        dialog.setCancelable(true);
	        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	            	finish();
	                }
	        });
	        b = new Bundle();
	        loading = true;
	        shoppings = new ArrayList<Shopping>();
	        nomes = new ArrayList<String>();
	        bImages = new ArrayList<Bitmap>();
	        images = new ArrayList<String>();
	        listview = (ListView) findViewById(R.id.listView1);
	        iAdapter = new ImageAdapterShopping(ListShoppings.this, bImages);
	        listview.setAdapter(iAdapter);
	        listview.setOnItemClickListener(new OnItemClickListener() {
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
	                b.putInt("id", sb.getId());
	                Bitmap image = sb.getbImage();
	                if(image != null){
	                	b.putByteArray("shoppingImageByte", Utils.encodeBitmap(image));
	                }
	                else
	                	b.putByteArray("shoppingImageByte", null);
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
					line = Utils.getJSONLine(url);
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
						int id = shopping.getInt("id");
						images.add(Imagem);
						nomes.add(Nome);
						Shopping s = new Shopping(id,Nome,Localizacao,Descricao,Telefone,Email,Latitude,Longitude,Imagem);
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
			protected void onPostExecute(String result) { 
				if(nomes != null && !nomes.isEmpty() && !images.isEmpty()){
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
					if(loading){
						Bitmap image = Utils.loadImageFromNetwork(images.get(0));
						bImages.add(image);
						shoppings.get(0).setbImage(image);
					}
					else{
						Bitmap image = Utils.loadImageFromNetwork(images.get(0));
						bImages.add(bImages.size()-1,image);
						shoppings.get(bImages.size()-2).setbImage(image);
					}
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
					bImages.add(Utils.overlay(BitmapFactory.decodeResource(Utils.res, R.drawable.loading_shoppings),BitmapFactory.decodeResource(Utils.res, R.drawable.icon)));
					loading = false;
				}
				images.remove(0);
				iAdapter.notifyDataSetChanged();
				if(images.size()>0){
					new RetrieveImages().execute();
				}
				else
					bImages.remove(bImages.size()-1);
			}
		}
}
