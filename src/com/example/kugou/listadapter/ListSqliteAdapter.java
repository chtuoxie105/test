package com.example.kugou.listadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.kugou.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListSqliteAdapter extends BaseAdapter {
	private List<Mp3Info> mp3list = new ArrayList<Mp3Info>();
	private LayoutInflater mInflater;

	public ListSqliteAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<Mp3Info> mp3lists) {
		mp3list = mp3lists;
		notifyDataSetChanged();
	}

	public int getCount() {

		return mp3list.size();
	}

	public Object getItem(int position) {
		return mp3list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Reasou rea;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.kugou_listsqlite_music_adapter_layout, null);
			rea = new Reasou();
			rea.reaImage = (ImageView) convertView
					.findViewById(R.id.kugou_musicsqlite_list_image);
			rea.reasingerRea = (TextView) convertView
					.findViewById(R.id.kugou_musicsqlite_list_singer_name);
			rea.reaMusicTimeRea = (TextView) convertView
					.findViewById(R.id.kugou_musicsqlite_list_music_time);
			rea.reaMusicSizeRea = (TextView) convertView
					.findViewById(R.id.kugou_musicsqlite_list_music_size);

			convertView.setTag(rea);
		} else {
			rea = (Reasou) convertView.getTag();
		}

		Mp3Info mp3info = (Mp3Info) getItem(position);
		String musicSize = toMB(mp3info.getSize());
		String musicTime = timeMusicTO(mp3info.getDuration());

		rea.reasingerRea.setText(mp3info.getDisplayName());
		rea.reaMusicSizeRea.setText(musicSize+"MB");
		rea.reaMusicTimeRea.setText(musicTime);
		return convertView;
	}

	/**
	 * 歌曲时间转换
	 */
	public String timeMusicTO(long timeMusic) {
		long allTimePoint = (timeMusic / 1000) / 60;
		long allTimeSec = (timeMusic / 1000) % 60;
		String timeToList = null;
		// 设置时间
		if (allTimePoint < 10 && allTimeSec < 10) {	
			timeToList =  "0" + allTimePoint + ":0" + allTimeSec;
		} else if (allTimePoint < 10 && allTimeSec >= 10) {
			timeToList =  "0" + allTimePoint + ":" + allTimeSec;
		} else if (allTimePoint > 10 && allTimeSec < 10) {
			timeToList =  allTimePoint + ":0" + allTimeSec;
		} else if (allTimePoint > 10 && allTimeSec >= 10) {
			timeToList =  allTimePoint + ""
					+ ":0" + allTimeSec;
		}
		Log.i("11", "时间"+timeToList);
		return timeToList;
	}

	/**
	 * 文件大小转换，将B转换为MB
	 */
	public String toMB(long l) {
		float a = (float) l / (float) (1024 * 1024);
		String b = Float.toString(a);
		int c = b.indexOf(".");
		String fileSize = "";
		fileSize += b.substring(0, c + 2);
		return fileSize;
	}

	public class Reasou {
		public ImageView reaImage;
		public TextView reasingerRea;
		public TextView reaMusicTimeRea;
		public TextView reaMusicSizeRea;
	}
}
