package com.example.testone020.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.testone001.loning.R;
import com.example.testone012.TabActivity.MyTabHost;
import com.example.testone019.broadcast.ActionUserBootService;

public class NotificationActivity extends Activity implements OnClickListener {
	private Button mSendBtn, mUpdataBtn, mStopAppBtn, mProgressBarBtn,
			mMineBtn;
	private NotificationManager manger;
	private int RENEW_ID = 001;// �����õ�
	private int RENEW_ID_TO_SERVICE = 10;// �����ͣ����APP�е�int�����ڸ���
	private int RENEW_ID_STOP = 20;// �����ͣ����APP�е�int�����ڸ���
	private int RENEW_ID_PROGRESSBAR = 30;// �����ͣ����APP�е�int�����ڸ���

	private Handler mHandler = new Handler();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_layout);
		mSendBtn = (Button) findViewById(R.id.notification_send_message);
		mUpdataBtn = (Button) findViewById(R.id.notification_updata_message);
		mStopAppBtn = (Button) findViewById(R.id.notification_stopapp_message);
		mProgressBarBtn = (Button) findViewById(R.id.notification_progressbar_message);
		mMineBtn = (Button) findViewById(R.id.notification_mine_message);
		// ʵ����֪ͨ�����ߣ���������ˢ�·���>ȥˢ��֪ͨ
		manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mSendBtn.setOnClickListener(this);// �����ʾ�Լ�дһЩ���ԣ���ͨ��֪ͨ
		mUpdataBtn.setOnClickListener(this);// �����ʾдһ�����������֪ͨ
		mStopAppBtn.setOnClickListener(this);// �����ʾһ��֪ͨ�����ȥ���ͣ�����APP���棬������Ϊ������ؼ����˳���
		mProgressBarBtn.setOnClickListener(this);// �����ʾһ��֪ͨ����ʾ������һ��������
		mMineBtn.setOnClickListener(this);// �����ʾһ��֪ͨ����ʾ������һ��������

	}

	/**
	 * ��ť�ļ���
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notification_send_message:
			SystemNotificationTest();
			break;

		case R.id.notification_updata_message:
			StartServiceNotification();
			break;
		case R.id.notification_stopapp_message:
			NotificationStopTOAPP();
			break;
		case R.id.notification_progressbar_message:
			pregressBarNotification();
			break;
		case R.id.notification_mine_message:
			createNotification();
			break;
		}
	}

	/**
	 * ����һ:����ѧϰ֪ͨ��������ԣ�ϵͳ�Ľ���֪ͨ>>ģ�����֪ͨ
	 */
	public void SystemNotificationTest() {
		// ����֪ͨ��������֪ͨ������״̬����ʾ��������Ϣ����ʾ���û������ͼƬ�����⡢���ݡ�ʱ��.�������ʧ֪ͨ��....
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.m3);
		builder.setContentTitle("����֪ͨ");
		builder.setContentText("����1��δ�����ţ������鿴");
		// builder.setAutoCancel(true);// �����ʧ
		builder.setOngoing(true);// ����Ϊ�������ģʽ,��åʽ֪ͨ
		builder.setTicker("��������Ϣ��!");// ����������ʾ
		int kk = RENEW_ID++;
		builder.setNumber(kk);// ��ʾ������Ϣ
		builder.setWhen(System.currentTimeMillis());// ��ʾ��ǰʱ��

		Intent intentService = new Intent(this, MyTabHost.class);
		startService(intentService);

		PendingIntent pending = PendingIntent.getActivity(this, 0,
				intentService, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pending);
		// ˢ��֪ͨ
		manger.notify(RENEW_ID, builder.build());
	}

	/**
	 * ������:�����ȥ����һ����̨����service
	 */
	public void StartServiceNotification() {
		// ����֪ͨ��������֪ͨ������״̬����ʾ��������Ϣ����ʾ���û������ͼƬ�����⡢���ݡ�ʱ��.�������ʧ֪ͨ��....
		NotificationCompat.Builder builderService = new NotificationCompat.Builder(
				this);
		builderService.setSmallIcon(R.drawable.m3);
		builderService.setTicker("������һ����̨����");
		builderService.setContentTitle("��������֪ͨ");
		builderService.setContentText("������һ�����ż�����֪ͨ");
		builderService.setWhen(System.currentTimeMillis());// ��ʾ��ǰ��ʱ��
		builderService.setAutoCancel(true);// �����ȡ����֪ͨ
		// ��������
		Intent intentServ = new Intent(this, ActionUserBootService.class);
		intentServ.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intentServ);

		// ʵ����PendingIntent�ķ����е��ĸ�����(�����ģ���֪��ֱ�ӷ�0���У�Intent,ֱ����PendingIntent�㣬������4������);
		PendingIntent pendingService = PendingIntent.getService(this, 0,
				intentServ, PendingIntent.FLAG_UPDATE_CURRENT);
		// ����֪ͨ���ý�ȥ��PendingIntent��Intent������һ�������ǲ���������ת��δ����
		builderService.setContentIntent(pendingService);

		// ��;:ˢ��֪ͨ
		manger.notify(RENEW_ID_TO_SERVICE, builderService.build());
	}

	/**
	 * ������:���֪ͨ�󣬽�����ת�Ľ��棬Ȼ�������ؼ���ͣ�����Ǹ�App���棬����ֱ���˳���
	 */
	public void NotificationStopTOAPP() {
		NotificationCompat.Builder builderStop = new NotificationCompat.Builder(
				this);
		builderStop.setSmallIcon(R.drawable.m3);
		builderStop.setContentTitle("΢��֪ͨ");
		builderStop.setContentText("��ĺ���xxx���㷢����һ����Ϣ!");
		builderStop.setTicker("���΢�ŷ���һ����Ϣ֪ͨ!");
		builderStop.setAutoCancel(true);// ���õ����ȡ��֪ͨ

		Intent nextIntent = new Intent(this, MyTabHost.class);
		startActivity(nextIntent);
		// ͣ�����Ǹ�APPӦ����
		TaskStackBuilder taskstack = TaskStackBuilder.create(this);
		taskstack.addParentStack(MyTabHost.class);
		taskstack.addNextIntent(nextIntent);

		PendingIntent pengdingStop = taskstack.getPendingIntent(0,
				PendingIntent.FLAG_CANCEL_CURRENT);
		builderStop.setContentIntent(pengdingStop);
		manger.notify(RENEW_ID_STOP, builderStop.build());
	}
	/**
	 * ������:�Զ���֪ͨ
	 */
	public void createNotification() {
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("��ӭʹ�ò�����");// ����������ʾ
//		builder.setOngoing(true);// ����Ϊ�������ģʽ,��åʽ֪ͨ
		
		// ------�Զ���notification����
		RemoteViews remoteView = new RemoteViews(getPackageName(),
				R.layout.activity_sharedpreferences_layout);
		builder.setContent(remoteView);
		//�Զ���֪ͨ���水ť���¼�����
//		remoteView.setOnClickPendingIntent(R.id.main_sharedpreferences_btn, pendingIntent);
		// ----�Զ���notification�¼�����
		Intent intent = new Intent(this, MyTabHost.class);
		
		PendingIntent pendIntent = PendingIntent.getActivity(this, 01, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.main_sharedpreferences_btn, pendIntent);
		
//		builder.setContentIntent(pendIntent);
		int notificationId= 7;// �汾������
		NotificationManager manger6 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manger6.notify(notificationId, builder.build());
	
	}

	/**
	 * ������:��ʾһ����������֪ͨ�����Ұѽ������ļ��طŵ�������ȥִ�У��Ų���Ӱ�����ڽ�������������Ĳ�����
	 */
	public void pregressBarNotification() {
		final NotificationCompat.Builder progressBarActionBuilder = new NotificationCompat.Builder(
				this);
		progressBarActionBuilder.setTicker("XX�Ѽ�������!")
				.setSmallIcon(R.drawable.m3).setContentTitle("XX��������...")
				.setContentText("�������أ����Ժ�....")
				.setWhen(System.currentTimeMillis());
		progressBarActionBuilder.setAutoCancel(true);
		new Thread(new Runnable() {
			public void run() {
				for (int progress = 0; progress < 101; progress++) {
					try {
						Thread.sleep(100);
						progressBarActionBuilder.setProgress(100, progress,
								false);

						// ˢ����Ϣ��÷ŵ����߳�ȥִ��
						mHandler.post(new Runnable() {
							public void run() {
								manger.notify(RENEW_ID_PROGRESSBAR,
										progressBarActionBuilder.build());
							}
						});

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				// ˢ����Ϣ��÷ŵ����߳�ȥִ��
				mHandler.post(new Runnable() {
					public void run() {
						progressBarActionBuilder.setProgress(0, 0, false);
						progressBarActionBuilder.setContentTitle("�������!");
						progressBarActionBuilder.setContentText("������װ");
						manger.notify(RENEW_ID_PROGRESSBAR,
								progressBarActionBuilder.build());
					}
				});
			}
		}).start();

		// //ģ��������ɺ󷢲�֪ͨ��Ϣ
		//
		// manger.notify(RENEW_ID_PROGRESSBAR,
		// progressBarActionBuilder.build());
		//

	}
}
