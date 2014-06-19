package com.example.googlesearchimage;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DisplayImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_image);
		ImageResult imgResult = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView fullImgV = (SmartImageView) findViewById(R.id.imgViewFull);
		fullImgV.setImageUrl(imgResult.getFullUrl());
	}
}
