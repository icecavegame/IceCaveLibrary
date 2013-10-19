package com.tas.icecaveLibrary.utils.board;

import com.tas.icecaveLibrary.utils.Point;

/**
 * Interface representing a board.
 * @author Tom
 */
public abstract class BaseBoard
{
	/**
	 * Get the board tiles.
	 * @return The board tiles map.
	 */
	public abstract IBoardTile[][] getBoard();
	
	/**
	 * Get the number of rows on the board.
	 * @return number of rows on the board.
	 */
	public int getRowsNum(){
		return getBoard().length;
	}
	
	/**
	 * Get the number of columns on the board.
	 * @return number of columns on the board.
	 */
	public int getColumnNum(){
		return getBoard()[0].length;
	}
	
	/**
	 * Get a tile from the board.
	 * @param row - Row of the tile on the board.
	 * @param column - Column of the tile on the board.
	 * @return The requested tile by given position or null if dimensions
	 * 		   do not fit.
	 */
	public IBoardTile getTile(int row, int column){
		if(getRowsNum() <= row ||
		   getColumnNum()<= column)
		{
			return null;
		}
		
		return getBoard()[row][column];
	}
	
	/**
	 * Get a tile from the board.
	 * @param tileLocation - Location of the requested tile.
	 * @return The requested tile by given position or null if dimensions
	 * 		   do not fit.
	 */
	public IBoardTile getTile(Point tileLocation){
		return getTile(tileLocation.y, tileLocation.x);
	}
	
	/**
	 * Sets a tile on the board.
	 * @param row - Row of the tile on the board.
	 * @param column - Column of the tile on the board.
	 * @param tile - Tile to place.
	 * 
	 * @return true if successful.
	 */
	public abstract boolean setTile(int row, int column, IBoardTile tile);
	
	/**
	 * Sets a tile on the board.
	 * @param tileLocation - Location of the requested tile.
	 * @pararm tile - Tile to place.
	 * @return true if successful.
	 */
	public boolean setTile(Point tileLocation, IBoardTile tile){
		return setTile(tileLocation.y, tileLocation.x, tile);
	}
	
	/**
	 * Get the starting point of player.
	 * @return Point of start on the board for the player.
	 */
	public abstract Point getStartPoint();
}
