package com.example.testone028.fragment_two;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.testone001.loning.R;

public class DialogFragmentActivity extends Activity {
	private Button getDataBtn, showDialogBtn;
	private TextView showDataTxt;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialogfragment_main_layout);

		getDataBtn = (Button) findViewById(R.id.dialog_main_btn_one);
		showDialogBtn = (Button) findViewById(R.id.dialog_main_show_dialog_btn);
		showDataTxt = (TextView) findViewById(R.id.dialog_main_text_one);

		getDataBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// �ص����ƣ�ʵ�ֽӿڵķ���
				new MyDialogFragmet.CallBackData() {
					public void callBackdataToMain(String user, String password) {
						showDataTxt
								.setText("�˺�::" + user + "<<<���룺" + password);

					}
				};
			}
		});

		showDialogBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//����һ����DialogFragment
//				showDialog();
				//����������DialogFragment
				getFragmentManager().beginTransaction()
						.add(new MyDialogFragmet(), "diaolog").commit();
			}
		});
	}
	public void showDialog() {
		MyDialogFragmet mMyDialogFragmet = new MyDialogFragmet();
		mMyDialogFragmet.show(getFragmentManager(), "mMyDialogFragmet");
	}

}
