package com.example.testone005.mybaseadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.testone001.loning.R;

public class BaseAdapterActivity extends Activity {
	private ListView mListV;	
	
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.activity_list_view);
			mListV = (ListView)findViewById(R.id.list_view);
			
			MyBaseadapter  base = new MyBaseadapter(this,getData());
			
			mListV.setAdapter(base);
		}
		
		
		public List<ResourceBean> getData(){
			List<ResourceBean> list = new ArrayList<ResourceBean>();
			
			ResourceBean bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("11 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("����39Ԫ����ֵ58Ԫ���ѻ��ų���Ʊ������ʱ�̣�������.");
			list.add(bean);
			
			bean = new ResourceBean();
			bean.setmImage(R.drawable.wmgj);
			bean.setmContent("2 �������");
			bean.setmTitle("�����ˣ���¥�����,�������ʴ�Ƭ");
			list.add(bean);

			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("3 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
						
			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("4 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
			
			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("5 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
			
			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("6 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
			
			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("7 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
			
			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("8 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
			
			bean = new ResourceBean();
			bean.setmImage(R.drawable.simple1);
			bean.setmContent("9 ���ɶ���̫ƽ���Ӱ��");
			bean.setmTitle("2D��ӰƱ1�ţ�3DӰƬ���10Ԫ");
			list.add(bean);
			return list;
		}
}
