package com.tas.icecaveLibrary.general;

import com.tas.icecaveLibrary.utils.Point;

public interface IFunction<return_type> {
	/**
	 * Invoke the collision function.
	 * @param collisionPoint - Point of the tile the player collisioned with.
	 * @return result.
	 */
	public return_type invoke(Point collisionPoint);
}
