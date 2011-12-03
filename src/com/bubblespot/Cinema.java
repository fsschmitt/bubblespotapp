package com.bubblespot;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.adapter.CinemaViewAdapter;

public class Cinema extends FragmentActivity {
	    static final int NUM_ITEMS = 10;

	    CinemaViewAdapter mAdapter;
	    ViewPager mPager;
	    
	    static ArrayList<String> texts;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.viewpager);
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, this);
	        texts = new ArrayList<String>();
	        texts.add("ola");
	        texts.add("ola2");
	        texts.add("ola3");

	        mAdapter = new CinemaViewAdapter(this,texts,texts);
	        mPager = (ViewPager)findViewById(R.id.pager);
	        mPager.setAdapter(mAdapter);
	        
	    }
}