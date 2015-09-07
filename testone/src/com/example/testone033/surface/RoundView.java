package com.example.testone033.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class RoundView extends View {
	private static final float STROKE_WIDTH = 1F / 256F, // ��߿��ռ��
			LINE_LENGTH = 3F / 32F, // �߶γ���ռ��
			CRICLE_LARGER_RADIU = 3F / 32F,// ��Բ�뾶
			CRICLE_SMALL_RADIU = 5F / 64F,// СԲ�뾶
			ARC_RADIU = 1F / 8F,// ���뾶
			ARC_TEXT_RADIU = 5F / 32F;// ��Χ�����ְ뾶

	private Paint paint;
	private int size;// ���Ʊ߳�,��ߵĿ��
	private float strokeWidth;// ���Ʊ߳�,��ߵĿ��

	private float x, y;// Բ������
	private float largeCricleRadiu;// Բ�İ뾶

	public RoundView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// ��ʼ������
	private void inint() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeCap(Cap.ROUND);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		// ǿ�Ƴ���һ��
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		size = w;
		calculation();
	}

	// ��Ʋ���
	private void calculation() {
		// ������߿��
		strokeWidth = STROKE_WIDTH * size;
		// Բ�İ뾶
		largeCricleRadiu = size * CRICLE_LARGER_RADIU;
		// Բ������
		x = size / 2;
		y = size / 2 + size * CRICLE_LARGER_RADIU;
		setPara();
	}

	// ���ò���
	private void setPara() {
		paint.setStrokeWidth(strokeWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ���Ʊ���
		canvas.drawColor(0xFFF29B76);
		canvas.drawCircle(x, y, largeCricleRadiu, paint);
	}
}
