package com.example.testone028.fragment.viewpager001;

import java.util.ArrayList;

import com.example.testone001.loning.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentViewpagerTabActivity extends FragmentActivity implements
		OnPageChangeListener, OnCheckedChangeListener {
	private ViewPager mViewPager;
	private RadioGroup mRadioGroup;
	private ArrayList<Fragment> mList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.fragmentviewpager_tab_layout);

		mRadioGroup = (RadioGroup) findViewById(R.id.fragment_tabhost_radiogroup);
		mViewPager = (ViewPager) findViewById(R.id.fragment_viewpager);

		mList = new ArrayList<Fragment>();
		mList.add(ViewpagerImgFragment.newFragment());
		mList.add(ViewpagerMineFragment.newFragment());
		mList.add(ViewpagerListFragment.newFragment());
		mList.add(ViewpagerImgFourFragment.newFragment());

		FragmentAdapter adapter = new FragmentAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(adapter);

		adapter.setData(mList);
		// ����viewpager�������ٸ���ʾ����
		mViewPager.setOffscreenPageLimit(1);
		// Ĭ��ѡ�е�һ��
		((RadioButton) mRadioGroup.getChildAt(0)).toggle();

		mViewPager.setOnPageChangeListener(this);
		mRadioGroup.setOnCheckedChangeListener(this);
	}

	public class FragmentAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> lists = new ArrayList<Fragment>();

		public FragmentAdapter(FragmentManager fm) {
			super(fm);

		}

		public void setData(ArrayList<Fragment> listsm) {
			lists = listsm;
			notifyDataSetChanged();
		}

		public Fragment getItem(int arg0) {
		
			return lists.get(arg0);
		}

		public int getCount() {
			return lists.size();
		}

	}

	// ��ʼ
	public void onPageScrollStateChanged(int arg0) {

	}

	// ���ڸı���
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// ������ʱ���������ʾӢ��ȥ��ʾ��һ��tabҳ��
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:			
			((RadioButton) mRadioGroup.getChildAt(arg0)).toggle();
			break;
		case 1:
			((RadioButton) mRadioGroup.getChildAt(arg0)).toggle();
			break;
		case 2:
			((RadioButton) mRadioGroup.getChildAt(arg0)).toggle();
			break;
		case 3:
			((RadioButton) mRadioGroup.getChildAt(arg0)).toggle();
			break;

		}

	}
	/**
	 * Radiogroup�ļ�������ȡViewPager�ĵ�ǰͬ�������
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		switch (checkedId) {
		case R.id.fragment_groupon:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.fragment_merchant:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.fragment_mine:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.fragment_more:
			mViewPager.setCurrentItem(3);
			break;
		}
	}

	public void hideSaveMsgFragment(FragmentTransaction transaction) {

		if (ViewpagerImgFragment.newFragment() != null) {
			Log.i("11", "���ص�һ��"+0);
			transaction.hide(ViewpagerImgFragment.newFragment());
			
		}if (ViewpagerMineFragment.newFragment() != null) {
			transaction.hide(ViewpagerMineFragment.newFragment());
			Log.i("11", "���ص�һ��"+1);
		}if (ViewpagerListFragment.newFragment() != null) {
			transaction.hide(ViewpagerListFragment.newFragment());
			Log.i("11", "���ص�һ��"+2);
		}if (ViewpagerImgFourFragment.newFragment() != null) {
			transaction.hide(ViewpagerImgFourFragment.newFragment());
			Log.i("11", "���ص�һ��"+3);
		}

	}
}
