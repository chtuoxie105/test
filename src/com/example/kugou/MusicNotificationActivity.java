package com.example.kugou;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * ��Ҫʵ�ֶ���̵Ĺ���Ҳ�ǳ��򵥣�ֻ��Ҫ��AndroidManifest�ļ���Ӧ�ó������������һ�� android:process���ԾͿ�����
 * ������˵����ϣ���������ֵ�Service����������һ�������Ľ��̵��У��Ϳ�������д��
 * 
 * @author Administrator <service android:name=".PlaybackService"
 *         android:process=":background" />
 * 
 * ��ָ���Ľ�������background����Ҳ���Խ����ĳ�������ϲ�������֡���Ҫע����ǣ���������ǰ�涼Ӧ�ü���һ��ð�ţ�
 * ��ʾ�ý�����һ����ǰӦ�ó����˽�н���
 */
public class MusicNotificationActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void createNotification() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setTicker("��ӭʹ�ò�����");// ����������ʾ
		builder.setOngoing(true);// ����Ϊ�������ģʽ,��åʽ֪ͨ

		// ------�Զ���notification����
		RemoteViews remoteView = new RemoteViews(getPackageName(),
				R.layout.activity_kugou_music_notificstion_layout);
		builder.setContent(remoteView);
		// ----�Զ���notification�¼�����
		Intent intent = new Intent(this, NewKugouMainActivity.class);
		startActivity(intent);
		PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// ����֪ͨ���ý�ȥ��PendingIntent��Intent������һ�������ǲ���������ת��δ����
		builder.setContentIntent(pendIntent);
		Intent intentText = getIntent();

		remoteView.setOnClickPendingIntent(R.id.notification_music_name_text,
				pendIntent);
		remoteView.setOnClickPendingIntent(R.id.notification_music_up_btn,
				pendIntent);
		remoteView.setOnClickPendingIntent(
				R.id.notification_music_start_suspend_btn, pendIntent);
		remoteView.setOnClickPendingIntent(R.id.notification_next_btn,
				pendIntent);
		remoteView.setOnClickPendingIntent(R.id.notification_destroy_btn,
				pendIntent);

		NotificationManager manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manger.notify(1, builder.build());
	}

}
