package com.bubblespot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PromoDetail extends Activity {

	private String shopping;
	private int id;
	private String nomeLoja;
	private String imagem_url;
	private String produto;
	private String detalhes;
	private String desconto;
	private String precoFinal;
	private String precoInicial;
	private String dataFinal;
	private ProgressDialog dialog;
	private Bitmap bImage;
	private int shopping_id;
	private int loja_id;
	private Promocao p;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PromoDetail.this.setContentView(R.layout.promodetail);
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(PromoDetail.this, PromoDetail.this);

		Bundle b = this.getIntent().getExtras();
		setId(b.getInt("id"));
		nomeLoja = b.getString("nomeLoja");
		desconto = b.getString("desconto");
		produto = b.getString("produto");
		detalhes = b.getString("detalhes");
		precoFinal = b.getString("precoFinal");
		precoInicial = b.getString("precoInicial");
		dataFinal = b.getString("dataFinal");
		shopping = b.getString("shopping");
		imagem_url = b.getString("imagem");
		shopping_id = b.getInt("idShopping");
		loja_id = b.getInt("idLoja");
		b.putString("shopping", shopping);
		
		p = new Promocao(id,dataFinal,desconto,detalhes,imagem_url,loja_id,nomeLoja,shopping_id,shopping,precoInicial,precoFinal,produto);

		TextView text_promo = (TextView) PromoDetail.this.findViewById(R.id.promo_text);
		text_promo.setText(produto);

		TextView promoLoja = (TextView) PromoDetail.this.findViewById(R.id.promoLoja);
		promoLoja.setText(nomeLoja + " (" + shopping + ")");

		TextView promoDetalhes = (TextView) PromoDetail.this.findViewById(R.id.promoDetalhes);
		promoDetalhes.setText(detalhes);
		
		String[] temp = new String[2];

		TextView promoAntes = (TextView) PromoDetail.this.findViewById(R.id.promoAntes);
		if(precoInicial==null)
			promoAntes.setVisibility(View.GONE);
		else{
			temp = precoInicial.split("\\.");
			if (temp[1].equals("0"))
				precoInicial=temp[0];
			else if (temp[1].length()==1)
				precoInicial=precoInicial.concat("0");
			promoAntes.setText("Antes: "+precoInicial + " €");
		}

		TextView promoDepois = (TextView) PromoDetail.this.findViewById(R.id.promoDepois);
		if(precoFinal==null)
			promoDepois.setVisibility(View.GONE);
		else{
			temp = precoFinal.split("\\.");
			if (temp[1].equals("0"))
				precoFinal=temp[0];
			else if (temp[1].length()==1)
				precoFinal=precoFinal.concat("0");
			promoDepois.setText("Depois: "+precoFinal + " €");
		}

		TextView promoDesconto = (TextView) PromoDetail.this.findViewById(R.id.promoDesconto);
		if(desconto==null)
			promoDesconto.setVisibility(View.GONE);
		else{
			temp = desconto.split("\\.");
			if (temp[1].equals("0"))
				desconto=temp[0];
			else if (temp[1].length()==1)
				desconto=desconto.concat("0");
			promoDesconto.setText("Desconto: "+desconto+" %");
		}

		TextView promoDataLimite = (TextView) PromoDetail.this.findViewById(R.id.promoDataLimite);
		promoDataLimite.setText(dataFinal.substring(8, 10) + "-" + dataFinal.substring(5, 7) + "-" + dataFinal.substring(0, 4) + " " + dataFinal.substring(11, 16));

		byte[] byteImage = b.getByteArray("promoImageByte");
		if(byteImage != null){
			bImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
			ImageView logo = (ImageView) PromoDetail.this.findViewById(R.id.promo_logo);
			logo.setImageBitmap(bImage);
		}
		else{
			dialog = ProgressDialog.show(this, "", "A Carregar...",true);
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
			new RetrieveLogo().execute();
		}
	}

	class RetrieveLogo extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... arg0) {

			bImage = null;
			try {
				bImage = Utils.loadImageFromNetwork(imagem_url);
			} catch (Exception e) {
				try {
					Bitmap image = Utils.loadImageFromNetwork("http://placehold.it/128");
					image = Bitmap.createScaledBitmap(image, image.getWidth()*240/image.getHeight(), 240, false);
					bImage = image;
				} catch (Exception e1) {
					Log.e("Erro ao baixar as imagens.", e1.getMessage());
				}
				Log.e("Erro ao baixar as imagens.", e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			ImageView logo = (ImageView) PromoDetail.this.findViewById(R.id.promo_logo);
			logo.setImageBitmap(bImage);

			dialog.dismiss();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.promo_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.promo_partilhar:
		{
			Utils.share(p,this);
		}
		return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
