package game.sprites;

import game.frameworks.Weapon;
import game.frameworks.impl.Texture;
import game.frameworks.impl.Vertices;

import java.util.LinkedList;

public class Enemy extends Sprite {
	
	
	
	public void update(float dt){
		super.update(dt);
		
		if(weapon!=null && weapon.isReadyToShoot(dt))
		weapon.shoot(DOWN);
	}
	
	
	public void recycle() {
		freeObjects.addFirst(this);
		motion.recycle();
		shape.recycle();
	}//recycle

	
	/**TO REUSE CALL THIS METHOD INSTEAD OF CALLING CONSTRUCTOR**/
	public static Enemy create(float x, float y,int width,int height,Texture texture,short health) {
		if(freeObjects.isEmpty())
			return new Enemy(x,y,width,height,texture,health);		
			
			
			Enemy newE=freeObjects.removeFirst();
			newE.x=x;
			newE.y=y;
			newE.shape=Vertices.getRectangularShape(width, height);
			newE.width=width;
			newE.height=height;
			newE.texture=texture;
			
			newE.health=health;
			newE.life=health;
			
			newE.alive=true;
			
			
		return newE;		
	}//create
	
	public void onPlayerCollision(){
		
	}
	
	
	
	
	
	protected Enemy(float x, float y,int width,int height,Texture texture,short health) {
		super(x, y,width,height,texture);
		this.health=health;
		life=health;
	}//constructor
	
	
	
	
	private static LinkedList<Enemy> freeObjects=new LinkedList<Enemy>();
	public Weapon weapon;
	public short health;
	public short life;
}
