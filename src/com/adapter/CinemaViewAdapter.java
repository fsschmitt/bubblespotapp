package com.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubblespot.Filme;
import com.bubblespot.R;
import com.bubblespot.Utils;

public class CinemaViewAdapter extends PagerAdapter{
	private ArrayList<Filme> filmes;
	private Context mContext;

	public CinemaViewAdapter(Context ctx, ArrayList<Filme> filmes) {
		this.mContext = ctx;
		this.filmes = filmes;
	}

	@Override
	public int getCount() {
		return filmes.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public Object instantiateItem(View collection, final int position) {

		LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = vi.inflate(R.layout.filme, null);

		TextView nome = ((TextView) v.findViewById(R.id.filmeNome));
		nome.setText(filmes.get(position).getNome());
		nome.setTypeface(Utils.tf);

		if(filmes.get(position).getbImage() != null)
		{
			ImageView imagem = ((ImageView) v.findViewById(R.id.filmeImagem));
			imagem.setImageBitmap(filmes.get(position).getbImage());
		}

		TextView detalhes = ((TextView) v.findViewById(R.id.filmeDetalhesText));
		detalhes.setText(filmes.get(position).getDetalhes());
		detalhes.setTypeface(Utils.tf);
		
		TextView sala = ((TextView) v.findViewById(R.id.filmeSalaText));
		sala.setText(filmes.get(position).getSala());
		sala.setTypeface(Utils.tf);

		TextView horario = ((TextView) v.findViewById(R.id.filmeHorarioText));
		horario.setText(filmes.get(position).getHorarios());
		horario.setTypeface(Utils.tf);

		ImageView trailer = ((ImageView) v.findViewById(R.id.filmeTrailerPic));
		if(filmes.get(position).getbTrailer() != null)
		{
			Bitmap image = filmes.get(position).getbTrailer();
			image = Bitmap.createScaledBitmap(image, image.getWidth()*200/image.getHeight(), 200, false);
			trailer.setImageBitmap(image);
		}
		
		trailer.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Uri uri = Uri.parse(filmes.get(position).getTrailer());
				uri = Uri.parse("vnd.youtube:"  + uri.getQueryParameter("v"));

				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				mContext.startActivity(intent);
			}
		});

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
