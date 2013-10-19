package com.tas.icecaveLibrary.mapLogic.tiles;

import com.tas.icecaveLibrary.utils.Point;

/**
 * A breakable boulder, on hit is removed.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class BreakableBoulderTile extends BaseTile
{
	/**
	 * Create a new instance of the Breakable boulder object.
	 * @param x
	 * @param y
	 */
	public BreakableBoulderTile(int x, int y)
	{
		super(x, y);
	}
	
	/**
	 * Create a new instance of the Breakable boulder object.
	 * @param location
	 */
	public BreakableBoulderTile(Point location)
	{
		super(location);
	}

	@Override
	public ITile clone()
	{
		return new BreakableBoulderTile(mLocation);
	}

}
