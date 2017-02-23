package game.weapons;

import game.frameworks.Weapon;
import game.frameworks.impl.Texture;
import game.sprites.Projectile;
import game.sprites.Sprite;
public class RailGun extends Weapon{

	
	/**
	 * create a weapon of type railgun with the specified rate of fire
	 * @param sprite the sprite with which this weapon is attached to
	 * @param rateOfFire the rate of fire i.e. round per second
	 * @param speedOfProjectile pixel per second a projectile from this weapon pass
	 */
	public RailGun(Sprite sprite,short rateOfFire,short speedOfProjectile,Texture texture){
			super(sprite,rateOfFire,speedOfProjectile);
		tex=texture;
	}
	
	
	@Override
	public void shoot(byte dirY) {
			projectiles.add(Projectile.create(s.x, s.y, dirY,speedOfProjectile,tex,(byte) 0,(short)0));
			shootTime=0;
			//play animation
	}//shoot
	public boolean isReadyToShoot(float dt) {//this is called evey loop
		shootTime+=dt;
		
		return shootTime>=shootFactor;
	}//is readyto shoot
	private Texture tex;
}
