package com.bubblespot.lojas;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.adapter.ImageAdapter;
import com.bubblespot.Header;
import com.bubblespot.R;
import com.bubblespot.Search;
import com.bubblespot.Utils;

public class ListShops extends Activity{
	private int idShopping;
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<String> images;
	private ArrayList<Loja> lojas;
	private ProgressDialog dialog;
	private GridView gridview;
	private Bundle b;
	private String text;
	private ImageAdapter adapter;
	private EditText filterText;
	private boolean loading;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shops);
		Bundle c = this.getIntent().getExtras();
		this.text = c.getString("text");
		this.idShopping = c.getInt("idShopping");
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, ListShops.this);

		filterText = (EditText) findViewById(R.id.filter_box);
		filterText.addTextChangedListener(filterTextWatcher);
		loading = true;
		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		b = new Bundle();
		lojas = new ArrayList<Loja>();
		nomes = new ArrayList<String>();
		bImages = new ArrayList<Bitmap>();
		images = new ArrayList<String>();
		gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setNumColumns(2);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(v.getContext(), ShopDetail.class);
				Loja loja = lojas.get(position);
				b.putInt("lojaID", loja.getId());
				b.putString("lojaNome", loja.getNome());
				b.putInt("lojaPiso", loja.getPiso());
				b.putInt("lojaNumero", loja.getNumero());
				b.putString("lojaTelefone", loja.getTelefone());
				b.putString("lojaDetalhes", loja.getDetalhes());
				b.putString("lojaImagem", loja.getImagem());
				b.putString("lojaTags", loja.getTags());
				b.putString("lojaShopping", loja.getShopping());
				b.putInt("idShopping", idShopping);
				Bitmap image = loja.getbImage();
				if(image != null){
					b.putByteArray("shopImageByte", Utils.encodeBitmap(image));
				}
				else
					b.putByteArray("shopImageByte", null);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		new RetrieveLojas().execute();
	}

	class RetrieveLojas extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... arg0) {

			nomes.clear();
			bImages.clear();
			String uri = "http://bubblespot.heroku.com/" + text;

			URL url = null;
			try {
				url = new URL(uri);				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			String line = null;
			JSONArray jo = null;
			try {
				line = Utils.getJSONLine(url);
				if(line != null){
					jo = new JSONArray(line);
					for (int i = 0; i < jo.length(); i++) {
						JSONObject loja = jo.getJSONObject(i);
						int id = loja.getInt("id");
						String Nome = loja.getString("nome");
						int piso = loja.getInt("piso");
						int numero = loja.getInt("numero");
						String Telefone = loja.getString("telefone");
						String Detalhes = loja.getString("detalhes");
						String Imagem = loja.getString("imagem");
						String tags = loja.getString("tags");
						String shoppingNome = loja.getString("shopping_nome");
						int shoppingId = loja.getInt("shopping_id");

						Loja s = new Loja(id, Nome, piso, numero, Telefone, Detalhes, Imagem, tags, shoppingNome,shoppingId);
						lojas.add(s);
						nomes.add(Nome);
						images.add(Imagem);
					}
				}
				else
					return null;

			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		// Called once the background activity has completed
		@Override
		protected void onPostExecute(String result) { //
			if(nomes != null && !nomes.isEmpty()){
				for(int i = 0; i<images.size();i++)
					bImages.add(BitmapFactory.decodeResource(Utils.res, R.drawable.loading_images));
				adapter =  new ImageAdapter(ListShops.this,bImages,nomes);
				gridview.setAdapter(adapter);
				gridview.setTextFilterEnabled(true);
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
				Bitmap image = Utils.loadImageFromNetwork(images.get(0));
				image = Bitmap.createScaledBitmap(image, image.getWidth()*120/image.getHeight(), 120, false);
				lojas.get(lojas.size()-images.size()).setbImage(image);
				bImages.set(lojas.size()-images.size(),image);
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
			adapter.notifyDataSetChanged();
			if(images.size()>0)
				new RetrieveImages().execute();
		}
	}

	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			adapter.getFilter().filter(s);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		filterText.removeTextChangedListener(filterTextWatcher);
	}	
}
