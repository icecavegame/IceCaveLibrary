package com.tas.icecaveLibrary.mapLogic.tiles.validators;

import java.util.HashMap;

import com.tas.icecaveLibrary.mapLogic.tiles.BoulderTile;
import com.tas.icecaveLibrary.mapLogic.tiles.BreakableBoulderTile;
import com.tas.icecaveLibrary.utils.board.IBoardTile;

/**
 * Factory for tile validators.
 * @author Tom
 *
 */
public class TileValidatorFactory {

	private HashMap<Class<?>, ITileValidator> mValidators = 
			new HashMap<Class<?>, ITileValidator>();
	
	/**
	 * Create a new instance of the tile validator factory.
	 */
	public TileValidatorFactory(){
		mValidators.put(BoulderTile.class, new BoulderTileValidator());
		mValidators.put(BreakableBoulderTile.class, new BoulderTileValidator());
	}
	
	/**
	 * Validate a tile.
	 * @param toValidate - tile to validate class.
	 * @param xLocation - X argument of location for tile.
	 * @param yLocation - Y argument of location for tile.
	 * @param xPlayerLocation - X argument of location for player.
	 * @param yPlayerLocation - Y argument of location for player.
	 * @param board - The current stage used board.
	 * @return true if valid.
	 */
	public boolean validate(Class<?>  toValidate, 
							int 	  xLocation,
							int       yLocation,
							int		  xPlayerLocation,
							int		  	   yPlayerLocation,
							IBoardTile[][] board){
		// Check if there is a validator.
		if(!mValidators.containsKey(toValidate)){
			// Not an error.
			return true;
		}
				
		return mValidators.get(toValidate).isValid(xLocation, 
												   yLocation, 
												   xPlayerLocation, 
												   yPlayerLocation, 
												   board);
	}
}
