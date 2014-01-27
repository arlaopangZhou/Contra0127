package com.player.boxplayer.tile;

import java.util.ArrayList;

public class TileGroup {
	private ArrayList<Tile> tiles;
	private int maxTiles = 9;
	private String title;
	
	public TileGroup() {
		tiles = new ArrayList<Tile>(maxTiles);
	}

	public void addTile(Tile tile) {
		// TODO Auto-generated method stub
		tiles.add(tile);
	}

	public Tile getTileAt(int i) {
		// TODO Auto-generated method stub
		return tiles.get(i);
	}
	
	public int getTileCount() {
		return tiles.size();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() { 
		return this.title;
	}
}


