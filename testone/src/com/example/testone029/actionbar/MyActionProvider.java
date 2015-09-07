package com.example.testone029.actionbar;

import android.content.Context;
import android.util.Log;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

import com.example.testone001.loning.R;

public class MyActionProvider extends ActionProvider {

	public MyActionProvider(Context context) {
		super(context);

	}

	public View onCreateActionView() {

		return null;
	}

	public void onPrepareSubMenu(SubMenu subMenu) {
		super.onPrepareSubMenu(subMenu);
		subMenu.clear();
		subMenu.add("��ť1").setIcon(R.drawable.m3)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {

						Log.i("11", "�Զ���ĵ�һ��");

						return false;
					}
				});
		subMenu.add("��ť2").setIcon(R.drawable.w01)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						Log.i("11", "�Զ���ĵڶ���");

						return false;
					}
				});

	}

	public boolean hasSubMenu() {

		return true;
	}
}
