package com.bubblespot;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.adapter.CulturalViewAdapter;
import com.zylinc.view.ViewPagerIndicator;

public class Cultural extends FragmentActivity {
	static final int NUM_ITEMS = 10;

	CulturalViewAdapter mAdapter;
	ViewPager mPager;
	ArrayList<Evento> eventos;
	ArrayList<String> images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, this);

		Evento e = new Evento(3,"Jantar de Natal","24/12/2011","Jantar de Natal no shopping com todas as pessoas envolventes \n21 Horas - Salão Silo", "http://placehold.it/200x300");
		Evento e2 = new Evento(3,"Teatro de beneficiência","23/12/2011","Teatro de beneficiência para ajudar todas as pessoas necessitadas nesta altura de Natal \n18 Horas","http://placehold.it/200x300");
		eventos = new ArrayList<Evento>();
		images = new ArrayList<String>();
		eventos.add(e2);
		eventos.add(e);
		images.add(e2.getImagem_url());
		images.add(e.getImagem_url());

		mAdapter = new CulturalViewAdapter(this,eventos);
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);   
		ViewPagerIndicator indicator = (ViewPagerIndicator)findViewById(R.id.indicator);
		mPager.setOnPageChangeListener(indicator);
		indicator.init(0, mAdapter.getCount(), eventos);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);
		indicator.setArrows(prev, next);

		new RetrieveImages().execute();
	}

	class RetrieveImages extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... arg0) {
			try{
				Bitmap image = Utils.loadImageFromNetwork(images.get(0));
				eventos.get(eventos.size()-images.size()).setbImage(image);
			}
			catch(Exception e){
				Log.e("Erro ao baixar as imagens.", e.getMessage());
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			images.remove(0);
			mAdapter.notifyDataSetChanged();
			if(images.size()>0){
				new RetrieveImages().execute();
			}
		}
	}
}