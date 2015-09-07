package com.example.kugou.listadapter;

import com.example.kugou.NewKugouMainActivity.MusicRefrshTime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class sdsaasdas extends View {
	
	Bitmap bitmapBlue;
	Bitmap bitmapGray;
	Bitmap bitmapRadar;
	 int height;  
	float percent;  
	int value;// 显示的值  
	float degrees;  
	boolean hideRadar;
	
	float ox;
	float oy;
	float cx;
	float cy;
	
	
	public sdsaasdas(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		hideRadar = true;
	}
	public void srartThread(){
		hideRadar = false;//启动一个线程进行不断的旋转角度 	
	new Thread(new Runnable() {
		public void run() {
			while (!hideRadar) {
				degrees = (degrees += 2) >= 360 ? 0 : degrees;
				postInvalidate();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
		
					e.printStackTrace();
				}
			}
		}
	});
	}
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		height = getHeight();
		percent = height / 100f;
		
		
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public float getDegrees() {
		return degrees;
	}

	public void setDegrees(float degrees) {
		this.degrees = degrees;
	}

	public boolean isHideRadar() {
		return hideRadar;
	}

	public void setHideRadar(boolean hideRadar) {
		this.hideRadar = hideRadar;
	} 
}
