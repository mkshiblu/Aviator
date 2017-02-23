package game.sprites;

import game.frameworks.impl.Texture;

import javax.microedition.khronos.opengles.GL10;

public class MissileBonus extends Sprite {
	
	public MissileBonus(float x,float y,short width,short height,Texture texture,short rotation){
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
		gl.glRotatef(angle, 0, -.75f,0);
		shape.draw(gl);
		texture.unbind();
	}

	
	/***DO THINGS FOR PLAYER COLLISION i.e when player reach this ***/
	public void onPlayerCollision(Player player){
	
		player.missiles+=10;
	}//collision
	
	short speedRotation;
	
	public float angle;
}
