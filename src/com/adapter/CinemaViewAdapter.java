package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bubblespot.R;

public class CinemaViewAdapter extends PagerAdapter {
	private ArrayList<String> nomes;
	private ArrayList<String> horarios;
    private Context mContext;
    
    public CinemaViewAdapter(Context ctx, ArrayList<String> nomes, ArrayList<String> horarios) {
        this.mContext = ctx;
        this.nomes = nomes;
        this.horarios = horarios;
    }

    @Override
    public int getCount() {
        return nomes.size();
    }

    @Override
    public Object instantiateItem(View collection, int position) {
		
    	LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.filme, null);
        
        TextView view = ((TextView) v.findViewById(R.id.filmeNome));
        view.setText(nomes.get(position));
        
        TextView view2 = ((TextView) v.findViewById(R.id.filmeContador));
        view2.setText((position+1)+"/"+nomes.size());
        
        ListView lv1=(ListView) v.findViewById(R.id.filmeHorarios);
        lv1.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1 , horarios));
        
        ((ViewPager)collection).addView(v);

        return v;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
         ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }
}
