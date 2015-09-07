package com.example.testone007.SpinnerAndAutocompleterTextView;

import java.util.ArrayList;
import java.util.List;

import com.example.testone001.loning.R;
import com.example.testone006.myGridview.MyGridView;
import com.example.testone006.myGridview.MyGridViewBase;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

/**
 * ��ѡ��ť,�ص���һ��,������ package com.example.testone.myGridview006
 * �е�MyGridView�࣬�ͱ����е�MySpinnerBaseAdapter�ࡣ
 */
public class SpinnerActivity extends Activity {
	private Spinner mSpinner;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner_layout);
		mSpinner = (Spinner) findViewById(R.id.spinner_one);

		MyGridViewBase adapter = new MyGridViewBase(this, getData());
		mSpinner.setAdapter(adapter);
	}

	public List<MyGridView> getData() {
		List<MyGridView> list = new ArrayList<MyGridView>();
		MyGridView grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("1��");
		list.add(grid);

		grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("2��");
		list.add(grid);

		grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("3��");
		list.add(grid);

		grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("4��");
		list.add(grid);

		grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("5��");
		list.add(grid);

		grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("6��");
		list.add(grid);

		grid = new MyGridView();
		grid.setmImage(R.drawable.simple1);
		grid.setmText("7��");
		list.add(grid);
		return list;
	}
}
