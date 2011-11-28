package com.bubblespot;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PromoDetail extends Activity {

	private String nome;
	private String shopping;
	private int loja_id;
	private ProgressDialog dialog;
	private Bitmap bImage;
	private Promocao promo;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = ProgressDialog.show(this, "", "Loading...",true);
		Bundle b = this.getIntent().getExtras();
		this.nome = b.getString("lojaNome");
		this.loja_id = b.getInt("lojaID");
		this.shopping = b.getString("lojaShopping");
		new RetrieveInfo().execute();
		

	}
	
	class RetrieveInfo extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			bImage = null;
			try {
				bImage = Utils.loadImageFromNetwork("http://placehold.it/200x150");
				//bImage = Utils.loadImageFromNetwork(imagem);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			PromoDetail.this.setContentView(R.layout.promodetail);
			Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(PromoDetail.this, PromoDetail.this);
			TextView text_promo = (TextView) PromoDetail.this.findViewById(R.id.text_promo);
			text_promo.setText(nome + " (" + shopping + ")");
			
			ImageView logo = (ImageView) PromoDetail.this.findViewById(R.id.promo_logo);
			logo.setImageBitmap(bImage);
			
			TextView loja_detalhes = (TextView) PromoDetail.this.findViewById(R.id.detalhes_promo);
			loja_detalhes.setText("\tPiso: " + "\n\tNúmero: " + "\n\tTelefone: " + "\n\tÁreas de Negócio: " +  "\n\tDetalhes: " );
			dialog.dismiss();
			
		}
		
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
			/*Dialog dialog = new Dialog(ShopDetail.this);
			dialog.setContentView(R.layout.shoppingdialog);
			dialog.setTitle("Detalhes do Shopping");
			dialog.setCancelable(true);
			TextView nome = (TextView) dialog.findViewById(R.id.sNome);
			nome.setText("Nome: "+this.nome);
			TextView local = (TextView) dialog.findViewById(R.id.sLocal);
			local.setText("Local: "+this.localizacao);
			TextView descricao = (TextView) dialog.findViewById(R.id.sDescricao);
			descricao.setText("Descrição: "+this.descricao);
			TextView telefone = (TextView) dialog.findViewById(R.id.sTelefone);
			telefone.setText("Telefone: "+this.telefone);
			TextView email = (TextView) dialog.findViewById(R.id.sEmail);
			email.setText("Email: "+this.email);
			TextView coordenadas = (TextView) dialog.findViewById(R.id.sCoordenadas);
			coordenadas.setText("Coordenadas:\n\t" + this.latitude + "\n\t" + this.longitude);
			dialog.show();*/

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
