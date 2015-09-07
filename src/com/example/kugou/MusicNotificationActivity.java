package com.example.kugou;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 想要实现多进程的功能也非常简单，只需要在AndroidManifest文件的应用程序组件中声明一个 android:process属性就可以了
 * ，比如说我们希望播放音乐的Service可以运行在一个单独的进程当中，就可以这样写：
 * 
 * @author Administrator <service android:name=".PlaybackService"
 *         android:process=":background" />
 * 
 * 里指定的进程名是background，你也可以将它改成任意你喜欢的名字。需要注意的是，进程名的前面都应该加上一个冒号，
 * 表示该进程是一个当前应用程序的私有进程
 */
public class MusicNotificationActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void createNotification() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setTicker("欢迎使用播放器");// 任务栏里提示
		builder.setOngoing(true);// 设置为不可清除模式,流氓式通知

		// ------自定义notification界面
		RemoteViews remoteView = new RemoteViews(getPackageName(),
				R.layout.activity_kugou_music_notificstion_layout);
		builder.setContent(remoteView);
		// ----自定义notification事件处理
		Intent intent = new Intent(this, NewKugouMainActivity.class);
		startActivity(intent);
		PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// 将此通知设置进去，PendingIntent和Intent的属性一样，但是不是立即跳转，未决的
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
