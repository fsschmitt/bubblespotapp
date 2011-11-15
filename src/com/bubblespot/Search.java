package com.bubblespot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Search {
	
	
	public static void pesquisa(final Context c, final Activity a) {
		
		final CharSequence[] items = {"Shoppings", "Lojas", "Promoções"};

		ImageView lupa = (ImageView) a.findViewById(R.id.lupa);
		lupa.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(c);
				builder.setTitle("Pesquisar");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch(item){
						case 0:
							if (!checkNetwork(c, a)){
								Intent intent = new Intent(c, SearchShopping.class);
								a.startActivityForResult(intent, 0);
							}
							break;
						case 1:
							Toast.makeText(c, items[item], Toast.LENGTH_SHORT).show();
							break;
						case 2:
							Toast.makeText(c, items[item], Toast.LENGTH_SHORT).show();
							break;
						}

					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		

		
	}
	
	
	public static boolean checkNetwork(final Context c, final Activity a) {
		if (!isNetworkAvailable(c, a)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(c);
			builder.setMessage("Por favor ligue-se à Internet!")
			.setCancelable(false)
			.setPositiveButton("Definições",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					a.startActivityForResult(
							new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS),
									0);
				}
			})
			.setNegativeButton("Sair",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int id) {
					a.finish();

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return true;

		}
		return false;
	}

	private static boolean isNetworkAvailable(final Context c, final Activity a) {
		ConnectivityManager connectivityManager = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}
