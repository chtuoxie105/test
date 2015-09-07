package com.example.testone031.viewstub.valueanimator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;

import com.example.testone001.loning.R;

/**
 * ������β�������Щ�����õ�Ԫ�ؽ�����Ҫʱ��ȥ�����أ�AndroidΪ���ṩ��һ�ַǳ��������Ŀؼ���
 * ViewStub��ViewStub��˵Ҳ��View��һ�ֵ�����û�д�С��û�л��ƹ��ܣ�Ҳ�����벼�֣�
 * ��Դ���ķǳ��ͣ����������ڲ��ֵ��л���������Ϊ����ȫ����Ӱ�����ܵġ�
 */
public class MyViewStubActivity extends Activity {
	private Button showViewStubBtn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewstub_layout);

		showViewStubBtn = (Button) findViewById(R.id.test_viewstub_btn);
		showViewStubBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//ʵ�������صĲ���
				ViewStub viewstub = (ViewStub) findViewById(R.id.test_viewstub);
				View falter = viewstub.inflate();
				if (falter != null) {
					//ʵ�������ز��ֵĿؼ�,Ȼ���ٽ�����صĲ���
					EditText editOne = (EditText) falter
							.findViewById(R.id.viewstub_hide_edit_one);
					EditText editTwo = (EditText) falter
							.findViewById(R.id.viewstub_hide_edit_two);
					Button btnThree = (Button) falter
							.findViewById(R.id.viewstub_hide_btn_three);
				}

			}
		});
	}
}
