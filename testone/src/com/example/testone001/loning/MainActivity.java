package com.example.testone001.loning;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

	private Button mLongingbtu;
	private Button mExitbtu;
	private EditText mNameEdit;
	private EditText mPasswordEdit;
	private TextView mHintText;
	private File allFile = Environment.getExternalStorageDirectory();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative_loyout);

		// setContentView(getResources().getColor(android.R.color.holo_orange_dark));

		mLongingbtu = (Button) findViewById(R.id.loging_button);
		mExitbtu = (Button) findViewById(R.id.exit_button);

		// ��ȡ��Ϣ
		mNameEdit = (EditText) findViewById(R.id.name_edit);
		mPasswordEdit = (EditText) findViewById(R.id.password_edit);
		mHintText = (TextView) findViewById(R.id.hint_text);

		final String s = "�˺Ż����벻��Ϊ��";
		final String st = "��¼�ɹ�";
		mLongingbtu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!mNameEdit.getText().toString().equals("")
						&& !mPasswordEdit.getText().equals("")) {
					mHintText.setText(st);
				} else {
					mHintText.setText(s);
					mPasswordEdit.setText("");
					mNameEdit.setText("");
				}
			}
		});
		mExitbtu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.i("11", "111" + allFile.getPath());

			}
		});

	}

}
