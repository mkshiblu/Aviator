package game.weapons;

import game.frameworks.Weapon;
import game.frameworks.impl.Texture;
import game.sprites.Projectile;
import game.sprites.Sprite;

public class TripleGun extends Weapon{

	short xSpeed;
	public TripleGun(Sprite sprite, short rateOfFire, short speedOfProjectile,Texture tex,short xSpeed) {
		super(sprite, rateOfFire, speedOfProjectile);
		this.xSpeed=xSpeed;
		this.tex=tex;
	}

	@Override
	public void shoot(byte dirY) {
		projectiles.add(Projectile.create(s.x, s.y, dirY,speedOfProjectile,tex,LEFT,xSpeed));
		projectiles.add(Projectile.create(s.x, s.y, dirY,speedOfProjectile,tex,(byte)0,(short)0));		
		projectiles.add(Projectile.create(s.x, s.y, dirY,speedOfProjectile,tex,RIGHT,xSpeed));
		
		shootTime=0;
	}


	@Override
	public boolean isReadyToShoot(float dt) {
	shootTime+=dt;
		
		return shootTime>=shootFactor;
	}//ready shoot
	
	private Texture tex;
}
