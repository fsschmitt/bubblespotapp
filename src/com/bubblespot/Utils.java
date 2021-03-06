package com.bubblespot;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.bubblespot.partilha.FacebookActivity;
import com.bubblespot.partilha.Twitter;
import com.bubblespot.promocoes.Promocao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Utils {

	public static int raio = 5; //km
	public static String link = "http://bubblespot.heroku.com";
	public static String link_shopping = "/shoppings/";
	public static String link_loja = "/lojas/";
	public static String link_promo = "/promos/";
	public static String link_filme = "/filmes/";
	public static String link_evento = "/eventos/";

	public static String link_shopping_ = "/shoppings";
	public static String link_loja_ = "/lojas";
	public static String link_promo_ = "/promos";
	public static String link_filme_ = "/filmes";
	public static String link_evento_ = "/eventos";
	public static String link_format = ".json";

	public static String imagem_default = "http://placehold.it/128";

	public static Resources res;
	public static Typeface tf;


	public static Location getLocation(Context ctx) {
		LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);
		Location l = null;

		while(l==null){
			for (String s : providers) {
				lm.requestLocationUpdates(s, 100, 1, locationListener);
				l = lm.getLastKnownLocation(s);
				if (l != null)
					break;
			}
		}
		return l;
	}

	private final static LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};


	public static Bitmap loadImageFromNetwork(String url) throws MalformedURLException, IOException {
		URL u;
		try{
			u = new URL(url);
		}
		catch(MalformedURLException m){
			u = new URL(imagem_default );
		}
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.connect();
		return BitmapFactory.decodeStream(new FlushedInputStream(conn.getInputStream()));
	}

	public static double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
	}

	public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
		Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		canvas.drawBitmap(bmp1, new Matrix(), null);
		canvas.drawBitmap(bmp2, new Matrix(), null);
		return bmOverlay;
	}

	public static String getJSONLine(URL url) throws IOException {
		BufferedReader in;

		URLConnection tc = url.openConnection();
		tc.setDoInput(true);
		tc.setDoOutput(true);
		in = new BufferedReader(new InputStreamReader(tc.getInputStream()));	
		return in.readLine();
	}

	public static byte[] encodeBitmap(Bitmap b)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		b.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos); 
		return bos.toByteArray();
	}



	public static void share(final Promocao promo, final Context c) {

		final CharSequence[] items = { "Facebook", "Twitter" };

		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		final String message = "";
		builder.setTitle("Partilhar");

		builder.setItems(items, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {

				String url = link + link_shopping +promo.getShopping_id()+ link_loja +promo.getLoja_id()+
						link_promo +promo.getId();
				switch (item) {

				case 0: {
					Intent intent;
					Bundle b;
					String desconto;
					intent = new Intent(c,
							FacebookActivity.class);
					b = new Bundle();

					b.putString("message", message);
					b.putString("name", promo.getProduto());
					b.putString("link", url);

					desconto = "Desconto: " + promo.getDesconto() + "%";
					if(promo.getPreco_final()!=null)
						desconto+=" Pre�o Final: " + promo.getPreco_final() + "�";
					b.putString("caption", desconto);

					b.putString("imageLink", promo.getImagem_url());

					b.putString("description", promo.getDetalhes());

					intent.putExtras(b);
					c.startActivity(intent);
					break;
				}

				case 1: {
					Bundle b = new Bundle();
					String msg = "Vejam esta promo��o: " + promo.getProduto() + " com " + promo.getDesconto() + "% de desconto. " +
							url;
					b.putString("text", msg);
					Intent intent = new Intent(c,
							Twitter.class);
					intent.putExtras(b);
					c.startActivity(intent);
					break;
				}

				default:
					break;
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
