package com.bubblespot;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.zylinc.view.*;

import com.adapter.CinemaViewAdapter;

public class Cinema extends FragmentActivity {
	    static final int NUM_ITEMS = 10;

	    CinemaViewAdapter mAdapter;
	    ViewPager mPager;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.viewpager);
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, this);
			
			ArrayList<String> texts = new ArrayList<String>();
			ArrayList<ArrayList<String>> horarios = new ArrayList<ArrayList<String>>();
	        texts.add("Inception");
	        texts.add("In time");
	        texts.add("The art of getting by");
	        ArrayList<String> h1 = new ArrayList<String>();
	        ArrayList<String> h2 = new ArrayList<String>();
	        ArrayList<String> h3 = new ArrayList<String>();
	        h1.add("15:10");
	        h1.add("18:50");
	        horarios.add(h1);
	        h2.add("15:10");
	        h2.add("18:50");
	        h2.add("21:15");
	        horarios.add(h2);
	        h3.add("18:50");
	        h3.add("23:15");
	        horarios.add(h3);
	        
	        

	        mAdapter = new CinemaViewAdapter(this,texts,horarios);
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