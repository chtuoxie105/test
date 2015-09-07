package com.example.testone002.Serializables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.testone001.loning.R;


public class AToB extends Activity {
	private Button stringBtu, bundleBtu, parcelableBtu, serializableBtu;
	private TextView mBackBToATxt;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_serializable_layout);

		stringBtu = (Button) findViewById(R.id.string_btu);
		bundleBtu = (Button) findViewById(R.id.bundle_btu);
		parcelableBtu = (Button) findViewById(R.id.parcelable_btu);
		serializableBtu = (Button) findViewById(R.id.serializable_btu);

		mBackBToATxt = (TextView) findViewById(R.id.back_meassge_btu);

		stringBtu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stringToB();
			}
		});
		bundleBtu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				bundleToB();
			}
		});
		parcelableBtu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				parcelableToB();
			}
		});
		serializableBtu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loginToPrivateToInterface();
			}
		});
	}

	/**
	 * ���ջش���:requestCode ����Ĵ��ݲ���;resultCode ������Ĵ��ݲ���;���ݵĿ���������Ҳ�����Ƕ���
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.w("����", "1111111111111");
		if (data != null) {
			String s = data.getStringExtra("MESSAGE");

			Log.w("����", s);
			mBackBToATxt.setText(s);

		}
	}

	/**
	 * ֱ�Ӵ���
	 */
	public void stringToB() {
		Intent inte = new Intent(AToB.this, B.class);
		inte.putExtra("NAME", "����");
		inte.putExtra("SEX", "Ů");
		inte.putExtra("AGE", 22);
		inte.putExtra("SCORE", "98��");

		inte.putExtra("YES", 1);
		startActivityForResult(inte, 1);
	}

	/**
	 * ��Bundle���󴫲�
	 */
	public void bundleToB() {

		Bundle bundle = new Bundle();
		bundle.getString("NAME", "����");
		bundle.getString("SEX", "Ů");
		bundle.getInt("AGE", 19);
		bundle.getString("SCORE", "100��");

		Intent ints = new Intent(AToB.this, B.class);
		ints.putExtra("YES", 2);
		ints.putExtra("USER_BUND", bundle);
		startActivityForResult(ints, 2);
	}

	/**
	 * ����android�����еķ�������
	 */
	public void parcelableToB() {
		ParcelableToInter par = new ParcelableToInter();
		par.setName("����");
		par.setSex("Ů");
		par.setAge(19);
		par.setScore("98��");

		Intent inte = new Intent(AToB.this, B.class);
		inte.putExtra("YES", 3);
		inte.putExtra("USER_PAR", par);
		startActivityForResult(inte, 3);
	}

	/**
	 * �������л�serializable�����еķ�������
	 */
	public void loginToPrivateToInterface() {
		StudentToSerializable studentOne = new StudentToSerializable();
		studentOne.setmName("����");
		studentOne.setmSex("Ů");
		studentOne.setmAge(22);

		studentOne.setmScore("98��");

		Intent inte = new Intent(AToB.this, B.class);
		inte.putExtra("YES", 4);
		inte.putExtra("StudentOne", studentOne);

		startActivityForResult(inte, 4);
	}
}
