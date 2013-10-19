package com.tas.icecaveLibrary.mapLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.tas.icecaveLibrary.general.EDifficulty;
import com.tas.icecaveLibrary.general.EDirection;
import com.tas.icecaveLibrary.general.GeneralServiceProvider;
import com.tas.icecaveLibrary.mapLogic.tiles.BoulderTile;
import com.tas.icecaveLibrary.mapLogic.tiles.BreakableBoulderTile;
import com.tas.icecaveLibrary.mapLogic.tiles.EmptyTile;
import com.tas.icecaveLibrary.mapLogic.tiles.FlagTile;
import com.tas.icecaveLibrary.mapLogic.tiles.IBlockingTile;
import com.tas.icecaveLibrary.mapLogic.tiles.ITile;
import com.tas.icecaveLibrary.mapLogic.tiles.WallTile;
import com.tas.icecaveLibrary.mapLogic.tiles.validators.TileValidatorFactory;
import com.tas.icecaveLibrary.utils.Point;
import com.tas.icecaveLibrary.utils.board.BaseBoard;
import com.tas.icecaveLibrary.utils.board.IBoardTile;

@SuppressWarnings("serial")
public class IceCaveBoard extends BaseBoard implements Serializable
{
	/**
	 * The actual board.
	 */
	private ITile[][] mBoard;
	
	/**
	 * Boulders the player have hit.
	 */
	private ArrayList<ITile> mBoulders;
	
	/**
	 * The player starting location.
	 */
	private Point mPlayerStart;
	
	/**
	 * The point of the flag.
	 */
	private Point mFlagPoint;
	
	/**
	 * The starting move for the player.
	 */
	private EDirection mStartingMove;

	/**
	 * Min moves to solve the stage.
	 */
	private int mMoves;

	private ArrayList<ITile>	mBreakableBoulder;
	
	/**
	 * Get the starting direction to move the player in.
	 * @return Starting direction to move the player in.
	 */
	public EDirection getStartingMove(){
		return mStartingMove;
	}
	
	@Override
	public Point getStartPoint(){
		return mPlayerStart;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new IceCaveBoard(this);
	};
	
	/**
	 * Get the point of the flag.
	 * @return The location of the flag on the board.
	 */
	public Point getFlagLocation(){
		return mFlagPoint;
	}	
	
	/**
	 * Creates an empty board, all tiles are walls.
	 * @param colsNumber - Number of rows in board.
	 * @param rowsNumber - Number of columns in board.
	 */
	private void createEmptyBoard(int colsNumber, int rowsNumber) {
		// Initializing walls
		for (int i = 0; i < colsNumber; i++)
		{
			for (int j = 0; j < rowsNumber; j++)
			{
				mBoard[i][j] = new WallTile(j,i);
			}
		}
	}
	
	/**
	 * Initialize the board.
	 * @param colsNumber - Number of rows in board.
	 * @param rowsNumber - Number of columns in board.
	 * @param wallWidth - Width of the wall in tiles.
	 */
	private void initializeBoard(int colsNumber, int rowsNumber, int wallWidth)
	{
		createEmptyBoard(colsNumber, rowsNumber);

		fillWithEmptyTles(colsNumber, rowsNumber, wallWidth);
	}

	/**
	 * Fill the initialized board with empty tiles.
	 * 
	 * @param colsNumber - Number of rows in board.
	 * @param rowsNumber - Number of columns in board.
	 * @param wallWidth - Width of the wall in tiles.
	 */
	private void fillWithEmptyTles(int colsNumber, int rowsNumber, int wallWidth) {
		for (int i = wallWidth; i < colsNumber - wallWidth; i++)
		{
			for (int j = wallWidth; j < rowsNumber - wallWidth; j++)
			{
				// Initializing board.
				mBoard[i][j] = new EmptyTile(j,i);
			}
		}
	}
	
