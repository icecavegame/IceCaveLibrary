package com.tas.icecaveLibrary.utils.bundle;

import com.tas.icecaveLibrary.general.EDifficulty;
import com.tas.icecaveLibrary.general.EDirection;
import com.tas.icecaveLibrary.utils.Point;

/**
 * Base implementation of the IBundleMetaData.
 * @author Tom
 *
 */
public class BaseBundleMetaData implements IBundleMetaData
{
	/**
	 * Width of the board in tiles.
	 */
	protected int mBoardWidth;
	
	/**
	 * Height of the board in tiles.
	 */
	protected int mBoardHeight;
	
	/**
	 * Difficulty of the map.
	 */
	protected EDifficulty mDifficulty;
	
	/**
	 * The width of the board wall in tiles.
	 */
	protected int mWallWidth;
	
	/**
	 * The starting player on the board.
	 */
	protected Point mPlayerStart;
	
	/**
	 * Number of boulders in the map.
	 */
	private int mBouldersNum;
	
	/**
	 * Version of the map.
	 */
	protected String mVersion;
	
	/**
	 * The starting move of the player on the map.
	 */
	protected EDirection mStartingMove;
	
	/**
	 * Create a new instance of the BaseBunleMetaData object.
	 * @param playerStart - Starting location of the player.
	 * @param difficulty - Difficulty of the map.
	 * @param firstMove - The first move to take on the board.
	 * @param tilesHeight - Number of tiles in height 
	 * 					   (width will be calculated with resolution).
	 * @param tilesWidth - Number of tiles in width.
	 * @param boulderNum - number of boulders.
	 * @param version - Version of the map.
	 * @param wallWidth - Width of the wall in tiles.
	 */
	public BaseBundleMetaData(Point playerStart,
	                          EDifficulty difficulty,
	                          EDirection firstMove,
	                          int tilesHeight,
	                          int tilesWidth,
	                          int boulderNum,
	                          String version,
	                          int wallWidth)
	{
		mWallWidth = wallWidth;
		mBoardHeight = tilesHeight;
		mBoardWidth = tilesWidth;
		mBouldersNum = boulderNum;
		mVersion = new String(version);
		mStartingMove = firstMove;
		mDifficulty = difficulty;
		mPlayerStart = new Point(playerStart);
	}
	
	@Override
	public int getBoulderNum()
	{
		return mBouldersNum;
	}

	@Override
	public int getBoardWidth()
	{
		return mBoardWidth;
	}

	@Override
	public int getBoardHeight()
	{
		return mBoardHeight;
	}

	@Override
	public int getWallWidth()
	{
		return mWallWidth;
	}

	@Override
	public String getVersion()
	{
		return mVersion;
	}

	@Override
	public EDifficulty getDifficulty()
	{
		return mDifficulty;
	}

	@Override
	public EDirection getFirstMove()
	{
		return mStartingMove;
	}

	@Override
	public Point getPlayerStart()
	{
		return mPlayerStart;
	}
}
