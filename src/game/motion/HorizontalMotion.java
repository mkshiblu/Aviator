package game.motion;

import game.sprites.Sprite;

import java.util.LinkedList;

public class HorizontalMotion extends Motion {

	/**
	 * @param sprite
	 * @param speedX pixel per second
	 */
	short speedX;
	private HorizontalMotion(Sprite sprite,short speedX) {
		super(sprite);
		dirX=RIGHT;
		this.speedX=speedX;
	}
/*
	public HorizontalMotion(short speedX) {
		this(null,speedX);
		dirX=RIGHT;
		this.speedX=speedX;
	}
*/
	
	
	@Override
	public void move(float dt){
		
		s.x=s.x+(dirX)*dt*speedX;
		
		//if()
		s.y=s.y-MAP_SCROLL_SPEED*dt;
		
		if(s.x>=SCREEN_WIDTH)
			dirX=LEFT;
		else if(s.x<=0)
			dirX=RIGHT;
	}//move
	
	/**
	 * to prevent GC use this method instead of Constructor
	 */

	public static HorizontalMotion create(Sprite sprite,short speedX){
		if(freeObjects.isEmpty())
			return new HorizontalMotion(sprite,speedX);		
			HorizontalMotion newHM=freeObjects.removeFirst();
			newHM.s=sprite;
			newHM.speedX=speedX;								
		
		return newHM;	
	}
	
	@Override
	public  void recycle(){
		freeObjects.addFirst(this);
	}//recycle
	static LinkedList<HorizontalMotion> freeObjects=new LinkedList<HorizontalMotion>();

}
