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

public class ShopDetail extends Activity {

	private String nome;
	private int piso;
	private int numero;
	private String telefone;
	private String detalhes;
	private String imagem;
	private String tags;
	private String shopping;
	private ProgressDialog dialog;
	private Bitmap bImage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = ProgressDialog.show(this, "", "Loading...",true);
		Bundle b = this.getIntent().getExtras();
		this.nome = b.getString("lojaNome");
		this.piso = b.getInt("lojaPiso");
		this.numero = b.getInt("lojaNumero");
		this.telefone = b.getString("lojaTelefone");
		this.detalhes = b.getString("lojaDetalhes");
		this.imagem = b.getString("lojaImagem");
		this.tags = b.getString("lojaTags");
		this.shopping = b.getString("lojaShopping");
		
		
		new RetrieveLogo().execute();
		

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
			ShopDetail.this.setContentView(R.layout.shopdetail);
			Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(ShopDetail.this, ShopDetail.this);
			
			TextView loja_shopping = (TextView) ShopDetail.this.findViewById(R.id.loja_shopping);
			loja_shopping.setText(nome + "(" + shopping + ")");
			
			ImageView logo = (ImageView) ShopDetail.this.findViewById(R.id.loja_logo);
			logo.setImageBitmap(bImage);
			
			TextView loja_detalhes = (TextView) ShopDetail.this.findViewById(R.id.loja_detalhes);
			loja_detalhes.setText("\tPiso: " + piso + "\n\tNúmero: " + numero + "\n\tTelefone: " + telefone + "\n\tÁreas de Negócio: " + tags + "\n\tDetalhes: " + detalhes);
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