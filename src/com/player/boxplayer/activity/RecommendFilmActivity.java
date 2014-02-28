package com.player.boxplayer.activity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.player.boxplayer.R;
import com.player.boxplayer.model.VideoDetailHandler;
import com.player.boxplayer.model.VideoDetailInfo;
import com.player.boxplayer.tile.RemmondFilmTitle;
import com.player.boxplayer.util.ScaleBitmapTool;
import com.player.boxplayer.view.FilmAdapter;
import com.player.boxplayer.view.HoriScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendFilmActivity extends Activity implements
		OnFocusChangeListener{
	private HoriScrollView horiScroll;
	private ImageView bgImageview;
	private ImageView filmImageview;
	private String recommendPath;// 推荐影视页面的图片路径。
	private RemmondFilmTitle reTile;
	List<RemmondFilmTitle> reTileList;
	FilmAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_film);
		adapter = new FilmAdapter(this);
		parseData();
		initView();
		
		
	}

	/**
	 * 初始化每个图片的数据信息。
	 */
	private void initData(List<RemmondFilmTitle> reTileList) {
		// TODO Auto-generated method stub
		for (int i = 0; i < reTileList.size(); i++) {
			RemmondFilmTitle reTile = reTileList.get(i);
			adapter.addObject(reTile);
		}
		
		horiScroll.setAdapter(adapter);
//		Log.i("Contra", "onClick is select count"+horiScroll.getChildCount());
//		for (int i = 0; i < horiScroll.getChildCount(); i++) {
//			View v = horiScroll.getChildAt(i);
//			v.setClickable(true);
//			v.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Log.i("Contra", "onClick is select");
//				}
//			});
//		}
	}

	
	
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				break;
			case 0:
				reTileList = (List<RemmondFilmTitle>) msg.obj;
				initData(reTileList);
//				for (int i = 0; i < reTileList.size(); i++) {
//					View v = getImageView(reTileList.get(i));
//					Log.i("Contra", "reTile.getImageUrl() ===="
//							+ reTileList.get(i).getImageUrl());
//					Log.i("Contra", "reTile.getImageUrl() ===="
//							+ reTileList.get(i).getTitle());
//					horiScroll.addView(v);
//				}
				
				break;
			}
		}
	};

	/**
	 * 初始化数据，解析XML数据。
	 */
	private void parseData() {
		// TODO Auto-generated method stub
		reTile = new RemmondFilmTitle();
		Intent intent = getIntent();
		recommendPath = intent.getStringExtra("desc");
		new Thread() {
			public void run() {
				try {
					File configFile = new File(getExternalFilesDir(null),
							recommendPath);
					FileInputStream recommendStream = new FileInputStream(
							configFile);
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser parser = factory.newSAXParser();
					RecommendFilmHandler saxHandler = new RecommendFilmHandler();
					parser.parse(recommendStream, saxHandler);
					reTileList = saxHandler.getRemmondFilmTitle();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (reTileList != null) {
					handler.sendMessage(handler.obtainMessage(0, reTileList));
				}
			};
		}.start();
	}

	/**
	 * 获取数据后将每个图片以及名称添加到HorizontalScrollView视图中。
	 */
	private void initView() {
		// TODO Auto-generated method stub
		horiScroll = (HoriScrollView) findViewById(R.id.filmHoriScroll);
	}

	/**
	 * 获取每个推荐电影界面。
	 * @param reTile
	 * @return
	 */
	public View getImageView(RemmondFilmTitle reTile) {
		String ImagePath = getExternalFilesDir(null)+"/"+reTile.getImageUrl();
		String filmName = reTile.getTitle();
		Bitmap bitmap = decodeBitmapFromFile(ImagePath,200,300);
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.recemmend_item, null);
		Holder hold = new Holder();
		hold.img = (ImageView) layout.findViewById(R.id.image_film);
		hold.txt = (TextView) layout.findViewById(R.id.film_name);
		hold.img.setImageBitmap(bitmap);
		hold.txt.setText(filmName);
//		hold.img.setOnFocusChangeListener(this);
//		LinearLayout linearLayout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(300, 400));
		((LinearLayout) layout).setGravity(Gravity.CENTER);
//		ImageView imageView = new ImageView(this);
////		imageView.setImageBitmap(bitmap);
//		imageView.setLayoutParams(new LayoutParams(200, 300));
//		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//		imageView.setImageBitmap(bitmap);
//		linearLayout.addView(layout);
		return layout;
	}

	class Holder {
		private View v;
		private ImageView img;
		private TextView txt;
	}

//
//	class FilmAdapter extends BaseAdapter{
//		private Context context;
//		private List<RemmondFilmTitle> reTileList;
//		
//		public FilmAdapter(Context context,List<RemmondFilmTitle> reTileList) {
//			super();
//			// TODO Auto-generated constructor stub
//			this.context = context;
//			reTileList = new ArrayList<RemmondFilmTitle>();
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return reTileList.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return reTileList.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			
//			LayoutInflater inflater = LayoutInflater.from(context);
//			View layout = inflater.inflate(R.layout.recemmend_item, null);
//			Holder hold = new Holder();
//			hold.img = (ImageView) layout.findViewById(R.id.image_film);
//			hold.txt = (TextView) layout.findViewById(R.id.film_name);
//			hold.img.setImageBitmap(ScaleBitmapTool.getImageView(reTileList.get(position).getImageUrl()));
////			hold.txt.setText(filmName);
////			hold.img.setOnFocusChangeListener(this);
////			LinearLayout linearLayout = new LinearLayout(getApplicationContext());
//			layout.setLayoutParams(new LayoutParams(300, 400));
//			((LinearLayout) layout).setGravity(Gravity.CENTER);
//			return layout;
//		}
//		
//	}
	
	/**
	 * 根据路径创建bitmap图片，并且以指定的宽高返回。
	 * 
	 * @param absolutePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private Bitmap decodeBitmapFromFile(String absolutePath, int reqWidth,
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
	private int calculateInSampleSize(Options options, int reqWidth,
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

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if (hasFocus) {
			Log.i("Contra", "onFocusChange is select");
//			Toast.makeText(getApplicationContext(), "" + v.getId(),
//					Toast.LENGTH_SHORT).show();
		}
	}

}
