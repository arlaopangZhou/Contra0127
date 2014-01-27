package com.player.boxplayer.weather;

import java.io.StringReader;
import java.net.URLEncoder;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.player.boxplayer.util.HttpUtils;

import android.content.Context;
//import android.util.Log;

public class WeatherBiz {

	public static String getCityCode(Context context) {
		String str = HttpUtils
				.getContent("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=");
		if (str != null) {
			try {
				JSONObject localJSONObject = new JSONObject(str);
				//String country = localJSONObject.getString("country");
				//String province = localJSONObject.getString("province");
				String city = localJSONObject.getString("city");
				//String district = localJSONObject.getString("district");

				return city;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CityWeatherInfoBean getWeatherFromHttp(String paramString) {
		String str = null;
		CityWeatherInfoBean localCityWeatherInfoBean = new CityWeatherInfoBean();
		
		try {
			String url = "http://php.weather.sina.com.cn/xml.php?city="
					+ URLEncoder.encode(paramString, "GB2312")
					+ "&password=DJOYnieT8234jlsK&day=0";
			str = HttpUtils.getContent(url);

			if (str != null) {
				XPathFactory factory = XPathFactory.newInstance();
				XPath xPath = factory.newXPath();

				InputSource is = new InputSource(new StringReader(str));
				Node weather = (Node) xPath.evaluate("Profiles/Weather", is,
						XPathConstants.NODE);
				NodeList info = weather.getChildNodes();
				for (int i = 0; i < info.getLength(); i++) {
					Node node = info.item(i);
					//Log.e("test", node.getNodeName() + ", " + node.getTextContent());
					if (node.getNodeName().equalsIgnoreCase("temperature1")) {
						localCityWeatherInfoBean
								.setfTemp(node.getTextContent());
					} else if (node.getNodeName().equalsIgnoreCase("temperature2")) {
						localCityWeatherInfoBean
								.settTemp(node.getTextContent());
					} else if (node.getNodeName().equalsIgnoreCase("city")) {
						localCityWeatherInfoBean.setCityName(node
								.getTextContent());
					} else if (node.getNodeName().equalsIgnoreCase("status1")) {
						localCityWeatherInfoBean.setWeatherInfo(node
								.getTextContent());
					} else if (node.getNodeName().equalsIgnoreCase("status2")) {
						String storedWeather = localCityWeatherInfoBean.getWeatherInfo();
						if (!storedWeather.equalsIgnoreCase(node.getTextContent())) {
							localCityWeatherInfoBean.setWeatherInfo(storedWeather + "ת" + node.getTextContent());
						}
					}
				}
				//Log.e("test", localCityWeatherInfoBean.toString());
			}

		} catch (Exception e) {
			
		}

		return localCityWeatherInfoBean;
	}
}
