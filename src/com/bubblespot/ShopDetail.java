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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.adapter.GalleryAdapter;

public class ShopDetail extends Activity {

	private int id;
	private String nome;
	private int piso;
	private int numero;
	private String telefone;
	private String detalhes;
	private String imagem;
	private String tags;
	private String shopping;
	private int idShopping;
	private ProgressDialog dialog;
	private Bitmap bImage;
	private ArrayList<Bitmap> bImages;
	private ArrayList<String> nomes;
	private ArrayList<String> imagens;
	private ArrayList<Promocao> promos;
	private Context context;
	private Gallery gPromos;
	private GalleryAdapter iAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		bImages = new ArrayList<Bitmap>();
        nomes = new ArrayList<String>();
        imagens = new ArrayList<String>();
        promos = new ArrayList<Promocao>();
		ShopDetail.this.setContentView(R.layout.shopdetail);
		Header header = (Header) findViewById(R.id.header);
	    header.initHeader();
		Search.pesquisa(ShopDetail.this, ShopDetail.this);
		gPromos = (Gallery) findViewById(R.id.galleryPromos);
		iAdapter = new GalleryAdapter(context, bImages, nomes);
		gPromos.setAdapter(iAdapter);
		Bundle b = this.getIntent().getExtras();
		this.id = b.getInt("lojaID");
		this.nome = b.getString("lojaNome");
		this.piso = b.getInt("lojaPiso");
		this.numero = b.getInt("lojaNumero");
		this.telefone = b.getString("lojaTelefone");
		this.detalhes = b.getString("lojaDetalhes");
		this.imagem = b.getString("lojaImagem");
		this.tags = b.getString("lojaTags");
		this.shopping = b.getString("lojaShopping");
		this.idShopping = b.getInt("idShopping");
		
		TextView loja_shopping = (TextView) findViewById(R.id.loja_shopping);
		loja_shopping.setText(nome + " (" + shopping + ")");
		
		TextView lojaPiso = (TextView) findViewById(R.id.lojaPiso);
		lojaPiso.setText("Piso: "+piso);
		
		TextView lojaNumero = (TextView) findViewById(R.id.lojaNumero);
		lojaNumero.setText("Número da Loja: "+numero);
		
		TextView lojaTelefone = (TextView) findViewById(R.id.lojaTelefone);
		lojaTelefone.setText("Número de Telefone: "+telefone);
		
		TextView lojaArea = (TextView) findViewById(R.id.lojaArea);
		lojaArea.setText("Áreas de Negócio: "+tags);
		
		TextView lojaDescricao = (TextView) findViewById(R.id.lojaDescricao);
		lojaDescricao.setText(detalhes);
		
