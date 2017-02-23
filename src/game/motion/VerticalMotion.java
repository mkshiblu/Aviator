package game.motion;

import java.util.LinkedList;


import game.sprites.Sprite;

public class VerticalMotion extends Motion {

	
	protected VerticalMotion(Sprite sprite,byte dirY,short speedY) {
		super(sprite);
		this.speedY=speedY;
		this.dirY=dirY;
	}

	@Override
	public void move(float dt) {
		s.y+=dirY*speedY*dt;
		
	}//move

	/**
	 * to prevent GC use this method instead of Constructor
	 */

	public static VerticalMotion create(Sprite sprite,byte dirY,short speedY){
		if(freeObjects.isEmpty())
			return new VerticalMotion(sprite,dirY,speedY);		
			VerticalMotion newHM=freeObjects.removeFirst();
			newHM.s=sprite;
			newHM.speedY=speedY;								
		
		return newHM;	
	}
	
	@Override
	public  void recycle(){
		freeObjects.addFirst(this);
	}//recycle
	short speedY;
	byte dirY;
	static LinkedList<VerticalMotion> freeObjects=new LinkedList<VerticalMotion>();

}
