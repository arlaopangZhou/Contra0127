package com.player.boxplayer.model;

import java.io.Serializable;
import java.util.ArrayList;


public class VideoSeriesList implements Serializable {
	private static final long serialVersionUID = 6340622314169844480L;
	
	public String title;
	public int listType;	// 0: GridView; 1: ListView; 2: No list
	public ArrayList<VideoSeriesListItem> videoItemList;

	private VideoSeriesListItem seriesItem = null;
	
	//public void initSeriesList() {
	//	videoItemList = new ArrayList<VideoSeriesListItem>();
	//}
	
	public void initVideoSeriesListItemList(String titl) {
		//seriesItem = new VideoSeriesListItem();
		title = titl;
		videoItemList = new ArrayList<VideoSeriesListItem>();
	}
	
	public void initVideoSeriesItemInfo(String titl) {
		seriesItem = new VideoSeriesListItem();
		seriesItem.initUrlList(titl);
		videoItemList.add(seriesItem);
	}
	
	public void addPlayUrl(String rate, String url) {
		seriesItem.addPlayUrl(rate, url);
	}
	
	public ArrayList<VideoSeriesListItem> getSeriesItemList()
	{
		return this.videoItemList;
	}

	@Override
	public String toString() {
		return "VideoList [title=" + title + "]";
	}

}
