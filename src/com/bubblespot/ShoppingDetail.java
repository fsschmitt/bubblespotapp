package com.bubblespot;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingDetail extends Activity {

	private Context context;
	private String latitude;
	private String longitude;
	private String email;
	private String descricao;
	private String localizacao;
	private String nome;
	private String telefone;
	private String imagem_url;
	private int id;
	private ProgressDialog dialog;
	private Bitmap bImage;
	private ImageView bPromocoes;
	private ImageView bAgenda;
	private ImageView bCinema;
	private ImageView bPlanta;
	private ImageView bDirecoes;
	private ImageView bLojas;
	private TextView dPromocoes;
	private TextView dAgenda;
	private TextView dCinema;
	private TextView dPlanta;
	private TextView dDirecoes;
	private TextView dLojas;
	private int SEARCH_REQUEST = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		ShoppingDetail.this.setContentView(R.layout.shoppingdetail);
		Header header = (Header) findViewById(R.id.header);
	    header.initHeader();
	    Search.pesquisa(this, this);
	    initButtons();
		
	    dialog = ProgressDialog.show(this, "", "A Carregar...",true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            	finish();
                }
        });
		Bundle b = this.getIntent().getExtras();
		this.nome = b.getString("shoppingNome");
		this.localizacao = b.getString("shoppingLocal");
		this.descricao = b.getString("shoppingDescricao");
		this.telefone = b.getString("shoppingTelefone");
		this.latitude = b.getString("shoppingLatitude");
		this.longitude = b.getString("shoppingLongitude");
		this.email = b.getString("shoppingEmail");
		this.imagem_url = b.getString("shoppingUrl");
		this.id = b.getInt("id");
		byte[] byteImage = b.getByteArray("shoppingImageByte");
		if(byteImage != null){
			bImage =  BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
			
			
			ImageView logo = (ImageView) ShoppingDetail.this.findViewById(R.id.sdLogo);
			logo.setImageBitmap(bImage);
			bPromocoes.setVisibility(View.VISIBLE);
			dPromocoes.setVisibility(View.VISIBLE);
			bAgenda.setVisibility(View.VISIBLE);
			dAgenda.setVisibility(View.VISIBLE);
			bCinema.setVisibility(View.VISIBLE);
			dCinema.setVisibility(View.VISIBLE);
			bPlanta.setVisibility(View.VISIBLE);
			dPlanta.setVisibility(View.VISIBLE);
			bDirecoes.setVisibility(View.VISIBLE);
			dDirecoes.setVisibility(View.VISIBLE);
			bLojas.setVisibility(View.VISIBLE);
			dLojas.setVisibility(View.VISIBLE);
			dialog.dismiss();
		}
		else
		{
			new RetrieveLogo().execute();
		}
		
	}
	
	private void initButtons() {
		bPromocoes = (ImageView) ShoppingDetail.this.findViewById(R.id.imagePromocoes);
		bPromocoes.setVisibility(View.GONE);
		bPromocoes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ListPromo.class);
				Bundle b = new Bundle();
				b.putString("text", "shoppings/"+id+"/promos.json");
				b.putInt("idShopping", id);
				b.putString("nomeShopping", nome);
				intent.putExtras(b);
				startActivityForResult(intent, 0);
			}
		});
		dPromocoes = (TextView) ShoppingDetail.this.findViewById(R.id.textPromocoes);
		dPromocoes.setVisibility(View.GONE);
		
		bAgenda = (ImageView) ShoppingDetail.this.findViewById(R.id.imageCultural);
		bAgenda.setVisibility(View.GONE);
		bAgenda.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
			}
		});
		dAgenda = (TextView) ShoppingDetail.this.findViewById(R.id.textCultural);
		dAgenda.setVisibility(View.GONE);
		
		bCinema = (ImageView) ShoppingDetail.this.findViewById(R.id.imageCinema);
		bCinema.setVisibility(View.GONE);
		bCinema.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
			}
		});
		dCinema = (TextView) ShoppingDetail.this.findViewById(R.id.textCinema);
		dCinema.setVisibility(View.GONE);
		
		bPlanta = (ImageView) ShoppingDetail.this.findViewById(R.id.imagePlanta);
		bPlanta.setVisibility(View.GONE);
		bPlanta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(ShoppingDetail.this, "Brevemente Disponível", Toast.LENGTH_SHORT).show();
			}
		});
		dPlanta = (TextView) ShoppingDetail.this.findViewById(R.id.textPlanta);
		dPlanta.setVisibility(View.GONE);
		
		bDirecoes = (ImageView) ShoppingDetail.this.findViewById(R.id.imageDirecoes);
		bDirecoes.setVisibility(View.GONE);
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
		dDirecoes = (TextView) ShoppingDetail.this.findViewById(R.id.textDirecoes);
		dDirecoes.setVisibility(View.GONE);
		
		bLojas = (ImageView) ShoppingDetail.this.findViewById(R.id.imageLojas);
		bLojas.setVisibility(View.GONE);
		bLojas.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), ListShops.class);
					Bundle b = new Bundle();
					b.putString("text", "shoppings/"+id+"/lojas?format=json");
					b.putInt("idShopping", id);
					intent.putExtras(b);
					startActivityForResult(intent, SEARCH_REQUEST);
			}
		});
		dLojas = (TextView) ShoppingDetail.this.findViewById(R.id.textLojas);
		dLojas.setVisibility(View.GONE);
		
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
			ImageView logo = (ImageView) ShoppingDetail.this.findViewById(R.id.sdLogo);
			logo.setImageBitmap(bImage);
			bPromocoes.setVisibility(View.VISIBLE);
			dPromocoes.setVisibility(View.VISIBLE);
			bAgenda.setVisibility(View.VISIBLE);
			dAgenda.setVisibility(View.VISIBLE);
			bCinema.setVisibility(View.VISIBLE);
			dCinema.setVisibility(View.VISIBLE);
			bPlanta.setVisibility(View.VISIBLE);
			dPlanta.setVisibility(View.VISIBLE);
			bDirecoes.setVisibility(View.VISIBLE);
			dDirecoes.setVisibility(View.VISIBLE);
			bLojas.setVisibility(View.VISIBLE);
			dLojas.setVisibility(View.VISIBLE);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(resultCode == RESULT_FIRST_USER && requestCode == SEARCH_REQUEST) {
	    	Toast.makeText(this, "Este shopping não tem nenhuma loja registada!", Toast.LENGTH_LONG).show();
	    }     
	}


}