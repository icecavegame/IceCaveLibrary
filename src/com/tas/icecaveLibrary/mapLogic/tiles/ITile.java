package com.tas.icecaveLibrary.mapLogic.tiles;

import java.io.Serializable;

import com.tas.icecaveLibrary.mapLogic.collision.ICollisionable;
import com.tas.icecaveLibrary.utils.board.IBoardTile;


/**
 * Basic tile interface.
 * @author Tom
 *
 */
public interface ITile extends IBoardTile, ICollisionable, Serializable
{

}
