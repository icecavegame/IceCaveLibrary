package com.tas.icecaveLibrary.mapLogic.collision;


import com.tas.icecaveLibrary.general.IFunction;
import com.tas.icecaveLibrary.utils.Point;

public class BaseCollisionInvoker<return_type> implements ICollisionInvoker<return_type> {
	
	IFunction<return_type> mFunction;
	
	public BaseCollisionInvoker(IFunction<return_type> function) {
		mFunction = function;
	}
	
	@Override
	public return_type onCollision(Point collisionPoint)
	{
		return mFunction.invoke(new Point(collisionPoint));
	}
}
