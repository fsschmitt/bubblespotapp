package com.bubblespot;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Touch extends Activity{
	String url=null;
	ZoomableImageView view = null;
	private ProgressDialog dialog;
	Bitmap bmp = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = this.getIntent().getExtras();
		url = b.getString("planta");
		setContentView(R.layout.planta);

		Header header = (Header) findViewById(R.id.header);
		header.initHeader();
		Search.pesquisa(this, Touch.this);

		dialog = ProgressDialog.show(this, "", "A Carregar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});

		new RetrieveImages().execute();
		view = (ZoomableImageView) findViewById(R.id.ziv);
	}

	class RetrieveImages extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... arg0) {
			try{
				bmp = Utils.loadImageFromNetwork(url);
			}
			catch(Exception e){
				Log.e("Erro ao baixar as imagens.", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(bmp!=null){
				view.setBitmap(bmp);
				System.out.println("carreguei");
			}
			dialog.dismiss();
		}
	}
}
