package game.sprites;

import game.aviator.GameConstants;
import game.frameworks.GameObjects;
import game.frameworks.impl.Texture;
import game.frameworks.impl.Vertices;
import game.motion.Motion;

import javax.microedition.khronos.opengles.GL10;

public  abstract class Sprite extends GameObjects implements GameConstants {
	public Vertices shape;
	
	public Sprite(float x, float y, int width, int height, Texture texture) {
		super(x, y, width, height, texture);
		shape=Vertices.getRectangularShape(width, height);
		alive=true;
	}
	
	
	
	public Sprite(){
		
	}
	
	public void draw(GL10 gl){
		texture.bind();
		shape.bind(gl);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		shape.draw(gl);
		texture.unbind();
	//	shape.unbind(gl);
	}
	public void setMotion(Motion motion){
		this.motion=motion;//=motionarray[1];
	}
	
	
	public void update(float dt){
		if(motion!=null)
			motion.move(dt);
		y=y-MAP_SCROLL_SPEED*dt;
		//if(y+(height>>1)<=0)
		if(this.isOutOfScreen())	
		alive=false;
	}
	/**call when the invoking sprite colllides with player**/
	public void onPlayerCollision(Player player){
		
	}
	public void recycle(){
		
	}
	
	protected Motion motion;
	/**true when the sprite is out of the screen or destroyed by player bullet**/
	public boolean alive=true;
}
