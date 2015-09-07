package com.example.testone006.myGridview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.example.testone001.loning.R;
/**
 * GridView �Ź������ϰ
 */
public class MyGridViewActivity extends Activity {
	private GridView mGridView;
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.grid_view_one_layout);
			
			mGridView = (GridView) findViewById(R.id.gridview_one);				
			MyGridViewBase adapter = new MyGridViewBase(this,getData());		
			mGridView.setAdapter(adapter);
			
			mGridView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.e("11111","1111222"+position);
					MyGridViewBase gridVie = (MyGridViewBase) parent.getAdapter();
//					List<MyGridView> listOne = (List<MyGridView>) gridVie.getItem(position);
					MyGridView myGrid = (MyGridView) gridVie.getItem(position);
//					MyGridView  myGrid= listOne.get(position);
					
					Intent intent = new Intent(MyGridViewActivity.this,ZoomActivity.class);
					Bundle bundle  = new Bundle();
					bundle.getInt("IMAGE",myGrid.getmImage());
					intent.putExtra("BUNDLR", bundle);
					startActivity(intent);
				}
			});
		}
		public List<MyGridView> getData(){
			
			List<MyGridView> list = new ArrayList<MyGridView>();
			
			MyGridView grid = new MyGridView();
			
			grid.setmImage(R.drawable.simple1);	
			grid.setmText("��һ��ͼƬ");
			list.add(grid);
	
			grid = new MyGridView();
			
			grid.setmImage(R.drawable.simple2);	
			grid.setmText("�ڶ���ͼƬ");
			list.add(grid);
			
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple1);			
			grid.setmText("������ͼƬ");
			list.add(grid);
			
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple2);			
			grid.setmText("���ĸ�ͼƬ");
			list.add(grid);
			
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple3);			
			grid.setmText("�����ͼƬ");
			list.add(grid);
			
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple3);			
			grid.setmText("������ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple3);			
			grid.setmText("���߸�ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple3);			
			grid.setmText("�ڰ˸�ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple1);			
			grid.setmText("�ھŸ�ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple2);			
			grid.setmText("��ʮ��ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple3);			
			grid.setmText("��11��ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple4);			
			grid.setmText("��12��ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple1);			
			grid.setmText("��13��ͼƬ");
			list.add(grid);
			
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple2);			
			grid.setmText("��14��ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple3);			
			grid.setmText("��15��ͼƬ");
			list.add(grid);
			grid = new MyGridView();
			grid.setmImage(R.drawable.simple4);			
			grid.setmText("��16��ͼƬ");
			list.add(grid);
			return list;
		}

}
