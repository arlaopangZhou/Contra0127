package com.player.boxplayer.model;

import java.io.Serializable;
import java.util.ArrayList;

//import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 电视剧 电影 详情信息
 */

public class VideoDetailInfo implements Serializable {

	private static final long serialVersionUID = 6340622314169844477L;
//	public int id; // 电视 id
	public String area;
	public String des;
	public String director;
	public String actor;
	public String title;
	public String year;
	public String catalog;
	public String pic;
	public String total;
	public String update;
	public ArrayList<String> ratesList;
	public ArrayList<VideoSeriesList> videoSeriesList;
	
	private VideoSeriesList seriesInfo;
	
	public void initRatesList() {
		ratesList = new ArrayList<String>();
	}
	
	public void initSeriesList() {
		videoSeriesList = new ArrayList<VideoSeriesList>();
	}
	
	public void initSeriesInfo(String title) {
		seriesInfo = new VideoSeriesList();
		seriesInfo.initVideoSeriesListItemList(title);
		seriesInfo.listType = 0;
		videoSeriesList.add(seriesInfo);
	}
	
	public void initSeriesItem(String title) {
		if(title.length() > 4)
			seriesInfo.listType = 1;
		seriesInfo.initVideoSeriesItemInfo(title);
	}
	
	public void addPlayUrl(String rate, String url) {
		seriesInfo.addPlayUrl(rate, url);
	}
	
	public ArrayList<VideoSeriesList> getSeriesList()
	{
		return this.videoSeriesList;
	}
	
	public ArrayList<VideoSeriesListItem> getSeriesItemList(int index) {
		VideoSeriesList info = videoSeriesList.get(index);
		if(info != null) {
			return info.videoItemList;
		}
		
		return null;
	}
	
	public int getItemListType(int index) {
		VideoSeriesList info = videoSeriesList.get(index);
		if(info != null) {
			return info.listType;
		}
		
		return 2;
	}
	
	public String getRatesList(int index) {
		String rate = "";
		if(ratesList != null) {
			rate = ratesList.get(index);
		}
		
		return rate;
	}
	
	@Override
	public String toString() {
		return "VideoDetailInfo [area=" + area + ", des=" + des + ", director="
				+ director + ", actor=" + actor + ", title=" + title + ", year=" + year
				+ ", catalog=" + catalog + ", pic=" + pic + ", total=" + total
				+ ", update=" + update + "]";
	}

}
