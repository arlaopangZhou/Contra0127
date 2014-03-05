package com.player.boxplayer.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;
import com.player.boxplayer.tile.RemmondFilmTitle;

public class RecommendFilmHandler extends DefaultHandler {

	private final String BACKGROUP = "backgroup";
	// private final String FILMTV = "filmtv";
	private final String TILE = "tile";
	private final String IMG = "image";
	private final String NAME = "name";
	private final String DESC = "desc";
	private final String TARGET = "target";
	private final String ACTIVITY = "activity";
	private RemmondFilmTitle reTile;
	private String backGrop;
	
	
	private List<RemmondFilmTitle> reTileList;

	public String getBackGroup(){
		return backGrop;
	}
	
	public List<RemmondFilmTitle> getRemmondFilmTitle() {
		return reTileList;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		reTileList = new ArrayList<RemmondFilmTitle>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (localName.equalsIgnoreCase(TILE)) {
			reTile = new RemmondFilmTitle();
			reTile.setTitle(attributes.getValue(NAME));
			reTile.setImageUrl(attributes.getValue(IMG));
			reTile.setDesc(attributes.getValue(DESC));
			reTile.setTarget(attributes.getValue(TARGET));
			reTile.setActivity(attributes.getValue(ACTIVITY));
			reTileList.add(reTile);
		} else if (localName.equalsIgnoreCase(BACKGROUP)) {
			backGrop = attributes.getValue(IMG);
//			Log.i("Contra", "BACKGROUP === "+attributes.getValue(IMG));
			reTile.setBgImageUrl(attributes.getValue(IMG));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		// if(localName.equalsIgnoreCase("BACKGROUP")){
		// reTileList.add(reTile);
		// }
	}
}
