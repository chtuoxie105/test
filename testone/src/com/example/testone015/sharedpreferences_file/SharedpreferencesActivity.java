package com.example.testone015.sharedpreferences_file;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.testone001.loning.R;

public class SharedpreferencesActivity extends Activity {
	private EditText mEditShared;
	private Button mConfirmBtnShared;
	private String NAME = "com.example.edit_name_to_sharedpreferences";// �ļ���
	private SharedPreferences shared;
	private String[] str = { "nameone", "nametwo", "namethree", "namefour",
			"namefive", "namesix", "nameseven" };
	ArrayList<String> list = new ArrayList<String>();
	int i = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharedpreferences_layout);

		mEditShared = (EditText) findViewById(R.id.main_sharedpreferences_edit);
		mConfirmBtnShared = (Button) findViewById(R.id.main_sharedpreferences_btn);

		//����shared_prefs�ļ���(data-->data-->�ļ�����)
		shared = getSharedPreferences(NAME, MODE_PRIVATE);

		for (int n = 0; n < str.length; n++) {
			list.add(str[n]);
		}

		
			mConfirmBtnShared.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//�Լ�ֵ�Եķ�ʽ�����ݣ�������Editor��putString()������commit()�ύ�洢����Ϣ;clear()ɾ���洢������remove(key)ͨ�������Ƴ�
					Editor edits = shared.edit();
					edits.putString(list.get(i), mEditShared.getText()
							.toString());
					edits.commit();
				
					String s = shared.getString(list.get(0), "");
					Log.e("11aaa", "���ݸ���: "+s);
					i++;
				}
			});
			
			//����һ���������Ϣ���ͳ���
			String s = shared.getString(list.get(0), "");
			if (!s.equals("")) {
				mEditShared.setText(s);
				Log.e("11", s);
			
		}
	}
}
