package com.bubblespot.shoppings;

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

import com.bubblespot.Header;
import com.bubblespot.R;
import com.bubblespot.Search;
import com.bubblespot.Touch;
import com.bubblespot.Utils;
import com.bubblespot.cinema.Cinema;
import com.bubblespot.evento.Cultural;
import com.bubblespot.lojas.ListShops;
import com.bubblespot.promocoes.ListPromo;

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
	private String planta;
	private int id;
	private ProgressDialog dialog;
	private Bitmap bImage;
	private TextView dPromocoes;
	private TextView dAgenda;
	private TextView dCinema;
	private TextView dPlanta;
	private TextView dDirecoes;
	private TextView dLojas;
	private int LOJAS_REQUEST = 1;
	private int CINEMA_REQUEST = 2;
	private int PROMOCOES_REQUEST = 3;
	private int EVENTOS_REQUEST = 4;

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
		this.planta = b.getString("shoppingPlanta");
		this.id = b.getInt("id");
		byte[] byteImage = b.getByteArray("shoppingImageByte");
		if(byteImage != null){
			bImage =  BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

			ImageView logo = (ImageView) ShoppingDetail.this.findViewById(R.id.sdLogo);
			logo.setImageBitmap(bImage);
			dPromocoes.setVisibility(View.VISIBLE);
			dAgenda.setVisibility(View.VISIBLE);
			dCinema.setVisibility(View.VISIBLE);
			dPlanta.setVisibility(View.VISIBLE);
			dDirecoes.setVisibility(View.VISIBLE);
			dLojas.setVisibility(View.VISIBLE);
			dialog.dismiss();
		}
		else
		{
			new RetrieveLogo().execute();
		}
	}

	private void initButtons() {
		dPromocoes = (TextView) ShoppingDetail.this.findViewById(R.id.textPromocoes);
		dPromocoes.setCompoundDrawablesWithIntrinsicBounds(null, getBaseContext().getResources().getDrawable( R.drawable.promocao ), null, null);
		dPromocoes.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ListPromo.class);
				Bundle b = new Bundle();
				b.putString("text", Utils.link_shopping+id+Utils.link_promo_+Utils.link_format);
				b.putInt("idShopping", id);
				b.putString("nomeShopping", nome);
				intent.putExtras(b);
				startActivityForResult(intent, PROMOCOES_REQUEST);
			}
		});
		dPromocoes.setVisibility(View.GONE);

		dAgenda = (TextView) ShoppingDetail.this.findViewById(R.id.textCultural);
		dAgenda.setCompoundDrawablesWithIntrinsicBounds(null, getBaseContext().getResources().getDrawable( R.drawable.cultural ), null, null);
		dAgenda.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, Cultural.class);
				Bundle b = new Bundle();
				b.putString("text", Utils.link_shopping+id+Utils.link_evento_+Utils.link_format);
				b.putInt("idShopping", id);
				b.putString("nomeShopping", nome);
				intent.putExtras(b);
				startActivityForResult(intent, EVENTOS_REQUEST);
			}
		});
		dAgenda.setVisibility(View.GONE);

		dCinema = (TextView) ShoppingDetail.this.findViewById(R.id.textCinema);
		dCinema.setCompoundDrawablesWithIntrinsicBounds(null, getBaseContext().getResources().getDrawable( R.drawable.cinema ), null, null);
		dCinema.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, Cinema.class);
				Bundle b = new Bundle();
				b.putString("text", Utils.link_shopping+id+Utils.link_filme_+Utils.link_format);
				b.putInt("idShopping", id);
				b.putString("nomeShopping", nome);
				intent.putExtras(b);
				startActivityForResult(intent, CINEMA_REQUEST);
			}
		});
		dCinema.setVisibility(View.GONE);

		
		dPlanta = (TextView) ShoppingDetail.this.findViewById(R.id.textPlanta);
		dPlanta.setCompoundDrawablesWithIntrinsicBounds(null, getBaseContext().getResources().getDrawable( R.drawable.planta ), null, null);
		dPlanta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, Touch.class);
				Bundle b = new Bundle();
				b.putString("planta", planta);
				intent.putExtras(b);
				startActivityForResult(intent, 0);
			}
		});
		dPlanta.setVisibility(View.GONE);

		dDirecoes = (TextView) ShoppingDetail.this.findViewById(R.id.textDirecoes);
		dDirecoes.setCompoundDrawablesWithIntrinsicBounds(null, getBaseContext().getResources().getDrawable( R.drawable.direcoes ), null, null);
		dDirecoes.setOnClickListener(new View.OnClickListener() {
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
					Toast.makeText(getApplicationContext(), "Não foi possivel obter a sua localização!", Toast.LENGTH_LONG).show();
			}
		});
		dDirecoes.setVisibility(View.GONE);

		dLojas = (TextView) ShoppingDetail.this.findViewById(R.id.textLojas);
		dLojas.setCompoundDrawablesWithIntrinsicBounds(null, getBaseContext().getResources().getDrawable( R.drawable.lojas ), null, null);
		dLojas.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ListShops.class);
				Bundle b = new Bundle();
				b.putString("text", Utils.link_shopping+id+Utils.link_loja_+Utils.link_format);
				b.putInt("idShopping", id);
				intent.putExtras(b);
				startActivityForResult(intent, LOJAS_REQUEST);
			}
		});
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
			dPromocoes.setVisibility(View.VISIBLE);
			dAgenda.setVisibility(View.VISIBLE);
			dCinema.setVisibility(View.VISIBLE);
			dPlanta.setVisibility(View.VISIBLE);
			dDirecoes.setVisibility(View.VISIBLE);
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
		if(resultCode == RESULT_FIRST_USER && requestCode == LOJAS_REQUEST) {
			Toast.makeText(this, "Este shopping não tem nenhuma loja registada!", Toast.LENGTH_LONG).show();
		}
		else if(resultCode == RESULT_FIRST_USER && requestCode == CINEMA_REQUEST) {
			Toast.makeText(this, "Este shopping não tem nenhum filme registado!", Toast.LENGTH_LONG).show();
		}
		else if(resultCode == RESULT_FIRST_USER && requestCode == PROMOCOES_REQUEST) {
			Toast.makeText(this, "Este shopping não tem nenhuma promoção registada!", Toast.LENGTH_LONG).show();
		}
		else if(resultCode == RESULT_FIRST_USER && requestCode == EVENTOS_REQUEST) {
			Toast.makeText(this, "Este shopping não tem nenhum evento registado!", Toast.LENGTH_LONG).show();
		}
	}
}