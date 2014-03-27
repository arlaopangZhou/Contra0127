package com.player.boxplayer.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.CoreConnectionPNames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.util.LruCache;

import com.example.android.bitmapfun.util.DiskLruCache;
//import com.player.boxplayer.https.HttpUtils;

/**
 * ʹ�� �ڴ�Ӳ�� ˫�ػ���
 * 
 * @author shenhui
 * 
 */

public class BitmapUtil {

	private static final String TAG = "BitmapUtil";
	private static Context mContext;
	private static BitmapUtil instance;

	private static LruCache<String, Bitmap> memoryCache;
	private static DiskLruCache mDiskCache;
	private static final long DISK_CACHE_SIZE = 1024 * 1024 * 80; // 80MB
	private static final int MEMORY_CACHE_SIZE = 1024 * 1024 * 20; // 20MB
	private static final String DISK_CACHE_SUBDIR = "diskCache";

	private BitmapUtil(Context context) {
		mContext = context;
		// init memoryCache
		memoryCache = new LruCache<String, Bitmap>(MEMORY_CACHE_SIZE);
		// init DiskCache
		File cacheDir = new File(mContext.getCacheDir(), DISK_CACHE_SUBDIR);
		mDiskCache = DiskLruCache
				.openCache(mContext, cacheDir, DISK_CACHE_SIZE);
	}

	public static synchronized BitmapUtil getInstance(Context context) {
		if (null == instance) {
			instance = new BitmapUtil(context);
		}
		return instance;
	}

	/**
	 * �õ�ָ����С�� bitmap
	 * 
	 * @param data
	 * @param width
	 * @param height
	 * @return
	 */
	public Bitmap getBitmap(byte[] data, int width, int height) {
		Bitmap bitmap = null;
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		opts.inSampleSize = calculateInSampleSize(opts, width, height);
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		return bitmap;
	}

	/**
	 * �������ű���
	 * 
	 * @param options
	 * @param reqWidth
	 *            Ŀ���
	 * @param reqHeight
	 *            Ŀ���
	 * @return
	 */
	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {

		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (reqWidth == 0 || reqHeight == 0) {
			return inSampleSize;
		}
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		Log.d("", "ԭͼ�ߴ磺" + width + "x" + height + ",ʵ�ʳߴ磺" + reqWidth + "x"
				+ reqHeight + ",inSampleSize = " + inSampleSize);
		return inSampleSize;
	}

	/**
	 * ֱ�Ӵ����� ��ȡ ͼƬ ������
	 * 
	 * @param url
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws IOException
	 */

	public static Bitmap getBitmap(String url) {
		Bitmap bitmap = null;
/*		byte[] data = HttpUtils.getBinary(url, null, null);
		if (data != null) {
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		}*/
		return bitmap;
	}

	/**
	 * �÷���Ϊ��̬����������������ͼƬ�����Ӳ������� �����ȴ�Ӳ�̻����ȡ<br>
	 * �����뵽�ڴ滺��� Ӳ�̻���<br>
	 * �����������أ�ֻ�������ڹ����߳�<br>
	 * 
	 * @param context
	 * @param url
	 * @param isLurCache
	 *            �Ƿ���뵽�ڴ滺�棬��̨���µ�ʱ����Ҫ���뵽�ڴ滺��
	 * @return
	 */
	public static Bitmap getBitmap(Context context, String url,
			boolean isLurCache) {
		Bitmap bitmap = null;
		getInstance(context);
		bitmap = instance.getBitmapFromDisk(url);
		if (bitmap != null) {
			Log.d(TAG, "sp:from disk bitmap" + bitmap);
			instance.addToCache(url, bitmap, isLurCache, true);
			return bitmap;
		}
		bitmap = instance.getBitmapFromNet(url, 0, 0);
		if (bitmap != null) {
			instance.addToCache(url, bitmap, isLurCache, true);
		}
		return bitmap;
	}

	public Bitmap getBitmapFromMemory(String url) {
		String key = MD5Util.getMD5String(url);
		Bitmap bitmap = memoryCache.get(key);
		return bitmap;
	}

	public Bitmap getBitmapFromDisk(String url) {

		if (url != null) {
			String key = MD5Util.getMD5String(url).substring(8, 17) + ".png";
			// Log.d("info", "tvRcommendKey="+key);
			return mDiskCache.get(key);
		} else {

			return null;
		}

	}

	public Bitmap getBitmapFromNet(String url, int width, int height) {
		Bitmap bitmap = null;
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
				60000);
		client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(5,
				false));
		HttpGet get = new HttpGet();
		int retryCount = 0;
		try {
			get.setURI(new URI(url));
			// while (bitmap == null && retryCount < 3) {
			// retryCount++;
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				bitmap = BitmapFactory.decodeStream(response.getEntity()
						.getContent());
			}
			if (bitmap == null) {
				Log.e(TAG, "getBitmapFromNet ����ʧ�� " + retryCount + " ��, url = "
						+ url);
			}
			// }
		} catch (Exception e) {
		} finally {
			get.abort();
			client.getConnectionManager().shutdown();
		}
		return bitmap;
	}

	public void addToCache(String url, Bitmap bitmap, boolean lruCache,
			boolean diskLruCache) {
		if (url == null || bitmap == null) {
			return;
		}
		String key = MD5Util.getMD5String(url);
		if (lruCache) {
			memoryCache.put(key, bitmap);
		}
		if (diskLruCache) {
			mDiskCache.put(key + ".png", bitmap);
		}
	}

}
