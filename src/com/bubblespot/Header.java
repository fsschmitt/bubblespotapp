package com.bubblespot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Header extends LinearLayout {
	public static final String TAG = Header.class.getSimpleName();

	protected ImageView logo;
	protected ImageView lupa;

	public Header(Context context) {
		super(context);
	}

	public Header(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initHeader() {
		inflateHeader();
	}

	private void inflateHeader() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);
		logo = (ImageView) findViewById(R.id.logo);
		lupa = (ImageView) findViewById(R.id.lupa);
	}
}
