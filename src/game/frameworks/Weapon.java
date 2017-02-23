package game.frameworks;

import game.aviator.GameConstants;
import game.sprites.Projectile;
import game.sprites.Sprite;

import java.util.LinkedList;

public abstract class Weapon implements GameConstants {
	/** keep tracks of last shoot time**/
	protected float shootTime;
	
	/**bullet per minute**/
	protected short rateOfFire;
	/**how many seconds need to fire the next round**/
	protected float shootFactor;
	
	
	public static LinkedList<Projectile> projectiles;
	protected Sprite s;
	protected short speedOfProjectile;
	public abstract void shoot(byte dirY);

	protected Weapon(Sprite sprite,short rateOfFire,short speedOfProjectile){
		s=sprite;
		this.rateOfFire=rateOfFire;
		this.speedOfProjectile=speedOfProjectile;
		if(rateOfFire!=0)
		shootFactor=60.0f/rateOfFire;
	}

	public abstract boolean isReadyToShoot(float dt);
	public float angle;
	
}
