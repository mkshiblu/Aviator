package game.aviator;



public  interface GameConstants {
	
	short BOSS_HEALTH=1000;
	short BOMB_DAMAGE=200;
	byte ENEMY_BULLET=-1;
	byte PLAYER_BULLET=1;
	byte RIGHT=1;
	byte LEFT=-1;
	/**bullet per minute**/
	short PLAYER_FIRE_RATE=450;
	short PLAYER_BULLET_SPEED=300;
	short PLAYER_INITIAL_HEALTH=100;
	byte PLAYER_SHIELDED_TIME=8;//15 seconds shielded
//	byte ENEMY_FIRE_RATE=1;
	
	byte UP=1;
	byte DOWN=-1;
	
	
/////////////MAP   CONSTANTS\\\\\\\\\\\\\\\\\\
	short SCREEN_WIDTH=320;
	short SCREEN_HEIGHT=480;
	
	
	byte COLUMNS_IN_A_SINGLE_SCREEN=8;
	byte ROWS_IN_A_SINGLE_SCREEN=12;//480/12=40 pixel per rows 
	byte PIXEL_PER_COLUMN=SCREEN_WIDTH/COLUMNS_IN_A_SINGLE_SCREEN;//40
	byte PIXEL_PER_ROW=SCREEN_HEIGHT/ROWS_IN_A_SINGLE_SCREEN;//40
	
	byte MAP_SCROLL_SPEED=30;/// pixel per second vertical downward auto scrolling of map
	/**milliseconds after which a new map line is read from the text file**/
	int MAP_LINE_UPDATE_TIME=(1000/MAP_SCROLL_SPEED)*PIXEL_PER_ROW;//not perfectly working recalculation needed with backgrounds scrolling
	
//Resource
	byte HORIZONTAL_MOTION=1;
	byte DOWN_MOTION=2;
	byte DIAGONAL_MOTION=3;
	byte BOSS_MOTION=4;
	
	byte RAILGUN=1;
	byte TRIPLEGUN=2;
	byte PENTAGUN=3;
	byte BOSS_GUN=4;
	byte BACKGROUNDS_IN_A_LEVEL=10;
	/***************UI HUD***************/
	byte ITEM_WIDTH=20;
	byte ITEM_HEIGHT=25;
	byte DIGIT_WIDTH=20;
	byte DIGIT_HEIGHT=20;
	
	float BOMB_DETONATION_TIME=.5f;
}