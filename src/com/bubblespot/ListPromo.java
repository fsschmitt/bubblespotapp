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

import com.adapter.PromocaoAdapter;



public class ListPromo extends Activity{
	//private ListView list;
	private int idLoja;
	private int idShopping;
	private String nomeShopping;
	private ArrayList<String> nomes;
	private ArrayList<Bitmap> bImages;
	private ArrayList<String> images;
	private ArrayList<Promocao> promos;
	private ProgressDialog dialog;
	private GridView gridview;
	private Bundle b;
	private String text;
	private EditText filterText;
	private PromocaoAdapter adapter;
	private Boolean loading;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.shops);
	        Bundle c = this.getIntent().getExtras();
	        this.text = c.getString("text");
	        idLoja = c.getInt("idLoja");
	        idShopping = c.getInt("idShopping");
	        nomeShopping = c.getString("nomeShopping");
	        
	        Header header = (Header) findViewById(R.id.header);
		    header.initHeader();
			Search.pesquisa(this, ListPromo.this);
			
			filterText = (EditText) findViewById(R.id.filter_box);
		    filterText.addTextChangedListener(filterTextWatcher);
		    filterText.setHint("Filtrar Promoções");
	        loading=true;
			dialog = ProgressDialog.show(this, "", "A Carregar...",true);
	        dialog.setCancelable(true);
	        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	            	finish();
	                }
	        });
	        b = new Bundle();
	        promos = new ArrayList<Promocao>();
	        nomes = new ArrayList<String>();
	        bImages = new ArrayList<Bitmap>();
	        images = new ArrayList<String>();
	        gridview = (GridView) findViewById(R.id.gridView1);
	        gridview.setNumColumns(1);
	        gridview.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
	        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                Intent intent = new Intent(v.getContext(), PromoDetail.class);
	                Promocao promo = promos.get(position);
	                b.putInt("idLoja", idLoja);
	                b.putInt("id", promo.getId());
	                b.putInt("idShopping", idShopping);
	                b.putString("nomeLoja", promo.getLoja_nome());
	                b.putString("desconto", promo.getDesconto());
	                b.putString("produto", promo.getProduto());
	                b.putString("detalhes", promo.getDetalhes());
	                b.putString("precoFinal", promo.getPreco_final());
	                b.putString("precoInicial", promo.getPreco_inicial());
	                b.putString("dataFinal", promo.getData_final());
	                b.putString("imagem", promo.getImagem_url());
	                b.putString("shopping", nomeShopping);
	                Bitmap image = promo.getbImage();
	                if(image != null){
	                	b.putByteArray("shoppingImageByte", Utils.encodeBitmap(image));
	                }
	                else
	                	b.putByteArray("shoppingImageByte", null);
	                intent.putExtras(b);
					startActivity(intent);
	        	}
	        });

	        new RetrievePromos().execute();
	        }
	 
	 class RetrievePromos extends AsyncTask<String, Integer, String> {

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
					line = getJSONLine(url);
					if(line != null){
						jo = new JSONArray(line);
						for (int i = 0; i < jo.length(); i++) {
							JSONObject promo = jo.getJSONObject(i);
							int id = promo.getInt("id");
							String desconto = promo.getString("desconto");
							String dataf = promo.getString("dataf");
							String detalhes = promo.getString("detalhes");
							String imagem = promo.getString("imagem");
							String precof = promo.getString("precof");
							String precoi = promo.getString("precoi");
							String produto = promo.getString("produto");
							String lojaNome = promo.getString("loja_nome");
							
							
							nomes.add(produto);
							images.add(imagem);
							Promocao p = new Promocao(id,dataf,desconto,detalhes,imagem,idLoja, lojaNome,precoi,precof,produto);
							promos.add(p);
							
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
				if(nomes != null && !nomes.isEmpty()){
					for(int i = 0; i<images.size();i++)
						bImages.add(BitmapFactory.decodeResource(Utils.res, R.drawable.loading_images));
					adapter = new PromocaoAdapter(ListPromo.this, bImages, promos, nomes);
					gridview.setAdapter(adapter);
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
						promos.get(promos.size()-images.size()).setbImage(image);
						bImages.set(promos.size()-images.size(),image);
				}
				catch(Exception e){
					try {
						Bitmap image = Utils.loadImageFromNetwork("http://placehold.it/128");
						image = Bitmap.createScaledBitmap(image, image.getWidth()*240/image.getHeight(), 240, false);
						bImages.add(image);
						promos.get(promos.size()-1).setbImage(image);
					} catch (Exception e1) {
						Log.e("Erro ao baixar as imagens.", e1.getMessage());
					}
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
	 
	 public static String getJSONLine(URL url) throws IOException {
			BufferedReader in;

			URLConnection tc = url.openConnection();
			tc.setDoInput(true);
			tc.setDoOutput(true);
			in = new BufferedReader(new InputStreamReader(tc.getInputStream()));	
			return in.readLine();
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
