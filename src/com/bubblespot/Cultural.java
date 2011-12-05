package com.bubblespot;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.adapter.CulturalViewAdapter;
import com.zylinc.view.ViewPagerIndicator;

public class Cultural extends FragmentActivity {
	    static final int NUM_ITEMS = 10;

	    CulturalViewAdapter mAdapter;
	    ViewPager mPager;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.viewpager);
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, this);
			
			Evento e = new Evento(3,"Jantar de Natal","24/12/2011","Jantar de Natal no shopping com todas as pessoas envolventes \n21 Horas - Salão Silo");
			Evento e2 = new Evento(3,"Teatro de beneficiência","23/12/2011","Teatro de beneficiência para ajudar todas as pessoas necessitadas nesta altura de Natal \n18 Horas");
			ArrayList<Evento> eventos = new ArrayList<Evento>();
			eventos.add(e2);
			eventos.add(e);
	        
	        
	        

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
	    }
}