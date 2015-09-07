package com.example.testone019.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testone001.loning.R;

/**
 * 1��������(IntentFilter)����ж�̬�㲥��ע��,ע����
 * ע��:registerReceiver()������:unregisterReceiver().
 * 2����̬�㲥ֻ�ڵ�ǰActivity�������ã����ԣ�ע���ע������Activity��ǰ�������������洴��������.
 * 3����onResume()��onPause()���� .
 * 4���㲥����������ֻ��10�����ҵ�ʱ�䣬���Բ��ܴ��������¼�������ᱨ��
 * 5��ֻ�Ǹ���Է��ͳ����Ĺ㲥���й��ˡ����ա���Ӧ������������������¼�. 
 * 6���㲥�Ķ�������ͼ(intent).
 */
public class MyBroadCast extends Activity implements OnClickListener {
	private EditText mBroadCastEdit;
	private Button mStartBroadCastBtn, mCancelBroadCastBtn;
	public static final String ACTION_DEFINE = "android.content.BroadcastReceiver.MyBroadCast";
	public String ACTION_DEFINE_TRENDS = "android.content.BroadcastReceiver.trends";
	public String ACTION_SYATEM_SMS = "android.provider.Telephony.SMS_RECEIVED";
	public String ACTION_USER_BOOT = "android.intent.action.BOOT_COMPLETED";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast_layout);

		mBroadCastEdit = (EditText) findViewById(R.id.broadcast_edittext);
		mStartBroadCastBtn = (Button) findViewById(R.id.start_broadcast_btn);
		mCancelBroadCastBtn = (Button) findViewById(R.id.cancel_broadcast_btn);

		mStartBroadCastBtn.setOnClickListener(this);
	}

	/**
	 * ��ť�ļ��������¼�
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_broadcast_btn:
			Intent intent = new Intent(ACTION_DEFINE);
			// sendBroadcast(intent);
			sendOrderedBroadcast(intent, null);
			Log.i("11", "����ɹ�");
			break;
		}
	}

	/**
	 * ��װ��̬ע��
	 */
	public void trendsRegister(String s) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(s);
		registerReceiver(receiver, filter);
	}

	/**
	 * ע��㲥
	 */
	protected void onResume() {
		super.onResume();
		trendsRegister(ACTION_DEFINE_TRENDS);// �Զ��嶯̬ע��
		trendsRegister(ACTION_SYATEM_SMS);// �Զ��嶯̬ע����ż���
		trendsRegister(ACTION_DEFINE);// �Զ���㲥��������
		trendsRegister(ACTION_USER_BOOT);// �Զ���㲥��������
	}
	/**
	 * ��̬ע��㲥�Ľ���
	 */
	BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_DEFINE_TRENDS)) {
				Toast.makeText(context, "��̬ע��㲥>�Զ���", 0).show();
			} else if (action.equals(ACTION_SYATEM_SMS)) {
				Toast.makeText(context, "��̬ע��㲥��������>�Զ���", 0).show();
				abortBroadcast();// ��ֹ�㲥�ļ�������
			} else if (action.equals(ACTION_DEFINE)) {
				Toast.makeText(context, "�Զ���㲥>.>��̬ע��", 0).show();
				abortBroadcast();// ��ֹ�㲥�ļ�������
			} else if (action.equals(ACTION_USER_BOOT)) {
				Toast.makeText(context, "��������>>��̬ע��", 0).show();
				trendsRegister(ACTION_SYATEM_SMS);// �Զ��嶯̬ע����ż���

				Intent service = new Intent(MyBroadCast.this,
						ActionUserBootService.class);
				startService(service);
				Toast.makeText(context, "��������>��������>��̬ע��", 0).show();
			}
		}
	};

	/**
	 * ע����̬�㲥
	 */
	protected void onPause() {
		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	};
}
