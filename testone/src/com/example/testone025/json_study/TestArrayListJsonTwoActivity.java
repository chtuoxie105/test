package com.example.testone025.json_study;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.testone001.loning.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class TestArrayListJsonTwoActivity extends Activity {
	private ListView mListView;
	private ProgressBar mProgressBar;
	String url = "http://192.168.1.203/json/json_x";
	List<HashMap<String, String>> mJsonListToMapList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json_arraylist_layout);
		mListView = (ListView) findViewById(R.id.json_list_arraylist);
		mProgressBar = (ProgressBar) findViewById(R.id.json_before_progressbar);

		mJsonListToMapList = new ArrayList<HashMap<String, String>>();

		// �������첽����ȥ����ȡ���ݣ�������ܶ������߳�
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... params) {
				try {
					String banckStr = getHttpToJson(params[0]);
					JSONArray jsonList = new JSONArray(banckStr);
					
					for (int i = 0; i < jsonList.length(); i++) {
						JSONObject jsonObject = jsonList.getJSONObject(i);
						String title = jsonObject.getString("title");
						String description = jsonObject
								.getString("description");
						String image = jsonObject.getString("image");
						String comment = jsonObject.getString("comment");
						Log.i("11", "��ַ>>:" + image);
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("title", title);
						map.put("description", description);
						map.put("comment", comment + "������");
						mJsonListToMapList.add(map);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		}.execute(url);
		/**
		 * Ū��һ������������ӵ�ListView�ؼ���,ע����ǣ����������������ArrayList��ʱ�����List����Ϊ�գ�
		 * ��Ϊ���̺߳����̵߳�ʱ�䲢����ȷ��
		 * .��������Լ���װ������ȡ���ݵ��࣬���ֿ�ָ�룬�ͰѸ���������������Դ���Ƕδ���ŵ�����Jsonѭ�������ǡ�
		 */
		//ProgressBarģ����ؽ��棬�����߳�ȥ����ȡ���ݣ�ȡ�������ProgressBar
		mProgressBar.setVisibility(View.GONE);
		SimpleAdapter adapter = new SimpleAdapter(this, mJsonListToMapList,
				R.layout.listview_json_arraylist_layout, new String[] {
						"title", "description", "comment" }, new int[] {
						R.id.json_arraylist_title, R.id.json_arraylist_content,
						R.id.json_arraylist_number });
		mListView.setAdapter(adapter);

	}

	/**
	 * ����Get�����������ӣ�����JSON��ʽ������Դ
	 */
	public String getHttpToJson(String abc) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			// HttpGet����get����ʽ
			HttpGet httpGet = new HttpGet(abc);
			// ȡ��HttpResponse,����get����
			HttpResponse response = httpClient.execute(httpGet);
			// HttpStatus.SC_OK ��װ�˹���ķ����룬ֵʱ200
			int n = response.getStatusLine().getStatusCode();
			//�жϷ����룬200�������ӳɹ��ı�ʶ
			if (n == HttpStatus.SC_OK) {
				String line = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				return line;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
