package com.example.testone026.json_study_bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testone001.loning.R;
import com.example.testone001.tooloclass001.ToolClassStorBitmap;

public class ListViewJsonActivity extends Activity {
	
	String urlAddress = "http://192.168.1.203/json/around";
	
	private ListView mListViewJsonData;
	//�������̵߳��Ǹ���
	private Executor mExcutor;
	//�������װ���ǵ�Bean���ݣ����ǽ����������Ǹ��������������
	private ArrayList<ListViewBeanJson> listJSONObject = new ArrayList<ListViewBeanJson>();
	//�Լ������һ��������
	private ListViewAdapterGotHttp mAdapter;
	//����������
	private ToolClassStorBitmap mToolClassStorBitmap;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_listview_json_layout);
		// ThreadPoolExecutor����ǿ������̵߳�һ���࣬���췽������:
		//(һ��ֻ�ܿ���10���߳����У�һ����������100���߳������棬ÿ���߳����е�ʱ��Ϊ10����,����2��������ɶ��˼�������þ���)��
		mExcutor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>());
		//��װ��һ�������࣬��������˵�Ŀ��ɶ�ģ���˼���ǵ�һ�νӴ������
		mToolClassStorBitmap = ToolClassStorBitmap.getIntance();
		//��������������Ķ��߳�����ȡͼƬ�����ݣ�
		mToolClassStorBitmap.startMoreThread();
		//Ϊ�˷�ֹ���»���ʱ��ͼƬ���ʹ�λ�������˻���android�ṩ��LruCache
		mToolClassStorBitmap.getBitmapStorageSpace();

		mAdapter = new ListViewAdapterGotHttp(this);
		mListViewJsonData = (ListView) findViewById(R.id.json_listview_for_http);
		getHttpData(urlAddress);
	}

	/**
	 * ��������߳�ȡȡJSON����
	 */
	public void getHttpData(String url) {
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... params) {
				String str = httpGetMesg(params[0]);
				return str;
			}

			protected void onPostExecute(String result) {
				try {
					// �Ȼ�������JSON����
					JSONObject jsonObkect = new JSONObject(result);
					// �������JSON�����д˴����info��JSON����
					JSONObject jsonInfo = jsonObkect.getJSONObject("info");
					// �ٴ�info�����л�����ǵ�JSON�������merchantKey�����������������Ҫ��������Դ����
					JSONArray jsonMerchantKey = jsonInfo
							.getJSONArray("merchantKey");
					// ����Ϳ��������ǵ�ѭ����������ȥ�������ǵ�������
					int n = jsonMerchantKey.length();
					for (int i = 0; i < n; i++) {
						//�����������ľ������Ȼ������������ȡ������Ҫ������
						JSONObject jsonArrayDat = jsonMerchantKey
								.getJSONObject(i);
						ListViewBeanJson beanJson = new ListViewBeanJson();

						beanJson.setShopImg(jsonArrayDat.getString("picUrl"));
						beanJson.setCardShopImg(jsonArrayDat
								.getString("cardType"));
						beanJson.setGroupShopImg(jsonArrayDat
								.getString("groupType"));
						beanJson.setCouponShopImg(jsonArrayDat
								.getString("couponType"));

						beanJson.setShopNameText(jsonArrayDat.getString("name"));
						beanJson.setShopMessageText(jsonArrayDat
								.getString("coupon"));
						beanJson.setShopAddressText(jsonArrayDat
								.getString("location"));
						beanJson.setShopMapText(jsonArrayDat
								.getString("distance"));
						
						listJSONObject.add(beanJson);
					}
					mAdapter.setData(listJSONObject);
					mListViewJsonData.setAdapter(mAdapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}.executeOnExecutor(mExcutor, url);
	}

	/**
	 * �����ȡJSON����
	 */
	public String httpGetMesg(String urls) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urls);
			HttpResponse response = httpClient.execute(httpGet);
			int n = response.getStatusLine().getStatusCode();
			if (n == HttpStatus.SC_OK) {
				String line = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				return line;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �Զ�������������������ݵ�����
	 */
	public class ListViewAdapterGotHttp extends BaseAdapter {
		private LayoutInflater infalter;
		private ArrayList<ListViewBeanJson> listJSONAdapter = new ArrayList<ListViewBeanJson>();

		public ListViewAdapterGotHttp(Context context) {
			infalter = LayoutInflater.from(context);
		}

		public void setData(ArrayList<ListViewBeanJson> listJSONObjects) {
			listJSONAdapter = listJSONObjects;
			notifyDataSetChanged();
		}

		public int getCount() {
			return listJSONAdapter.size();
		}

		public Object getItem(int position) {
			return listJSONAdapter.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			final ListViewBeanJsonReasou mListViewBeanJson;
			if (convertView == null) {
				convertView = infalter.inflate(
						R.layout.listview_item_jsondata_layout, null);
				mListViewBeanJson = new ListViewBeanJsonReasou();

				mListViewBeanJson.shopImg = (ImageView) convertView
						.findViewById(R.id.listview_json__shopimg);
				mListViewBeanJson.groupShopImg = (ImageView) convertView
						.findViewById(R.id.listview_json__groupshopping);
				mListViewBeanJson.couponShopImg = (ImageView) convertView
						.findViewById(R.id.listview_json__coupon);
				mListViewBeanJson.cardShopImg = (ImageView) convertView
						.findViewById(R.id.listview_json__shoppingcard);

				mListViewBeanJson.ShopNameText = (TextView) convertView
						.findViewById(R.id.listview_json_shopname);
				mListViewBeanJson.ShopMessageText = (TextView) convertView
						.findViewById(R.id.listview_json_shopmessage);
				mListViewBeanJson.ShopAddressText = (TextView) convertView
						.findViewById(R.id.listview_json_shopaddress);
				mListViewBeanJson.ShopMapText = (TextView) convertView
						.findViewById(R.id.listview_json_shopmap);
				convertView.setTag(mListViewBeanJson);
			} else {
				mListViewBeanJson = (ListViewBeanJsonReasou) convertView
						.getTag();
			}

			ListViewBeanJson jsonDataBean = (ListViewBeanJson) getItem(position);

			mListViewBeanJson.ShopNameText.setText(jsonDataBean
					.getShopNameText());
			mListViewBeanJson.ShopMessageText.setText(jsonDataBean
					.getShopMessageText());
			mListViewBeanJson.ShopAddressText.setText(jsonDataBean
					.getShopAddressText());
			mListViewBeanJson.ShopMapText
					.setText(jsonDataBean.getShopMapText());

			if (jsonDataBean.getGroupShopImg().equals("YES")) {
				mListViewBeanJson.groupShopImg
						.setImageResource(R.drawable.near_group);
			}
			if (jsonDataBean.getCouponShopImg().equals("YES")) {
				mListViewBeanJson.couponShopImg
						.setImageResource(R.drawable.near_ticket);
			}
			if (jsonDataBean.getCardShopImg().equals("YES")) {
				mListViewBeanJson.cardShopImg
						.setImageResource(R.drawable.near_card);
			}
			//��Bean�������ȡͼƬ�ĵ�ַ
			String ms = jsonDataBean.getShopImg();
			mToolClassStorBitmap.loadBitmap(getResources(), ms,
					mListViewBeanJson.shopImg, R.drawable.m3);

			return convertView;
		}
	}

	class ListViewBeanJsonReasou {
		private ImageView shopImg;
		private ImageView groupShopImg;
		private ImageView couponShopImg;
		private ImageView cardShopImg;

		private TextView ShopNameText;
		private TextView ShopMessageText;
		private TextView ShopAddressText;
		private TextView ShopMapText;
	}
}
