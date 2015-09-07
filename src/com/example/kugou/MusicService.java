package com.example.kugou;

import java.io.IOException;
import java.util.ArrayList;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.kugou.listadapter.MusicWayBean;

public class MusicService extends Service {
	NotificationManager manger;
	int notifinumber = 1;// �汾������
	// --------��̬ע��㲥>>֪ͨ�Ĺ㲥--------------
	public final String ACTION_UP_MUSIC = "com.kugou.up_music";
	public final String ACTION_START_SUSPEND_MUSIC = "com.kugou.start_suspend_music";
	public final String ACTION_NEXT_MUSIC = "com.kugou.next_music";
	public final String ACTION_CLEAN_NOTIFICATION = "com.kugou.clean_notification";
	// -----------������Ĺ㲥----------
	public final String ACTIVITY_UP_CLICK = "com.kugou.activity.up_click_musicbtn";
	public final String ACTIVITY_NEXT_CLICK = "com.kugou.activity.next_click_musicbtn";
	public final String ACTIVITY_START_AND_SUSPEND_CLICK = "com.kugou.activity.start_and_suspend_click_musicbtn";
	public final String ACTIVITY_LISTVIEW_CLICK = "com.kugou.activity.listView_click_musicbtn";

	private ArrayList<MusicWayBean> musicBeanList;// ���մ��е�ַ�����ֵ�����
	private int checknumber;// ���մ��ݵ������һ��,Ҳ������Ϊÿ�ε�������ĵ�ǰѡֵ

	private MediaPlayer mediaPlay = new MediaPlayer();
	private MusicWayBean mBeanMusicList;
	private String musicPath;
	private String musicName;
	private boolean mNitifiSwitch = false;

	/**
	 * ������һ�ס���һ�ס���ͣ����ʼ�Ľӿ�
	 */
	public interface ICopyMusicService {
		/**
		 * ��һ�׸���
		 */
		public void iUpMusic(String upmusic);

		/**
		 * ��һ�׸���
		 */
		public void iNextMusic(String nextmusic);

		/**
		 * ��ͣ�����Ÿ���
		 */
		public boolean iStartAndSuspendMusic();

		/**
		 * ���ص�ǰ��������ʱ��
		 */
		public int iAllTimeMusic();

		/**
		 * ���ص�ǰ���������ڲ���ʱ��
		 */
		public int iCurrentTimeMusic();

		/**
		 * �������϶���ʱ�����
		 */
		public void iSeekBarcheck(int checknumb);
	}

	/**
	 * ʵ���Լ�����Ľӿں�binder����
	 */

	public class MusicBinderService extends Binder implements ICopyMusicService {

		public void iUpMusic(String upmusic) {
			musicMediaPlayerPrepare(upmusic);

		}

		public void iNextMusic(String nextmusic) {
			musicMediaPlayerPrepare(nextmusic);

		}

		public boolean iStartAndSuspendMusic() {
			return startAndSuspendMusic();
		}

		public int iAllTimeMusic() {
			if (mediaPlay != null) {
				return mediaPlay.getDuration();
			} else {
				return 0;
			}
		}

		public int iCurrentTimeMusic() {
			if (mediaPlay != null) {
				return mediaPlay.getCurrentPosition();
			} else {
				return 0;
			}

		}

		public void iSeekBarcheck(int checkNumb) {
			seekBarCheck(checkNumb);

		}

	}

	MusicBinderService mMusicBinderService = new MusicBinderService();

