package com.player.boxplayer.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
public enum ScaleBitmapTool {

	SINGLE;
	
	/**
	 * 获取每个推荐电影界面。
	 * @param reTile
	 * @return
	 */
	public  static Bitmap getImageView(String imagePath) {
		Bitmap bitmap = decodeBitmapFromFile(imagePath,200,300);
		return bitmap;
	}

	/**
	 * 根据路径创建bitmap图片，并且以指定的宽高返回。
	 * 
	 * @param absolutePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static Bitmap decodeBitmapFromFile(String absolutePath, int reqWidth,
			int reqHeight) {
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(absolutePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(absolutePath, options);

		return bm;
	}
	
	/**
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	
}
