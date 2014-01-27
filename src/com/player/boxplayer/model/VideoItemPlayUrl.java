package com.player.boxplayer.model;

import java.io.Serializable;

public class VideoItemPlayUrl implements Serializable {
	private static final long serialVersionUID = 6340622314169844478L;
	
	public String rate;
	public String url;

	@Override
	public String toString() {
		return "VideoList [title=" + rate + "url" + url + "]";
	}
}
