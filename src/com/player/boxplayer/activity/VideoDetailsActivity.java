package com.player.boxplayer.activity;

//import java.io.FileInputStream; 
//import java.io.FileOutputStream; 
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.ArrayList;
//import java.util.List;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
//import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
//import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
//import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.player.boxplayer.R;
import com.umeng.analytics.MobclickAgent;
//import com.player.boxplayer.effect.Reflect3DImage;
import com.player.boxplayer.model.VideoDetailInfo;
import com.player.boxplayer.model.VideoDetailHandler;
//import com.player.boxplayer.model.VideoSeriesList;
import com.player.boxplayer.model.VideoSeriesListItem;
import com.player.boxplayer.model.VideoItemPlayUrl;
import com.player.boxplayer.util.BitmapWorkerTask;
import com.player.boxplayer.util.BitmapWorkerTask.PostCallBack;
import com.player.boxplayer.view.SubPage;

public class VideoDetailsActivity extends BaseActivity implements OnKeyListener {
	private Button play, colection;
	private TextView videoName, point, editors, actors;
	private TextView introduce, area, episodes, year;
	private ImageView poster, sharpness, shadow;
	private VideoDetailInfo media;
	private String videoInfoFilePath;

	ArrayList<VideoSeriesListItem> videoPlayList = null; // 播放列表

	private final static int MSG_INIT = 11;
	private final static int MSG_ERROR = 12;
	
	private GridView layoutGridView;
	private GridView layoutListView;
	
	private List<String> listVideoDetail = null;
	GridAdapter mGridAdapter = null;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_ERROR:

