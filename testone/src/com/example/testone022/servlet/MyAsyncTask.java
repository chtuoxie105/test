package com.example.testone022.servlet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * ���ַ������ͷֱ������������ִ�е����������������̨����ִ�еĽ��ȡ�������̨�����������͡��� ���ض������£�
 * �������������Ͷ���ʹ�ã����û�б�ʹ�ã�������java.lang.Void���ʹ��档
 *
 */
public class MyAsyncTask extends AsyncTask<String, Integer, String> {

	protected String doInBackground(String... params) {
		String httpurl = params[0];
		String str = null;
		// HttpPost���Ӷ���

		try {
			HttpPost httpRequest = new HttpPost(httpurl);
			// ���ò���
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("one", ""));
			param.add(new BasicNameValuePair("options", "dododo"));
			param.add(new BasicNameValuePair("two", ""));
			// �����ַ���,�Լ����뷽ʽ
			HttpEntity httpEntity = new UrlEncodedFormEntity(param, "utf-8");
			// ����HttpRequest
			httpRequest.setEntity(httpEntity);
			// ȡ��Ĭ�ϵ�HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			// ȡ��HttpResponse
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			str = EntityUtils.toString(httpResponse.getEntity());
			// HttpStatus.SC_OK��ʾ���ӳɹ�
			Log.v("gzg", "" + httpResponse.getStatusLine().getStatusCode());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	protected void onPostExecute(String result) {
		// ((TextView) findViewById(R.id.a)).setText("�첽����ִ�н���"+result);
		// //�첽����ִ�������TextView����ʾ������Ҳ����ִ�б�Ĳ�����

	};

	protected void onProgressUpdate(Integer... values) {

	};
}