	public void onCreate() {
		super.onCreate();

	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			mediaPlay.reset();

			IntentFilter filter = new IntentFilter();
			filter.addAction(ACTION_UP_MUSIC);// ֪ͨ����һ��
			filter.addAction(ACTION_START_SUSPEND_MUSIC);// ֪ͨ�� ��ͣ����ʼ
			filter.addAction(ACTION_NEXT_MUSIC);// ֪ͨ����һ��
			filter.addAction(ACTION_CLEAN_NOTIFICATION);// ֪ͨ�� ���֪ͨ

			filter.addAction(ACTIVITY_UP_CLICK);// ��෢�㲥����һ��
			filter.addAction(ACTIVITY_START_AND_SUSPEND_CLICK);// ��෢�㲥����ͣ����ʼ�㲥
			filter.addAction(ACTIVITY_NEXT_CLICK);// ����㲥����һ��
			filter.addAction(ACTIVITY_LISTVIEW_CLICK);// ����㲥�ĵ��ListView�������ֺ�

			registerReceiver(notifiReceiver, filter);
			// createNotification();// ���� ˢ��֪ͨ
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public IBinder onBind(Intent intent) {
		return mMusicBinderService;
	}

	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mediaPlay != null) {
			if (mediaPlay.isPlaying()) {
				mediaPlay.stop();
			}
			mediaPlay.release();
			mediaPlay = null;
		}

	}

	/**
	 * �������ֲ�������Ҫ��ħ��ͣ��Ҫô����
	 */

	public boolean startAndSuspendMusic() {
		if (!mediaPlay.isPlaying()) {
			mediaPlay.start();// ����
			return false;
		} else {
			mediaPlay.pause();// ��ͣ
			return true;
		}
	}

	/**
	 * ���������������׼���׶�
	 */
	public void musicMediaPlayerPrepare(String s) {
		try {
			if (mediaPlay == null) {
				mediaPlay = new MediaPlayer();
			}
			mediaPlay.reset();

			mediaPlay.setDataSource(s);
			mediaPlay.prepare();
			mediaPlay.start();

		} catch (IllegalArgumentException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void seekBarCheck(int n) {
		mediaPlay.seekTo(n);
	}

	/**
	 * ����һ��֪ͨ
	 */
	public void createNotification() {
		// ----------------״̬����ʾ��Ϣ-----------------
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.common_m54);
		builder.setTicker("��ӭʹ�ò�����");// ����������ʾ
		// builder.setOngoing(true);// ����Ϊ�������ģʽ,��åʽ֪ͨ
		// ----------------������֪ͨ������ת��������-----------------
		Intent intents = new Intent(this, NewKugouMainActivity.class);
		intents.putExtra("nameTimeNotifi", musicName);

		PendingIntent pendIntent = PendingIntent.getActivity(this, 11, intents,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendIntent);

		// ------�Զ���notification����
		RemoteViews remoteView = new RemoteViews(getPackageName(),
				R.layout.activity_kugou_music_notificstion_layout);
		// -----------�Զ���֪ͨ��ť�ļ�����ͨ���㲥��ִ��-----------------

		// ----------------֪ͨ���������ָ���-----------------

		// remoteView.setTextViewText(R.id.notification_music_name_text,
		// "�ṷ���ֲ�����");

		remoteView
				.setTextViewText(R.id.notification_music_name_text, musicName);

		// ----------------��һ�װ�ť-----------------
		Intent upMusicIntent = new Intent(ACTION_UP_MUSIC);
		if (checknumber == 0) {
			upMusicIntent.putExtra("UP_MUSIC_TIME_NOTIFI", checknumber);
		} else {
			upMusicIntent.putExtra("UP_MUSIC_TIME_NOTIFI", checknumber - 1);
		}
		PendingIntent pendUpMusic = PendingIntent.getBroadcast(this, 2,
				upMusicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.notification_music_up_btn,
				pendUpMusic);
		// ----------------��һ�װ�ť-----------------

		Intent nextMusicIntent = new Intent(ACTION_NEXT_MUSIC);

		if (checknumber < musicBeanList.size() - 1) {
			nextMusicIntent.putExtra("NEXT_MUSIC_TIME_NOTIFI", checknumber + 1);
		} else {
			nextMusicIntent.putExtra("NEXT_MUSIC_TIME_NOTIFI", checknumber);
		}
		PendingIntent pendNextMusic = PendingIntent.getBroadcast(this, 2,
				nextMusicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.notification_next_btn,
				pendNextMusic);
		// ----------------��ͣ����ʼ��ť-----------------
		Intent startAndSuspendMusicIntent = new Intent(
				ACTION_START_SUSPEND_MUSIC);
		startAndSuspendMusicIntent.putExtra("SWITCH_START_SUSPEND_NOTIFI",
				mNitifiSwitch);
		PendingIntent pendStartAndSuspendMusic = PendingIntent.getBroadcast(
				this, 2, startAndSuspendMusicIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// -----------������ͣ�����ŵİ�ť����ͼƬ------------
		if (mediaPlay.isPlaying()) {
			remoteView.setImageViewResource(
					R.id.notification_music_start_suspend_btn,
					R.drawable.minilyric_pause_button_press);
		} else {
			remoteView.setImageViewResource(
					R.id.notification_music_start_suspend_btn,
					R.drawable.minilyric_play_button_press);
		}
		remoteView.setOnClickPendingIntent(
				R.id.notification_music_start_suspend_btn,
				pendStartAndSuspendMusic);
		// ----------------�Ƴ��㲥��ť-----------------
		Intent cleanNotifiIntent = new Intent(ACTION_CLEAN_NOTIFICATION);
		PendingIntent pendcleanNotifiMusic = PendingIntent.getBroadcast(this,
				2, cleanNotifiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.notification_destroy_btn,
				pendcleanNotifiMusic);

		builder.setContent(remoteView);
		// -----------�Զ���֪ͨ�ĸ���-----------------

		manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manger.notify(notifinumber, builder.build());

	}

	BroadcastReceiver notifiReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_UP_MUSIC)) {
				// ���������֪ͨ�㲥----��һ��
				if (checknumber - 1 < 0) {// �����0��ʼ����
					checknumber = 0;// ��һ�θ�ֵ,����
				} else {
					musicPath = musicBeanList.get(checknumber - 1)
							.getMusicPathBean();
					musicName = musicBeanList.get(checknumber - 1)
							.getMusicNameBean();

					musicMediaPlayerPrepare(musicPath);
					checknumber = checknumber - 1;// ��һ�θ�ֵ,����
					createNotification();
				}

			} else if (action.equals(ACTION_START_SUSPEND_MUSIC)) {
				// ���������֪ͨ�㲥----���š���ͣ
				musicName = musicBeanList.get(checknumber).getMusicNameBean();
				Log.i("11", "ˢ��ǰ>>>" + musicName);
				mNitifiSwitch = startAndSuspendMusic();
				createNotification();

			} else if (action.equals(ACTION_NEXT_MUSIC)) {
				// ���������֪ͨ�㲥----��һ��

				Log.i("11", "��һ������ǰ��ֵ>>" + checknumber);
				if (checknumber + 1 < musicBeanList.size()) {// �����0��ʼ����
					musicPath = musicBeanList.get(checknumber + 1)
							.getMusicPathBean();
					musicName = musicBeanList.get(checknumber + 1)
							.getMusicNameBean();
					musicMediaPlayerPrepare(musicPath);

					checknumber = checknumber + 1;// ��һ�θ�ֵ,����
					createNotification();
				} else {
					checknumber = musicBeanList.size();
				}

			} else if (action.equals(ACTION_CLEAN_NOTIFICATION)) {
				// ���������֪ͨ�㲥----���֪ͨ
				manger.cancel(notifinumber);
			} else if (action.equals(ACTIVITY_UP_CLICK)) {
				// Activity�������֪ͨ�㲥----��һ��
				int activiUpClickNumb = intent.getIntExtra(
						"activity_up_click_music", 0);
				checknumber = activiUpClickNumb;
				if (checknumber > 0) {
					musicName = musicBeanList.get(checknumber - 1)
							.getMusicNameBean();
					createNotification();
				}
			} else if (action.equals(ACTIVITY_NEXT_CLICK)) {
				// Activity�������֪ͨ�㲥----��һ��
				int activiNextClickNumb = intent.getIntExtra(
						"activity_next_click_music", 0);

				checknumber = activiNextClickNumb;
				musicName = musicBeanList.get(checknumber).getMusicNameBean();
				Log.i("11", "�����һ�׹㲥��������>>" + musicName);
				createNotification();
			} else if (action.equals(ACTIVITY_START_AND_SUSPEND_CLICK)) {
				// Activity�������֪ͨ�㲥----���š���ͣ
				mNitifiSwitch = intent.getBooleanExtra(
						"activity_startandsuspend_click_music", true);
				createNotification();
			} else if (action.equals(ACTIVITY_LISTVIEW_CLICK)) {
				Log.i("11", "������ջ���>>>>LISTVIEW�Ĺ㲥");
				checknumber = intent.getIntExtra("LIST_V_CLICK_POSITON", 0);
				musicBeanList = intent
						.getParcelableArrayListExtra("LIST_V_CLICK_MUSICLIST");

				mBeanMusicList = musicBeanList.get(checknumber);

				musicPath = mBeanMusicList.getMusicPathBean();
				musicName = mBeanMusicList.getMusicNameBean();

				Log.i("11", musicPath);

				musicMediaPlayerPrepare(musicPath);
				createNotification();
			}
		}
	};

}
