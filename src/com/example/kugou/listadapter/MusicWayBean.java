package com.example.kugou.listadapter;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicWayBean implements Parcelable{
	private String musicPathBean;
	private String musicNameBean;
	public String getMusicPathBean() {
		return musicPathBean;
	}
	public void setMusicPathBean(String musicPathBean) {
		this.musicPathBean = musicPathBean;
	}
	public String getMusicNameBean() {
		return musicNameBean;
	}
	public void setMusicNameBean(String musicNameBean) {
		this.musicNameBean = musicNameBean;
	}

	public int describeContents() {

		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(musicPathBean);
		dest.writeString(musicNameBean);
	}
	public static final Parcelable.Creator<MusicWayBean> CREATOR = new Parcelable.Creator<MusicWayBean>() {
		public MusicWayBean createFromParcel(Parcel source) {
			MusicWayBean bean = new MusicWayBean();
			bean.musicPathBean = source.readString();
			bean.musicNameBean = source.readString();
			return bean;
		}
		public MusicWayBean[] newArray(int size) {
		
			return new MusicWayBean[size];
		}
		
	};
	
}
