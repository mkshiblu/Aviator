package game.sprites;

import static game.aviator.GameResource.projectileTextures;
import game.frameworks.impl.Texture;
import game.weapons.PentaGun;
import game.weapons.RailGun;
import game.weapons.TripleGun;

import javax.microedition.khronos.opengles.GL10;

public class GunUpgrade extends Sprite {

	public GunUpgrade(float x,float y,short width,short height,Texture texture,short rotation){
		super(x,y,width,height,texture);
		speedRotation=rotation;
	}
	
	
	@Override
	public void update(float dt) {
		super.update(dt);
		angle+=speedRotation*dt;
	}//update
	
	@Override
	public void draw(GL10 gl){
		texture.bind();
		shape.bind(gl);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 1,0);
		shape.draw(gl);
		texture.unbind();
		//shape.unbind(gl);
	}
	
	
	/***DO THINGS FOR PLAYER COLLISION i.e when player reach this ***/
	public void onPlayerCollision(Player player){
		if(player.weapon instanceof RailGun)
		player.weapon=new TripleGun(player,(short) (PLAYER_FIRE_RATE*1.5f),(short) (PLAYER_BULLET_SPEED*1.5),projectileTextures[1],(short)50);
		else if(player.weapon instanceof TripleGun)
			player.weapon=new PentaGun(player,(short) (PLAYER_FIRE_RATE),PLAYER_BULLET_SPEED,projectileTextures[2],(short)250);
			
	}//collision
	short speedRotation;
	
	public float angle;
}//bonus
