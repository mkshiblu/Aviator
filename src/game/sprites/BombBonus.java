package game.sprites;

import static game.aviator.GameResource.bonusTextures;
import game.frameworks.impl.Texture;
import game.weapons.Bomb;

import javax.microedition.khronos.opengles.GL10;

public class BombBonus extends Sprite {
	
	Texture boundary;
	public BombBonus(float x,float y,short width,short height,Texture texture,short rotation,Texture border){
		super(x,y,width,height,texture);
		speedRotation=rotation;
		this.boundary=border;
	}
	
	
	@Override
	public void update(float dt) {
		super.update(dt);
		angle+=speedRotation*dt;
		
		
	}//update
	
	@Override
	public void draw(GL10 gl){
		shape.bind(gl);
		
		boundary.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 0,1);
		gl.glScalef(1.2f, 1.2f, 0);
		shape.draw(gl);
		boundary.unbind();
		
		
		texture.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		
		shape.draw(gl);
		texture.unbind();
		
		//shape.unbind(gl);
	}
	
	
	/***DO THINGS FOR PLAYER COLLISION i.e when player reach this ***/
	public void onPlayerCollision(Player player){
	
		Bomb b=null;
		 b=Bomb.create(player.x,player.y+(player.height>>1),(short)50,(short)50,bonusTextures[3]);
		
		player.bombs.add(b);
	}//collision
	
	short speedRotation;
	
	public float angle;
}
