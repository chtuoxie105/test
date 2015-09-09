package com.example.kugou.listadapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;

import com.example.kugou.R;

public class ListSqliteActivity extends Activity {
	private ListView mListView;
	private List<Mp3Info> mMusicAllMessage;

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listsqlite_layout);

		mListView = (ListView) findViewById(R.id.lsit_sqlite_main);
		
		ListSqliteAdapter adapter = new ListSqliteAdapter(this);
		mListView.setAdapter(adapter);
		mMusicAllMessage = getMp3Infos(this);
		
		adapter.setData(mMusicAllMessage);
	}

	public List<Mp3Info> getMp3Infos(Context context) {
		List<Mp3Info> mp3InfosList = new ArrayList<Mp3Info>();
		// Cursor cursor =
		// context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		// null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		while (cursor.moveToNext()) {
			// int id = cursor.getInt(cursor
			// .getColumnIndex(MediaStore.Audio.Media._ID)); // ����id
			String displayName = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));// ������
			String title = cursor.getString((cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE)));// ���ֱ���
			String artist = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));// ������

			long duration = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION));// ʱ��
			long size = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.SIZE)); // �ļ���С
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // �ļ�·��
			int isMusic = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));// �Ƿ�Ϊ����
			
			Mp3Info mp3Info = new Mp3Info();
//			if (isMusic != 0) { // ֻ��������ӵ����ϵ���
//				mp3Info.setId(id); // ����id
			
				mp3Info.setDisplayName(displayName);// ʱ��
				mp3Info.setTitle(title);// ���ֱ���,������
				mp3Info.setArtist(artist);// �����ҡ�������
				mp3Info.setDuration(duration);// ʱ��
				mp3Info.setSize(size);// �ļ���С
				// mp3Info.setUrl(url);// �ļ�·��
				mp3InfosList.add(mp3Info);
//			}
		}
		return mp3InfosList;
	}
}
