package game.motion;

import game.sprites.Sprite;

import java.util.LinkedList;

public class DiagonalMotion extends Motion {

	/**
	 * @param sprite
	 * @param speedX pixel per second
	 */
	short speedX;
	short speedY;
	byte dirY;
	byte dirX;
	private DiagonalMotion(Sprite sprite,short speedX,short speedY,byte dirX,byte  dirY) {
		super(sprite);
		this.dirX=dirX;
		this.dirY=dirY;
		this.speedX=speedX;
		this.speedY=speedY;
	
	}

	
	@Override
	public void move(float dt){
		
		s.x=s.x+(dirX)*dt*speedX;
		
		
		//if(s.y)
		s.y=s.y+(dirY)*dt*speedY*dt;
		
		if(s.x>=SCREEN_WIDTH)
		dirX=LEFT;
		else if(s.x<=0)
			dirX=RIGHT;
	}//move
	
	/**
	 * to prevent GC use this method instead of Constructor
	 */

	public static DiagonalMotion create(Sprite sprite,short speedX,short speedY,byte dirX,byte  dirY){
		if(freeObjects.isEmpty())
			return new DiagonalMotion(sprite,speedX,speedY,dirX,dirY);		
		DiagonalMotion newHM=freeObjects.removeFirst();
			newHM.s=sprite;
			newHM.speedX=speedX;								
			newHM.dirX=dirX;
			newHM.dirY=dirY;
			newHM.speedX=speedX;
			newHM.speedY=speedY;
		return newHM;	
	}
	
	@Override
	public  void recycle(){
		freeObjects.addFirst(this);
	}//recycle
	static LinkedList<DiagonalMotion> freeObjects=new LinkedList<DiagonalMotion>();

}
