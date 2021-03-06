package com.tas.icecaveLibrary.mapLogic.tiles.validators;

import com.tas.icecaveLibrary.utils.board.IBoardTile;

/**
 * Validator for a tile.
 * @author Tom
 *
 */
public interface ITileValidator {
	
	/**
	 * Validate location to place tile on.
	 * @param xLocation - X argument of location for tile.
	 * @param yLocation - Y argument of location for tile.
	 * @param xPlayerLocation - X argument of location for player.
	 * @param yPlayerLocation - Y argument of location for player.
	 * @param board - The current stage used board.
	 * @return true if valid.
	 */
	public boolean isValid(int xLocation, 
						   int yLocation, 
						   int xPlayerLocation, 
						   int yPlayerLocation, 
						   IBoardTile[][] board);
}
