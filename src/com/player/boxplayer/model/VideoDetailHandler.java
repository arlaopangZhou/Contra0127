package com.player.boxplayer.model;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.Date;
//import java.util.GregorianCalendar;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import android.location.Location;
import android.util.Log;

public class VideoDetailHandler extends DefaultHandler{
	//xml解析用到的Tag
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
	
	private String kViewName = "View";
	private String kChildViewName = "ChildView";
	private String kRatesName = "rates";
	private String kAreaName = "area";
	private String kDesName = "des";
	private String kDirectorName = "director";
	private String kActorName = "actor";
	private String kTitleName = "title";
	private String kYearName = "year";
	private String kCatalogName = "catalog";
	private String kPicName = "pic";
	private String kTotalName = "total";
	private String kUpdateName = "update";
	private String kItemName = "item";
	private String kListName = "list";
	private String kOnPlayName = "onPlay";
	private String kUrlName = "url";

	private ArrayList<VideoDetailInfo> videoInfoList;
	private VideoDetailInfo videoInfo;
	private StringBuilder currentDataBuilder;
	private Boolean startViewFlag = false;
	private Boolean startDetailFlag = false;
	private Boolean startRatesFlag = false;
	private Boolean startSeriesFlag = false;
	private Boolean startSeriesListFlag = false;
	private Boolean startSeriesItemFlag = false;
	private Boolean startPlayUrlFlag = false;
	
	private String videoRate = "";

	public ArrayList<VideoDetailInfo> getVideoInfoList() {
		return this.videoInfoList;
	}
	
	public VideoDetailInfo getVideoDetailInfo() {
		return videoInfo;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();

		videoInfoList = new ArrayList<VideoDetailInfo>();
		currentDataBuilder = new StringBuilder();
	}
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		Log.v("Sax", "End");		
	}
	//解析每一个标签Tag
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
//		Log.v("Sax_StartElement", localName);
		if(localName.equalsIgnoreCase(kViewName))
		{
			videoInfo = new VideoDetailInfo();
			startViewFlag = true; 
		}
		else if ((localName.equalsIgnoreCase(kChildViewName))&&(startViewFlag == true)) {
			String relAttribute = attributes.getValue("type");
			if(relAttribute.equalsIgnoreCase("detail")) {
				startDetailFlag = true;
			}
			else if(relAttribute.equalsIgnoreCase("series")) {
				videoInfo.initSeriesList();
				startSeriesFlag = true;
			}
		}
		else if ((localName.equalsIgnoreCase(kRatesName))&&(startDetailFlag == true)) {
			videoInfo.initRatesList();
			startRatesFlag = true;
		}
		else if ((localName.equalsIgnoreCase(kListName))&&(startSeriesFlag == true)) {
			String relAttribute = attributes.getValue("title");
			videoInfo.initSeriesInfo(relAttribute);
			startSeriesListFlag = true;
		}
		else if ((localName.equalsIgnoreCase(kItemName))&&(startSeriesListFlag == true)) {
			startSeriesItemFlag = true;
		}
		else if ((localName.equalsIgnoreCase(kOnPlayName))&&(startSeriesItemFlag == true)) {
			startPlayUrlFlag = true;
		}
		else if ((localName.equalsIgnoreCase(kUrlName))&&(startPlayUrlFlag == true)) {
			videoRate = attributes.getValue("rate");
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		currentDataBuilder.append(ch, start, length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if(startViewFlag == true) {
			Pattern repString = Pattern.compile("\t|\r|\n");
			String currentData = currentDataBuilder.toString();
			Matcher matString = repString.matcher(currentData);
			currentData = matString.replaceAll("");
			
			if(startDetailFlag == true) {
				if (localName.equalsIgnoreCase(kAreaName)) {
					videoInfo.area = currentData;
				}
				else if (localName.equalsIgnoreCase(kDesName)) {
					videoInfo.des = currentData;
				}
				else if (localName.equalsIgnoreCase(kDirectorName)) {
					videoInfo.director = currentData;
				}
				else if (localName.equalsIgnoreCase(kActorName)) {
					videoInfo.actor = currentData;
				}
				else if (localName.equalsIgnoreCase(kTitleName)) {
					videoInfo.title = currentData;
				}
				else if (localName.equalsIgnoreCase(kYearName)) {
					videoInfo.year = currentData;
				}
				else if (localName.equalsIgnoreCase(kCatalogName)) {
					videoInfo.catalog = currentData;
				}
				else if (localName.equalsIgnoreCase(kPicName)) {
					videoInfo.pic = currentData;
				}
				else if (localName.equalsIgnoreCase(kTotalName)) {
					videoInfo.total = currentData;
				}
				else if (localName.equalsIgnoreCase(kUpdateName)) {
					videoInfo.update = currentData;
				}
				else if (startRatesFlag && localName.equalsIgnoreCase(kItemName)) {
					videoInfo.ratesList.add(currentData);
				}
				else if(localName.equalsIgnoreCase(kChildViewName)) {
					videoInfoList.add(videoInfo);
					//startViewFlag = false;
					//startDetailFlag = false;
					//startSeriesFlag = false;
					//startRatesFlag = false;
				}
				currentDataBuilder.setLength(0);
			}
			else if(startSeriesItemFlag == true) {
				if (localName.equalsIgnoreCase(kTitleName)) {
					//videoInfo.area = currentData;
					videoInfo.initSeriesItem(currentData);
				}
				else if (localName.equalsIgnoreCase(kUrlName)) {
					//videoInfo.area = currentData;
					//videoInfo.initSeriesItem(currentData);
					videoInfo.addPlayUrl(videoRate, currentData);
				}
				currentDataBuilder.setLength(0);
			}
			
			if(localName.equalsIgnoreCase(kChildViewName)) {
				//videoInfoList.add(videoInfo);
				//startViewFlag = false;
				startDetailFlag = false;
				startSeriesFlag = false;
				startRatesFlag = false;
				startSeriesListFlag = false;
				startSeriesItemFlag = false;
				startPlayUrlFlag = false;
			}
			else if(localName.equalsIgnoreCase(kItemName)) {
				startSeriesItemFlag = false;
			}
			else if(localName.equalsIgnoreCase(kOnPlayName)) {
				startPlayUrlFlag = false;
			}
		}
	}	
}
