package com.example.testone033.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View implements Runnable {
	private Paint mPaint;
	private Context mContext;
	private int pint;

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// ʵ��������
		inint();
	}

	/** ʵ�������ʣ������ÿ���� ***/
	public void inint() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// ��һ��Բ
		// ������ʽ�����֣�
		// 1.Paint.Style.STROKE�����
		// 2.Paint.Style.FILL_AND_STROKE����߲����
		// 3.Paint.Style.FILL�����
		mPaint.setStyle(Style.FILL_AND_STROKE);
		// ������ɫ
//		mPaint.setColor(Color.BLUE);
		//���嵭��ɫ
		mPaint.setColor(Color.argb(255,255,128,103));
		mPaint.setStrokeWidth(10);
		ColorMatrix colorFil = new ColorMatrix(new float[]{
				1,0,0,0,0,
				0,1,0,0,0,
				0,0,1,0,0,
				0,0,0,1,0
		});
		//R(���)G����ɫ��B����ɫ��A��͸���ȣ����䷶Χ��0.0F��2.0F֮��
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(50, 50, 100, mPaint);

	}

	/** �ṩһ��setter�����������ð뾶ֵ�����������˸�ֵ�����invalidate()�����ػ�View */
	public synchronized void setPint(int point) {
		pint = point;
		invalidate();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (pint < 200) {
					pint = pint + 10;
					//ˢ��
					postInvalidate();
				} else {
					pint = 0;
				}
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
