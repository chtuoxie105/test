package com.example.testone022.servlet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.testone001.loning.R;

public class TestServlet extends Activity {
	private WebView mWebView;
	String urlMine = "http://192.168.1.191:8080/Html/wanmei.html";

	@SuppressLint("JavascriptInterface")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.servlet_activity_layout);
		
		mWebView = (WebView) findViewById(R.id.servlet_webview_main);
		mWebView.loadUrl(urlMine);

		WebSettings setting = mWebView.getSettings();
		setting.setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new ConnectHtml(), "androidConnectHtml");
	}

	public class ConnectHtml {
		public void androidPlayMusic() {

			Toast.makeText(TestServlet.this, "�����ɹ�", 0).show();
		}

		/** ����һ��url��ַ��html�ļ�����ļ�ͦ�¼� */
		public String androidToHtmlUrl() {
			String url = "http://reg.jiayuan.com";
			return url;
		}

		/** ����html����ķ���������html�����һ���ؼ����ôη�����Ȼ���ô˷�������html����ķ�����ֱ�Ӵ�activity��ת��������ҳ */
		public void toneHtmlMain() {
			mWebView.loadUrl("javascript:toneHtmlMain()");
		}
	}
}
