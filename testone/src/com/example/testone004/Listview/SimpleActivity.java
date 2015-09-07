package com.example.testone004.Listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.testone001.loning.R;

public class SimpleActivity extends Activity {

	private ListView mSimpleList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.simple_linear_layout);

		mSimpleList = (ListView) findViewById(R.id.simple_linear_text_view);
		
		String[] from = new String[] { "img", "title", "titlebelow" };
		int[] to = new int[] { R.id.simple_image, R.id.simple_text,
				R.id.simple_below_text };

		SimpleAdapter adapter = new SimpleAdapter(this, getdata(),
				R.layout.simple_adapter_layout, from, to);
		mSimpleList.setAdapter(adapter);
		
		mSimpleList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					
			}
		});
	}

	public List<HashMap<String, Object>> getdata() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("img", R.drawable.wmgj);
		map.put("title", "�������");
		map.put("titlebelow", "�����ˣ���¥�����,�������ʴ�Ƭ");
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.simple1);
		map.put("title", "���ɶ���̫ƽ���Ӱ��");
		map.put("titlebelow", "2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.simple2);
		map.put("title", "��Ԫ�������ѻ��ų���Ʊ");
		map.put("titlebelow", "����39Ԫ����ֵ58Ԫ���ѻ��ų���Ʊ������ʱ�̣�������.");
		data.add(map);
		
		return data;
	}
}
