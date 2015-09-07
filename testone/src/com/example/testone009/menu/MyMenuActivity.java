package com.example.testone009.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.example.testone001.loning.R;

public class MyMenuActivity extends Activity {
	private Button mPopBtn;
	private ListView mList;
	private PopupMenu mPop;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_popup);

		mPopBtn = (Button) findViewById(R.id.main_pop_btn);
		mList = (ListView) findViewById(R.id.main_list);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData());
		mList.setAdapter(adapter);

		// ע�������Ĳ˵�
		registerForContextMenu(mList);

		mList.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ArrayAdapter adpater = (ArrayAdapter) parent.getAdapter();
				Toast.makeText(MyMenuActivity.this,
						"�����¼� :  " + adpater.getItem(position), 1).show();
				return false;
			}
		});

		// -=============�����˵�==============
		mPop = new PopupMenu(this, mPopBtn);//��ʾ��λ����mPopBtn������
		mPop.inflate(R.menu.menu_pop_activity);//��������

		mPopBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPop.show();//��ʾ����

			}
		});
		mPop.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.main_pop_newcons:
					Toast.makeText(MyMenuActivity.this, "����ɨ��!", 1).show();
					break;
				case R.id.main_pop_addcons:
					Toast.makeText(MyMenuActivity.this, "ѡ����ӵ���", 1).show();
					break;
				case R.id.main_pop_delecons:
					Toast.makeText(MyMenuActivity.this, "�����һζ�", 1).show();
					break;
				case R.id.main_pop_findcons:
					Toast.makeText(MyMenuActivity.this, "���ڲ��Ҹ�������", 1).show();
					break;
				}

				return false;
			}
		});

	}

	public ArrayList<String> getData() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("ѡ��˵�");
		list.add("�����Ĳ˵�");
		list.add("�����˵�");
		return list;
	}

	// ==================ѡ��˵�===================
	/**
	 * ѡ��˵�����������:(����¼�) ����Դ����:onCreateOptionsMenu; �����¼�����:onOptionsItemSelected
	 */

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 2, "����");
		menu.add(1, 0, 1, "�˳�");
		return true;
	}

	// ����>>>>��Ӧ>>>>�¼�
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 0:
			Toast.makeText(this, "�˳��ɹ�", 1).show();
			break;
		case 1:
			Toast.makeText(this, "���óɹ�", 1).show();
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	// ==================ѡ��˵�===================

	// =================�����Ĳ˵�===================
	/**
	 * �����Ĳ˵�����������:(�����¼�) ����Դ����:onCreateContextMenu; �����¼�����:onContextItemSelected
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("�ļ�����");
		menu.add(1, 1, Menu.NONE, "����");
		menu.add(1, 2, Menu.NONE, "���Ϊ��Ҫ");
		menu.add(1, 3, Menu.NONE, "������");
		menu.add(1, 4, Menu.NONE, "ɾ��");
	}

	// ����>>>��Ӧ>>>>�¼�
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Toast.makeText(this, "����", 1).show();
			break;
		case 2:
			Toast.makeText(this, "���Ϊ��Ҫ", 1).show();
			break;
		case 3:
			Toast.makeText(this, "������", 1).show();
			break;
		case 4:
			Toast.makeText(this, "ɾ��", 1).show();
			break;
		}
		return super.onContextItemSelected(item);
	}
}
