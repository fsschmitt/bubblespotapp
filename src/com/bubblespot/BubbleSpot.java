package com.bubblespot;

import com.bubblespot.cinema.ListAllCinema;
import com.bubblespot.evento.ListAllCultural;
import com.bubblespot.lojas.ListAllShops;
import com.bubblespot.promocoes.ListAllPromo;
import com.bubblespot.shoppings.ListShoppings;
import com.bubblespot.shoppings.ShoppingNear;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BubbleSpot extends Activity {
	public static Context mainCont;
	/** Called when the activity is first created. */

	private Bundle b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		mainCont=this;
		Utils.res = this.getResources();
		Utils.tf = Typeface.createFromAsset(this.getAssets(),"fonts/MyriadPro.otf");
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, BubbleSpot.this);

		final ImageView shopping = (ImageView) findViewById(R.id.cima);
		shopping.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (!checkNetwork()){
					Intent intent = new Intent(v.getContext(), ListShoppings.class);
					b = new Bundle();
					b.putString("text", Utils.link_shopping_ + Utils.link_format);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
		});

		final ImageView loja = (ImageView) findViewById(R.id.centro_esq);
		loja.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (!checkNetwork()){
					Intent intent = new Intent(v.getContext(), ListAllShops.class);
					b = new Bundle();
					b.putString("text", Utils.link_loja_ + Utils.link_format);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
		});

		final ImageView filme = (ImageView) findViewById(R.id.centro_dir_cima);
		filme.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (!checkNetwork()){
					Intent intent = new Intent(v.getContext(), ListAllCinema.class);
					b = new Bundle();
					b.putString("text", Utils.link_filme_ + Utils.link_format);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
		});

		final ImageView evento = (ImageView) findViewById(R.id.centro_dir_baixo);
		evento.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (!checkNetwork()){
					Intent intent = new Intent(v.getContext(), ListAllCultural.class);
					b = new Bundle();
					b.putString("text", Utils.link_evento_ + Utils.link_format);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
		});

		final ImageView promocao = (ImageView) findViewById(R.id.baixo);
		promocao.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (!checkNetwork()){
					Intent intent = new Intent(v.getContext(), ListAllPromo.class);
					b = new Bundle();
					b.putString("text", Utils.link_promo_ + Utils.link_format);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_FIRST_USER && requestCode == 0) {
			Toast.makeText(this, "N�o foi encontrado nenhum resultado!", Toast.LENGTH_LONG).show();
		}     
	}

	public boolean checkNetwork() {
		if (!isNetworkAvailable()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Por favor ligue-se � Internet!")
			.setCancelable(true)
			.setPositiveButton("Defini��es",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					startActivityForResult(
							new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS),
									0);
				}
			})
			.setNegativeButton("Sair",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					BubbleSpot.this.finish();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return true;

		}
		return false;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.perto:
			if (!checkNetwork()){
				Location l = Utils.getLocation(this);
				if (l==null)
					Toast.makeText(BubbleSpot.this, "N�o foi possivel obter a sua localiza��o!", Toast.LENGTH_LONG).show();
				else{
					String pesquisa = "/search/shoppings?latitude=" + l.getLatitude() + "&longitude=" + l.getLongitude() + "&radius=" + Utils.raio + "&format=json";
					Intent intent = new Intent(this, ShoppingNear.class);
					b = new Bundle();
					b.putString("text", pesquisa);
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			}
			return true;
		case R.id.sobre:
			Dialog dialog = new Dialog(BubbleSpot.this);
			dialog.setContentView(R.layout.empty);
			dialog.setTitle("Sobre");
			dialog.setCancelable(true);
			TextView texto = (TextView) dialog.findViewById(R.id.corpo);
			texto.setText("O BubbleSpot � uma base de dados centralizada com informa��es �teis relativas a centros comerciais." +
					"\n\nDesenvolvido em 2011, na Faculdade de Engenharia da Universidade do Porto." +
					"\n\n\n\n\nRealizado por:" +
					"\n\tAndr� Dias;" +
					"\n\tBruno Ferreira;" +
					"\n\tCarlos Babo;" +
					"\n\tFelipe Schmitt;" +
					"\n\tH�lder Moreira." +
					"\n\n\n\n" +
					"\nTodos os direitos reservados.");
			dialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}