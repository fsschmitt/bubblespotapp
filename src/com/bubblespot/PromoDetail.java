package com.bubblespot;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PromoDetail extends Activity {

	private String shopping;
	private int id;
	private int idLoja;
	private int idShopping;
	private String nomeLoja;
	private String imagem_url;
	private String produto;
	private String detalhes;
	private String desconto;
	private String precoFinal;
	private String precoInicial;
	private String dataFinal;
	private ProgressDialog dialog;
	private Bitmap bImage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PromoDetail.this.setContentView(R.layout.promodetail);
		Header header = (Header) findViewById(R.id.header);
	    header.initHeader();
		Search.pesquisa(PromoDetail.this, PromoDetail.this);
		
		Bundle b = this.getIntent().getExtras();
		setId(b.getInt("id"));
		setIdShopping(b.getInt("idShopping"));
		setIdLoja(b.getInt("idLoja"));
		nomeLoja = b.getString("nomeLoja");
		desconto = b.getString("desconto");
		produto = b.getString("produto");
		detalhes = b.getString("detalhes");
		precoFinal = b.getString("precoFinal");
		precoInicial = b.getString("precoInicial");
		dataFinal = b.getString("dataFinal");
		shopping = b.getString("shopping");
		imagem_url = b.getString("imagem");
        b.putString("shopping", shopping);
        
        
        TextView text_promo = (TextView) PromoDetail.this.findViewById(R.id.promo_text);
		text_promo.setText(produto);
		
		TextView promoLoja = (TextView) PromoDetail.this.findViewById(R.id.promoLoja);
		promoLoja.setText(nomeLoja + " (" + shopping + ")");
		
		TextView promoDetalhes = (TextView) PromoDetail.this.findViewById(R.id.promoDetalhes);
		promoDetalhes.setText(detalhes);
        
		TextView promoAntes = (TextView) PromoDetail.this.findViewById(R.id.promoAntes);
		promoAntes.setText("Antes: "+precoInicial);
		
		TextView promoDepois = (TextView) PromoDetail.this.findViewById(R.id.promoDepois);
		promoDepois.setText("Depois: "+precoFinal);
		
		TextView promoDesconto = (TextView) PromoDetail.this.findViewById(R.id.promoDesconto);
		promoDesconto.setText("Desconto: "+desconto+"%");
		
		TextView promoDataLimite = (TextView) PromoDetail.this.findViewById(R.id.promoDataLimite);
		promoDataLimite.setText(dataFinal);
        
        
		byte[] byteImage = b.getByteArray("promoImageByte");
		if(byteImage != null){
			bImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
			ImageView logo = (ImageView) PromoDetail.this.findViewById(R.id.promo_logo);
			logo.setImageBitmap(bImage);
		}
		else{
			dialog = ProgressDialog.show(this, "", "A Carregar...",true);
	        dialog.setCancelable(true);
	        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	            	finish();
	                }
	        });
			new RetrieveLogo().execute();
		}
		

	}
	
	class RetrieveLogo extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			
			bImage = null;
			try {
				bImage = Utils.loadImageFromNetwork(imagem_url);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {

			ImageView logo = (ImageView) PromoDetail.this.findViewById(R.id.promo_logo);
			logo.setImageBitmap(bImage);
			
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

	public int getIdShopping() {
		return idShopping;
	}

	public void setIdShopping(int idShopping) {
		this.idShopping = idShopping;
	}

	public int getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(int idLoja) {
		this.idLoja = idLoja;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}




}
