package com.example.testone014.SQLiteDataBase;



import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testone001.loning.R;

public class SqlDataBaseActivity extends Activity {
	private SQLiteDatabase db;
	private EditText mUserEditTextSQLite, mPasswordEditTextSQLite,
			mSexEditTextSQLite, mScoreEditTextSQLite, mAddressEditTextSQLite;
	private Button mInsertSQLiteBtn, mUpdateSQLiteBtn, mDeleteSQLiteBtn,
			mQuerySQLiteBtn;
	private ListView mListView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlitebase_layout);

		SqliteHelper sqlHelper = SqliteHelper.geiInsentence(this);
		db = sqlHelper.getReadableDatabase();

		mListView = (ListView) findViewById(R.id.activity_sqlite_listview);

		mUserEditTextSQLite = (EditText) findViewById(R.id.activity_sqlite_usertext_edit);
		mPasswordEditTextSQLite = (EditText) findViewById(R.id.activity_sqlite_passwordtext_edit);
		mSexEditTextSQLite = (EditText) findViewById(R.id.activity_sqlite_sextext_edit);
		mScoreEditTextSQLite = (EditText) findViewById(R.id.activity_sqlite_scoretext_edit);
		mAddressEditTextSQLite = (EditText) findViewById(R.id.activity_sqlite_addresstext_edit);

		mInsertSQLiteBtn = (Button) findViewById(R.id.activity_sqlite_insert_btn);
		mUpdateSQLiteBtn = (Button) findViewById(R.id.activity_sqlite_update_btn);
		mDeleteSQLiteBtn = (Button) findViewById(R.id.activity_sqlite_delete_btn);

		mQuerySQLiteBtn = (Button) findViewById(R.id.activity_sqlite_query_btn);

		// �������
		mInsertSQLiteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String password = mPasswordEditTextSQLite.getText().toString();
				String uesr = mUserEditTextSQLite.getText().toString();
				String sex = mSexEditTextSQLite.getText().toString();
				String score = mScoreEditTextSQLite.getText().toString();
				String address = mAddressEditTextSQLite.getText().toString();
				
				//android�������ݿ��һ����
				ContentValues values = new ContentValues();
				values.put(MySQLPackaging.Student.NUMBER, password);
				values.put(MySQLPackaging.Student.NAME, uesr);
				values.put(MySQLPackaging.Student.SEX, sex);
				values.put(MySQLPackaging.Student.SCORE, score);
				values.put(MySQLPackaging.Student.ADDRESS, address);

				db.insert(MySQLPackaging.Student.TABLE, null, values);
				Toast.makeText(SqlDataBaseActivity.this, "��ӳɹ�", 0).show();
				mPasswordEditTextSQLite.setText("");
				mUserEditTextSQLite.setText("");
				mSexEditTextSQLite.setText("");
				mScoreEditTextSQLite.setText("");
				mAddressEditTextSQLite.setText("");
				
			}
		});
		// �޸����ݿ�
		mUpdateSQLiteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				ContentValues values = new ContentValues();
				values.put(MySQLPackaging.Student.NAME, "����");
				//����Ԥ����
				String whereClause = MySQLPackaging.Student.NUMBER + "= ?";
				String[] whereArgs = { "11" };
				
				db.update(MySQLPackaging.Student.TABLE, values, whereClause,
						whereArgs);
				Toast.makeText(SqlDataBaseActivity.this, "�޸Ĳ����ɹ�", 0).show();

			}
		});
		// ɾ�����ݿ����
		mDeleteSQLiteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String whereClause = MySQLPackaging.Student.NUMBER + "= ?";
				String[] whereArgs = { "11" };
				db.delete(MySQLPackaging.Student.TABLE, whereClause, whereArgs);
				Toast.makeText(SqlDataBaseActivity.this, "ɾ�������ɹ�", 0).show();
			}
		});
		// ��ѯ���ݿ����
		mQuerySQLiteBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Cursor cursor = db.query("yonghu",new
				// String[]{"id"},"id = ?",new String[]{"11"}
				// , null,null, null);
				// ֻ��ѯID
				Cursor cursor = db.query(MySQLPackaging.Student.TABLE, null,
						null, null, null, null, null);

				while (cursor.moveToNext()) {
					String number = cursor.getString(cursor
							.getColumnIndex(MySQLPackaging.Student.NUMBER));
					String name = cursor.getString(cursor
							.getColumnIndex(MySQLPackaging.Student.NAME));
					Log.e("11", "ѧ��:" + number + "  " + "����:" + name);

				}
				// ����Cursor������
				MyCursorAdapter curAdapter = new MyCursorAdapter(
						SqlDataBaseActivity.this, cursor);
				mListView.setAdapter(curAdapter);
				Toast.makeText(SqlDataBaseActivity.this, "��ѯ�����ɹ�", 0).show();
			}
		});
	}

	// �ر����ݿ�
	protected void onDestroy() {
		super.onDestroy();
		if (!(db == null)) {
			if (db.isOpen()) {
				db.close();
				db = null;// ϵͳ����
			}
		}
	}

}
