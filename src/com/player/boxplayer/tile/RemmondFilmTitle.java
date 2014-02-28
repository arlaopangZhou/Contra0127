package com.player.boxplayer.tile;

import java.util.ArrayList;
import java.util.List;

public class RemmondFilmTitle {
	private String title, subTitle, url;
	private String desc;
	private String type;
	private String activity;
	private String backgroupImageUrl;
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setImageUrl(String url) {
		this.url = url;
	}
	
	public String getImageUrl() {
		return url;
	}
	
	public void setBgImageUrl(String backgroupImageUrl) {
		this.backgroupImageUrl = backgroupImageUrl;
	}
	
	public String getBgImageUrl() {
		return backgroupImageUrl;
	}

	public void setDesc(String value) {
		// TODO Auto-generated method stub
		this.desc = value;
	}

	public String getDesc() {
		return this.desc;
	}
	
	public String getTarget() {
		return this.type;
	}
	
	public void setTarget(String target) {
		this.type = target;
	}
	
	public String getActivity() {
		return this.activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}

}
