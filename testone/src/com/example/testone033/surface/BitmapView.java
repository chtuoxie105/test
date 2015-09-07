package com.example.testone033.surface;

import com.example.testone001.loning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BitmapView extends View {
	private Bitmap mapOne, mapTwo;
	private Context context;
	private Paint paint;
	private int x, y;

	public BitmapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		context = context;
	}

	private void inint() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setColor(Color.BLUE);
		// ����ͼƬ��ߵ���Ӱ
		paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
	}

	private void intRes() {
		// ��ȡλͼ
		mapOne = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		// ��ȡλͼ��Alphaͨ��ͼ
		mapTwo = mapOne.extractAlpha();
		x = 50;
		y = 50;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �Ȼ�����Ӱ
		canvas.drawBitmap(mapTwo, x, y, paint);
		// �ٻ���λͼ
		canvas.drawBitmap(mapOne, x, y, null);

	}

}
