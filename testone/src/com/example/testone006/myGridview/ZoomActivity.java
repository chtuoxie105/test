package com.example.testone006.myGridview;

import com.example.testone001.loning.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
/**
 * ���� MyGridViewActivity���� �ഫ������ͼƬ���зŴ���
 */
public class ZoomActivity extends Activity {
	private ImageView mImage;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zoom_layout);
		
		mImage = (ImageView) findViewById(R.id.zoom_image);
		
		Bundle bundle = getIntent().getBundleExtra("BUNDLR");
		mImage.setBackgroundResource(bundle.getInt("IMAGE"));
	}
}
