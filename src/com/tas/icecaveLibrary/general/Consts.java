package com.tas.icecaveLibrary.general;

import com.tas.icecaveLibrary.utils.Point;

public class Consts
{
	// Keys
	public final static String PREFS_FILE_TAG = "prefsFile";
	public final static String LEVEL_SELECT_TAG = "levelSelect";
	public final static String PLAYER_SELECT_TAG = "playerSelect";
	public final static String THEME_SELECT_TAG = "themeSelect";
	public final static String SELECT_BOARD_SIZE_SIZE = "selectBoardSize";
	public final static String MUSIC_MUTE_FLAG = "musicMuteFlag";
	public final static String RESET_SHARED_DATA_TAG = "ResetSharedDataTag";

	public final static Point DEFAULT_START_POS = new Point(1, 1);

	// Updates
	public static final int PLAYER_FINISH_MOVE_UPDATE = 0;
	public static final int LOADING_LEVEL_FINISHED_UPDATE = 1;

	/***
	 * Level default values
	 */
	public final static int DEFAULT_WALL_WIDTH = 1;
	public final static int DEFAULT_BOULDER_RELATION = 7;
	public final static int DEFAULT_BOARD_SIZE = 14;

	/***
	 * Standard tile values and positioning
	 */
	// Player tiles
	public final static int PLAYER_ERROR_ROW = -1;
	public final static int PLAYER_DOWN_ROW = 0;
	public final static int PLAYER_LEFT_ROW = 1;
	public final static int PLAYER_RIGHT_ROW = 2;
	public final static int PLAYER_UP_ROW = 3;
	public final static int PLAYER_MOVING_1 = 0;
	public final static int PLAYER_STANDING = 1;
	public final static int PLAYER_MOVING_2 = 2;
	public final static int PLAYER_MOVEMENTS_SUM = 3; // TODO Might want to change these into an enum

	// Columns and rows
	public final static int DEFAULT_TILES_BMP_COLUMNS = 8;
	public final static int DEFAULT_TILES_BMP_ROWS = 2;
	public final static int DEFAULT_PLAYER_BMP_ROWS = 4;
	public final static int DEFAULT_PLAYER_BMP_COLUMNS = 3;

	/***
	 * Absolute Paths
	 */

	// Styles
	public final static String STYLE_PIXELART = "fonts/pixelart.ttf";
	public final static String STYLE_SNOW_TOP = "fonts/SnowtopCaps.otf";
	public final static String STYLE_ROBOTO_THIN = "fonts/Roboto-Thin.ttf";
	public final static String STYLE_ROBOTO_BLACK = "fonts/Roboto-Black.ttf";
	public final static String STYLE_ROBOTO_CONDENSED_LIGHT = "fonts/RobotoCondensed-Light.ttf";

	/**
	 * Special keys for flags
	 */

	// Difficulty lock
	public final static String LOCK_HARD_DIFFICULTY = "LockHardDifficulty";

	/**
	 * Versions
	 */

	// Map Generator Version
	public final static String MAP_GEN_VERSION = "1.0";

	// Number of resets throughout the different versions so far
	public final static int RESET_NUMBER = 1;

	/**
	 * Package file names
	 */
	public final static String[] PACKAGE_NAMES = new String[]
		{ "Cave Entrance", "Hidden Dungeon", "Frozen Caverns", "Ancient Tombs" };
}
