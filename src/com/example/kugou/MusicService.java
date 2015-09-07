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
	int notifinumber = 1;// 版本更新用
	// --------动态注册广播>>通知的广播--------------
	public final String ACTION_UP_MUSIC = "com.kugou.up_music";
	public final String ACTION_START_SUSPEND_MUSIC = "com.kugou.start_suspend_music";
	public final String ACTION_NEXT_MUSIC = "com.kugou.next_music";
	public final String ACTION_CLEAN_NOTIFICATION = "com.kugou.clean_notification";
	// -----------活动发出的广播----------
	public final String ACTIVITY_UP_CLICK = "com.kugou.activity.up_click_musicbtn";
	public final String ACTIVITY_NEXT_CLICK = "com.kugou.activity.next_click_musicbtn";
	public final String ACTIVITY_START_AND_SUSPEND_CLICK = "com.kugou.activity.start_and_suspend_click_musicbtn";
	public final String ACTIVITY_LISTVIEW_CLICK = "com.kugou.activity.listView_click_musicbtn";

	private ArrayList<MusicWayBean> musicBeanList;// 接收带有地址和名字的数组
	private int checknumber;// 接收传递点击的那一项,也可以作为每次点击更换的当前选值

	private MediaPlayer mediaPlay = new MediaPlayer();
	private MusicWayBean mBeanMusicList;
	private String musicPath;
	private String musicName;
	private boolean mNitifiSwitch = false;

	/**
	 * 定义上一首、下一首、暂停，开始的接口
	 */
	public interface ICopyMusicService {
		/**
		 * 上一首歌曲
		 */
		public void iUpMusic(String upmusic);

		/**
		 * 下一首歌曲
		 */
		public void iNextMusic(String nextmusic);

		/**
		 * 暂停、播放歌曲
		 */
		public boolean iStartAndSuspendMusic();

		/**
		 * 返回当前歌曲的总时间
		 */
		public int iAllTimeMusic();

		/**
		 * 返回当前歌曲的正在播放时间
		 */
		public int iCurrentTimeMusic();

		/**
		 * 进度条拖动的时间监听
		 */
		public void iSeekBarcheck(int checknumb);
	}

	/**
	 * 实现自己定义的接口和binder对象
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
			filter.addAction(ACTION_UP_MUSIC);// 通知的上一首
			filter.addAction(ACTION_START_SUSPEND_MUSIC);// 通知的 暂停、开始
			filter.addAction(ACTION_NEXT_MUSIC);// 通知的下一首
			filter.addAction(ACTION_CLEAN_NOTIFICATION);// 通知的 清除通知

			filter.addAction(ACTIVITY_UP_CLICK);// 活动类发广播的上一首
			filter.addAction(ACTIVITY_START_AND_SUSPEND_CLICK);// 活动类发广播的暂停、开始广播
			filter.addAction(ACTIVITY_NEXT_CLICK);// 活动发广播的下一首
			filter.addAction(ACTIVITY_LISTVIEW_CLICK);// 活动发广播的点击ListView播放音乐后

			registerReceiver(notifiReceiver, filter);
			// createNotification();// 启动 刷新通知
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
	 * 启动音乐播放器，要恶魔暂停，要么播放
	 */

	public boolean startAndSuspendMusic() {
		if (!mediaPlay.isPlaying()) {
			mediaPlay.start();// 播放
			return false;
		} else {
			mediaPlay.pause();// 暂停
			return true;
		}
	}

	/**
	 * 播放器里面歌曲的准备阶段
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
	 * 启动一个通知
	 */
	public void createNotification() {
		// ----------------状态上提示消息-----------------
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.common_m54);
		builder.setTicker("欢迎使用播放器");// 任务栏里提示
		// builder.setOngoing(true);// 设置为不可清除模式,流氓式通知
		// ----------------定义点击通知后能跳转到主界面-----------------
		Intent intents = new Intent(this, NewKugouMainActivity.class);
		intents.putExtra("nameTimeNotifi", musicName);

		PendingIntent pendIntent = PendingIntent.getActivity(this, 11, intents,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendIntent);

		// ------自定义notification界面
		RemoteViews remoteView = new RemoteViews(getPackageName(),
				R.layout.activity_kugou_music_notificstion_layout);
		// -----------自定义通知按钮的监听，通过广播来执行-----------------

		// ----------------通知的音乐名字更改-----------------

		// remoteView.setTextViewText(R.id.notification_music_name_text,
		// "酷狗音乐播放器");

		remoteView
				.setTextViewText(R.id.notification_music_name_text, musicName);

		// ----------------上一首按钮-----------------
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
		// ----------------下一首按钮-----------------

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
		// ----------------暂停、开始按钮-----------------
		Intent startAndSuspendMusicIntent = new Intent(
				ACTION_START_SUSPEND_MUSIC);
		startAndSuspendMusicIntent.putExtra("SWITCH_START_SUSPEND_NOTIFI",
				mNitifiSwitch);
		PendingIntent pendStartAndSuspendMusic = PendingIntent.getBroadcast(
				this, 2, startAndSuspendMusicIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// -----------更换暂停、播放的按钮背景图片------------
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
		// ----------------移除广播按钮-----------------
		Intent cleanNotifiIntent = new Intent(ACTION_CLEAN_NOTIFICATION);
		PendingIntent pendcleanNotifiMusic = PendingIntent.getBroadcast(this,
				2, cleanNotifiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteView.setOnClickPendingIntent(R.id.notification_destroy_btn,
				pendcleanNotifiMusic);

		builder.setContent(remoteView);
		// -----------自定义通知的更新-----------------

		manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manger.notify(notifinumber, builder.build());

	}

	BroadcastReceiver notifiReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_UP_MUSIC)) {
				// 服务里面的通知广播----上一首
				if (checknumber - 1 < 0) {// 数组从0开始计数
					checknumber = 0;// 再一次赋值,更新
				} else {
					musicPath = musicBeanList.get(checknumber - 1)
							.getMusicPathBean();
					musicName = musicBeanList.get(checknumber - 1)
							.getMusicNameBean();

					musicMediaPlayerPrepare(musicPath);
					checknumber = checknumber - 1;// 再一次赋值,更新
					createNotification();
				}

			} else if (action.equals(ACTION_START_SUSPEND_MUSIC)) {
				// 服务里面的通知广播----播放、暂停
				musicName = musicBeanList.get(checknumber).getMusicNameBean();
				Log.i("11", "刷新前>>>" + musicName);
				mNitifiSwitch = startAndSuspendMusic();
				createNotification();

			} else if (action.equals(ACTION_NEXT_MUSIC)) {
				// 服务里面的通知广播----下一首

				Log.i("11", "下一首提提前的值>>" + checknumber);
				if (checknumber + 1 < musicBeanList.size()) {// 数组从0开始计数
					musicPath = musicBeanList.get(checknumber + 1)
							.getMusicPathBean();
					musicName = musicBeanList.get(checknumber + 1)
							.getMusicNameBean();
					musicMediaPlayerPrepare(musicPath);

					checknumber = checknumber + 1;// 再一次赋值,更新
					createNotification();
				} else {
					checknumber = musicBeanList.size();
				}

			} else if (action.equals(ACTION_CLEAN_NOTIFICATION)) {
				// 服务里面的通知广播----清除通知
				manger.cancel(notifinumber);
			} else if (action.equals(ACTIVITY_UP_CLICK)) {
				// Activity类里面的通知广播----上一首
				int activiUpClickNumb = intent.getIntExtra(
						"activity_up_click_music", 0);
				checknumber = activiUpClickNumb;
				if (checknumber > 0) {
					musicName = musicBeanList.get(checknumber - 1)
							.getMusicNameBean();
					createNotification();
				}
			} else if (action.equals(ACTIVITY_NEXT_CLICK)) {
				// Activity类里面的通知广播----下一首
				int activiNextClickNumb = intent.getIntExtra(
						"activity_next_click_music", 0);

				checknumber = activiNextClickNumb;
				musicName = musicBeanList.get(checknumber).getMusicNameBean();
				Log.i("11", "活动类下一首广播》》名字>>" + musicName);
				createNotification();
			} else if (action.equals(ACTIVITY_START_AND_SUSPEND_CLICK)) {
				// Activity类里面的通知广播----播放、暂停
				mNitifiSwitch = intent.getBooleanExtra(
						"activity_startandsuspend_click_music", true);
				createNotification();
			} else if (action.equals(ACTIVITY_LISTVIEW_CLICK)) {
				Log.i("11", "服务接收活动里的>>>>LISTVIEW的广播");
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
