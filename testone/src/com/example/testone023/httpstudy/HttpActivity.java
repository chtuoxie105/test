package com.example.testone023.httpstudy;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.testone001.loning.R;

public class HttpActivity extends Activity implements OnItemClickListener {
	private ListView httpStudyList;
	private WebView httpStudyWebView;
	// String urls = "http://www.baidu.com";
	// String urls = "http://yiny.zicp.net/webone";

	String urls = "http://192.168.1.191:8080/WebRoot/wanmei.html";
	HttpStudyConnect mHttpStudyConnect;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_study_layout);

		mHttpStudyConnect = new HttpStudyConnect();

		String[] s = { "httpClient����1", "JAVA_����2", "post����" };

		httpStudyList = (ListView) findViewById(R.id.http_study_listview);
		httpStudyWebView = (WebView) findViewById(R.id.http_study_webview);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, s);
		httpStudyList.setAdapter(adapter);

		// httpStudyWebView.loadUrl(urls);�Ѱٶ���ҳ��ʾ����

		httpStudyList.setOnItemClickListener(this);

	}

	/**
	 * ��������Ƿ����
	 */
	public boolean inspectMesh() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;

	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		boolean temp = inspectMesh();
		switch (position) {
		case 0:
			if (temp == true) {
				// ���߳���ȥ�����첽����
				new AsyncTask<String, Void, String>() {
					// String...:˵������һ���ɱ�����飬���Ȳ�ȷ��
					protected String doInBackground(String... params) {
						// �˴�ȡ����ʱ���������洫�ݲ�����ʱ����ŵĵڼ���λ�ã�execute("s1","s2","s3")
						String toHttp = params[0];
						String contentUrl = mHttpStudyConnect
								.httpClient(toHttp);
						return contentUrl;
					}

					// ����doInBackground���صĲ���
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						if (result != null) {
							httpStudyWebView.loadData(result,
									"text/html;utf-8", null);
						}
					}
				}.execute(urls);
			} else {
				Toast.makeText(HttpActivity.this, "�������Ӳ�����", 0).show();
			}

			break;
		case 1:
			if (temp == true) {
				// ���߳���ȥ�����첽����
				new AsyncTask<String, Void, String>() {
					// String...:˵������һ���ɱ�����飬���Ȳ�ȷ��
					protected String doInBackground(String... params) {
						// �˴�ȡ����ʱ���������洫�ݲ�����ʱ����ŵĵڼ���λ�ã�execute("s1","s2","s3")
						String toHttp = params[0];
						String contentUrl = mHttpStudyConnect
								.JavaConnectHttp(toHttp);

						return contentUrl;
					}

					// ����doInBackground���صĲ���
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						if (result != null) {
							httpStudyWebView.loadData(result,
									"text/html;utf-8", null);
						}
					}
				}.execute(urls);
			} else {
				Toast.makeText(HttpActivity.this, "�������Ӳ�����", 0).show();
			}
			break;
		case 2:
			if (temp == true) {

				// ���߳���ȥ�����첽����
				new AsyncTask<String, Void, String>() {
					// String...:˵������һ���ɱ�����飬���Ȳ�ȷ��
					protected String doInBackground(String... params) {
						// �˴�ȡ����ʱ���������洫�ݲ�����ʱ����ŵĵڼ���λ�ã�execute("s1","s2","s3")
						String toHttp = params[0];
						String contentUrl = mHttpStudyConnect
								.postHttpClient(toHttp);
						return contentUrl;
					}

					// ����doInBackground���صĲ���
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						if (result != null) {
							httpStudyWebView.loadData(result,
									"text/html;utf-8", null);
						}
					}
				}.execute(urls);
			}

			else {
				Toast.makeText(HttpActivity.this, "�������Ӳ�����", 0).show();
			}
			break;
		}

	}
}
