package com.example.testone015.sharedpreferences_file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testone001.loning.R;

/**
 * Environment.getExternalStorageDirectory();//�õ���Ŀ¼mnt/sdcard
 * ��SD�����ⲿ����:��������:�������ͼƬ ������Ŀ¼
 */
public class OutPublicFilesActivity extends Activity {
	private ImageView mImageFile;
	private TextView mTextFile;
	private Button mAddBtnFile, mFindBtnFile;
	private String PIC_NAME = "pic.png";// ͼƬ������
	private String path;// �ļ��ĵ�ַ
	private Bitmap bit;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file1_out_txt_layout);
		mImageFile = (ImageView) findViewById(R.id.file_out_image);
		mTextFile = (TextView) findViewById(R.id.file_out_text);
		mAddBtnFile = (Button) findViewById(R.id.file_out_btn);
		mFindBtnFile = (Button) findViewById(R.id.file_out_find_btn);

		// BitmapFactory ������ͼƬת����bitmap�Ͳ��ܱ���ӵ������ļ���
		bit = BitmapFactory.decodeResource(getResources(), R.drawable.m3);
		mImageFile.setImageBitmap(bit);// ��Bitmap����ת����ͼƬ
		// getExternalStoragePublicDirectory�����η�����˵���洢���ⲿ�洢����Ĺ����������������Ǵ洢������

		mAddBtnFile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!isUseable()) {// �ж�SD���Ƿ�����
					Toast.makeText(OutPublicFilesActivity.this, "�ⲿ�洢������",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Log.e("11", "111");

				// File fil = new File(path, PIC_NAME);// (��ַ,�ļ�������)
				File file = foundExternalStoragePublicDirectory(
						Environment.DIRECTORY_PICTURES, PIC_NAME);
				path = file.getPath();// �洢��·��
				mTextFile.setText(path);// ��ӡ�ļ��ĵ�ַ

				FileOutputStream os;
				try {
					os = new FileOutputStream(file);
					// compress();ѹ��ͼƬ���ļ���
					bit.compress(Bitmap.CompressFormat.PNG, 90, os);

					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		mFindBtnFile.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!isUseable()) {
					Toast.makeText(OutPublicFilesActivity.this, "�ⲿ�洢������", 0)
							.show();
				}

				File file = Environment.getExternalStorageDirectory();// �õ���Ŀ¼mnt/sdcard
//				 File file = new File("storage");

				Log.e("11", "" + file);
				followFile(file);

			}
		});

	}

	/**
	 * �����ļ���
	 * 
	 * @param file
	 */
	public void followFile(File file) {
		File[] fileList = file.listFiles();
		if (fileList != null) {

			for (File filesList : fileList) {
				if (filesList.isDirectory()) {// �ж����ļ�����Ŀ¼��Ŀ¼�����ļ���Ŀ¼�ͼ���ִ��
					File fileNew = filesList.getAbsoluteFile();//����µ�·��

					followFile(fileNew);

					String s = filesList.getName();
					Log.e("���ҳ�����Ŀ¼��1", s);
				} else {// ���ļ��ʹ�ӡ���ļ���λ��
					String s = filesList.getName();
					
					if(filesList.getPath().endsWith(".jpg")){
						Log.e("���ҳ������ļ�2",s+"  " + filesList.getPath().endsWith(".jpg"));// ��ӡ����.png��׺����ͼƬ
					}
				

				}
			}
		}
	}

	/**
	 * �ж�SD���Ƿ���� ���ص���true:����
	 * 
	 * @return
	 */
	public boolean isUseable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����һ���ⲿ�洢����Ĺ��������ַ (��Ҫ�������������ͼƬ�����֡���Ӱ���������ݡ����ӡ�����,�����ݵ�����)(Environment.,)
	 * \Pictures��Music��Movies��Download��Alarms��Ringtones;
	 * 
	 * @param parameter
	 * @param PICName
	 * @return
	 */
	public File foundExternalStoragePublicDirectory(String parameter,
			String PICName) {
		File fileAddress = Environment
				.getExternalStoragePublicDirectory(parameter);
		if (fileAddress.exists()) {
			fileAddress.mkdir();
		}
		File filePicAddress = new File(fileAddress, PICName);
		if (filePicAddress.exists()) {
			try {
				fileAddress.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filePicAddress;
	}
}
