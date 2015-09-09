package com.example.kugou;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kugou.MusicService.ICopyMusicService;
import com.example.kugou.listadapter.ListMusicAdapter;
import com.example.kugou.listadapter.MusicWayBean;

public class NewKugouMainActivity extends Activity implements OnClickListener,
		OnSeekBarChangeListener {

	private ImageView mUpMusicBtn, mStartAndSuspendMusicBtn, mNextMusicBtn,
			mListMusicBtn;
	public TextView mTitleMusicNameText;
	private TextView mCurrentTimeText;
	private TextView mAllTimeText;
	private SeekBar mSeekBar;
	private Handler mHander = new Handler();

	private boolean timeSwitch = true;

	// 弹出歌曲列表
	private PopupWindow mPopupWindom;
	private LayoutInflater mInflater;
	// SD卡的目录
	private File allFile = Environment.getExternalStorageDirectory();
	// 装遍历出来的音乐文件的名字
	private List<File> fileName = new ArrayList<File>();
	private ArrayList<MusicWayBean> musicBeanList = new ArrayList<MusicWayBean>();

	// 装遍历出来的音乐文件的名字，是分割过后的音乐文件名字，没有后缀名
	private ArrayList<String> sdcardMusicName = new ArrayList<String>();

	// 在点击ListView中会初次实例化，是当前选择的ListView中的哪一项，用于上一首，下一首，和名字数组中取名字，会在点击上一首，下一首中再次实例化
	private int checkMusicNumber = -1;
	// 服务定义的接口
	private ICopyMusicService mIMusicService;
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {

		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			mIMusicService = (ICopyMusicService) service;

		}
	};

	String mDivicionUpName;// 点击上一首时取得上一首歌曲的名字
	String mDivicionNextName;// 点击下一首时取得下一首歌曲的名字
	String mNotificationName;// 通知处的音乐名字
	String blankString;
	// ---------界面销毁后，点击通知传进来当前歌曲的时间--------------
	private int mAllNotifiTime;
	private int mCurrentNotifiTime;

	// ----------活动法广播-------------
	public final String ACTIVITY_UP_CLICK = "com.kugou.activity.up_click_musicbtn";
	public final String ACTIVITY_NEXT_CLICK = "com.kugou.activity.next_click_musicbtn";
	public final String ACTIVITY_START_AND_SUSPEND_CLICK = "com.kugou.activity.start_and_suspend_click_musicbtn";
	public final String ACTIVITY_LISTVIEW_CLICK = "com.kugou.activity.listView_click_musicbtn";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kugou_music);
		
		musicSQLite();
		
		// getFileWithMusic(allFile);// 遍历文件获取音乐文件
		startThreadFile(allFile);// 开启线程遍历文件获取音乐文件
		example();// 实例化控件
		
		mStartAndSuspendMusicBtn
				.setImageResource(R.drawable.kg_lock_screen_play_button_pressed);

		Intent serviceIntent = new Intent(NewKugouMainActivity.this,
				MusicService.class);
		Log.i("11", "启动服务");
		startService(serviceIntent);
		bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);

		mListMusicBtn.setOnClickListener(this);
		mUpMusicBtn.setOnClickListener(this);
		mStartAndSuspendMusicBtn.setOnClickListener(this);
		mNextMusicBtn.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_kuguo_up_music_btn:
			Log.i("11", "检测上一曲前>>>" + checkMusicNumber);
			if (checkMusicNumber < 0) {
				Toast.makeText(this, "当前没有音乐可以播放", 0).show();
				return;
			} else {
				upMusic(checkMusicNumber);
			}
			break;
		case R.id.new_kuguo_start_and_suspend_music_btn:
			if (checkMusicNumber < 0) {
				Toast.makeText(this, "当前没有音乐可以播放", 0).show();
				return;
			} else {
				boolean falg = mIMusicService.iStartAndSuspendMusic();
				startORsuspendMusic(falg);

			}
			break;
		case R.id.new_kuguo_next_music_btn:
			Log.i("11", "检测下一曲前>>>" + checkMusicNumber);
			if (checkMusicNumber < 0) {
				Toast.makeText(this, "当前没有音乐可以播放", 0).show();
				return;
			} else {
				nextMusic(checkMusicNumber);
			}
			break;
		case R.id.new_kugou_list_music_imagebtn:
			if (!isUseable()) {// 判断SD卡是否能用
				Toast.makeText(NewKugouMainActivity.this, "外部存储不可用",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				listMusicForPopupWindom();
			}
			break;
		}
	}

	/**
	 * 实例化控件
	 */
	public void example() {
		mUpMusicBtn = (ImageView) findViewById(R.id.new_kuguo_up_music_btn);
		mStartAndSuspendMusicBtn = (ImageView) findViewById(R.id.new_kuguo_start_and_suspend_music_btn);
		mNextMusicBtn = (ImageView) findViewById(R.id.new_kuguo_next_music_btn);
		mListMusicBtn = (ImageView) findViewById(R.id.new_kugou_list_music_imagebtn);

		mTitleMusicNameText = (TextView) findViewById(R.id.new_music_name_text);
		mAllTimeText = (TextView) findViewById(R.id.new_kuguo_all_time_text);
		mCurrentTimeText = (TextView) findViewById(R.id.new_kuguo_current_time_text);

		mSeekBar = (SeekBar) findViewById(R.id.new_kuguo_seekbar);
		mInflater = LayoutInflater.from(this);
	}

	/**
	 * 点击Title最右边的按钮弹出音乐列表窗口，用PopupWindom来实现
	 */
	public void listMusicForPopupWindom() {
		// 高度要适当的设置
		mPopupWindom = new PopupWindow(getListViewForPopupWindom(), 700, 500);
		mPopupWindom.setFocusable(true);// 此方法可以保证PopupWindom里面嵌套的ListView有点击响应
		// 下面这三行代码可以让你点击空白处来关闭PopWindow 弹出的窗口
		mPopupWindom.setTouchable(true);
		mPopupWindom.setOutsideTouchable(true);
		mPopupWindom.setBackgroundDrawable(new BitmapDrawable());
		
		mPopupWindom.getContentView().setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mPopupWindom.setFocusable(false);// 失去焦点
				mPopupWindom.dismiss();// 消除pw
				return true;
			}
		});

		// 点击按钮就弹出窗口
		mListMusicBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mPopupWindom.isShowing()) {// 判断窗口是否已经弹出弹出
					mPopupWindom.dismiss();// 关闭窗口
				} else {
					mPopupWindom.showAsDropDown(v);// 弹出窗口
				}

			}
		});
	}

	/**
	 * 把装有ListView的布局返回给PopupWindom的实例化中，才有弹窗的布局类型
	 */
	public View getListViewForPopupWindom() {
		View v = mInflater.inflate(R.layout.copykugou_listview_music_layout,
				null);
		ListView mListview = (ListView) v
				.findViewById(R.id.copykugou_listview_to_musicname);
		TextView allMusicPopupText = (TextView) v
				.findViewById(R.id.popupwindow_allmusic_to_text);
		allMusicPopupText.setText("总共" + fileName.size() + "首歌曲");
		ListMusicAdapter listBaseadapter = new ListMusicAdapter(this);
		mListview.setAdapter(listBaseadapter);
		listBaseadapter.setData(fileName);
		// 点击事件，就启动服务，和添加歌曲的名字
		mListview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListMusicAdapter backAdapter = (ListMusicAdapter) parent
						.getAdapter();
				File fileCheckMusic = (File) backAdapter.getItem(position);

				String backPrepareMusic = fileCheckMusic.getPath();

				Log.i("11", position + "" + musicBeanList);
				// --------发送广播-传递数组（地址、名字）和当前的选项------------
				listSendBroacast(position, musicBeanList);

				mStartAndSuspendMusicBtn
						.setImageResource(R.drawable.kg_lock_screen_pause_button_pressed);

				// 点击就初始化
				checkMusicNumber = position;
				// 分割后缀名,并显示当前点击的歌曲名字
				String divisionNameAfter = divisionMusicName(fileCheckMusic
						.getName());

				mTitleMusicNameText.setText(divisionNameAfter);
				// PopupWindow关闭
				mPopupWindom.dismiss();
				new Thread(new MusicRefrshTime()).start();
			}
		});

		return v;
	}

	/**
	 * 点击listview也发广播，传递当前点击的某一项，和音乐列表
	 * 
	 * @param position
	 * @param ListMusicBeanList
	 */
	public void listSendBroacast(int positions,
			ArrayList<MusicWayBean> ListMusicBeanList) {

		Intent activityBroadcastIntent = new Intent(ACTIVITY_LISTVIEW_CLICK);
		activityBroadcastIntent.putExtra("LIST_V_CLICK_POSITON", positions);
		activityBroadcastIntent.putParcelableArrayListExtra(
				"LIST_V_CLICK_MUSICLIST", ListMusicBeanList);

		sendBroadcast(activityBroadcastIntent);
		Log.i("11", "发送成功");
	}

	public void startThreadFile(final File fileAddress) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getFileWithMusic(fileAddress);
			}
		}).start();
	}

	/**
	 * 无限遍历一个文件夹，找出音乐文件，并添加到List里面，然后设置给适配器
	 */
	public void getFileWithMusic(File fileAddress) {
		if (fileAddress != null) {
			File[] fileList = fileAddress.listFiles();
			if (fileList != null) {
				for (File f : fileList) {
					if (f.isDirectory()) {
						getFileWithMusic(f);

					} else {
						String s = f.getName();
						if (s.endsWith(".mp3") || s.endsWith(".amr")) {
							fileName.add(f);// 添加的是地址
							String namePostfix = divisionMusicName(s);// 返回的没有后缀名的音乐文件名

							MusicWayBean bean = new MusicWayBean();

							bean.setMusicPathBean("file://" + f.getPath());
							bean.setMusicNameBean(namePostfix);

							musicBeanList.add(bean);

							sdcardMusicName.add(namePostfix);
						}
					}
				}
			}
		}
	}

	public void musicSQLite() {
		Cursor cursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		while (cursor.moveToNext()) {
			MusicWayBean bean = new MusicWayBean();

			String musicPath = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
			String musicName = cursor.getString((cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE)));// 音乐标题
			String namePostfix = divisionMusicName(musicName);// 返回的没有后缀名的音乐文件名
			Log.i("11","名字::"+musicName);
			Log.i("11","路径::"+musicPath);
		}
	}

	/**
	 * 判断SD卡是否存在 返回的是true:存在
	 * 
	 * @return
	 */
	public boolean isUseable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回没有后缀名的音乐名字
	 */
	public String divisionMusicName(String s) {
		String[] names = s.split("[.]");
		return names[0];
	}

	/**
	 * 界面销毁时，就取消用户交互的连接
	 */
	protected void onDestroy() {
		super.onDestroy();
		Log.i("11", "销毁界面");
		if (mConnection != null && mIMusicService != null) {
			unbindService(mConnection);
			mConnection = null;
			timeSwitch = false;
		}

	}

	/**
	 * 上一首歌曲
	 */
	public void upMusic(int nowNumb) {
		if (nowNumb - 1 < 0) {// 数组从0开始计数
			Toast.makeText(this, "已经是第一个了", 0).show();

			nowNumb = 0;// 再一次赋值,更新
		} else {
			// 传入新的歌曲播放地址
			mIMusicService.iUpMusic("File://" + fileName.get(nowNumb - 1));// 传入歌曲，
			// 设置title上面显示的名字
			mDivicionUpName = divisionMusicName(sdcardMusicName
					.get(nowNumb - 1));
			// Title处显示当前播放歌曲的名字
			mTitleMusicNameText.setText(mDivicionUpName);

			activityBroadcastReceiver(ACTIVITY_UP_CLICK,
					"activity_up_click_music", blankString, checkMusicNumber,
					true);
			checkMusicNumber = nowNumb - 1;// 再一次赋值,更新
		}
	}

	/**
	 * 暂停会播放更改图片按钮
	 */
	public void startORsuspendMusic(boolean fals) {

		if (fals == true) {
			mStartAndSuspendMusicBtn
					.setImageResource(R.drawable.kg_lock_screen_play_button_pressed);
			activityBroadcastReceiver(ACTIVITY_START_AND_SUSPEND_CLICK,
					blankString, "activity_startandsuspend_click_music", 0,
					true);
		} else {
			mStartAndSuspendMusicBtn
					.setImageResource(R.drawable.kg_lock_screen_pause_button_pressed);
			activityBroadcastReceiver(ACTIVITY_START_AND_SUSPEND_CLICK,
					blankString, "activity_startandsuspend_click_music", 0,
					false);

		}
	}

	/**
	 * 下一首歌曲
	 */
	public void nextMusic(int naexClickNumb) {
		if (naexClickNumb + 1 < fileName.size()) {// 数组从0开始计数

			// 传入新的歌曲播放地址
			mIMusicService.iNextMusic("File://"
					+ fileName.get(naexClickNumb + 1));// 传入歌曲，
			// 设置title上面显示的名字
			mDivicionNextName = divisionMusicName(sdcardMusicName
					.get(naexClickNumb + 1));
			mTitleMusicNameText.setText(mDivicionNextName);

			checkMusicNumber = naexClickNumb + 1;// 再一次赋值,更新
			activityBroadcastReceiver(ACTIVITY_NEXT_CLICK,
					"activity_next_click_music", blankString, checkMusicNumber,
					true);
		} else {
			Toast.makeText(this, "已经是最后一个了", 0).show();
			naexClickNumb = fileName.size();
		}
	}

	/**
	 * 刷新进度条和文本书的时间
	 * 
	 * @author Administrator
	 *
	 */
	public class MusicRefrshTime implements Runnable {

		public void run() {
			while (timeSwitch) {
				if (mIMusicService != null) {

					mHander.post(new Runnable() {
						public void run() {

							mAllNotifiTime = mIMusicService.iAllTimeMusic();
							mCurrentNotifiTime = mIMusicService
									.iCurrentTimeMusic();

							mSeekBar.setMax(mAllNotifiTime);
							mSeekBar.setProgress(mCurrentNotifiTime);

							// 设置总的时间
							int allTimePoint = (mAllNotifiTime / 1000) / 60;
							int allTimeSec = (mAllNotifiTime / 1000) % 60;
							if (allTimePoint < 10 && allTimeSec < 10) {
								mAllTimeText.setText("0" + allTimePoint + ":0"
										+ allTimeSec);
							} else if (allTimePoint < 10 && allTimeSec >= 10) {
								mAllTimeText.setText("0" + allTimePoint + ":"
										+ allTimeSec);
							} else if (allTimePoint > 10 && allTimeSec < 10) {
								mAllTimeText.setText(allTimePoint + ":0"
										+ allTimeSec);
							} else if (allTimePoint > 10 && allTimeSec >= 10) {
								mAllTimeText.setText(allTimePoint + ":0"
										+ allTimeSec);
							}

							// 设置当前的时间
							int currentPoint = (mCurrentNotifiTime / 1000) / 60;
							int currentSec = (mCurrentNotifiTime / 1000) % 60;
							if (currentPoint < 10 && currentSec < 10) {
								mCurrentTimeText.setText("0" + currentPoint
										+ ":0" + currentSec);
							} else if (currentPoint < 10 && currentSec >= 10) {
								mCurrentTimeText.setText("0" + currentPoint
										+ ":" + currentSec);
							} else if (currentPoint > 10 && currentSec < 10) {
								mCurrentTimeText.setText(currentPoint + ":0"
										+ currentSec);
							} else if (currentPoint > 10 && currentSec >= 10) {
								mCurrentTimeText.setText(currentPoint + ":"
										+ currentSec);
							}
							if (mCurrentTimeText.getText().toString()
									.equals(mAllTimeText.getText().toString())
									&& !mAllTimeText.getText().toString()
											.equals("")) {
								Log.i("11", "自动播放");
								nextMusic(checkMusicNumber);
							}
							//
							// if (allTimePoint == currentPoint
							// && allTimeSec == currentSec) {
							//
							//
							// }
						}
					});
					try {
						// 设置刷新的时间
						Thread.sleep(1000 - (mCurrentNotifiTime % 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// ------------进度条的控制---------------
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// fromUser判断是用户改变的滑块的值,拖动SeekBar的监听并响应
		if (fromUser == true && mIMusicService != null) {
			mIMusicService.iSeekBarcheck(progress);
		}
	}

	// 该方法拖动进度条开始拖动的时候调用。
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	// 该方法拖动进度条停止拖动的时候调用
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	/**
	 * 界面点击上一首、(暂停、播放)、下/上一首发广播专用,暂停专用, (广播名、接收具体的对应，数组中已经跳到第几曲歌的下标，暂停按钮专用) 例如:
	 * activityBroadcastReceiver
	 * (ACTIVITY_NEXT_CLICK,"activity_next_click_music"
	 * ,str,checkMusicNumber,true); 你要传不同的参数时，就需要不同的标签，否则会被覆盖
	 */

	public void activityBroadcastReceiver(String activityBroadcast,
			String upAndNextClickLable, String startAndSuspend,
			int activityClickNumber, boolean temp) {

		Intent activityBroadcastIntent = new Intent(activityBroadcast);
		activityBroadcastIntent.putExtra(upAndNextClickLable,
				activityClickNumber);
		activityBroadcastIntent.putExtra(startAndSuspend, temp);
		sendBroadcast(activityBroadcastIntent);
	}

	// -----------注册接收通知的广播---------------
	public final String ACTION_UP_MUSIC = "com.kugou.up_music";
	public final String ACTION_START_SUSPEND_MUSIC = "com.kugou.start_suspend_music";
	public final String ACTION_NEXT_MUSIC = "com.kugou.next_music";

	/**
	 * 接收广播改变音乐的名字和下面暂停、开始的 按钮的图片
	 */
	BroadcastReceiver notifiReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_UP_MUSIC)) {// 上一首
				int notifiUpTime = intent
						.getIntExtra("UP_MUSIC_TIME_NOTIFI", 0);
				// 设置title上面显示的名字
				mDivicionUpName = divisionMusicName(sdcardMusicName
						.get(notifiUpTime));
				// Title处显示当前播放歌曲的名字
				mTitleMusicNameText.setText(mDivicionUpName);
				checkMusicNumber = notifiUpTime;
			} else if (action.equals(ACTION_START_SUSPEND_MUSIC)) {

				boolean notifiSwitch = intent.getBooleanExtra(
						"SWITCH_START_SUSPEND_NOTIFI", false);
				startORsuspendMusic(!notifiSwitch);

			} else if (action.equals(ACTION_NEXT_MUSIC)) {// 下一首
				int notifiNextTime = intent.getIntExtra(
						"NEXT_MUSIC_TIME_NOTIFI", 0);
				// 设置title上面显示的名字
				mDivicionNextName = divisionMusicName(sdcardMusicName
						.get(notifiNextTime));
				mTitleMusicNameText.setText(mDivicionNextName);
				checkMusicNumber = notifiNextTime;// 再一次赋值,更新
			}
		}
	};

	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_UP_MUSIC);// 通知的 上一首
		filter.addAction(ACTION_START_SUSPEND_MUSIC);// 通知的 暂停、开始
		filter.addAction(ACTION_NEXT_MUSIC);// 通知的下一首

		registerReceiver(notifiReceiver, filter);

	};

	protected void onPause() {
		super.onPause();
		if (notifiReceiver != null) {
			unregisterReceiver(notifiReceiver);
			notifiReceiver = null;
		}
	}
}
