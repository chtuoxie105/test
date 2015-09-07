package com.example.testone008.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.testone001.loning.R;
import com.example.testone006.myGridview.MyGridView;

public class MySotckProgressBar extends Activity {
	private ListView mListViewForBar;
	private LinearLayout mLinearFroBar;
	public MyStackProgressBarToBaseAdapter adspToBase;
	
	public Handler mHandle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//	������ȡ Message  ����ֱ����msg ��ȡ���������
			//	mLinearFroBar.setVisibility(View.GONE);//���ؼ��صĽ���
//			int n = msg.what;
			
			adspToBase.setData((List<MyGridViewStack>) msg.obj);//�Ż����������
			
		};
	};
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stack_myprogressbar_layout);

		mListViewForBar = (ListView) findViewById(R.id.stack_bar_list_view);
		mLinearFroBar = (LinearLayout) findViewById(R.id.linear_stack_bar);
		
		mListViewForBar.setEmptyView(mLinearFroBar);//����ListView�Ƿ�Ϊ�����ж���ʾ��������	mLinearFroBar ����
		
		adspToBase = new MyStackProgressBarToBaseAdapter(this);//�����Զ����������
		mListViewForBar.setAdapter(adspToBase);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
					
					List<MyGridViewStack> list = getdata();					
					Message age = Message.obtain();				
					age.obj = list;
					age.what = 0;					
					mHandle.sendMessage(age);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	public List<MyGridViewStack> getdata() {
		List<MyGridViewStack> list = new ArrayList<MyGridViewStack>();
		MyGridViewStack grid = new MyGridViewStack();

		grid.setmImage(R.drawable.wmgj);
		grid.setmText( "�����ˣ���¥�����,�������ʴ�Ƭ");
		list.add(grid);
		
		grid = new MyGridViewStack();
		grid.setmImage(R.drawable.simple1);
		grid.setmText(  "2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
		list.add(grid);
		
		grid = new MyGridViewStack();
		grid.setmImage(R.drawable.simple2);
		grid.setmText( "����39Ԫ����ֵ58Ԫ���ѻ��ų���Ʊ������ʱ�̣�������.");
		list.add(grid);

		return list;
	}

}
