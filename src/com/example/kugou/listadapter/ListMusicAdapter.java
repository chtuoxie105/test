package com.example.kugou.listadapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.kugou.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListMusicAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;

	private List<File> file = new ArrayList<File>();

	public ListMusicAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {

		return file.size();
	}

	public void setData(List<File> files) {
		file = files;
		notifyDataSetChanged();
	}

	public Object getItem(int position) {

		return file.get(position);
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ListMusicName mListMusicName;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.newkugou_listmusic_item_layout, null);
			mListMusicName = new ListMusicName();
			mListMusicName.musicNameText = (TextView) convertView
					.findViewById(R.id.copykugou_listmusic_name);
			mListMusicName.musicTimeText = (TextView) convertView
					.findViewById(R.id.copykugou_listmusic_time);
			convertView.setTag(mListMusicName);
		} else {
			mListMusicName = (ListMusicName) convertView.getTag();
		}
		File fileList = (File) getItem(position);

		mListMusicName.musicNameText.setText(fileList.getName());
		// mListMusicName.musicTimeText.setText(fileList.get);

		return convertView;
	}

	public class ListMusicName {
		private TextView musicNameText;
		private TextView musicTimeText;
	}
}
