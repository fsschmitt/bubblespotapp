package com.bubblespot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.adapter.CinemaViewAdapter;
import com.zylinc.view.ViewPagerIndicator;

public class Cinema extends FragmentActivity {
	static final int NUM_ITEMS = 10;

	private CinemaViewAdapter mAdapter;
	private ViewPager mPager;
	private ArrayList<String> images;
	private ArrayList<Filme> filmes;
	private ProgressDialog dialog;
	private boolean loading;
	private String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, this);
		Bundle c = this.getIntent().getExtras();
		this.text = c.getString("text");

		images = new ArrayList<String>();
		filmes = new ArrayList<Filme>();

		loading=true;
		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		
		new RetrieveFilme().execute();

	}


	private void initAdapter() {
		mPager = (ViewPager)findViewById(R.id.pager);
		mAdapter = new CinemaViewAdapter(this,filmes);
		mPager.setAdapter(mAdapter);
		ViewPagerIndicator indicator = (ViewPagerIndicator)findViewById(R.id.indicator);
		mPager.setOnPageChangeListener(indicator);
		indicator.init(0, mAdapter.getCount(), filmes);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);
		indicator.setArrows(prev, next);
	}


	class RetrieveFilme extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {

			String uri = "http://bubblespot.heroku.com/"+text;

			URL url = null;
			try {
				url = new URL(uri);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			String line = null;
			JSONArray jo = null;
			try {
				line = getJSONLine(url);
				if(line != null){
					jo = new JSONArray(line);
					for (int i = 0; i < jo.length(); i++) {
						JSONObject filme = jo.getJSONObject(i);
						int idShopping = filme.getInt("shopping_id");
						String detalhes = filme.getString("detalhes");
						String horarios = filme.getString("horarios");
						String nome = filme.getString("nome");
						String trailer = filme.getString("trailer");
						String imagem = filme.getString("imagem");
						filmes.add(new Filme(idShopping,nome,detalhes,"[falta este campo]",horarios,imagem,trailer));
						images.add(imagem);
					}
				}
				else return null;
			} catch (IOException e) {

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		// Called once the background activity has completed
		@Override
		protected void onPostExecute(String result) { //
			if(images != null && !images.isEmpty()){
				initAdapter();
				new RetrieveImages().execute();
			}
			else{
				dialog.dismiss();
				setResult(RESULT_FIRST_USER);
				finish();
			}
		}
	}

	class RetrieveImages extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			try{
				if(images.get(0)!=null || !images.get(0).equals("null")){
					Bitmap image = Utils.loadImageFromNetwork(images.get(0));
					filmes.get(filmes.size()-images.size()).setbImage(image);
				}
			}
			catch(Exception e){
				Log.e("Erro ao baixar as imagens.", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(loading){
				dialog.dismiss();
				loading = false;
			}
			images.remove(0);
			mAdapter.notifyDataSetChanged();
			if(images.size()>0){
				new RetrieveImages().execute();
			}
		}
	}

	public static String getJSONLine(URL url) throws IOException {
		BufferedReader in;

		URLConnection tc = url.openConnection();
		tc.setDoInput(true);
		tc.setDoOutput(true);
		in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
		return in.readLine();
	}
}