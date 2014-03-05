package com.player.boxplayer.view;
/**
 * 每个ViewPage下面的布局。
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.player.boxplayer.R;
import com.player.boxplayer.effect.ImageReflect;
import com.player.boxplayer.effect.ScaleAnimEffect;
import com.player.boxplayer.tile.Tile;
import com.player.boxplayer.tile.TileGroup;

public class PageViewLayout extends LinearLayout implements TileGroupView,
		OnClickListener, OnFocusChangeListener {
	private Context mContext;
	private TileGroup mTitleGroup;

	private FrameLayout[] fls = new FrameLayout[10];
	private ImageView[] posts = new ImageView[10];
	private ImageView[] backGrounds = new ImageView[10];
	private Bitmap[] bitmaps = new Bitmap[10];
	private TextView[] tvs = new TextView[10];
	private EffectPos[] effPos = new EffectPos[10];
	private ImageView refImageView[] = new ImageView[6];

	private HorizontalScrollView hsScrollView;
	private ImageView whiteBorder;
	private String mImgBaseFolder;

	public PageViewLayout(Context context) {
		super(context);
	}

	public PageViewLayout(Context context, TileGroup tilGroup,
			String imgBaseFolder) {
		super(context);
		mContext = context;
		mTitleGroup = tilGroup;
		mImgBaseFolder = imgBaseFolder;
	}

	@Override
	public void initView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(VERTICAL);
		int nResId = GetPageViewID(mTitleGroup.getTileCount());
		hsScrollView = (HorizontalScrollView) LayoutInflater.from(mContext)
				.inflate(nResId, null);
		addView(hsScrollView);

		refImageView[0] = (ImageView) findViewById(R.id.hot_reflected_img_0);
		refImageView[1] = (ImageView) findViewById(R.id.hot_reflected_img_1);
		refImageView[2] = (ImageView) findViewById(R.id.hot_reflected_img_2);
		refImageView[3] = (ImageView) findViewById(R.id.hot_reflected_img_3);
		refImageView[4] = (ImageView) findViewById(R.id.hot_reflected_img_4);
		refImageView[5] = (ImageView) findViewById(R.id.hot_reflected_img_5); 

		fls[0] = (FrameLayout) findViewById(R.id.latest_recommend_fl_0);
		fls[1] = (FrameLayout) findViewById(R.id.latest_recommend_fl_1);
		fls[2] = (FrameLayout) findViewById(R.id.latest_recommend_fl_2);
		fls[3] = (FrameLayout) findViewById(R.id.latest_recommend_fl_3);
		fls[4] = (FrameLayout) findViewById(R.id.latest_recommend_fl_4);
		fls[5] = (FrameLayout) findViewById(R.id.latest_recommend_fl_5);
		fls[6] = (FrameLayout) findViewById(R.id.latest_recommend_fl_6);
		fls[7] = (FrameLayout) findViewById(R.id.latest_recommend_fl_7);
		fls[8] = (FrameLayout) findViewById(R.id.latest_recommend_fl_8);
		fls[9] = (FrameLayout) findViewById(R.id.latest_recommend_fl_9);

		tvs[0] = (TextView) findViewById(R.id.latest_recommend_text_0);
		tvs[1] = (TextView) findViewById(R.id.latest_recommend_text_1);
		tvs[2] = (TextView) findViewById(R.id.latest_recommend_text_2);
		tvs[3] = (TextView) findViewById(R.id.latest_recommend_text_3);
		tvs[4] = (TextView) findViewById(R.id.latest_recommend_text_4);
		tvs[5] = (TextView) findViewById(R.id.latest_recommend_text_5);
		tvs[6] = (TextView) findViewById(R.id.latest_recommend_text_6);
		tvs[7] = (TextView) findViewById(R.id.latest_recommend_text_7);
		tvs[8] = (TextView) findViewById(R.id.latest_recommend_text_8);
		tvs[9] = (TextView) findViewById(R.id.latest_recommend_text_9);

		posts[0] = (ImageView) findViewById(R.id.latest_recommend_poster_0);
		posts[1] = (ImageView) findViewById(R.id.latest_recommend_poster_1);
		posts[2] = (ImageView) findViewById(R.id.latest_recommend_poster_2);
		posts[3] = (ImageView) findViewById(R.id.latest_recommend_poster_3);
		posts[4] = (ImageView) findViewById(R.id.latest_recommend_poster_4);
		posts[5] = (ImageView) findViewById(R.id.latest_recommend_poster_5);
		posts[6] = (ImageView) findViewById(R.id.latest_recommend_poster_6);
		posts[7] = (ImageView) findViewById(R.id.latest_recommend_poster_7);
		posts[8] = (ImageView) findViewById(R.id.latest_recommend_poster_8);
		posts[9] = (ImageView) findViewById(R.id.latest_recommend_poster_9);

		backGrounds[0] = (ImageView) findViewById(R.id.latest_recommend_bg_0);
		backGrounds[1] = (ImageView) findViewById(R.id.latest_recommend_bg_1);
		backGrounds[2] = (ImageView) findViewById(R.id.latest_recommend_bg_2);
		backGrounds[3] = (ImageView) findViewById(R.id.latest_recommend_bg_3);
		backGrounds[4] = (ImageView) findViewById(R.id.latest_recommend_bg_4);
		backGrounds[5] = (ImageView) findViewById(R.id.latest_recommend_bg_5);
		backGrounds[6] = (ImageView) findViewById(R.id.latest_recommend_bg_6);
		backGrounds[7] = (ImageView) findViewById(R.id.latest_recommend_bg_7);
		backGrounds[8] = (ImageView) findViewById(R.id.latest_recommend_bg_8);
		backGrounds[9] = (ImageView) findViewById(R.id.latest_recommend_bg_9);

		effPos[0] = new EffectPos(0, 0, 292, 445, 50f, 0f);//控制边框宽高
		effPos[1] = new EffectPos(0, 0, 445, 220, 391f, -103f);
		effPos[2] = new EffectPos(0, 0, 220, 220, 289f, 103f);
		effPos[3] = new EffectPos(0, 0, 220, 220, 494f, 103f);
		effPos[4] = new EffectPos(0, 0, 292, 445, 734f, 0f);//9张图的中间竖图
		effPos[5] = new EffectPos(0, 0, 220, 220, 975f, -103f);
		effPos[6] = new EffectPos(0, 0, 220, 220, 1181f, -103f);
		effPos[7] = new EffectPos(0, 0, 445, 220, 1078f, 103f);
		effPos[8] = new EffectPos(0, 0, 220, 220, 1423f, -103f);
		effPos[9] = new EffectPos(0, 0, 220, 220, 1423f, 103f);

		int nTileCount = mTitleGroup.getTileCount();
		if (nTileCount > 10)
			nTileCount = 10;

		RelayoutPage(nTileCount);

		for (int i = 0; i < nTileCount; i++) {
			posts[i].setOnClickListener(this);
			posts[i].setOnFocusChangeListener(this);
			posts[i].setScaleType(ScaleType.CENTER_CROP);
			backGrounds[i].setVisibility(View.GONE);
			tvs[i].setVisibility(View.GONE);
			tvs[i].setText(mTitleGroup.getTileAt(i).getTitle());

			try {
				String strImgName = mTitleGroup.getTileAt(i).getImageUrl();
				if (strImgName.length() > 0) {
					File file = new File(mImgBaseFolder + "/" + strImgName);
					Log.i("Contra", "PageViewLayout ====="+mImgBaseFolder + "/" + strImgName);
					// Log.e("xml test", "set image uri " + file.toString());
					if (file != null) {
//						posts[i].setImageURI(Uri.fromFile(file));
					}
				}
			} catch (Exception e) {
				Log.i("ERROR", "PICTURE NOT���FOUND锛�");
			}
			ReflectedImage(i, nTileCount);
		}

		whiteBorder = (ImageView) findViewById(R.id.white_boder);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(292, 445);//此处的尺寸要和首次进入界面的图片的尺寸一样。
		lp.leftMargin = 100;//设置边框距离左边的距离尺寸
		lp.topMargin = 0;//设置距离顶部的距离尺寸
		whiteBorder.setLayoutParams(lp);
	}

	@Override
	public void initListener() {
	}

	int refIndex = 0;

	@Override
	public void updateData() {

	}

	@Override
	public void destroy() {
		for (int i = 0; i < 10; i++) {
			if (bitmaps[i] != null && !bitmaps[i].isRecycled()) {
				bitmaps[i].recycle();
				bitmaps[i] = null;
			}
			fls[i] = null;
			tvs[i] = null;
			backGrounds[i] = null;
			posts[i] = null;
			System.gc();
		}
	}

	ScaleAnimEffect animEffect = new ScaleAnimEffect();

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		
		switch (v.getId()) {
		case R.id.latest_recommend_poster_0:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(0, 0);
				showOnFocusAnimation(0);
				flyWhiteBorder(effPos[0].nWidth, effPos[0].nHeight,
						effPos[0].fToX, effPos[0].fToY);
			} else {
				showLooseFocusAinimation(0);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_1:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(0, 0);
				showOnFocusAnimation(1);
				flyWhiteBorder(effPos[1].nWidth, effPos[1].nHeight,
						effPos[1].fToX, effPos[1].fToY);
			} else {
				showLooseFocusAinimation(1);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_2:
			if (hasFocus) {
				showOnFocusAnimation(2);
				flyWhiteBorder(effPos[2].nWidth, effPos[2].nHeight,
						effPos[2].fToX, effPos[2].fToY);
			} else {
				showLooseFocusAinimation(2);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_3:
			if (hasFocus) {
				showOnFocusAnimation(3);
				flyWhiteBorder(effPos[3].nWidth, effPos[3].nHeight,
						effPos[3].fToX, effPos[3].fToY);
			} else {
				showLooseFocusAinimation(3);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_4:
			if (hasFocus) {
				showOnFocusAnimation(4);
				flyWhiteBorder(effPos[4].nWidth, effPos[4].nHeight,
						effPos[4].fToX, effPos[4].fToY);
			} else {
				showLooseFocusAinimation(4);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_5:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(effPos[5].xPos, effPos[5].yPos);
				showOnFocusAnimation(5);
				flyWhiteBorder(effPos[5].nWidth, effPos[5].nHeight,
						effPos[5].fToX, effPos[5].fToY);
			} else {
				showLooseFocusAinimation(5);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_6:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(effPos[6].xPos, effPos[6].yPos);
				showOnFocusAnimation(6);
				flyWhiteBorder(effPos[6].nWidth, effPos[6].nHeight,
						effPos[6].fToX, effPos[6].fToY);
			} else {
				showLooseFocusAinimation(6);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_7:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(effPos[7].xPos, effPos[7].yPos);
				showOnFocusAnimation(7);
				flyWhiteBorder(effPos[7].nWidth, effPos[7].nHeight,
						effPos[7].fToX, effPos[7].fToY);
			} else {
				showLooseFocusAinimation(7);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_8:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(effPos[8].xPos, effPos[8].yPos);
				showOnFocusAnimation(8);
				flyWhiteBorder(effPos[8].nWidth, effPos[8].nHeight,
						effPos[8].fToX, effPos[8].fToY);
			} else {
				showLooseFocusAinimation(8);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.latest_recommend_poster_9:
			if (hasFocus) {
				hsScrollView.smoothScrollTo(effPos[9].xPos, effPos[9].yPos);
				showOnFocusAnimation(9);
				flyWhiteBorder(effPos[9].nWidth, effPos[9].nHeight,
						effPos[9].fToX, effPos[9].fToY);
			} else {
				showLooseFocusAinimation(9);
				whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		}
	}

	/**
	 * 焦点选中时的方格的动画以及图片框显示动画
	 * @param toWidth
	 * @param toHeight
	 * @param toX
	 * @param toY
	 */
	private void flyWhiteBorder(int toWidth, int toHeight, float toX, float toY) {
		if (whiteBorder != null) {
//			whiteBorder.setVisibility(View.VISIBLE);
			int width = whiteBorder.getWidth();
			int height = whiteBorder.getHeight();
			ViewPropertyAnimator animator = whiteBorder.animate();
			animator.setDuration(150);
			animator.scaleX((float) toWidth / (float) width);
			animator.scaleY((float) toHeight / (float) height);
			animator.x(toX);
			animator.y(toY);
			animator.start();
			
		}
	}

	/**
	 * 根据传入的单个网格图片的总数返回相应的布局文件。
	 * @param nCount 传入的用于区分布局文件的字段。
	 * @return 相应的布局文件
	 */
	private int GetPageViewID(int nCount) {
		int nViewId = R.layout.page_view_10;
		switch (nCount) {
		case 1:
			nViewId = R.layout.page_view_1;
			break;
		case 2:
			nViewId = R.layout.page_view_2;
			break;
		case 3:
			nViewId = R.layout.page_view_3;
			break;
		case 4:
			nViewId = R.layout.page_view_4;
			break;
		case 5:
			nViewId = R.layout.page_view_5;
			break;
		case 6:
			nViewId = R.layout.page_view_6;
			break;
		case 7:
			nViewId = R.layout.page_view_7;
			break;
		case 8:
			nViewId = R.layout.page_view_8;
			break;
		case 9:
			nViewId = R.layout.page_view_9;
			break;
		default:
			break;
		}

		return nViewId;
	}

	/**
	 * 设置每个网格的图标的坐标。
	 * @param nCount 每个图标的标识。
	 */
	private void RelayoutPage(int nCount) {
		switch (nCount) {
		case 1:
			break;
		case 2:
			effPos[1].SetParams(0, 0, 292, 445, 322f, 0f);
			break;
		case 3:
			effPos[1].SetParams(0, 0, 220, 220, 291f, -103f);
			effPos[2].SetParams(0, 0, 220, 220, 291f, 103f);
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			effPos[4].SetParams(0, 0, 220, 220, 699f, -101f);
			effPos[5].SetParams(0, 0, 220, 220, 699f, 103f);
			
			break;
		case 7:
			effPos[6].SetParams(0, 0, 220, 220, 975f, 103f);
			break;
		case 8:
			effPos[6].xPos = 1386;
			effPos[7].xPos = 1386;
			break;
		case 9:
			effPos[6].xPos = 1597;
			effPos[7].xPos = 1597;
			effPos[8].SetParams(1780, 0, 300, 445, 1423f, 0f);//9张图的最后一张竖图。
			break;
		case 10:
			effPos[6].xPos = 1597;
			effPos[7].xPos = 1597;
			effPos[8].SetParams(1780, 0, 220, 220, 1385f, -103f);//9张图的最后一张竖图。
			effPos[9].SetParams(1780, 0, 220, 220, 1385f, 103f);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置每个页面的图片倒影效果
	 * @param nIndex 图片数量
	 * @param nCount 每个页面的图片数量。
	 */
	private void ReflectedImage(int nIndex, int nCount) {
		switch (nCount) {
		case 2:
			if (nIndex == 0 || nIndex == 1) {
				refImageView[refIndex].setImageBitmap(ImageReflect
						.createCutReflectedImage(
								ImageReflect.convertViewToBitmap(fls[nIndex]),
								0)); 
				refIndex++;
			}
			break;
		case 3:
			if (nIndex == 0 || nIndex == 2) {
				refImageView[refIndex].setImageBitmap(ImageReflect
						.createCutReflectedImage(
								ImageReflect.convertViewToBitmap(fls[nIndex]),
								0));
				refIndex++;
			}
			break;
		case 6:
			if (nIndex == 0 || nIndex == 2 || nIndex == 3 || nIndex == 5) {
				refImageView[refIndex].setImageBitmap(ImageReflect
						.createCutReflectedImage(
								ImageReflect.convertViewToBitmap(fls[nIndex]),
								0));
				refIndex++;
			}
			break;
		case 7:
			if (nIndex == 0 || nIndex == 2 || nIndex == 3 || nIndex == 4
					|| nIndex == 6) {
				refImageView[refIndex].setImageBitmap(ImageReflect
						.createCutReflectedImage(
								ImageReflect.convertViewToBitmap(fls[nIndex]),
								0));
				refIndex++;
			}
			break;
		case 9:
			if (nIndex == 0 || nIndex == 2 || nIndex == 3 || nIndex == 4
					|| nIndex == 7 || nIndex == 8) {
				refImageView[refIndex].setImageBitmap(ImageReflect
						.createCutReflectedImage(
								ImageReflect.convertViewToBitmap(fls[nIndex]),
								0));
				refIndex++;
			}
			break;
		default:
			if (nIndex == 0 || nIndex == 2 || nIndex == 3 || nIndex == 4
					|| nIndex == 7 || nIndex == 9) {
				refImageView[refIndex].setImageBitmap(ImageReflect
						.createCutReflectedImage(
								ImageReflect.convertViewToBitmap(fls[nIndex]),
								0));
				refIndex++;
			}
			break;
		}
	}

	/**
	 * 显示每个图片的移动动画
	 * @param position 传入的网格图片的坐标。
	 */
	private void showOnFocusAnimation(final int position) { 
		fls[position].bringToFront();
		animEffect.setAttributs(1.0f, 1.10f, 1.0f, 1.10f, 100);
		Animation anim1 = animEffect.createAnimation();
		anim1.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				whiteBorder.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				backGrounds[position].startAnimation(animEffect.alphaAnimation(
						0, 1, 150, 0));
				tvs[position].startAnimation(animEffect.alphaAnimation(0, 1,
						150, 0));
				tvs[position].setVisibility(View.VISIBLE);
				backGrounds[position].setVisibility(View.VISIBLE);
				
				whiteBorder.setVisibility(View.INVISIBLE);
			}
		});
		posts[position].startAnimation(anim1);
	}

	private void showLooseFocusAinimation(final int position) {
		animEffect.setAttributs(1.10f, 1.0f, 1.10f, 1.0f, 100);
		posts[position].startAnimation(animEffect.createAnimation());
		tvs[position].setVisibility(View.GONE);
		backGrounds[position].setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		ArrayList<Integer> poster = new ArrayList<Integer>(Arrays.asList(
				R.id.latest_recommend_poster_0, R.id.latest_recommend_poster_1,
				R.id.latest_recommend_poster_2, R.id.latest_recommend_poster_3,
				R.id.latest_recommend_poster_4, R.id.latest_recommend_poster_5,
				R.id.latest_recommend_poster_6, R.id.latest_recommend_poster_7,
				R.id.latest_recommend_poster_8, R.id.latest_recommend_poster_9));

		int posterId = poster.indexOf(v.getId());
		Tile tile = mTitleGroup.getTileAt(posterId);
		try {
			Intent intent = new Intent();
			intent.setClassName(tile.getTarget(), tile.getActivity());
			intent.putExtra("desc", tile.getDesc());
			mContext.startActivity(intent);
		} catch (Exception e) {
			
		}
	}

	private class EffectPos {
		int xPos;
		int yPos;
		int nWidth;
		int nHeight;
		float fToX;
		float fToY;

		public EffectPos(int x, int y, int w, int h, float fx, float fy) {
			xPos = x;
			yPos = y;
			nWidth = w;
			nHeight = h;
			fToX = fx;
			fToY = fy;
		}

		public void SetParams(int x, int y, int w, int h, float fx, float fy) {
			xPos = x;
			yPos = y;
			nWidth = w;
			nHeight = h;
			fToX = fx;
			fToY = fy;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
