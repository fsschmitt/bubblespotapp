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
import android.location.LocationManager;
import android.os.Bundle;

public class Utils {

	public static int raio = 8000;
	public static Resources res;

	public static Typeface tf;

	public static Location getLocation(Context ctx) {
		LocationManager lm = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);
		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		return l;
	}

	public static Bitmap loadImageFromNetwork(String url) throws MalformedURLException, IOException {
		URL u;
		try{
			u = new URL(url);
		}
		catch(MalformedURLException m){
			u = new URL("http://placehold.it/128");
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
					b.putString("link", "http://bubblespot.heroku.com/shoppings/"+promo.getShopping_id()+"/lojas/"+promo.getLoja_id()+"/promos/"+promo.getId());

					desconto = "Desconto: " + promo.getDesconto() + "%";
					if(promo.getPreco_final()!=null)
						desconto+=" Preço Final: " + promo.getPreco_final() + "€";
					b.putString("caption", desconto);

					b.putString("imageLink", promo.getImagem_url());

					b.putString("description", promo.getDetalhes());

					intent.putExtras(b);
					c.startActivity(intent);
					break;
				}

				case 1: {
					Bundle b = new Bundle();
					String msg = "Vejam esta promoção: " + promo.getProduto() + " com " + promo.getDesconto() + "% de desconto. " + "http://bubblespot.heroku.com/shoppings/"+promo.getShopping_id()+"/lojas/"+promo.getLoja_id()+"/promos/"+promo.getId();
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
