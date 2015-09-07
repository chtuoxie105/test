package com.example.testone029.actionbar;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.ViewConfiguration;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.testone001.loning.R;

/***
 * actionBar�ĳ���ѧϰ http://blog.csdn.net/guolin_blog/article/details/18234477
 * ***/
public class MyActionBar extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar action = getActionBar();
		action.show();
		// ���õ���,���Ƿ��ؼ�
		action.setDisplayHomeAsUpEnabled(true);
		// ��overflow��ťһֱ��ʾ�������ǲ�����menu��
		setOverflowShowingAlways();
	}

	/**
	 * ����ֻ�û������Menu���Ļ���overflow��ť�Ϳ�����ʾ�����������Menu���Ļ���
	 * overflow��ť�Ͳ�����ʾ����,�η���������overflow��ťһֱ��ʾ,
	 * ViewConfiguration���������һ������sHasPermanentMenuKey�ľ�̬������
	 * ϵͳ���Ǹ������������ֵ���ж��ֻ���û������Menu����. ��Ȼ����һ���ڲ������������޷�ֱ�ӷ����������ǿ���ͨ������ķ�ʽ�޸�����ֵ��
	 * ������ԶΪfalse�Ϳ�����
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration figtion = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(figtion, false);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/** �Ѷ���menu��action��ť���ּ��뵽titleȥ **/
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_actionbar_activity, menu);

		// ActionView��һ�ֿ�����ActionBar���滻Action��ť�Ŀؼ���
		// �����������û��ڲ��л�����������ͨ��ActionBar���һЩ��Ϊ�ḻ�Ĳ�����
		// ����˵������Ҫ���һ����������
		MenuItem searchItem = menu.findItem(R.id.menu_actionbar_serach);
		SearchView searchView = (SearchView) searchItem.getActionView();
		// ��������actionView���Ƿ�չ��״̬,�������������Կ��Խ���UI����
		searchItem.setOnActionExpandListener(new OnActionExpandListener() {
			public boolean onMenuItemActionExpand(MenuItem item) {

				Log.i("11", "չ��");
				return true;
			}

			public boolean onMenuItemActionCollapse(MenuItem item) {
				Log.i("11", "�ϲ�");

				return true;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	/** actionbar��action�ĵ���¼��ļ��� **/
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_actionbar_add:
			Toast.makeText(this, "���", 0).show();
			break;

		case R.id.menu_actionbar_deletel:
			Toast.makeText(this, "ɾ��", 0).show();
			break;
		case R.id.menu_actionbar_updata:
			Toast.makeText(this, "��ѯ", 0).show();
			break;
		case R.id.menu_actionbar_change:
			Toast.makeText(this, "����", 0).show();
			break;
		case android.R.id.home:
			Intent intent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, intent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(intent).startActivities();
			} else {
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, intent);
			}

			Toast.makeText(this, "�����ķ��ؼ��ļ���", 0).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
