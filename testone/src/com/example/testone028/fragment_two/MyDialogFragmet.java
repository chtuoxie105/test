package com.example.testone028.fragment_two;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testone001.loning.R;
/**
 * ���Զ���Ի������棺onCreateDialog������onCreateView�����Զ���2���Ի�������ѡһ���Ի���
 * ����onCreateDialog����������onCreateView����֮ǰִ��
 * @author Administrator
 *
 */
public class MyDialogFragmet extends DialogFragment{
	private EditText userEdid;
	private EditText passwordEdid;
	private CallBackData mcallBackData;
	
	
	
	//R.layout.fragmentdialog_user_password_layout
	/*
	 * public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 * Bundle savedInstanceState) {
	 * 
	 * getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * View v = inflater.inflate(R.layout.fragment_dialog_custom_layout, null);
	 * 
	 * return v; }
	 */
	/*
	 * public void onActivityCreated(Bundle savedInstanceState) {
	 * super.onActivityCreated(savedInstanceState); View view = getView();
	 * Button yesBtn = (Button) view.findViewById(R.id.custom_fragment_yesbtn);
	 * Button backBtn = (Button) view
	 * .findViewById(R.id.custom_fragment_backbtn);
	 * 
	 * yesBtn.setOnClickListener(this); backBtn.setOnClickListener(this);
	 * 
	 * }
	 */
	public interface CallBackData {
		public void callBackdataToMain(String user, String password);
	}

	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
		builder.setTitle("ȷ���뿪��?");
		builder.setMessage("�뿪���㽫�ղ������µ���Ϣ!");
		builder.setNegativeButton("ȷ��",new OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setNeutralButton("ȡ��",new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
//		return builder.create();
		return	builder.create();
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_fragment_yesbtn:

			getActivity().finish();

			dismiss();
			break;

		case R.id.custom_fragment_backbtn:
			dismiss();
			break;
		}

	}
}