	/**
	 * Place all tiles on the board.
	 * 
	 * @param colsNumber - Board row length in tiles. 
	 * @param rowsNumber - Board column length in tiles.
	 * @param wallWidth - Width of the wall in tiles.
	 * @param playerLoc - Player location.
	 * @param boulderNum - Number of boulders to place.
	 * 
	 * @return The location of the flag on the board.
	 */
	private Point placeTiles(int colsNumber, int rowsNumber, int wallWidth,
			Point playerLoc, int boulderNum) {
		// Creating the board
		initializeBoard(colsNumber, rowsNumber, wallWidth);

		// Creating exit point
		Point flagLocation = 
				createExit(colsNumber, rowsNumber, playerLoc);
		
		setTile(flagLocation, new FlagTile(flagLocation));

		// Place the boulders on the board.
		placeBoulders(colsNumber,
					  rowsNumber, 
					  playerLoc,
					  boulderNum);
		
		return flagLocation;
	}

	/**
	 * Find a location on the board to place the flag in.
	 * 
	 * @param colsNumber 	- Number of columns in the board.
	 * @param rowsNumber 	- Number of rows in the board.
	 * @param playerLoc - Location of the player.
	 * 
	 * @return Location to place the flag.
	 */
	private Point createExit(int colsNumber, int rowsNumber, Point playerLoc)
	{
		// Get a random number
		Random rand = GeneralServiceProvider.getInstance().getRandom();
		
		int flagXposition = rand.nextInt(rowsNumber - 2) + 1;
		int flagYposition = rand.nextInt(colsNumber - 2) + 1;
		
		while(playerLoc.equals(flagXposition, flagYposition)){
			flagXposition = rand.nextInt(rowsNumber - 2) + 1;
			flagYposition = rand.nextInt(colsNumber - 2) + 1;
		}
			
		return new Point(flagXposition, flagYposition);
	}

	/**
	 * Place boulders on the board.
	 * @param colsNumber - Row length of the board in tiles.
	 * @param rowsNumber - Column length of the board in tiles.
	 * @param playerLoc - Player location.
	 * @param boulderNum - Number of boulders to place on board.
	 */
	private void placeBoulders(int colsNumber,
							   int rowsNumber,
							   Point playerLoc,
							   int boulderNum) {
		Random rand = 
				GeneralServiceProvider.getInstance().getRandom();
		
		int boulderColRand, boulderRowRand, boulderCounter = 0;
		
		// Place boulders.
		// TODO: Might be an infinite loop.
		int retryCounter = 0;
		
		// Check if we placed all boulders, 
		// or failed placing one for ten times in a row.
		while (retryCounter < 10 && boulderCounter < boulderNum)
		{
			// Making random points
			boulderRowRand = rand.nextInt(rowsNumber - 2) + 1;
			boulderColRand = rand.nextInt(colsNumber - 2) + 1;

			// Validate the position.
			TileValidatorFactory tileValidatorFactory =
					MapLogicServiceProvider.getInstance().getTileValidatorFactory();
			
			// If the location is not valid.
			if(!tileValidatorFactory.validate(BoulderTile.class, 
											 boulderRowRand, 
											 boulderColRand, 
											 playerLoc.x, 
											 playerLoc.y, 
											 mBoard)){
				retryCounter++;
				continue;
			}
			
			BoulderTile newBoulder = 
					new BoulderTile(boulderRowRand, 
		    					    boulderColRand);
			
			setTile(boulderColRand, 
				    boulderRowRand, 
				    newBoulder);
			
			mBoulders.add(newBoulder);
			
			// Increase counter after creating a boulder
			boulderCounter++; 
		}
	}
	
	
	/**
	 * Create a new instance of the ice cave board.
	 * 
	 * @param board - Board to hold as IceCaveBoard.
	 * @param startPoint - The starting location of the player.
	 * @param startingMove - The starting move of the player.
	 * @param moves - Minimum moves to solve the stage in.
	 */
//	public IceCaveBoard(ITile[][] board, 
//	                    Point startPoint,
//	                    EDirection startingMove,
//	                    int moves)
//	{
//		mStartingMove = startingMove;
//		mBoard = board.clone();
//		mPlayerStart = new Point(startPoint);
//		mMoves = moves;
//	}
	
	/**
	 * Create a new instance of the IceCaveBoard.
	 * @param other - Other board to create about.
	 */
	@SuppressWarnings("unchecked")
	protected IceCaveBoard(IceCaveBoard other)
	{
		mBoard = new ITile[other.getRowsNum()][other.getColumnNum()];
		mMoves = other.getMinMoves();
		mBoulders = (ArrayList<ITile>) other.mBoulders.clone();
		mBreakableBoulder = (ArrayList<ITile>) other.mBreakableBoulder.clone();
		mFlagPoint = new Point(other.getFlagLocation());
		mPlayerStart = new Point(other.getStartPoint());
		mStartingMove = other.getStartingMove();
	}
	
