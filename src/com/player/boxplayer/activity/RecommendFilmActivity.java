package com.player.boxplayer.activity;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.player.boxplayer.R;
import com.player.boxplayer.tile.RemmondFilmTitle;
import com.player.boxplayer.util.RecommendFilmHandler;
import com.player.boxplayer.util.ScaleBitmapTool;
import com.player.boxplayer.view.FilmAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;

public class RecommendFilmActivity extends Activity {
	private Gallery gallryView;
	private String recommendPath;// 推荐影视页面的图片路径。
	private RemmondFilmTitle reTile;
	private List<RemmondFilmTitle> reTileList;
	private FilmAdapter adapter;
	private String backGroup;//推荐影视的背景图片。
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_film);
		parseData();
		initView();
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
				break;
			}
		}
	};
	
	/**
	 * 初始化每个图片的数据信息。
	 */
	private void initData(List<RemmondFilmTitle> reTileList) {
		// TODO Auto-generated method stub
		String backImagePath = getExternalFilesDir(null)+"/"+backGroup;//组装推荐影视界面的背景图片的路径。
		Bitmap bitmap = ScaleBitmapTool.getImageView(backImagePath);
		BitmapDrawable bitmap2 = new BitmapDrawable(bitmap);
		getWindow().setBackgroundDrawable(bitmap2);
		adapter = new FilmAdapter(this, reTileList);
		gallryView.setAdapter(adapter);
		
		gallryView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				adapter.setSelect(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		gallryView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				RemmondFilmTitle reTile = (RemmondFilmTitle) adapter
						.getItem(position);
				try {
					Intent intent = new Intent();
					intent.setClassName(reTile.getTarget(),
							reTile.getActivity());
					intent.putExtra("desc", reTile.getDesc());
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

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
					backGroup = saxHandler.getBackGroup();
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
		gallryView = (Gallery) findViewById(R.id.galleryView);
	}
}
