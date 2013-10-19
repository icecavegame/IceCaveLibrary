package com.tas.icecaveLibrary.mapLogic.tiles;

import com.tas.icecaveLibrary.utils.Point;

@SuppressWarnings("serial")
public abstract class BaseTile implements ITile
{
	/**
	 * Location for the tile.
	 */
	Point mLocation;
	
	/**
	 * Get the tile location.
	 * 
	 * @return Location of the tile.
	 */
	@Override
	public Point getLocation(){
		return mLocation;
	}

	@Override
	public abstract ITile clone();
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof BaseTile){
			BaseTile other = (BaseTile)o;
			return mLocation.equals(other.mLocation);
		}
		return false;
	};	
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param location - Location of the tile.
	 */
	public BaseTile(Point location){
		mLocation = new Point(location);
	}
	
	
	/**
	 * Create a new instance of the BaseTile object.
	 * @param x - X location of the tile.
	 * @param y - Y location of the tile.
	 */
	public BaseTile(int x, int y){
		mLocation = new Point(x, y);
	}
}
