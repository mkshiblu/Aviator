package game.motion;

import game.aviator.GameConstants;
import game.frameworks.Recyclable;
import game.sprites.Sprite;

public  abstract class Motion implements Movable,Recyclable,GameConstants{
	Sprite s;
	/**left=-1 or right=1 set values causes the sprite move left or right**/
	byte dirX;
	//byte dirY;
//	byte speedX,speedY;
	
	/**
	 * @param sprite sprite u want this motion to be attached
	 */
	protected Motion (Sprite sprite){
		s=sprite;
	}//Motion
	
		
}
