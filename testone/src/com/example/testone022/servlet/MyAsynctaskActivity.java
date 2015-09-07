package com.example.testone022.servlet;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testone001.loning.R;

public class MyAsynctaskActivity extends Activity {
	private ProgressBar mPregressBar;
	MyAsyncTask mTask;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asynctask_layout);

		mPregressBar = (ProgressBar) findViewById(R.id.asynctask_progressbar);

		// ע��ÿ����newһ��ʵ��,�½�������ֻ��ִ��һ��,���������쳣
		mTask = new MyAsyncTask();
		mTask.execute("http://www.sina.com.cn/");

		new AsyncTask<Void, Integer, Boolean>() {
			
			protected void onPreExecute() {
				//Ԥ������һ��ִ��
			};
			
			protected Boolean doInBackground(Void... params) {
				for (int i = 0; i < 100; i++) {
					publishProgress(i);
				
				}
				return true;
			}

			protected void onProgressUpdate(Integer... values) {
			//	onProgressUpdate(Progress... values)���ڵ���publishProgress(Progress... values)ʱ��
			//	�˷�����ִ�У�ֱ�ӽ�������Ϣ���µ�UI����ϡ�
				int n = values[0];
				mPregressBar.setProgress(n);
			};

			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (result == true) {
					Toast.makeText(MyAsynctaskActivity.this, "�������!", 0).show();
				}
			}
			//���ݲ�������
		}.execute();

	}

}
