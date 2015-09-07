package com.example.kugou.listadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Mp3Info {

	/*
	 * Cursor cursor = query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
	 * null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	 * 
	 * // 得到cursor后，我们可以调用Cursor的相关方法具体的音乐信息：
	 * 
	 * // 歌曲ID：MediaStore.Audio.Media._ID int id =
	 * cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
	 * 
	 * // 歌曲的名称：MediaStore.Audio.Media.TITLE String tilte =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
	 * 
	 * // 歌曲的专辑名：MediaStore.Audio.Media.ALBUM String album =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
	 * 
	 * // 歌曲的歌手名：MediaStore.Audio.Media.ARTIST String artist =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
	 * 
	 * // 歌曲文件的路径：MediaStore.Audio.Media.DATA String url =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
	 * 
	 * // 歌曲的总播放时长：MediaStore.Audio.Media.DURATION int duration =
	 * cursor.getInt(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
	 * 
	 * // 歌曲文件的大小：MediaStore.Audio.Media.SIZE int size =
	 * cursor.getLong(cursor.getColumnIndexOrThrow
	 * (MediaStore.Audio.Media.SIZE));
	 */
	/**
	 * 用于从数据库中查询歌曲的信息，保存在List当中
	 * 
	 * @return
	 */
	
	private int id;// 音乐id
	private String title; // 音乐标题,歌曲名
	private String artist;// 艺术家、歌手名
	private String displayName;// 艺术家、歌手名

	private long duration;// 时长
	private long size; // 文件大小
	private String url; // 文件路径

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
