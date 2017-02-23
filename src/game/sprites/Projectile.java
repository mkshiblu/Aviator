package game.sprites;
import game.frameworks.impl.Texture;

import java.util.LinkedList;


public class Projectile extends Sprite  {

	
	
	public void update(float dt){
		
		y+=speedY*dt*dirY;
		x+=speedX*dt*dirX;
		
		//if(y>=SCREEN_HEIGHT ||y<=0)
		if(this.isOutOfScreen())	
		isDead=true;
		
	}//update
	
	

	//@Override
	public void recycle() {
		isDead=false;
		freeObjects.addFirst(this);
	}//recycle

/**to prevent the garbage collector and creating new projectile again and agai
 * this method first check if there is any unused bullet/i.e dead projectile
 * if then it just pull it and set  the param values	
 * @param x the initial x coordinate of this projectile where it starts to draw/update
 * @param y the initial y coordinate of this projectile where it starts to draw/update
 * @param dirY vertical direction for player bullet it's usually 1 and for enemy it's -1
 * @return return a new or recycled projectile
 */
	public static Projectile create(float x, float y,byte dirY,short speedY,Texture texture,byte dirX,short speedX) {
		if(freeObjects.isEmpty())
			return new Projectile(x,y,dirY,speedY,texture,dirX,speedX);		
			Projectile newP=freeObjects.removeFirst();
			newP.x=x;
			newP.y=y;
			newP.dirY=dirY;								
			newP.speedY=speedY;
			
			newP.texture=texture;
			newP.dirX=dirX;
			newP.speedX=speedX;
		return newP;		
	}//create
	
	protected Projectile(float x, float y,byte dirY,short speedY,Texture texture,byte dirX,short speedX) {
		this.x=x;
		this.y=y;
		this.dirY=dirY;
		this.speedY=speedY;
		
		this.dirX=dirX;
		this.speedX=speedX;
		
		isDead=false;
		this.texture=texture;
	}
	
	public byte dirY;
	short speedY;
	public byte dirX;
	short speedX;
	
	public boolean isDead;
	private static LinkedList<Projectile> freeObjects=new LinkedList<Projectile>();
//	public Texture texture;
}
