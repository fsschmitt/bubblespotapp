package com.bubblespot;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;

public class Utils {

	public static int raio = 5;

	public static Location getLocation(Context ctx) {
		LocationManager lm = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		return l;
	}

	public static Bitmap loadImageFromNetwork(String url) throws MalformedURLException, IOException {
		HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
		conn.connect();
		return BitmapFactory.decodeStream(new FlushedInputStream(conn.getInputStream()));
	}

	public static double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
	}
}
