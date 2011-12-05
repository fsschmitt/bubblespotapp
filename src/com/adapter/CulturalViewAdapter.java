package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubblespot.Evento;
import com.bubblespot.R;

public class CulturalViewAdapter extends PagerAdapter {
	private ArrayList<Evento> eventos;
    private Context mContext;
    
    public CulturalViewAdapter(Context ctx, ArrayList<Evento> eventos) {
        this.mContext = ctx;
        this.eventos = eventos;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }
    
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(View collection, int position) {
		
    	LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.evento, null);
        
        TextView nome = ((TextView) v.findViewById(R.id.eventoNome));
        nome.setText(eventos.get(position).getNome());
        
        if(eventos.get(position).getbImage() != null)
        {
        	ImageView imagem = ((ImageView) v.findViewById(R.id.eventoImagem));
        	imagem.setImageBitmap(eventos.get(position).getbImage());
        }
        
        TextView info = ((TextView) v.findViewById(R.id.eventoInfoText));
        info.setText(eventos.get(position).getDetalhes());
        
        TextView data = ((TextView) v.findViewById(R.id.eventoDataText));
        data.setText(eventos.get(position).getData());
        
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
