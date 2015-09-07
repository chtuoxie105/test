package com.example.testone028.fragmentimg.viewpager002;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class HttpImg {
	private String mUrlImg;
	private LruCache<String, Bitmap> mLruCache;
	ImageView mImageView;
	/**
	 * ��Ҫ����ʵ����LruCache�͵�ַ�Ĵ���
	 * @param urlImg
	 * @param imageView
	 */
	public HttpImg(String urlImg, ImageView imageView) {
		mUrlImg = urlImg;
		mImageView = imageView;
		
		saveImgStorage();
	}

	/**
	 * ��ȡ�ڴ�ռ�����غõ�ͼƬ
	 */
	public void saveImgStorage() {
		//��ȡ��ǰ�ⲿ�����ڴ�Ĵ�С
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		//ȥ��ǰ�ڴ��С��8��֮1���������ǵ��ڴ�
		int useSize = maxMemory / 8;
		mLruCache = new LruCache<String, Bitmap>(useSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
		startAsynctaskGetImg(mUrlImg);
	}

	/**
	 * ���ͼƬ������
	 * 
	 * @param mapKey
	 * @param bitmap
	 */
	public void addBitmapToLrucache(String mapKey, Bitmap bitmap) {
		//�ȸ��ݵ�ַ�ж����ǵĴ洢�ռ��Ƿ�������ͼƬ���оͲ��᷵�ؿգ��Ͳ����
		if (getBitmapForLruCache(mapKey) == null) {
			mLruCache.put(mapKey, bitmap);
		}
	}

	/**
	 * �ӻ���ȥͼƬ
	 * 
	 * @param mapurl
	 * @return
	 */
	public Bitmap getBitmapForLruCache(String mapurl) {
		return mLruCache.get(mapurl);

	}
	/**
	 * �������ǵ�ͼƬ����ȥ�ڴ滺������Ū�ж��Ƿ�������ͼƬ��û����ȥ�����첽�����ȡͼƬ
	 * @param urls
	 */
	public void startAsynctaskGetImg(String urls) {
	
		Bitmap bitmapss = getBitmapForLruCache(urls);
		if (bitmapss != null) {
			mImageView.setImageBitmap(bitmapss);

			return;
		} else {

			new AsyncTask<String, Void, Bitmap>() {

				protected Bitmap doInBackground(String... params) {
					Bitmap bitmap = getHttpGetImg(params[0]);
					return bitmap;
				}

				protected void onPostExecute(Bitmap result) {
					super.onPostExecute(result);
					//��ʾ�����Ǵ�������imageview�ؼ���
					mImageView.setImageBitmap(result);
					//��ͼƬ���뵽���ǵĻ��洦
					addBitmapToLrucache(mUrlImg, result);
				}
			}.execute(urls);
		}
	}
	/**
	 * ������ȡͼƬ
	 * @param urls
	 * @return
	 */
	public Bitmap getHttpGetImg(String urls) {
		InputStream input = null;
		try {
			URL url = new URL(urls);
			input = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
