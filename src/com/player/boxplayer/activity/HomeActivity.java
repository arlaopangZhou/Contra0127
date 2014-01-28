package com.player.boxplayer.activity;
/**
 * 主界面
 * @author richardzhou
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.player.boxplayer.R;
import com.player.boxplayer.broadcast.WeatherReceiver;
import com.player.boxplayer.broadcast.WeatherReceiver.WeatherUpdateListener;
import com.player.boxplayer.tile.Tile;
import com.player.boxplayer.tile.TileGroup;
import com.player.boxplayer.util.FileManager;
import com.player.boxplayer.util.SaxFeedParser;
import com.player.boxplayer.view.PageViewLayout;
import com.player.boxplayer.weather.CityWeatherInfoBean;

public class HomeActivity extends Activity implements WeatherUpdateListener {
	private TimeTickReciver timeTickReceiver;
	private NetStatReceiver netStatReceiver;
	private ViewPager centerPager;
	private RadioGroup titleGroup;
	private Button search;
	
	/**
	 * 此集合是添加到每个ViewPage中的视图
	 */
	private ArrayList<View> pages = new ArrayList<View>();
	private TextView systemTime;
	private TextView weatherCity;
	private ImageView weatherLog2;
	private ImageView topNetType;
	private TextView weatherInfo;

	int nHotViewCount = 0;
	private PageViewLayout[] hotView = new PageViewLayout[8];
	private int[] buttonID = new int[8];
	private WeatherReceiver weatherReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_playerbox);
		initViews();
		initListener();
		registerReceiver();
		sendBroadcast(new Intent(WeatherReceiver.RESPONSE_WEATHER));
	}
	
	/**
	 * 加载配置xml文件。
	 */
	private void loadConfigs() {
		File configFile = new File(getExternalFilesDir(null), FileManager.FILENAME);
		if (configFile != null) {
			try {
				FileInputStream stream = new FileInputStream(configFile); 
				final SaxFeedParser parser = new SaxFeedParser(stream);
				final Context ctx = this;
				List<TileGroup> list = parser.parse();
				
				search.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							Tile searchTile = parser.getSearchTile();
							Intent intent = new Intent();
							intent.setClassName(searchTile.getTarget(), searchTile.getActivity()); 
							ctx.startActivity(intent);
						} catch (Exception e) {
							
						}
					}
				});

				// Log.i("xml test", "has tile groups " + list.size());
				for (int i = 0; i < list.size(); i++) {
					TileGroup tg = list.get(i);
					initView(i, tg.getTitle(), tg);
				}

				nHotViewCount = list.size();

				for (int k = 7; k >= list.size(); k--) {
					titleGroup.removeViewAt(k);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化标题UI，ViewPage初始化,首页的UI的初始化
	 */
	private void initViews() {
		titleGroup = (RadioGroup) findViewById(R.id.title_group);
		buttonID[0] = R.id.title_menu0;
		buttonID[1] = R.id.title_menu1;
		buttonID[2] = R.id.title_menu2;
		buttonID[3] = R.id.title_menu3;
		buttonID[4] = R.id.title_menu4;
		buttonID[5] = R.id.title_menu5;
		buttonID[6] = R.id.title_menu6;
		buttonID[7] = R.id.title_menu7;
		
		centerPager = (ViewPager) findViewById(R.id.main_layout_pager);
		centerPager.setAdapter(new AllPagesAdapter(pages));
		centerPager.setCurrentItem(0);
		
		search = (Button) findViewById(R.id.main_search);
		

		systemTime = (TextView) findViewById(R.id.top_system_time);
		systemTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(
				new Date()));
		
		weatherCity = (TextView) findViewById(R.id.top_weather_city);
		//weatherLog2 = (ImageView) findViewById(R.id.top_weather_log2);
		weatherInfo = (TextView) findViewById(R.id.top_weather_info);
		
		topNetType = (ImageView) findViewById(R.id.top_net_type);
		topNetType.setImageResource(R.drawable.et_connect_normal);

		if (!Environment.getExternalStorageState().startsWith(
				Environment.MEDIA_MOUNTED)) {
			return;
		}

		loadConfigs();
	}

	/**
	 * 初始化每个RadioButton下的视图UI
	 * @param nView 坐标
	 * @param strTitle 每个RadioButton的title标题名
	 * @param tilGroup TileGroup对象
	 */
	private void initView(int nView, String strTitle, TileGroup tilGroup) {
		if (nView > 7)
			return;

		RadioButton button = (RadioButton) findViewById(buttonID[nView]);
		button.setText(strTitle);
		Log.i("Contra", "HomeActivity strTitle====="+strTitle);

		hotView[nView] = new PageViewLayout(this, tilGroup,
				getExternalFilesDir(null).toString());
		hotView[nView].requestFocus();
		hotView[nView].initView();

		pages.add(hotView[nView]);
	}

	/**
	 * 调用更新方法初始化数据
	 */
	public void initData() {
		for (int i = 0; i < nHotViewCount; i++) {
			hotView[i].updateData();
		}
	}

	private void registerReceiver() {
		weatherReceiver = new WeatherReceiver();
	    registerReceiver(weatherReceiver, new IntentFilter(WeatherReceiver.RESPONSE_WEATHER));		
		this.timeTickReceiver = new TimeTickReciver();
		IntentFilter localIntentFilter = new IntentFilter();
		localIntentFilter.addAction("android.intent.action.TIME_SET");
		localIntentFilter.addAction("android.intent.action.TIME_TICK");
		registerReceiver(this.timeTickReceiver, localIntentFilter);
		this.netStatReceiver = new NetStatReceiver();
		registerReceiver(this.netStatReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(timeTickReceiver);
		unregisterReceiver(netStatReceiver);

		for (int i = 0; i < nHotViewCount; i++) {
			hotView[i].destroy();
		}
		super.onDestroy();
		Process.killProcess(Process.myPid());
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == 186) {
		}
		return super.onKeyDown(keyCode, event);
	}

	private class TimeTickReciver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("android.intent.action.TIME_TICK")) {
				systemTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT)
						.format(new Date()));
			}
		}
	}

	private class NetStatReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				// Network status
				boolean noConnectivity = intent.getBooleanExtra(
						"noConnectiviy", false);
				if (noConnectivity) {
					topNetType.setImageResource(R.drawable.et_disconnected);
				} else {
					topNetType.setImageResource(R.drawable.et_connect_normal);
				}

				ConnectivityManager localConnectivityManager = (ConnectivityManager) context
						.getSystemService("connectivity");
				NetworkInfo localNetworkInfo = localConnectivityManager
						.getActiveNetworkInfo();
				if (localNetworkInfo != null) {
					if (localNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
						topNetType.setImageResource(R.drawable.wifi0401);
					}
					
					sendBroadcast(new Intent(WeatherReceiver.RESPONSE_WEATHER));
				} else {
					topNetType.setImageResource(R.drawable.et_disconnected);
				}

			}
		}
	}

	public void initListener() {
		OnFocusChangeListener focusListener = new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					int position = (Integer) v.getTag();
					centerPager.setCurrentItem(position, true);
				}
			}
		};
		for (int i = 0; i < titleGroup.getChildCount(); i++) {
			View view = titleGroup.getChildAt(i);
			view.setTag(i);
			view.setOnFocusChangeListener(focusListener);
		}

		centerPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				if (arg0 < titleGroup.getChildCount()) {
					((RadioButton) titleGroup.getChildAt(arg0))
							.setChecked(true);

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		for (int i = 0; i < nHotViewCount; i++) {
			hotView[i].initListener();
		}
	}
	
	protected int num = 0;
	protected void setFlickerAnimation(final TextView view, final String str1, final String str2) {
		view.setText(str1);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(4000L);
		aa.setRepeatCount(-1);
		aa.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				num = 1 + num;
				if (num == 10) num = 0;
				if (num % 2 == 0) {
					view.setText(str1);
				} else {
					view.setText(str2);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});
		view.startAnimation(aa);
	}

	@Override
	public void updateWeather(CityWeatherInfoBean city) {		
		if (weatherCity != null)
			weatherCity.setText(city.getCityName());
		if (weatherInfo != null)
			weatherInfo.setText(city.getWeatherInfo());

		if (weatherLog2 != null)
			weatherLog2.setVisibility(View.VISIBLE);
		//weatherLog2.setImageResource(R.drawable.iweather_cloudy);
		
		String temp;
		if (city.gettTemp() == null) {
			temp = "无天气信息";
		}
		else {
			temp = city.gettTemp() + "~" + city.getfTemp() + "℃";
		}
		setFlickerAnimation(weatherInfo, city.getWeatherInfo(), temp);
	}
}
