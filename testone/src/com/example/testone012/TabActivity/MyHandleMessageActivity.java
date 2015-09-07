package com.example.testone012.TabActivity;

import com.example.testone001.loning.R;
import com.example.testone005.mybaseadapter.s001.MybaseActivity;
import com.example.testone019.broadcast.ActionUserBootService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
/**
 * 
 * ������ӭ����ͺ�̨����
 *
 */
public class MyHandleMessageActivity extends Activity {
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			Toast.makeText(MyHandleMessageActivity.this, "���ӳɹ�" + msg.obj, 1)
					.show();

			Intent IntentService = new Intent(MyHandleMessageActivity.this,
					ActionUserBootService.class);
			IntentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(IntentService);
		}
	};
	protected void onSaveInstanceState(Bundle outState) {
		
	};
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_main);

		// welcomeCommonThread();
		welcomeLooperThread();
	}

	/**
	 * ��ͨ���߳�
	 */
	public void welcomeCommonThread() {
		new Thread(new Runnable() {
			public void run() {
				try {

					Thread.sleep(5000);
					startActivity(new Intent(MyHandleMessageActivity.this,
							MybaseActivity.class));
					Message msg = Message.obtain();
					msg.obj = "��ͨ�̴߳�����Ϣ";
					msg.what = 0;

					mHandler.sendMessage(msg);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * Looper������Ϣ
	 */
	public void welcomeLooperThread() {

		mHandler.postDelayed(new Runnable() {
			public void run() {
				startActivity(new Intent(MyHandleMessageActivity.this,
						MybaseActivity.class));

				// Looper.prepare(); // ����ǰ�̳߳�ʼ��ΪLooper�߳�
				Message meas = Message.obtain();
				meas.obj = "Looper������Ϣ";
				meas.what = 1;
				mHandler.sendMessage(meas);
				// Looper.loop();// ��ʼѭ��������Ϣ����
				finish();
			}
		}, 100);
	}
}
