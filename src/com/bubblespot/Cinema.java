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

		Filme f = new Filme(3,"Inception","Visionário cineasta Christopher Nolan (Amnésia, O Cavaleiro das Trevas) escreve e dirige este filme de ação psicológica de ficção científica sobre um ladrão que possui o poder de entrar nos sonhos dos outros. Dom Cobb (Leonardo DiCaprio) não roubar as coisas, ele rouba idéias. Projetando-se profundamente no subconsciente de seus alvos, ele pode dar informações que até mesmo os hackers melhor computador não pode chegar. No mundo da espionagem corporativa, Cobb é a melhor arma. Mas mesmo armas têm sua fraqueza, e quando Cobb perde tudo, ele é forçado a embarcar em uma missão final, em uma busca desesperada por redenção. Desta vez, Cobb não será colher uma idéia, mas uma semeadura. Se ele e sua equipe de especialistas bem sucedidos, eles terão descoberto uma nova fronteira na arte da espionagem psíquica. Eles planejou tudo com perfeição, e eles têm todas as ferramentas para fazer o trabalho. Sua missão é complicada, no entanto, pela súbita aparição de um inimigo malévolo que parece saber exatamente o que estão fazendo, e precisamente como pará-los. ~ Jason Buchanan, Rovi","3","15:10 / 18:50 / 22:45 / 00:10","http://content8.flixster.com/movie/10/93/37/10933762_det.jpg","http://www.youtube.com/watch?v=dQw4w9WgXcQ");
		Filme f2 = new Filme(3,"In time","Quando Will Salas é falsamente acusado de assassinato, ele deve descobrir uma maneira de derrubar um sistema onde o tempo é dinheiro - literalmente - permitindo que os ricos para viver para sempre, enquanto os pobres, como Will, tem que mendigar, pedir emprestado e roubar minutos bastante para fazê-lo através de outro dia. - (C) a 20th Century Fox","5","15:10 / 18:50 / 22:45 / 00:10","http://content9.flixster.com/movie/11/15/93/11159399_det.jpg","http://www.youtube.com/watch?v=dQw4w9WgXcQ");
		Filme f3 = new Filme(3,"The art of getting by","The Art of Getting By estrelas Freddie Highmore (Finding Neverland, Charlie ea Fábrica de Chocolate) como George, um adolescente solitário e fatalista que fez todo o caminho para seu último ano sem nunca ter feito um verdadeiro dia de trabalho, que faz amizade com Sally (Emma Roberts - Scream 4), uma menina bonita e complicada, que reconhece nele uma alma gêmea. - (C) A Fox Searchlight","8","15:10 / 18:50 / 22:45 / 00:10","http://content9.flixster.com/movie/11/15/74/11157427_det.jpg","http://www.youtube.com/watch?v=dQw4w9WgXcQ");
		filmes.add(f);
		filmes.add(f2);
		filmes.add(f3);
		images.add(f.getImage_url());
		images.add(f2.getImage_url());
		images.add(f3.getImage_url());
		
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