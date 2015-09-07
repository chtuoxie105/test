package com.example.testone005.mybaseadapter.s001;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.testone001.loning.MainActivity;
import com.example.testone001.loning.R;
import com.example.testone002.Serializables.AToB;
import com.example.testone005.mybaseadapter.BaseAdapterActivity;
import com.example.testone006.myGridview.MyGridViewActivity;
import com.example.testone007.SpinnerAndAutocompleterTextView.AutocompleteTextViewActivity;
import com.example.testone008.ProgressBar.MyProgressBar;
import com.example.testone008.ProgressBar.MySeekBarActivity;
import com.example.testone008.ProgressBar.MySotckProgressBar;
import com.example.testone009.Popwindow001.PopupWindowActivity;
import com.example.testone009.menu.MyMenuActivity;
import com.example.testone010.Dialog.MyDialogActivity;
import com.example.testone011.ViewPager.MyFramPagerActivity;
import com.example.testone011.ViewPager.MyViewPagerActivity;
import com.example.testone012.TabActivity.MyTabHost;
import com.example.testone013.ShapAndLayer_list.MyLayerListActivity;
import com.example.testone014.SQLiteDataBase.SqlDataBaseActivity;
import com.example.testone015.sharedpreferences_file.OutPublicFilesActivity;
import com.example.testone015.sharedpreferences_file.SharedpreferencesActivity;
import com.example.testone017.contentprovider.ContentResplvreActivity;
import com.example.testone018.service.ServiceActivity;
import com.example.testone019.broadcast.MyBroadCast;
import com.example.testone020.notification.NotificationActivity;
import com.example.testone021.mediaplayer.KuGuActivity;
import com.example.testone021.mediaplayer.MyMediaplayerActivity;
import com.example.testone022.servlet.MyAsynctaskActivity;
import com.example.testone022.servlet.TestServlet;
import com.example.testone023.httpstudy.HttpActivity;
import com.example.testone024.Http_callback.HttpToolActivity;
import com.example.testone025.json_study.JsonStudyOneActivity;
import com.example.testone025.json_study.TestArrayListJsonTwoActivity;
import com.example.testone026.json_study_bitmap.BitmapGridViewActivity;
import com.example.testone026.json_study_bitmap.ListViewJsonActivity;
import com.example.testone028.fragment.viewpager001.FragmentViewpagerTabActivity;
import com.example.testone028.fragment_one.MainFragmentActivity;
import com.example.testone028.fragment_one.MusicFragmentActivity;
import com.example.testone028.fragment_two.DialogFragmentActivity;
import com.example.testone028.fragment_two.FragmentTextTwo;
import com.example.testone028.fragmentimg.viewpager002.ImgViewpagerFragment;
import com.example.testone028.slidefragment003.TestSlidingActivity;
import com.example.testone029.actionbar.ActionBarProviderActivity;
import com.example.testone029.actionbar.MyActionBar;
import com.example.testone030.customview.ActivityCustom;
import com.example.testone031.viewstub.valueanimator.MyViewStubActivity;
import com.example.testone032.animation.XmlAnimationActivity;
import com.example.testone034.myview.SanjiaoActivity;

/**
 * ��ֹ���̵�ס����� ��ϣ���ڵ�����activity����
 *  android:windowSoftInputMode="adjustPan" 
 *  ϣ����̬�����߶�
 * android:windowSoftInputMode="adjustResize"
 */

