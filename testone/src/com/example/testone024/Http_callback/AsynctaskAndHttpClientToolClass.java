package com.example.testone024.Http_callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class AsynctaskAndHttpClientToolClass extends
		AsyncTask<Object, Void, String> {
	public NotifiReceive mNotifiReceive;
	/**
	 * ����ӿڣ����лص�
	 */
	public interface NotifiReceive {
		public void backContent(String backString);
	}

	protected String doInBackground(Object... params) {
		String uriAddress = (String) params[0];
		HashMap<String, Object> mapContentMap = (HashMap<String, Object>) params[1];
		String diffrenceHttp = (String) params[2];
		mNotifiReceive = (NotifiReceive) params[3];
		Log.i("11", diffrenceHttp);
		String ss = getStringByClientHttpRequest(uriAddress, mapContentMap,
				diffrenceHttp);
		return ss;
	}
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mNotifiReceive.backContent(result);

	}

	/**
	 * ���Ϳͻ������󣬷�����Ӧ������
	 * 
	 */
	public String getStringByClientHttpRequest(String url,
			HashMap<String, Object> map, String askWay) {

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = null;
			if (askWay.equals("GET")) {
				if (url.indexOf("?") < 0) {
					url = url + "?";
				}
				Set<String> set = map.keySet();
				Iterator<String> iterator = set.iterator();
				if (iterator.hasNext()) {
					String one = iterator.next();
					String two = (String) map.get(one);
					url = url + "&" + one + "=" + two;
				}
				HttpGet httpGet = new HttpGet(url);
				
				response = httpClient.execute(httpGet);

			} else if (askWay.equals("POST")) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				// hashmap�����õ��������б������������ĸ���Set����
				Set<String> set = map.keySet();
				Iterator<String> iter = set.iterator();
				while (iter.hasNext()) {
					String name = iter.next();
					String password = (String) map.get(name);
					list.add(new BasicNameValuePair(name, password));
				}
				HttpPost httpPost = new HttpPost(url);
				response = httpClient.execute(httpPost);
			}
			int n = response.getStatusLine().getStatusCode();

			if (n == HttpStatus.SC_OK) {
				String backConnect = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				return backConnect;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "���񲻿���";
	}

	/**
	 * ��װget����
	 */
	public HttpGet zuzhuangGet(String url, HashMap<String, Object> map) {
		if (url.indexOf("?") < 0) {
			url = url + "?";
		}
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		if (iterator.hasNext()) {
			String one = iterator.next();
			String two = (String) map.get(one);
			url = url + "&" + one + "=" + two;
		}
		HttpGet httpGet = new HttpGet(url);
		return httpGet;
	}

	/**
	 * ��װpost����
	 */
	public HttpPost zuzhuangPost(String url, HashMap<String, Object> map) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		// hashmap�����õ��������б������������ĸ���Set����
		Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			String password = (String) map.get(name);
			list.add(new BasicNameValuePair(name, password));
		}
		HttpPost httpPost = new HttpPost(url);
		return httpPost;
	}
}
