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
			// .getColumnIndex(MediaStore.Audio.Media._ID)); // 音乐id
			String displayName = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));// 艺术家
			String title = cursor.getString((cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE)));// 音乐标题
			String artist = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));// 艺术家

			long duration = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION));// 时长
			long size = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.SIZE)); // 文件大小
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
			int isMusic = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));// 是否为音乐
			
			Mp3Info mp3Info = new Mp3Info();
//			if (isMusic != 0) { // 只把音乐添加到集合当中
//				mp3Info.setId(id); // 音乐id
			
				mp3Info.setDisplayName(displayName);// 时长
				mp3Info.setTitle(title);// 音乐标题,歌曲名
				mp3Info.setArtist(artist);// 艺术家、歌手名
				mp3Info.setDuration(duration);// 时长
				mp3Info.setSize(size);// 文件大小
				// mp3Info.setUrl(url);// 文件路径
				mp3InfosList.add(mp3Info);
//			}
		}
		return mp3InfosList;
	}
}
