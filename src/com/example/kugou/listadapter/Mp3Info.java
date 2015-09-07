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
	 * // �õ�cursor�����ǿ��Ե���Cursor����ط��������������Ϣ��
	 * 
	 * // ����ID��MediaStore.Audio.Media._ID int id =
	 * cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
	 * 
	 * // ���������ƣ�MediaStore.Audio.Media.TITLE String tilte =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
	 * 
	 * // ������ר������MediaStore.Audio.Media.ALBUM String album =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
	 * 
	 * // �����ĸ�������MediaStore.Audio.Media.ARTIST String artist =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
	 * 
	 * // �����ļ���·����MediaStore.Audio.Media.DATA String url =
	 * cursor.getString(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
	 * 
	 * // �������ܲ���ʱ����MediaStore.Audio.Media.DURATION int duration =
	 * cursor.getInt(cursor
	 * .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
	 * 
	 * // �����ļ��Ĵ�С��MediaStore.Audio.Media.SIZE int size =
	 * cursor.getLong(cursor.getColumnIndexOrThrow
	 * (MediaStore.Audio.Media.SIZE));
	 */
	/**
	 * ���ڴ����ݿ��в�ѯ��������Ϣ��������List����
	 * 
	 * @return
	 */
	
	private int id;// ����id
	private String title; // ���ֱ���,������
	private String artist;// �����ҡ�������
	private String displayName;// �����ҡ�������

	private long duration;// ʱ��
	private long size; // �ļ���С
	private String url; // �ļ�·��

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
