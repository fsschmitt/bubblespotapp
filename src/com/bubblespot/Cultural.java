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
			
			ArrayList<String> texts = new ArrayList<String>();
			ArrayList<String> info = new ArrayList<String>();
	        texts.add("Jantar de Natal");
	        texts.add("Teatro de beneficiência");
	        info.add("Jantar de Natal no shopping com todas as pessoas envolventes \n 21 Horas - Salão Silo");
	        info.add("Teatro de beneficiência para ajudar todas as pessoas necessitadas nesta altura de Natal \n 18 Horas");
	        
	        
	        

	        mAdapter = new CulturalViewAdapter(this,texts,info);
	        mPager = (ViewPager)findViewById(R.id.pager);
	        mPager.setAdapter(mAdapter);   
	        ViewPagerIndicator indicator = (ViewPagerIndicator)findViewById(R.id.indicator);
	        mPager.setOnPageChangeListener(indicator);
	        indicator.init(0, mAdapter.getCount(), texts);
	        Resources res = getResources();
	        Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
	        Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);
	        indicator.setArrows(prev, next);
	    }
}