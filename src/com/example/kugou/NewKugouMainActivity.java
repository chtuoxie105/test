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

	// ���������б�
	private PopupWindow mPopupWindom;
	private LayoutInflater mInflater;
	// SD����Ŀ¼
	private File allFile = Environment.getExternalStorageDirectory();
	// װ���������������ļ�������
	private List<File> fileName = new ArrayList<File>();
	private ArrayList<MusicWayBean> musicBeanList = new ArrayList<MusicWayBean>();

	// װ���������������ļ������֣��Ƿָ����������ļ����֣�û�к�׺��
	private ArrayList<String> sdcardMusicName = new ArrayList<String>();

	// �ڵ��ListView�л����ʵ�������ǵ�ǰѡ���ListView�е���һ�������һ�ף���һ�ף�������������ȡ���֣����ڵ����һ�ף���һ�����ٴ�ʵ����
	private int checkMusicNumber = -1;
	// ������Ľӿ�
	private ICopyMusicService mIMusicService;
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {

		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			mIMusicService = (ICopyMusicService) service;

		}
	};

	String mDivicionUpName;// �����һ��ʱȡ����һ�׸���������
	String mDivicionNextName;// �����һ��ʱȡ����һ�׸���������
	String mNotificationName;// ֪ͨ������������
	String blankString;
	// ---------�������ٺ󣬵��֪ͨ��������ǰ������ʱ��--------------
	private int mAllNotifiTime;
	private int mCurrentNotifiTime;

	// ----------����㲥-------------
	public final String ACTIVITY_UP_CLICK = "com.kugou.activity.up_click_musicbtn";
	public final String ACTIVITY_NEXT_CLICK = "com.kugou.activity.next_click_musicbtn";
	public final String ACTIVITY_START_AND_SUSPEND_CLICK = "com.kugou.activity.start_and_suspend_click_musicbtn";
	public final String ACTIVITY_LISTVIEW_CLICK = "com.kugou.activity.listView_click_musicbtn";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kugou_music);
		
		musicSQLite();
		
		// getFileWithMusic(allFile);// �����ļ���ȡ�����ļ�
		startThreadFile(allFile);// �����̱߳����ļ���ȡ�����ļ�
		example();// ʵ�����ؼ�
		
		mStartAndSuspendMusicBtn
				.setImageResource(R.drawable.kg_lock_screen_play_button_pressed);

		Intent serviceIntent = new Intent(NewKugouMainActivity.this,
				MusicService.class);
		Log.i("11", "��������");
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
			Log.i("11", "�����һ��ǰ>>>" + checkMusicNumber);
			if (checkMusicNumber < 0) {
				Toast.makeText(this, "��ǰû�����ֿ��Բ���", 0).show();
				return;
			} else {
				upMusic(checkMusicNumber);
			}
			break;
		case R.id.new_kuguo_start_and_suspend_music_btn:
			if (checkMusicNumber < 0) {
				Toast.makeText(this, "��ǰû�����ֿ��Բ���", 0).show();
				return;
			} else {
				boolean falg = mIMusicService.iStartAndSuspendMusic();
				startORsuspendMusic(falg);

			}
			break;
		case R.id.new_kuguo_next_music_btn:
			Log.i("11", "�����һ��ǰ>>>" + checkMusicNumber);
			if (checkMusicNumber < 0) {
				Toast.makeText(this, "��ǰû�����ֿ��Բ���", 0).show();
				return;
			} else {
				nextMusic(checkMusicNumber);
			}
			break;
		case R.id.new_kugou_list_music_imagebtn:
			if (!isUseable()) {// �ж�SD���Ƿ�����
				Toast.makeText(NewKugouMainActivity.this, "�ⲿ�洢������",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				listMusicForPopupWindom();
			}
			break;
		}
	}

	/**
	 * ʵ�����ؼ�
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
	 * ���Title���ұߵİ�ť���������б��ڣ���PopupWindom��ʵ��
	 */
	public void listMusicForPopupWindom() {
		// �߶�Ҫ�ʵ�������
		mPopupWindom = new PopupWindow(getListViewForPopupWindom(), 700, 500);
		mPopupWindom.setFocusable(true);// �˷������Ա�֤PopupWindom����Ƕ�׵�ListView�е����Ӧ
		// ���������д�������������հ״����ر�PopWindow �����Ĵ���
		mPopupWindom.setTouchable(true);
		mPopupWindom.setOutsideTouchable(true);
		mPopupWindom.setBackgroundDrawable(new BitmapDrawable());
		
		mPopupWindom.getContentView().setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mPopupWindom.setFocusable(false);// ʧȥ����
				mPopupWindom.dismiss();// ����pw
				return true;
			}
		});

		// �����ť�͵�������
		mListMusicBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mPopupWindom.isShowing()) {// �жϴ����Ƿ��Ѿ���������
					mPopupWindom.dismiss();// �رմ���
				} else {
					mPopupWindom.showAsDropDown(v);// ��������
				}

			}
		});
	}

	/**
	 * ��װ��ListView�Ĳ��ַ��ظ�PopupWindom��ʵ�����У����е����Ĳ�������
	 */
	public View getListViewForPopupWindom() {
		View v = mInflater.inflate(R.layout.copykugou_listview_music_layout,
				null);
		ListView mListview = (ListView) v
				.findViewById(R.id.copykugou_listview_to_musicname);
		TextView allMusicPopupText = (TextView) v
				.findViewById(R.id.popupwindow_allmusic_to_text);
		allMusicPopupText.setText("�ܹ�" + fileName.size() + "�׸���");
		ListMusicAdapter listBaseadapter = new ListMusicAdapter(this);
		mListview.setAdapter(listBaseadapter);
		listBaseadapter.setData(fileName);
		// ����¼������������񣬺���Ӹ���������
		mListview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListMusicAdapter backAdapter = (ListMusicAdapter) parent
						.getAdapter();
				File fileCheckMusic = (File) backAdapter.getItem(position);

				String backPrepareMusic = fileCheckMusic.getPath();

				Log.i("11", position + "" + musicBeanList);
				// --------���͹㲥-�������飨��ַ�����֣��͵�ǰ��ѡ��------------
				listSendBroacast(position, musicBeanList);

				mStartAndSuspendMusicBtn
						.setImageResource(R.drawable.kg_lock_screen_pause_button_pressed);

				// ����ͳ�ʼ��
				checkMusicNumber = position;
				// �ָ��׺��,����ʾ��ǰ����ĸ�������
				String divisionNameAfter = divisionMusicName(fileCheckMusic
						.getName());

				mTitleMusicNameText.setText(divisionNameAfter);
				// PopupWindow�ر�
				mPopupWindom.dismiss();
				new Thread(new MusicRefrshTime()).start();
			}
		});

		return v;
	}

	/**
	 * ���listviewҲ���㲥�����ݵ�ǰ�����ĳһ��������б�
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
		Log.i("11", "���ͳɹ�");
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
	 * ���ޱ���һ���ļ��У��ҳ������ļ�������ӵ�List���棬Ȼ�����ø�������
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
							fileName.add(f);// ��ӵ��ǵ�ַ
							String namePostfix = divisionMusicName(s);// ���ص�û�к�׺���������ļ���

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
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // �ļ�·��
			String musicName = cursor.getString((cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE)));// ���ֱ���
			String namePostfix = divisionMusicName(musicName);// ���ص�û�к�׺���������ļ���
			Log.i("11","����::"+musicName);
			Log.i("11","·��::"+musicPath);
		}
	}

	/**
	 * �ж�SD���Ƿ���� ���ص���true:����
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
	 * ����û�к�׺������������
	 */
	public String divisionMusicName(String s) {
		String[] names = s.split("[.]");
		return names[0];
	}

	/**
	 * ��������ʱ����ȡ���û�����������
	 */
	protected void onDestroy() {
		super.onDestroy();
		Log.i("11", "���ٽ���");
		if (mConnection != null && mIMusicService != null) {
			unbindService(mConnection);
			mConnection = null;
			timeSwitch = false;
		}

	}

	/**
	 * ��һ�׸���
	 */
	public void upMusic(int nowNumb) {
		if (nowNumb - 1 < 0) {// �����0��ʼ����
			Toast.makeText(this, "�Ѿ��ǵ�һ����", 0).show();

			nowNumb = 0;// ��һ�θ�ֵ,����
		} else {
			// �����µĸ������ŵ�ַ
			mIMusicService.iUpMusic("File://" + fileName.get(nowNumb - 1));// ���������
			// ����title������ʾ������
			mDivicionUpName = divisionMusicName(sdcardMusicName
					.get(nowNumb - 1));
			// Title����ʾ��ǰ���Ÿ���������
			mTitleMusicNameText.setText(mDivicionUpName);

			activityBroadcastReceiver(ACTIVITY_UP_CLICK,
					"activity_up_click_music", blankString, checkMusicNumber,
					true);
			checkMusicNumber = nowNumb - 1;// ��һ�θ�ֵ,����
		}
	}

	/**
	 * ��ͣ�Ქ�Ÿ���ͼƬ��ť
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
	 * ��һ�׸���
	 */
	public void nextMusic(int naexClickNumb) {
		if (naexClickNumb + 1 < fileName.size()) {// �����0��ʼ����

			// �����µĸ������ŵ�ַ
			mIMusicService.iNextMusic("File://"
					+ fileName.get(naexClickNumb + 1));// ���������
			// ����title������ʾ������
			mDivicionNextName = divisionMusicName(sdcardMusicName
					.get(naexClickNumb + 1));
			mTitleMusicNameText.setText(mDivicionNextName);

			checkMusicNumber = naexClickNumb + 1;// ��һ�θ�ֵ,����
			activityBroadcastReceiver(ACTIVITY_NEXT_CLICK,
					"activity_next_click_music", blankString, checkMusicNumber,
					true);
		} else {
			Toast.makeText(this, "�Ѿ������һ����", 0).show();
			naexClickNumb = fileName.size();
		}
	}

	/**
	 * ˢ�½��������ı����ʱ��
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

							// �����ܵ�ʱ��
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

							// ���õ�ǰ��ʱ��
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
								Log.i("11", "�Զ�����");
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
						// ����ˢ�µ�ʱ��
						Thread.sleep(1000 - (mCurrentNotifiTime % 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// ------------�������Ŀ���---------------
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// fromUser�ж����û��ı�Ļ����ֵ,�϶�SeekBar�ļ�������Ӧ
		if (fromUser == true && mIMusicService != null) {
			mIMusicService.iSeekBarcheck(progress);
		}
	}

	// �÷����϶���������ʼ�϶���ʱ����á�
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	// �÷����϶�������ֹͣ�϶���ʱ�����
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	/**
	 * ��������һ�ס�(��ͣ������)����/��һ�׷��㲥ר��,��ͣר��, (�㲥�������վ���Ķ�Ӧ���������Ѿ������ڼ�������±꣬��ͣ��ťר��) ����:
	 * activityBroadcastReceiver
	 * (ACTIVITY_NEXT_CLICK,"activity_next_click_music"
	 * ,str,checkMusicNumber,true); ��Ҫ����ͬ�Ĳ���ʱ������Ҫ��ͬ�ı�ǩ������ᱻ����
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

	// -----------ע�����֪ͨ�Ĺ㲥---------------
	public final String ACTION_UP_MUSIC = "com.kugou.up_music";
	public final String ACTION_START_SUSPEND_MUSIC = "com.kugou.start_suspend_music";
	public final String ACTION_NEXT_MUSIC = "com.kugou.next_music";

	/**
	 * ���չ㲥�ı����ֵ����ֺ�������ͣ����ʼ�� ��ť��ͼƬ
	 */
	BroadcastReceiver notifiReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_UP_MUSIC)) {// ��һ��
				int notifiUpTime = intent
						.getIntExtra("UP_MUSIC_TIME_NOTIFI", 0);
				// ����title������ʾ������
				mDivicionUpName = divisionMusicName(sdcardMusicName
						.get(notifiUpTime));
				// Title����ʾ��ǰ���Ÿ���������
				mTitleMusicNameText.setText(mDivicionUpName);
				checkMusicNumber = notifiUpTime;
			} else if (action.equals(ACTION_START_SUSPEND_MUSIC)) {

				boolean notifiSwitch = intent.getBooleanExtra(
						"SWITCH_START_SUSPEND_NOTIFI", false);
				startORsuspendMusic(!notifiSwitch);

			} else if (action.equals(ACTION_NEXT_MUSIC)) {// ��һ��
				int notifiNextTime = intent.getIntExtra(
						"NEXT_MUSIC_TIME_NOTIFI", 0);
				// ����title������ʾ������
				mDivicionNextName = divisionMusicName(sdcardMusicName
						.get(notifiNextTime));
				mTitleMusicNameText.setText(mDivicionNextName);
				checkMusicNumber = notifiNextTime;// ��һ�θ�ֵ,����
			}
		}
	};

	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_UP_MUSIC);// ֪ͨ�� ��һ��
		filter.addAction(ACTION_START_SUSPEND_MUSIC);// ֪ͨ�� ��ͣ����ʼ
		filter.addAction(ACTION_NEXT_MUSIC);// ֪ͨ����һ��

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
