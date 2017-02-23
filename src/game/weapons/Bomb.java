package game.weapons;

import static game.aviator.Aviator.enemies;
import static game.aviator.GameConstants.PLAYER_BULLET;
import static game.frameworks.Weapon.projectiles;
import android.util.Log;
import game.aviator.Aviator;
import game.frameworks.impl.Texture;
import game.sprites.Enemy;
import game.sprites.Explosion;
import game.sprites.Sprite;

public class Bomb extends Sprite {
	Texture boundary;
	
	protected Bomb(float x,float y,short width,short height,Texture texture){
		super(x,y,width,height,texture);
		
		
	}//bomb
	
	
	
	@Override
	public void update(float dt){
		super.update(dt);
		if(elapsedTime>BOMB_DETONATION_TIME){		//detonattion is after 400 milisecond
			
			Aviator.sprites.remove(this);
			Aviator.sprites.add(Explosion.create(x, y, Explosion.LONG, Explosion.VERY_LARGE));
			
			while(!Aviator.enemies.isEmpty()){
				
				if(enemies.peekFirst().life<BOSS_HEALTH){
				Enemy e=Aviator.enemies.removeFirst();
				
				Aviator.sprites.add(Explosion.create(e.x, e.y, Explosion.LONG, Explosion.SMALL));
				e.recycle();
				}//it's not an enmey
				else{
					enemies.peekFirst().health-=BOMB_DAMAGE;
					break;
				}
			}//empty enemy*/
			
			
	
			elapsedTime=0;
		}
			
		else{	
		elapsedTime+=dt;
		y+=200*dt;
		}
			
		
	}//update
	

	float elapsedTime;
	public static Bomb create(float x,float y,short width,short height,Texture texture){
		Bomb b=new Bomb(x,y,width,height,texture);
		
		return b;
	}
}
