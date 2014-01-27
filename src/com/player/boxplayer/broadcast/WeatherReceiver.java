package com.player.boxplayer.broadcast;

import com.player.boxplayer.util.HttpWorkTask;
import com.player.boxplayer.weather.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WeatherReceiver extends BroadcastReceiver {

	public static final String RESPONSE_WEATHER = "com.player.boxplayer.weatherservice.responseweather";

	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(RESPONSE_WEATHER)
				&& (context instanceof WeatherUpdateListener)) {
			final WeatherUpdateListener localWeatherUpdateListener = (WeatherUpdateListener) context;
			HttpWorkTask<CityWeatherInfoBean> httpTask = new HttpWorkTask<CityWeatherInfoBean>(
					new HttpWorkTask.ParseCallBack<CityWeatherInfoBean>() {

						@Override
						public CityWeatherInfoBean onParse() {
							// TODO Auto-generated method stub
							return WeatherBiz.getWeatherFromHttp(WeatherBiz
									.getCityCode(context));
						}

					}, new HttpWorkTask.PostCallBack<CityWeatherInfoBean>() {

						@Override
						public void onPost(
								CityWeatherInfoBean paramAnonymousCityWeatherInfoBean) {
							// TODO Auto-generated method stub
							localWeatherUpdateListener
									.updateWeather(paramAnonymousCityWeatherInfoBean);
						}

					});
			httpTask.execute();
		}
	}

	public static abstract interface WeatherUpdateListener {
		public abstract void updateWeather(
				CityWeatherInfoBean paramCityWeatherInfoBean);
	}
}
