package com.example.testone001.tooloclass001;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * ��װ������ȡͼƬ�����棬���»���ʱ��ͼƬ��λ������
 * 
 * @author Administrator
 *
 */
public class ToolClassStorBitmap {
	private Executor mExcutor;
	private LruCache<String, Bitmap> mLruCache;
	public static ToolClassStorBitmap mToolClassStorBitmap = null;

	
	/** ����ģʽ */
	public static ToolClassStorBitmap getIntance() {
		if (mToolClassStorBitmap == null) {
			mToolClassStorBitmap = new ToolClassStorBitmap();
		}
		return mToolClassStorBitmap;
	}
	/**
	 * ���ö��߳�ȡͼƬ
	 */
	public void startMoreThread() {
		mExcutor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS,
				new LinkedBlockingDeque<Runnable>());
	}

	/**
	 * LruCache:�ڳ����ڴ�ﵽ�趨ֵʱ�Ὣ�������ʹ�õ�ͼƬ�Ƴ����� �Ȼ�ȡ�洢������ڴ棬 ��ȡ6��֮1���洢ͼƬ
	 */
	public void getBitmapStorageSpace() {
		// ��ȡӦ�ó������Ӧ���ڴ�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		// ֻ��Ҫ������ڴ��8��֮1
		int cacheSize = maxMemory / 8;
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	/**
	 * ��ͼƬ�洢��LruCache ���ü�ֵ�Խ�����ӣ����ж��ڴ��Ƿ���ڴ˵�ַ��
	 */
	public void storeBitmapToMemory(String mapKey, Bitmap map) {
		if (getBitmapToMemory(mapKey) == null) {
			mLruCache.put(mapKey, map);
		}
	}

	/**
	 * �Ӵ洢����ȥȡͼƬ
	 * 
	 */
	public Bitmap getBitmapToMemory(String mapKey) {
		return mLruCache.get(mapKey);
	}

	/**
	 * ������ͼƬ
	 */
	public Bitmap getBitmapForHttp(String httpUrl) {
		InputStream input = null;
		try {
			URL url = new URL(httpUrl);
			input = url.openStream();
			Bitmap map = BitmapFactory.decodeStream(input);
			return map;
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

	public void loadBitmap(Resources rea, String imageUrl, ImageView imageView,
			int resImgID) {
		
		// ���ж��ڴ������Ƿ�������ͼƬ,�о�ֱ�ӷ���ȥ��Ȼ���������û�о�ִ�������
		Bitmap mapToLruCache = getBitmapToMemory(imageUrl);
		if (mapToLruCache != null) {
			imageView.setImageBitmap(mapToLruCache);
			return;
		} 

			if (cancelPotentialWork(imageUrl, imageView)) {
				BitmapWorkerTask task = new BitmapWorkerTask(imageView);

				AsyncDrawable asyncDrawable = new AsyncDrawable(rea,
						BitmapFactory.decodeResource(rea, resImgID), task);
				imageView.setImageDrawable(asyncDrawable);

				if (mExcutor == null) {
					task.execute(imageUrl);
				} else {
					task.executeOnExecutor(mExcutor, imageUrl);
				}
		}
	}

	public static boolean cancelPotentialWork(String imageUrl,
			ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final String bitmapData = bitmapWorkerTask.data;
			//�ж�2����ַ�Ƿ���ͬ����ͼƬ�Ƿ����
			if (!bitmapData.equals(imageUrl)) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

	/**
	 * ����һ��ר�õ�Drawable�����������淵�ع�����������á�����������£����������ʱBitmapDrawable�ᱻʹ��
	 * 
	 */
	static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
					bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return (BitmapWorkerTask) bitmapWorkerTaskReference.get();
		}
	}
	//�Ƚ�һ���࣬�̳����ǵ��첽�߳����񣬷���BitmapWorkerTask���󣬲�������������ɶ�õ�
	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private String data = "";

		public BitmapWorkerTask(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(String... params) {
			data = params[0];
			//����ȡͼƬ��������
			return getBitmapForHttp(data);
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = (ImageView) imageViewReference
						.get();
				final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
				if (this == bitmapWorkerTask && imageView != null) {
					imageView.setImageBitmap(bitmap);
					// �ڴ�û������ͼƬ������ӽ�ȥ
					storeBitmapToMemory(data, bitmap);
				}
			}
		}
	}
}
