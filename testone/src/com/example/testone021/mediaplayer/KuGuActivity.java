package com.example.testone021.mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.testone001.loning.R;

public class KuGuActivity extends Activity implements OnClickListener {
	private ImageView mUpMusicBtn,mStartAndSuspendMusicBtn, mNextMusicBtn;

	private SeekBar mSeekBarMusic;
	private TextView mAllTimeText, mCurrentTimeText;

	private Handler mHandler = new Handler();
	private MediaPlayer media = new MediaPlayer();

	String mMusicAddress = Environment.getExternalStoragePublicDirectory(
			Environment.DIRECTORY_MUSIC).getPath();
	String resetAddress = "file://" + mMusicAddress + "/qianlvyouhun.mp3";
	private boolean timeRefrsh = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kugu_mediaplayer_one_layout);

		mUpMusicBtn = (ImageView) findViewById(R.id.kuguo_up_music_btn);
		mStartAndSuspendMusicBtn = (ImageView) findViewById(R.id.kuguo_start_and_suspend_music_btn);
		mNextMusicBtn = (ImageView) findViewById(R.id.kuguo_next_music_btn);
		mSeekBarMusic = (SeekBar) findViewById(R.id.kuguo_seekbar);
		mCurrentTimeText = (TextView) findViewById(R.id.kuguo_current_time_text);
		mAllTimeText = (TextView) findViewById(R.id.kuguo_all_time_text);

		Log.i("11", resetAddress);
		new Thread(new SeekBarThread()).start();

		uploadResrouce(resetAddress);// ������Դ��׼��

		mStartAndSuspendMusicBtn.setOnClickListener(this);
		mUpMusicBtn.setOnClickListener(this);
		mNextMusicBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.kuguo_up_music_btn:// ��һ��
			media.reset();
			uploadResrouce(resetAddress);
			mStartAndSuspendMusicBtn.setImageResource(R.drawable.kugou1);
			mediaPlayerMusic();
			break;
		case R.id.kuguo_start_and_suspend_music_btn:// ��ͣ/��ʼ
			mediaPlayerMusic();
			mStartAndSuspendMusicBtn.setImageResource(R.drawable.kugou2);
			break;
		case R.id.kuguo_next_music_btn:// ��һ��
			media.reset();
			uploadResrouce(resetAddress);
			mStartAndSuspendMusicBtn.setImageResource(R.drawable.kugou1);
			mediaPlayerMusic();
			break;
		}

	}

	/**
	 * ������Դ��������
	 */
	public void uploadResrouce(String s) {
		try {
			mStartAndSuspendMusicBtn.setImageResource(R.drawable.kugou2);
			media.setDataSource(s);
			media.prepare();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �������ţ�Ҫô���У�Ҫô��ͣ
	 */
	public void mediaPlayerMusic() {
		if (media.isPlaying()) {// ������������У��������ͣ
			mStartAndSuspendMusicBtn.setImageResource(R.drawable.kugou1);
			media.pause();// ��ͣ״̬
			Log.i("11", "��ͣ");
		}
		// ����ʱ:û������״̬��-����
		else {
			Log.i("11", "���¿�ʼ����");
			mStartAndSuspendMusicBtn.setImageResource(R.drawable.kugou2);
			media.start();
		}

	}

	protected void onDestroy() {
		super.onDestroy();
		timeRefrsh = false;
	}

	public class SeekBarThread implements Runnable {
		public void run() {
			while (timeRefrsh) {
				if (media.isPlaying() && media != null) {
					mSeekBarMusic.setMax(media.getDuration());
					mSeekBarMusic.setProgress(media.getCurrentPosition());
				}
				mHandler.post(new Runnable() {
					public void run() {
						// �����ܵ�ʱ��
						int allPoint = (media.getDuration() / 1000) / 60;
						int allSec = (media.getDuration() / 1000) % 60;
						if (allSec < 10 && allPoint < 10) {
							mAllTimeText
									.setText("0" + allPoint + ":0" + allSec);
						} else if (allSec >= 10 && allPoint < 10) {
							mAllTimeText.setText("0" + allPoint + ":" + allSec);
						} else if (allSec < 10 && allPoint >= 10) {
							mAllTimeText.setText(allPoint + ":0" + allSec);
						}

						// �������ڽ���ʱ��ʱ��
						int currentPoint = (media.getCurrentPosition() / 1000) / 60;
						int currentSec = (media.getCurrentPosition() / 1000) % 60;
						if (currentSec < 10) {
							mCurrentTimeText.setText("0" + currentPoint + ":0"
									+ currentSec);
						} else {
							mCurrentTimeText.setText("0" + currentPoint + ":"
									+ currentSec);
						}
					}

				});
				try {
					// ����ˢ�µ�ʱ��
					Thread.sleep(1000 - (media.getCurrentPosition() % 1000));
					int n = 1000 - (media.getCurrentPosition() % 1000);
					Log.i("11", "ˢ�µ�ʱ��......" + media.getCurrentPosition());
					Log.i("11", "ˢ�µ�ʱ��" + n);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
