package com.bubblespot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchShopping extends Activity {

	private Bundle b;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoppingsearch);

		Header header = (Header) findViewById(R.id.header);
	    header.initHeader();
		Search.pesquisa(this, SearchShopping.this);
		
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.opcoes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		
		final EditText nome = (EditText) findViewById(R.id.pesq_nome);


		final Button pesquisa = (Button) findViewById(R.id.pesquisar);
		pesquisa.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (!checkNetwork()){
					String query = "";
					try {
						String nomep = ""+nome.getText();
						String queryTemp = nomep.trim().replace(" ", "+");
						query = URLEncoder.encode(queryTemp, "utf-8");
						System.out.println("QUERY: "+query);
						String pesquisa = "search/shoppings?query=" + query + "&pesquisa=";
						if(spinner.getSelectedItem().toString().equals("Nome")){
							pesquisa+="0";
						}
						else if (spinner.getSelectedItem().toString().equals("Localização")){
							pesquisa+="1";
						}
						pesquisa+="&format=json";
						Intent intent = new Intent(v.getContext(), ListShoppings.class);
						b = new Bundle();
						b.putString("text", pesquisa);
						intent.putExtras(b);
						startActivityForResult(intent, 0);
					} catch (UnsupportedEncodingException e) {
						Toast.makeText(SearchShopping.this, "Erro ao tentar efetuar a pesquisa.", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_FIRST_USER && requestCode == 0) {
			Toast.makeText(this, "Não foi encontrado nenhum shopping!", Toast.LENGTH_LONG).show();
		}  
	}


	public boolean checkNetwork() {
		if (!isNetworkAvailable()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Por favor ligue-se à Internet!")
			.setCancelable(false)
			.setPositiveButton("Definições",
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
					SearchShopping.this.finish();

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
}

