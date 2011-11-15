package com.bubblespot;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingDetail extends Activity {

	private String latitude;
	private String longitude;
	private String email;
	private String descricao;
	private String localizacao;
	private String nome;
	private String telefone;
	private String imagem_url;
	private ProgressDialog dialog;
	private Bitmap bImage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = ProgressDialog.show(this, "", "Loading...",true);
		Bundle b = this.getIntent().getExtras();
		this.nome = b.getString("shoppingNome");
		this.localizacao = b.getString("shoppingLocal");
		this.descricao = b.getString("shoppingDescricao");
		this.telefone = b.getString("shoppingTelefone");
		this.latitude = b.getString("shoppingLatitude");
		this.longitude = b.getString("shoppingLongitude");
		this.email = b.getString("shoppingEmail");
		this.imagem_url = b.getString("shoppingUrl");
		
		
		new RetrieveLogo().execute();
		

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
			ShoppingDetail.this.setContentView(R.layout.shoppingdetail);
			Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(ShoppingDetail.this, ShoppingDetail.this);
			ImageView logo = (ImageView) ShoppingDetail.this.findViewById(R.id.sdLogo);
			logo.setImageBitmap(bImage);
			
			
			Button bPromocoes = (Button) ShoppingDetail.this.findViewById(R.id.button2);
			bPromocoes.setText("Promoções");
			bPromocoes.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
				}
			});
			Button bAgenda = (Button) ShoppingDetail.this.findViewById(R.id.button3);
			bAgenda.setText("Agenda Cultural");
			bAgenda.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
				}
			});
			
			Button bCinema = (Button) ShoppingDetail.this.findViewById(R.id.button4);
			bCinema.setText("Cinema");
			bCinema.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
				}
			});
			
			Button bMapa = (Button) ShoppingDetail.this.findViewById(R.id.button5);
			bMapa.setText("Mapa");
			bMapa.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
				}
			});
			
			Button bDirecoes = (Button) ShoppingDetail.this.findViewById(R.id.button6);
			bDirecoes.setText("Obter Direções");
			bDirecoes.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View v) {
					Location l = Utils.getLocation(ShoppingDetail.this);
					if(l!=null){
			    	Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?"+
			    			"saddr=" + l.getLatitude()+","+ l.getLongitude() + 
			    			"&daddr=" + ShoppingDetail.this.latitude + "," + ShoppingDetail.this.longitude));
			    	startActivityForResult(i, 0);
					}
					else
						Toast.makeText(getApplicationContext(), "Não foi possivel obter a sua localização!", Toast.LENGTH_SHORT).show();
				}
			});
			
			Button bPesquisa = (Button) ShoppingDetail.this.findViewById(R.id.button7);
			bPesquisa.setText("Pesquisar");
			dialog.dismiss();
			
		}
		
	}
	
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.shopping_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.detalhes:
		{
			Dialog dialog = new Dialog(ShoppingDetail.this);
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
			dialog.show();

		}
		return true;
		case R.id.mapa:
			Dialog dialog = new Dialog(ShoppingDetail.this);
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
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}




}