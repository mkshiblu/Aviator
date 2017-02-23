package game.motion;

import game.sprites.Sprite;

import java.util.LinkedList;
import java.util.Random;

public class BossMotion extends Motion {

	/**
	 * @param sprite
	 * @param speedX pixel per second
	 */
	short speedX;
	private BossMotion(Sprite sprite,short speedX) {
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
		//s.y=s.y+MAP_SCROLL_SPEED*dt;
		if(s.y<=270)
			s.y=240;
			//s.y=new Random(200).nextInt()+240;
		if(s.x>=SCREEN_WIDTH)
			dirX=LEFT;
		else if(s.x<=0)
			dirX=RIGHT;
	}//move
	
	/**
	 * to prevent GC use this method instead of Constructor
	 */

	public static BossMotion create(Sprite sprite,short speedX){
		if(freeObjects.isEmpty())
			return new BossMotion(sprite,speedX);		
			BossMotion newHM=freeObjects.removeFirst();
			newHM.s=sprite;
			newHM.speedX=speedX;								
		
		return newHM;	
	}
	
	@Override
	public  void recycle(){
		freeObjects.addFirst(this);
	}//recycle
	static LinkedList<BossMotion> freeObjects=new LinkedList<BossMotion>();

}
