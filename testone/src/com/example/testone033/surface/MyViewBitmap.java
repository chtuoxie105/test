package com.example.testone033.surface;

import com.example.testone001.loning.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MyViewBitmap extends View {
	private Paint mPaint;// ����
	private Context mContext;// �����Ļ���������
	private Bitmap bitmap;// λͼ
	private int x, y;// λͼ����ʱ���Ͻǵľ���

	public MyViewBitmap(Context context) {
		super(context);
	}

	public MyViewBitmap(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// ʵ��������
		inint();
		// ��ʼ����Դ
		intRes();
	}

	/** ʵ�������ʣ������ÿ���� ***/
	public void inint() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ColorMatrix colorFil = new ColorMatrix(new float[]{
				 1,0,0,0,0,
				 0,1,0,0,0,
				 0,0,1,0,0,
				 0,0,0,1,0
				 });
		mPaint.setColorFilter(new ColorMatrixColorFilter(colorFil));
		// ��һ��Բ
		// ������ʽ�����֣�
		// 1.Paint.Style.STROKE�����
		// 2.Paint.Style.FILL_AND_STROKE����߲����
		// 3.Paint.Style.FILL�����
		// mPaint.setStyle(Style.FILL_AND_STROKE);
		// ������ɫ
		// mPaint.setColor(Color.BLUE);
		// ���嵭��ɫ
		// mPaint.setColor(Color.argb(255,255,128,103));
		// mPaint.setStrokeWidth(10);
		// ColorMatrix colorFil = new ColorMatrix(new float[]{
		// 1,0,0,0,0,
		// 0,1,0,0,0,
		// 0,0,1,0,0,
		// 0,0,0,1,0
		// });
		// R(���)G����ɫ��B����ɫ��A��͸���ȣ����䷶Χ��0.0F��2.0F֮��
	}

	/** ��ʼ����Դ **/
	public void intRes() {
		bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.btn_style_one_pressed);
		x = 50;
		y = 50;
		
		/*
		 * ����λͼ����ʱ���Ͻǵ�����ʹ��λ����Ļ���� ��Ļ����x������ƫ��λͼһ��Ŀ�� ��Ļ����y������ƫ��λͼһ��ĸ߶�
		 * 
		 * x = MeasureUtil.getScreenSize((Activity) mContext)[0] / 2 -
		 * bitmap.getWidth() / 2; y = MeasureUtil.getScreenSize((Activity)
		 * mContext)[1] / 2 - bitmap.getHeight() / 2;
		 */
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.drawCircle(50, 50, 100, mPaint);
		canvas.drawBitmap(bitmap,x,y,mPaint);
	}

}