	/**
	 * Create a new instance of the ice cave board.
	 * 
	 * @param rows - Number of rows on the board.
	 * @param columns - Number of columns on the board.
	 * @param startPoint - The starting location of the player.
	 * @param startingMove - The starting move of the player.
	 * @param boulderNum - Number of boulders on the board.
	 * @param wallWidth - The width of the wall on the board.
	 * @param difficulty - The difficulty the board should fill.
	 */
	public IceCaveBoard(int rows,
	                    int colums,
	                    Point startPoint,
	                    EDirection startingMove,
	                    int boulderNum,
	                    int wallWidth,
	                    EDifficulty difficulty)
	{
		mBoard = new ITile[rows][colums];
		mStartingMove = startingMove;
		mPlayerStart = new Point(startPoint);
		mBoulders = new ArrayList<ITile>();
		mBreakableBoulder = new ArrayList<ITile>();
		
		// Place tiles in the board.
		Point flagPoint = 
				placeTiles(colums, 
						   rows, 
						   wallWidth, 
						   startPoint, 
						   boulderNum);
		
		while (!validate(startingMove, startPoint, flagPoint, difficulty) ||
				mBreakableBoulder.size() == 0){

			// Re-initializing map
			flagPoint = 
					placeTiles(colums, 
							   rows, 
							   wallWidth, 
							   startPoint, 
							   boulderNum);			
//			printBoard(mTiles);
		}
		
		mFlagPoint = flagPoint;
		
		// Put the breakable tiles back.
		for (ITile breakable : mBreakableBoulder)
		{
			// Put it on the board.
			mBoard[breakable.getLocation().y]
				  [breakable.getLocation().x] = breakable;
		}
	}
	
	/**
	 * Get the number of additional moves to make because of breakable boulders.
	 * @param node - Node to get its level on the board.
	 * @return The number of steps to add.
	 */
	public int getLevel(MapNode node, ArrayList<ITile> boulders)
	{
		final int NUMERIC_ERROR = 999;

		int nLevelCounter = 0;
		int stepsToAdd = 0;
		for (MapNode curNode = (MapNode) node.getParent();
			 curNode != null && nLevelCounter < NUMERIC_ERROR; 
			 curNode = (MapNode) curNode.getParent())
		{
			nLevelCounter++;
			if(boulders.contains(curNode.getValue()))
			{
				@SuppressWarnings("unchecked")
				ArrayList<ITile> tempBoulders =
						(ArrayList<ITile>) boulders.clone();
				
				// Find the shortest way to get 
				// around the boulder and hit the same place again.
//				MapNode wayToBoulder =
//					findShortestRoad(curNode.getValue().getLocation(), 
//									 curNode.getValue().getLocation());
//				
//				if(wayToBoulder == null){
//					continue;
//				}

				stepsToAdd++;
				tempBoulders.remove(curNode.getValue());
				
//				stepsToAdd += wayToBoulder.getLevel();
//				stepsToAdd += getLevel(wayToBoulder,tempBoulders);
				
				// Remove the boulder.
				setTile(curNode.getValue().getLocation(), 
						new EmptyTile(curNode.getValue().getLocation()));
				mBreakableBoulder.add(new BreakableBoulderTile(curNode.getValue().getLocation()));
			}
		}

		if (nLevelCounter == NUMERIC_ERROR)
		{
			stepsToAdd = 0;
		}
		
		return stepsToAdd;
	}
	
	/**
	 * Validate that the map is solvable in a specific number of moves.
	 * @param defaultMoveDirection - The first direction the player moves in.
	 * @param playerPoint - The starting location of the player.
	 * @param flagPoint - Location of the flag on the board.
	 * @param difficulty - The difficulty of the stage.
	 * @return true if valid.
	 */
	private boolean validate(EDirection  defaultMoveDirection,
							 Point 	     playerPoint,
							 Point 		 flagPoint,
							 EDifficulty difficulty)
	{
		MapNode flagNode = 
				findShortestRoad(new Point(playerPoint), 
								 new Point(flagPoint));
		
		if(flagNode == null){
			return false;
		}
		
		mMoves = flagNode.getLevel();
		mMoves += getLevel(flagNode, mBoulders);
		
		//printBoard(mTiles);
		// Check if it's OK.
		if (mMoves >= difficulty.getMinMoves() &&
			mMoves <= difficulty.getMaxMoves())
		{ 
			return true;
		}

		return false;
	}

	
	@Override
	public ITile[][] getBoard()
	{
		return (ITile[][]) mBoard;
	}