				break;
			case MSG_INIT:
				media = (VideoDetailInfo) msg.obj;
				showDetailInfo();
				break;
			}
		}
	};
	 
	private void showDetailInfo() {
		/**
		 * 创建 源的 布局 创建 推荐布局 创建 3D图 动画 初始化 填充界面text
		 */

		if (media == null) {
			return;
		}

		colection
				.setBackgroundResource(R.drawable.video_details_colection_selector);
		colection.setText(getResources().getString(R.string.details_colection));

		updateView();

		videoName.setText(media.title);
		editors.setText(getResources().getString(R.string.details_director)
				+ media.director);
		actors.setText(getResources().getString(R.string.details_actors)
				+ media.actor);
		introduce.setText(getResources().getString(R.string.details_des)
				+ media.des);
		area.setText(getResources().getString(R.string.details_area)
				+ media.area);
		episodes.setText(getResources().getString(R.string.details_episodes)
				+ media.total);
		year.setText(getResources().getString(R.string.details_year)
				+ media.year);

		// 获取播放列表
		videoPlayList = media.getSeriesItemList(0);
		setSharpNessLog(media.getRatesList(0));
		progressDismiss();
		play.requestFocus();
		
		if(videoPlayList.size() > 1) {
			if(listVideoDetail != null)
				listVideoDetail.clear();
			
			if(media.getItemListType(0) == 0) {
				listVideoDetail = new ArrayList<String>(); 
				createGridWiewLayout();
				
				mGridAdapter = new GridAdapter(this);
				mGridAdapter.setList(listVideoDetail);
				layoutGridView.setAdapter(mGridAdapter);
				layoutGridView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				layoutGridView.setSelector(android.R.color.transparent);
				
			}
			else if(media.getItemListType(0) == 1) {
				listVideoDetail = new ArrayList<String>();
				createListViewLayout();
				
				mGridAdapter = new GridAdapter(this);
				mGridAdapter.setList(listVideoDetail);
				
				layoutListView.setAdapter(mGridAdapter);
				layoutListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
				layoutListView.setSelector(android.R.color.transparent);
			}
		}
	}

	private void updateView() {
		create3DPost(poster, media.pic);
		play.setText(getResources().getString(R.string.details_play));
		play.setVisibility(View.VISIBLE);
		colection.setVisibility(View.VISIBLE);
	}
	
	private void createListViewLayout() {
		if (layoutListView.getVisibility() != View.VISIBLE) {
			layoutListView.setVisibility(View.VISIBLE);
			layoutGridView.setVisibility(View.GONE);
		}
		
		if(videoPlayList == null)
			return;
		
		int nSize = videoPlayList.size();
		for(int i = 0; i < nSize; i++) {
			VideoSeriesListItem seriesListItem = videoPlayList.get(i);
			
			VideoItemPlayUrl playUrlItem = seriesListItem.getPlayUrlItem(0);
			if(playUrlItem != null) {
				listVideoDetail.add(seriesListItem.title);
			}
		}
	}
	
	private void createGridWiewLayout() {
		if (layoutGridView.getVisibility() != View.VISIBLE) {
			layoutGridView.setVisibility(View.VISIBLE);
			layoutListView.setVisibility(View.GONE);
		}
		
		if(videoPlayList == null)
			return;
		
		int nSize = videoPlayList.size();
		for(int i = 0; i < nSize; i++) {
			VideoSeriesListItem seriesListItem = videoPlayList.get(i);
			
			VideoItemPlayUrl playUrlItem = seriesListItem.getPlayUrlItem(0);
			if(playUrlItem != null) {
				listVideoDetail.add(seriesListItem.title);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_details);
		Process.setThreadPriority(Process.myTid(), -3);// UI线程-4，比UI低一级

		initView();
		initData();
		initListener();
		progressShow();
	}

	protected void create3DPost(final ImageView imageView, String imgUrl) {
		BitmapWorkerTask task = new BitmapWorkerTask(this, null, false, false);
		task.setCallback(new PostCallBack() {

			@Override
			public void post(Bitmap bitmap) {
				if (bitmap != null) {
					// imageView.setImageBitmap(Reflect3DImage.skewImage(bitmap,
					// 260, 366, 50));
					imageView.setImageBitmap(bitmap);
				}
				Animation anim = AnimationUtils.loadAnimation(
						VideoDetailsActivity.this, R.anim.triangleeffect);
				shadow.startAnimation(anim);
				shadow.setVisibility(View.VISIBLE);

			}
		});
		task.execute(imgUrl);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initView() {
		// 侧阴影
		shadow = (ImageView) findViewById(R.id.details_poster_shadow);
		shadow.setVisibility(View.GONE);
		// 海报
		poster = (ImageView) findViewById(R.id.details_poster);
		poster.setImageBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.poster260x366));
		// poster.setImageBitmap(Reflect3DImage.skewImage(BitmapFactory
		// .decodeResource(getResources(), R.drawable.hao260x366), 260,
		// 366, 50));
		// 清晰度
		sharpness = (ImageView) findViewById(R.id.details_sharpness);

		// 影视名称
		videoName = (TextView) findViewById(R.id.details_name);
		// 评分
		point = (TextView) findViewById(R.id.details_point);
		// 导演
		editors = (TextView) findViewById(R.id.details_director);
		// 所属地区
		area = (TextView) findViewById(R.id.details_area);
		// 演员
		actors = (TextView) findViewById(R.id.details_actors);
		// 集数
		episodes = (TextView) findViewById(R.id.details_episodes);
		// 年份
		year = (TextView) findViewById(R.id.details_year);
		// 介绍
		introduce = (TextView) findViewById(R.id.details_video_introduce);
		// 播放
		play = (Button) findViewById(R.id.details_play);
		play.setVisibility(View.GONE);
		// 收藏
		colection = (Button) findViewById(R.id.details_colection);
		colection.setVisibility(View.GONE);
		
		layoutGridView = (GridView) findViewById(R.id.details_gridview);
		
		layoutListView = (GridView) findViewById(R.id.details_listview);
	}

	private void initData() {
		Intent intent = getIntent();
		videoInfoFilePath = intent.getStringExtra("desc");
		new Thread() {
			public void run() {
				Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);
				VideoDetailInfo info = null;
				try {
					File configFile = new File(getExternalFilesDir(null),
							videoInfoFilePath);
					FileInputStream videoStream = new FileInputStream(configFile);
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser parser = factory.newSAXParser();
					VideoDetailHandler saxHandler = new VideoDetailHandler();
					parser.parse(videoStream, saxHandler);
					info = saxHandler.getVideoDetailInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (info != null) {
					handler.sendMessage(handler.obtainMessage(MSG_INIT, info));
				} else {
					handler.sendEmptyMessage(MSG_ERROR);
				}
			};
		}.start();
	}

	private void initListener() {
		/* 播放按键的 监听 跳转 */
		play.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (media != null) {
					Intent mIntent = new Intent();
					mIntent.setClassName("com.hisilicon.android.videoplayer","com.hisilicon.android.videoplayer.activity.VideoActivity");
					mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					VideoSeriesListItem item = media.getSeriesItemList(0).get(0);
					mIntent.setData(Uri.parse(item.getPlayUrlList().get(0).url));
					startActivity(mIntent);
				}
			}
		});
		play.setOnKeyListener(this);

		/* 收藏 按键 监听 */
		colection.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		colection.setOnKeyListener(this);
		
		
		layoutGridView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.i("layoutGridView", "setOnItemSelectedListener");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		layoutGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				VideoSeriesListItem seriesListItem = videoPlayList.get(position);
				VideoItemPlayUrl playUrlItem = seriesListItem.getPlayUrlItem(0);
				Log.i(seriesListItem.title, playUrlItem.url);
				
				Intent intent = new Intent(VideoDetailsActivity.this, SubPage.class);	
				intent.putExtra("subTitle", seriesListItem.title);
				intent.putExtra("intent", playUrlItem.url);			
				startActivity(intent);
			}
		});

		layoutGridView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= totalItemCount - visibleItemCount) {
					Log.i("layoutGridView", "setOnScrollListener");
				}
			}
		});
		
		
		layoutListView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.i("layoutListView", "setOnItemSelectedListener");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		layoutListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				VideoSeriesListItem seriesListItem = videoPlayList.get(position);
				VideoItemPlayUrl playUrlItem = seriesListItem.getPlayUrlItem(0);
				Log.i(seriesListItem.title, playUrlItem.url);
				
				Intent intent = new Intent(VideoDetailsActivity.this, SubPage.class);	
				intent.putExtra("subTitle", seriesListItem.title);
				intent.putExtra("intent", playUrlItem.url);			
				startActivity(intent);
			}
		});

		layoutListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= totalItemCount - visibleItemCount) {
					Log.i("layoutListView", "setOnScrollListener");
				}
			}
		});
		
	}

	/**
	 * 清晰度标记
	 * 
	 * @param sharp
	 */
	private void setSharpNessLog(String sharp) {
		if (sharp == null || sharp.equals("")) {
			return;
		}
		else if (sharp.contains("全高清")) {
			sharpness.setImageResource(R.drawable.video_details_fhd);
		}
		else if (sharp.contains("超清") || sharp.contains("SD")) {
			sharpness.setImageResource(R.drawable.video_details_superhd);
		}
		else if (sharp.contains("高清") || sharp.contains("HD")) {
			sharpness.setImageResource(R.drawable.video_details_hd);
		}
		else if (sharp.contains("DVD") || sharp.contains("流畅") || sharp.contains("标清")) {
			sharpness.setImageResource(R.drawable.video_details_dvd);
		}
		else {
			point.setText(sharp);
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				this.finish();
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
			return super.onKeyDown(keyCode, event);
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	public class GridAdapter extends BaseAdapter {

		private class GridHolder {
			TextView strItemText;
		}

		private Context context;

		private List<String> list;
		private LayoutInflater mInflater;

		public GridAdapter(Context c) {
			super();
			this.context = c;
		}

		public void setList(List<String> list) {
			this.list = list;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int index) {

			return list.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			GridHolder holder;
			if (convertView == null) {   
				convertView = mInflater.inflate(R.layout.video_detail_listitem, null);   
				holder = new GridHolder();
				holder.strItemText = (TextView)convertView.findViewById(R.id.ItemText);
				convertView.setTag(holder);   

			}else{
				 holder = (GridHolder) convertView.getTag();   

			}
			String text = list.get(index);
			if (text != null) {   
				holder.strItemText.setText(text);
			}
			
			return convertView;
		}
	}
}