public class MybaseActivity extends Activity implements OnItemClickListener {
	private ListView mlist;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_linear_layout);

		mlist = (ListView) findViewById(R.id.simple_linear_text_view);

		MyBaseAdapter adapter = new MyBaseAdapter(this, getdata());
		mlist.setAdapter(adapter);

		mlist.setOnItemClickListener(this);
	}

	public List<Resource> getdata() {
		List<Resource> list = new ArrayList<Resource>();

		Resource sour = new Resource();

		zuheCreat(list, "ListViewѧϰ1", MainActivity.class);
		zuheCreat(list, "�Զ���������", BaseAdapterActivity.class);
		zuheCreat(list, "��������2", AToB.class);
		zuheCreat(list, "GridViewѧϰ3", MyGridViewActivity.class);
		zuheCreat(list, "AutocompleteTextViewѧϰ5",
				AutocompleteTextViewActivity.class);
		zuheCreat(list, "MyProgressBarѧϰ6", MyProgressBar.class);
		zuheCreat(list, "MySotckProgressBarѧϰ7", MySotckProgressBar.class);
		zuheCreat(list, "MySeekBarActivityѧϰ8", MySeekBarActivity.class);
		zuheCreat(list, "MyMenuActivityѧϰ9", MyMenuActivity.class);
		zuheCreat(list, "PopupWindowActivityѧϰ10", PopupWindowActivity.class);
		zuheCreat(list, "MyDialogActivityѧϰ11", MyDialogActivity.class);
		zuheCreat(list, "MyViewPagerActivityѧϰ12", MyViewPagerActivity.class);
		zuheCreat(list, "MyFramPagerActivityѧϰ13", MyFramPagerActivity.class);
		zuheCreat(list, "MyTabHostѧϰ14", MyTabHost.class);
		zuheCreat(list, "LayerList_seekbarѧϰ15", MyLayerListActivity.class);
		zuheCreat(list, "SqlDataBaseActivity���ݿ��>>ѧϰ16",
				SqlDataBaseActivity.class);
		zuheCreat(list, "SharedpreferencesActivity��������ѧϰ17",
				SharedpreferencesActivity.class);
		zuheCreat(list, "�ⲿ�洢����>>��������ѧϰ18", OutPublicFilesActivity.class);

		sour = new Resource();
		sour.setmTitleText("�ļ�������>>ѧϰ19");
		// sour.setToMap("intent", new Intent(this,
		// MyExternalPublicActivity.class));
		list.add(sour);

		zuheCreat(list, "ContentProvider>>ѧϰ20", ContentResplvreActivity.class);
		zuheCreat(list, "ContentProvider>>ѧϰ20", ContentResplvreActivity.class);
		zuheCreat(list, "ServiceActivity����>Bind����ʽ>ѧϰ", ServiceActivity.class);
		zuheCreat(list, "MyBroadCast�㲥>�Զ��塢����>ѧϰ", MyBroadCast.class);
		zuheCreat(list, "NotificationActivity֪ͨѧϰ", NotificationActivity.class);
		zuheCreat(list, "MyMediaplayerActivity������ѧϰ",
				MyMediaplayerActivity.class);
		zuheCreat(list, "KuGuo������ѧϰ", KuGuActivity.class);
		zuheCreat(list, "TestServlet����html��ҳѧϰ", TestServlet.class);
		zuheCreat(list, "MyAsynctaskActivity�첽�������", MyAsynctaskActivity.class);
		zuheCreat(list, "HttpActivity���ӷ�����", HttpActivity.class);
		zuheCreat(list, "HttpToolActivity��½ȷ��", HttpToolActivity.class);
		zuheCreat(list, "JsonStudyActivity", JsonStudyOneActivity.class);
		zuheCreat(list, "TestArrayListJsonActivity",
				TestArrayListJsonTwoActivity.class);
		zuheCreat(list, "BitmapActivity����ȡͼƬ", BitmapGridViewActivity.class);
		zuheCreat(list, "ListViewJsonActivity����ȡ����", ListViewJsonActivity.class);
		zuheCreat(list, "MainFragmentActivity��̬���", MainFragmentActivity.class);
		zuheCreat(list, "MusicFragmentActivity��̬���",
				MusicFragmentActivity.class);
		zuheCreat(list, "FragmentTextTwo����fragmentͨ��activity���ݲ���",
				FragmentTextTwo.class);
		zuheCreat(list, "MyActionBarѧϰ", MyActionBar.class);
		zuheCreat(list, "ActionBarProviderActivityѧϰ",
				ActionBarProviderActivity.class);
		zuheCreat(list, "�Զ��岼�ֵ�ѧϰ", ActivityCustom.class);
		zuheCreat(list, "ViewStub�ڴ��Ż�֮������һЩ��ʼ����Ҫ�ĵĿؼ�", MyViewStubActivity.class);
		zuheCreat(list, "�Զ���DialogFragment����ʵ�ֻص�����",
				DialogFragmentActivity.class);
		zuheCreat(list, "fragmentViewpager��tab���ʹ��",
				FragmentViewpagerTabActivity.class);
		zuheCreat(list, "ImgViewpagerFragment����ȡͼƬ", ImgViewpagerFragment.class);
		zuheCreat(list, "SlidingMenuActivity", TestSlidingActivity.class);
		zuheCreat(list, "XmlAnimationActivity", XmlAnimationActivity.class);
		zuheCreat(list, "�Զ��������οؼ�", SanjiaoActivity.class);

		return list;
	}

	public void zuheCreat(List<Resource> lists, String s, Class<?> mmm) {
		Resource sour = new Resource();
		sour.setmTitleText(s);
		sour.setToMap("intent", new Intent(this, mmm));
		lists.add(sour);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyBaseAdapter asd = (MyBaseAdapter) parent.getAdapter();
		System.out.println("111111111" + asd);
		// List<Resource> s = (List<Resource>) asd.getItem(position);

		Resource s = (Resource) asd.getItem(position);

		System.out.println("111111111" + s);
		// Resource sou = s.get(position);

		// System.out.println("111111111"+sou);
		// Intent n = (Intent) sou.getToMap("intent");
		Intent n = (Intent) s.getToMap("intent");

		System.out.println("111111111555>>" + n);
		startActivity(n);
	}
}