		gPromos.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("rawtypes")
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Intent intent = new Intent(v.getContext(), PromoDetail.class);
                Promocao promo = promos.get(position);
                Bundle b = new Bundle();
                b.putInt("idLoja", ShopDetail.this.id);
                b.putInt("id", promo.getId());
                b.putInt("idShopping", idShopping);
                b.putString("nomeLoja", promo.getLoja_nome());
                b.putString("desconto", promo.getDesconto());
                b.putString("produto", promo.getProduto());
                b.putString("detalhes", promo.getDetalhes());
                b.putString("precoFinal", promo.getPreco_final());
                b.putString("precoInicial", promo.getPreco_inicial());
                b.putString("dataFinal", promo.getData_final());
                b.putString("imagem", promo.getImagem_url());
                b.putString("shopping", shopping);
                Bitmap image = promo.getbImage();
                if(image != null){
                	b.putByteArray("promoImageByte", Utils.encodeBitmap(image));
                }
                else
                	b.putByteArray("promoImageByte", null);
                intent.putExtras(b);
				startActivity(intent);
			}
		});
		byte[] byteImage = b.getByteArray("shopImageByte");
		if(byteImage != null){
			bImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
			ImageView logo = (ImageView) ShopDetail.this.findViewById(R.id.loja_logo);
			logo.setImageBitmap(bImage);
		}
		else {
			dialog = ProgressDialog.show(this, "", "A Carregar...",true);
	        dialog.setCancelable(true);
	        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	            	finish();
	                }
	        });
			new RetrieveLogo().execute();
		}
		new RetrievePromo().execute();
		
		
		

	}
	
	class RetrieveLogo extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			bImage = null;
			try {
				bImage = Utils.loadImageFromNetwork(imagem);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
	
			ImageView logo = (ImageView) ShopDetail.this.findViewById(R.id.loja_logo);
			logo.setImageBitmap(bImage);
			
			dialog.dismiss();
			
		}
		
	}
	
	class RetrievePromo extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			nomes.clear();
			promos.clear();
			bImages.clear();
			String uri = "http://bubblespot.heroku.com/shoppings/"+idShopping+"/lojas/"+id+"/promos.json";
			
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
				if(line != null){
					jo = new JSONArray(line);
					for (int i = 0; i < jo.length(); i++) {
						JSONObject promo = jo.getJSONObject(i);
						int idPromo = promo.getInt("id");
						String desconto = promo.getString("desconto");
						String dataf = promo.getString("dataf");
						String detalhes = promo.getString("detalhes");
						String imagemUrl = promo.getString("imagem");
						String precof = promo.getString("precof");
						String precoi = promo.getString("precoi");
						String produto = promo.getString("produto");
						String lojaNome = promo.getString("loja_nome");
						nomes.add(precof);
						imagens.add(imagemUrl);
						Promocao p = new Promocao(idPromo,dataf,desconto,detalhes,imagemUrl,id,lojaNome,precoi,precof,produto);
						promos.add(p);
					}
				}
				else return null;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;	
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(nomes != null && !nomes.isEmpty() && !imagens.isEmpty()){
				for(int i = 0; i<imagens.size();i++)
					bImages.add(BitmapFactory.decodeResource(Utils.res, R.drawable.loading_images));
				iAdapter.notifyDataSetChanged();
				new RetrieveImages().execute();
			}
			else{
				dialog.dismiss();
			}	
		}
		
	}
	
	class RetrieveImages extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			
			try{
				Bitmap image = Utils.loadImageFromNetwork(imagens.get(0));
				image = Bitmap.createScaledBitmap(image, image.getWidth()*240/image.getHeight(), 240, false);
				bImages.set(promos.size()-imagens.size(),image);
				promos.get(promos.size()-imagens.size()).setbImage(image);
			}
			catch(Exception e){
				try {
					Bitmap image = Utils.loadImageFromNetwork("http://placehold.it/128");
					image = Bitmap.createScaledBitmap(image, image.getWidth()*240/image.getHeight(), 240, false);
					bImages.set(promos.size()-imagens.size(),image);
					promos.get(promos.size()-imagens.size()).setbImage(image);
				} catch (Exception e1) {
					Log.e("Erro ao baixar as imagens.", e1.getMessage());
				}
				Log.e("Erro ao baixar as imagens.", e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			imagens.remove(0);
			iAdapter.notifyDataSetChanged();
			if(imagens.size()>0)
				new RetrieveImages().execute();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.shop_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.loja_promocoes:
		{
			Intent intent = new Intent(context, ListPromo.class);
			Bundle b = new Bundle();
			b.putString("text", "shoppings/"+idShopping+"/lojas/"+id+"/promos.json");
			b.putInt("idShopping", idShopping);
			b.putInt("idLoja", id);
			b.putString("nomeShopping", shopping);
			intent.putExtras(b);
			startActivityForResult(intent, 0);
		}
		return true;
		case R.id.loja_partilhar:
			/*
			Dialog dialog = new Dialog(ShopDetail.this);
			dialog.setContentView(R.layout.shoppingmap);
			dialog.setTitle("Localização do Shopping");
			dialog.setCancelable(true);
			ImageView mapa = (ImageView) dialog.findViewById(R.id.mapa);
			try {
				mapa.setImageBitmap(Utils.loadImageFromNetwork("http://maps.googleapis.com/maps/api/staticmap?markers=" + this.latitude + "," + this.longitude + "&zoom=16&size=400x400&sensor=false"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			dialog.show();
			return true;*/
		default:
			return super.onOptionsItemSelected(item);
		}
	}




}