	/**
	 * Get the new location after making move.
	 * @param toMove - Direction to move.
	 * @param currPoint - The current point of the player.
	 * @return Location of the player after making the move.
	 */
	private void getMove(MapNode nodeStart, EDirection toMove, Point currPoint)
	{
		getMove(nodeStart, toMove, currPoint, false);
	}
	
	/**
	 * Get the new location after making move.
	 * @param toMove - Direction to move.
	 * @param currPoint - The current point of the player.
	 * @param isInternalCall - true if the function call is from the get move function.
	 * @return Location of the player after making the move.
	 */
	private void getMove(MapNode nodeStart, EDirection toMove, Point currPoint, boolean isInternalCall)
	{
		// Getting the current point
		ITile tileCurr = 
				(ITile) getTile(currPoint.y + toMove.getDirection().y,
						        currPoint.x + toMove.getDirection().x);

		// While not blocked
		while (!(tileCurr instanceof IBlockingTile))
		{
			currPoint.x += toMove.getDirection().x;
			currPoint.y += toMove.getDirection().y;

			// Stopping if reached exit.
			// TODO: make this more generic.
			if (tileCurr instanceof FlagTile)
			{
				break;
			}

			// Advance
			tileCurr =
				(ITile) getTile(currPoint.y + toMove.getDirection().y,
							    currPoint.x + toMove.getDirection().x);
		}
		
		if(tileCurr instanceof BoulderTile){
			Point nextPoint = new Point(tileCurr.getLocation());
			getMove(nodeStart, toMove, nextPoint, true);
		}
		
		if(isInternalCall){
			MapNode nodeNext = new MapNode(nodeStart, (ITile)getTile(currPoint));
			nodeStart.push(nodeNext);
		}
	}
	
	/**
	 * Get a tree of map nodes,
	 * a product of BFS algorithm for the shortest way to reach requested point.
	 * @param start - Starting point.
	 * @param end - Finish point.
	 * @return Tree of map nodes of the shortest way, null if no way is found.
	 */
	public MapNode findShortestRoad(Point start, Point end)
	{
		// Create the root node
		MapNode root = 
				new MapNode(null, 
							(ITile) getTile(start));

		// Clear the previous stuff
		root.clear();

		// Add the root to the stack
		root.addRoot();

		boolean[][] visited = 
				new boolean[getRowsNum()][getColumnNum()];
		
		LinkedList<MapNode> queue = new LinkedList<MapNode>();
		
		// Add the root.
		queue.add(root);
		
		MapNode mapNode = null;
		// While the queue is not empty.
		while(!queue.isEmpty()){
			// Pop the node.
			mapNode = queue.remove();
			
			if(mapNode.getParent() != null &&
			   mapNode.getValue().getLocation().equals(end)){
				return mapNode;
			}		
			
			// Go through the directions.
			for (EDirection direction : EDirection.values()) {
				Point currPoint = new Point(mapNode.getValue().getLocation());
				getMove(mapNode, direction, currPoint);
				if(!visited[currPoint.y][currPoint.x]){
					visited[currPoint.y][currPoint.x] = true;
					MapNode newNode = 
							new MapNode(mapNode, 
									    (ITile) getTile(currPoint));
					// Push the node.
					queue.add(newNode);
					mapNode.push(newNode);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Get the minimum number of moves needed to solve the stage.
	 * @return Minimum number of moves needed to solve the stage.
	 */
	public int getMinMoves()
	{
		return mMoves;
	}

	@Override
	public boolean setTile(int row, int column, IBoardTile tile)
	{
		if(!(tile instanceof ITile) ||
		   row >= getRowsNum()      ||
		   column >= getColumnNum())
		{
			return false;
		}
		
		mBoard[row][column] = (ITile) tile;
		
		return true;
	}

}
