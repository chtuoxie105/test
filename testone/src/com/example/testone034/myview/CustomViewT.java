package com.example.testone034.myview;

import com.example.testone001.loning.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewT extends View {
	private Paint paint;
	private String text = "asdasdasdas";

	public CustomViewT(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setStyle(Style.FILL);
		
		// TypedArray�Ǵ����Դ��array,1.ͨ�������ĵõ��������,attrs�ǹ��캯����������,��Ӧattrs.xml
		TypedArray typed = context.obtainStyledAttributes(attrs,
				R.styleable.CustomViewT);
		// ���xml�ﶨ�������,��ʽΪ ����_������ ������Ĭ��ֵ
		int textColor = typed.getColor(R.styleable.CustomViewT_textColor,
				0xFFFFFFFF);
		float textSize = typed.getDimension(R.styleable.CustomViewT_textSize,
				15.0f);

		paint.setColor(textColor);
		paint.setTextSize(textSize);
		// Ϊ�˱����Ժ�ʹ�ø�����һ����,����һ������Դ�������źŸ���Դ
		typed.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText(text,10,60, paint);
	}
}
