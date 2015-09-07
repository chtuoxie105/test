package com.example.testone002.Serializables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.testone001.loning.R;

public class B extends Activity {
	private EditText mSerializableEdit;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b_serializable_layout);

		mSerializableEdit = (EditText) findViewById(R.id.a_to_b_editText);

		Intent inte = getIntent();
		int s = inte.getIntExtra("YES", 0);
		switch (s) {
		case 1:
			stringBToA();
			break;
		case 2:
			bundleBToA();
			break;
		case 3:
			parcelableBToA();
			break;
		case 4:
			serializableBToA();
			break;
		}

	}

	/**
	 * ����Aͨ�����ʼ��Intent���ݵĲ���
	 */
	public void stringBToA() {
		Intent inte = getIntent();

		Log.i("����", inte.getStringExtra("NAME"));
		mSerializableEdit.setText(inte.getStringExtra("NAME") + "\n"
				+ inte.getStringExtra("SEX") + "\n"
				+ inte.getIntExtra("AGE", 0) + "\n"
				+ inte.getStringExtra("SCORE"));
	}

	/**
	 * ����Aͨ��Bundle���ݵĲ���
	 */
	private void bundleBToA() {
		Intent inte = getIntent();
		Bundle bundles = inte.getBundleExtra("USER_BUND");

		mSerializableEdit.setText(bundles.getString("NAME") + "\n"
				+ bundles.getString("SEX") + "\n" + bundles.getInt("AGE")
				+ "\n" + bundles.getString("SCORE"));
	}

	/**
	 * ����Aͨ��parcelable���ݵĲ���
	 */
	public void parcelableBToA() {
		Intent inte = getIntent();
		ParcelableToInter parce = inte.getParcelableExtra("USER_PAR");

		mSerializableEdit.setText(parce.getName() + "\n" + parce.getSex()
				+ "\n" + parce.getAge() + "\n" + parce.getScore());
	}

	/**
	 * ����Aͨ��serializable���ݵĲ���
	 */
	public void serializableBToA() {
		Intent inte = getIntent();
		StudentToSerializable student = (StudentToSerializable) inte
				.getSerializableExtra("StudentOne");

		mSerializableEdit.setText(student.getmName() + "\n" + student.getmSex()
				+ "\n" + student.getmAge() + "\n" + student.getmScore());
	}

	// ������Ļ���·��ķ��ؼ�
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent inte = getIntent();
			inte.putExtra("MESSAGE", mSerializableEdit.getText().toString());
			setResult(1, inte);

			finish();
		}

		return super.onKeyDown(keyCode, event);
	}
}
