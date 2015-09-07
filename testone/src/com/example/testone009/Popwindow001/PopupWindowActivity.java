package com.example.testone009.Popwindow001;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.testone001.loning.R;

public class PopupWindowActivity extends Activity {
	private ListView mListPop;
	private Button mPopBtn;
	private PopupWindow mPopWind;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_layout);

		mPopBtn = (Button) findViewById(R.id.popupwindow_btn);
		// ʵ�������ڣ����õ����Ĵ�С
		mPopWind = new PopupWindow(getView(), 400, 400);

		// ���������д�������������հ״����ر�PopWindow �����Ĵ���
		mPopWind.setTouchable(true);
		mPopWind.setOutsideTouchable(true);
		mPopWind.setBackgroundDrawable(new BitmapDrawable());

		// �����ť�͵�������
		mPopBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mPopWind.isShowing()) {// �жϴ����Ƿ��Ѿ���������
					// mPopWind.setOutsideTouchable(false);
					mPopWind.dismiss();// �رմ���
				} else {
					mPopWind.showAsDropDown(v);// ��������
				}
			}
		});
	}

	// ����������������View ��ӵ�PopWindow��ȥ
	public View getView() {
		LayoutInflater mInfalter = LayoutInflater.from(this);
		View v = mInfalter.inflate(R.layout.activity_list_view, null);
		mListPop = (ListView) v.findViewById(R.id.list_view);
		PopupWindowBaseadapter adapter = new PopupWindowBaseadapter(this);
		adapter.setData(getData());// �Ż�������
		mListPop.setAdapter(adapter);
		return v;
	}

	// ��������Դ
	public List<PopWindowBean> getData() {
		List<PopWindowBean> list = new ArrayList<PopWindowBean>();
		PopWindowBean popBean = new PopWindowBean();
		popBean.setmImagePop(R.drawable.simple2);
		popBean.setmTextPop("ҡһҡ");
		list.add(popBean);
		popBean = new PopWindowBean();
		popBean.setmImagePop(R.drawable.simple2);
		popBean.setmTextPop("ɨ��ά��");
		list.add(popBean);
		popBean = new PopWindowBean();
		popBean.setmImagePop(R.drawable.simple2);
		popBean.setmTextPop("���Ҹ�������");
		list.add(popBean);
		popBean = new PopWindowBean();
		popBean.setmImagePop(R.drawable.simple2);
		popBean.setmTextPop("�鿴��ͼ");
		list.add(popBean);

		return list;
	}
}
