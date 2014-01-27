package com.player.boxplayer.model;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoSeriesListItem implements Serializable {
	private static final long serialVersionUID = 6340622314169844479L;
	
	public String title;
	public ArrayList<VideoItemPlayUrl> videoUrlList;

	private VideoItemPlayUrl playUrl = null;
	
	public void initUrlList(String titl) {
		title = titl;
		videoUrlList = new ArrayList<VideoItemPlayUrl>();
	}
	
	public void addPlayUrl(String rate, String url) {
		playUrl = new VideoItemPlayUrl();
		playUrl.rate = rate;
		playUrl.url = url;
		videoUrlList.add(playUrl);
	}
	
	public ArrayList<VideoItemPlayUrl> getPlayUrlList()
	{
		return this.videoUrlList;
	}
	
	public VideoItemPlayUrl getPlayUrlItem(int nIndex) {
		if(videoUrlList == null || nIndex >= videoUrlList.size())
			return null;
		
		return videoUrlList.get(nIndex);
	}

	@Override
	public String toString() {
		return "VideoList [title=" + title + "]";
	}

}
