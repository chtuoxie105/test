package com.example.testone019.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.testone005.mybaseadapter.s001.MybaseActivity;

/**
 * �㲥(��̬�㲥)�������ա����ˡ�����Ӧ�¼�
 */
public class ReceiveBroadCast extends BroadcastReceiver {
	public String ACTION_SYATEM_SMS = "android.provider.Telephony.SMS_RECEIVED";
	public String ACTION_USER_BOOT = "android.intent.action.BOOT_COMPLETED";

	public void onReceive(Context context, Intent intent) {
		String s = intent.getAction();
		if (s.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Toast.makeText(context, "���Ź㲥����..��̨����..��̬", 0).show();
		}
		// else if (s.equals(MyBroadCast.ACTION_DEFINE)) {
		// Toast.makeText(context, "�Զ���㲥....��̬", 0).show();
		// }
		else if (s.equals(ACTION_USER_BOOT)) {
			Toast.makeText(context, "��������>1>��̬ע��", 0).show();
			Toast.makeText(context, "��������>2>��̬ע��", 0).show();
			Toast.makeText(context, "��������>3>��̬ע��", 0).show();
			Toast.makeText(context, "��������>4>��̬ע��", 0).show();
			Toast.makeText(context, "��������>5>��̬ע��", 0).show();
			Toast.makeText(context, "��������>6>��̬ע��", 0).show();
			Toast.makeText(context, "��������>7>��̬ע��", 0).show();
			Toast.makeText(context, "��������>8>��̬ע��", 0).show();
		
			Intent IntentService = new Intent(context,
					ActionUserBootService.class);
			IntentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(IntentService);
			abortBroadcast();//���ز������´���
			Toast.makeText(context, "��������>��������>��̬ע��", 0).show();
		}

	}

}
