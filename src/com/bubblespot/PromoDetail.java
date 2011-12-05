package com.bubblespot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PromoDetail extends Activity {

	private String shopping;
	private int id;
	private int idLoja;
	private int idShopping;
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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PromoDetail.this.setContentView(R.layout.promodetail);
		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(PromoDetail.this, PromoDetail.this);

		Bundle b = this.getIntent().getExtras();
		setId(b.getInt("id"));
		setIdShopping(b.getInt("idShopping"));
		setIdLoja(b.getInt("idLoja"));
		nomeLoja = b.getString("nomeLoja");
		desconto = b.getString("desconto");
		produto = b.getString("produto");
		detalhes = b.getString("detalhes");
		precoFinal = b.getString("precoFinal");
		precoInicial = b.getString("precoInicial");
		dataFinal = b.getString("dataFinal");
		shopping = b.getString("shopping");
		imagem_url = b.getString("imagem");
		b.putString("shopping", shopping);

		TextView text_promo = (TextView) PromoDetail.this.findViewById(R.id.promo_text);
		text_promo.setText(produto);

		TextView promoLoja = (TextView) PromoDetail.this.findViewById(R.id.promoLoja);
		promoLoja.setText(nomeLoja + " (" + shopping + ")");

		TextView promoDetalhes = (TextView) PromoDetail.this.findViewById(R.id.promoDetalhes);
		promoDetalhes.setText(detalhes);

		TextView promoAntes = (TextView) PromoDetail.this.findViewById(R.id.promoAntes);
		promoAntes.setText("Antes: "+precoInicial);

		TextView promoDepois = (TextView) PromoDetail.this.findViewById(R.id.promoDepois);
		promoDepois.setText("Depois: "+precoFinal);

		TextView promoDesconto = (TextView) PromoDetail.this.findViewById(R.id.promoDesconto);
		promoDesconto.setText("Desconto: "+desconto+"%");

		TextView promoDataLimite = (TextView) PromoDetail.this.findViewById(R.id.promoDataLimite);
		promoDataLimite.setText(dataFinal);

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

	public int getIdShopping() {
		return idShopping;
	}

	public void setIdShopping(int idShopping) {
		this.idShopping = idShopping;
	}

	public int getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(int idLoja) {
		this.idLoja = idLoja;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
