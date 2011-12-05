package com.bubblespot;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.zylinc.view.*;

import com.adapter.CinemaViewAdapter;
import com.bubblespot.ListShoppings.RetrieveImages;
import com.bubblespot.ListShoppings.RetrieveShoppings;

public class Cinema extends FragmentActivity {
	    static final int NUM_ITEMS = 10;

	    CinemaViewAdapter mAdapter;
	    ViewPager mPager;
	    ArrayList<String> images;
	    ArrayList<Filme> filmes;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.viewpager);
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, this);
			images = new ArrayList<String>();
			filmes = new ArrayList<Filme>();
	        Filme f = new Filme(3,"Inception","Visionary filmmaker Christopher Nolan (Memento, The Dark Knight) writes and directs this psychological sci-fi action film about a thief who possesses the power to enter into the dreams of others. Dom Cobb (Leonardo DiCaprio) doesn't steal things, he steals ideas. By projecting himself deep into the subconscious of his targets, he can glean information that even the best computer hackers can't get to. In the world of corporate espionage, Cobb is the ultimate weapon. But even weapons have their weakness, and when Cobb loses everything, he's forced to embark on one final mission in a desperate quest for redemption. This time, Cobb won't be harvesting an idea, but sowing one. Should he and his team of specialists succeed, they will have discovered a new frontier in the art of psychic espionage. They've planned everything to perfection, and they have all the tools to get the job done. Their mission is complicated, however, by the sudden appearance of a malevolent foe that seems to know exactly what they're up to, and precisely how to stop them. ~ Jason Buchanan, Rovi","15:10 / 18:50 / 22:45 / 00:10","http://content8.flixster.com/movie/10/93/37/10933762_det.jpg","http://www.youtube.com/watch?v=dQw4w9WgXcQ");
	        Filme f2 = new Filme(3,"In time","When Will Salas is falsely accused of murder, he must figure out a way to bring down a system where time is money - literally - enabling the wealthy to live forever while the poor, like Will, have to beg, borrow, and steal enough minutes to make it through another day. -- (C) 20th Century Fox","15:10 / 18:50 / 22:45 / 00:10","http://content9.flixster.com/movie/11/15/93/11159399_det.jpg","http://www.youtube.com/watch?v=dQw4w9WgXcQ");
	        Filme f3 = new Filme(3,"The art of getting by","The Art of Getting By stars Freddie Highmore (Finding Neverland, Charlie and the Chocolate Factory) as George, a lonely and fatalistic teen who's made it all the way to his senior year without ever having done a real day of work, who is befriended by Sally (Emma Roberts - Scream 4), a beautiful and complicated girl who recognizes in him a kindred spirit. -- (C) Fox Searchlight","15:10 / 18:50 / 22:45 / 00:10","http://content9.flixster.com/movie/11/15/74/11157427_det.jpg","http://www.youtube.com/watch?v=dQw4w9WgXcQ");
	        filmes.add(f);
	        filmes.add(f2);
	        filmes.add(f3);
	        images.add(f.getImage_url());
	        images.add(f2.getImage_url());
	        images.add(f3.getImage_url());

	        mAdapter = new CinemaViewAdapter(this,filmes);
	        mPager = (ViewPager)findViewById(R.id.pager);
	        mPager.setAdapter(mAdapter);   
	        ViewPagerIndicator indicator = (ViewPagerIndicator)findViewById(R.id.indicator);
	        mPager.setOnPageChangeListener(indicator);
	        indicator.init(0, mAdapter.getCount(), filmes);
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
						filmes.get(filmes.size()-images.size()).setbImage(image);
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