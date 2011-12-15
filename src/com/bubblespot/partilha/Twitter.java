package com.bubblespot.partilha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bubblespot.R;
import com.twitter.TwitterApp;
import com.twitter.TwitterApp.TwDialogListener;

public class Twitter extends Activity {
	private TwitterApp mTwitter;
	private String username = "";
	private boolean postToTwitter = true;
	private ProgressDialog dialog;

	private static final String twitter_consumer_key = "ezfKI0nOh9iYC3gUbFfvaQ";
	private static final String twitter_secret_key = "tzxikNaZEqRoFp6FpockzPZCBGfEiK7GDBPsaTlM";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.post);
		mTwitter = new TwitterApp(this, twitter_consumer_key,twitter_secret_key);
		if (!mTwitter.hasAccessToken()) {
			mTwitter.authorize();
		}
		else {
			username 	= mTwitter.getUsername();
			Toast.makeText(Twitter.this, "Ligado ao twitter como " + username, Toast.LENGTH_LONG).show();
		}

		Button postBtn 				= (Button) findViewById(R.id.button1);
		final EditText reviewEdit   = (EditText) findViewById(R.id.revieew);

		Bundle b = this.getIntent().getExtras();
		String text = b.getString("text");
		reviewEdit.setText(text);

		postBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String review = reviewEdit.getText().toString();

				if (review.equals("")) return;

				if (postToTwitter) postToTwitter(review);
			}
		});

		mTwitter.setListener(mTwLoginDialogListener);
	}

	private void postToTwitter(final String review) {
		dialog = ProgressDialog.show(this, "", "A Enviar...",true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		new Thread() {
			@Override
			public void run() {
				int what = 0;

				try {
					mTwitter.updateStatus(review);
				} catch (Exception e) {
					what = 1;
				}

				mHandler.sendMessage(mHandler.obtainMessage(what));
				dialog.dismiss();
			}
		}.start();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String text = (msg.what == 0) ? "Publicado no Twitter" : "Publicação no Twitter falhou";	
			Toast.makeText(Twitter.this, text, Toast.LENGTH_SHORT).show();
			finish();
		}
	};
	private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {
		@Override
		public void onComplete(String value) {
			username 	= mTwitter.getUsername();
			username	= (username.equals("")) ? "Sem nome" : username;

			postToTwitter = true;

			Toast.makeText(Twitter.this, "Ligado ao Twitter como " + username, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(String value) {
			Toast.makeText(Twitter.this, "Ligação ao Twitter falhou", Toast.LENGTH_LONG).show();
		}
	};
}