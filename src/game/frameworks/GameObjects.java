package game.frameworks;

import static game.aviator.GameConstants.SCREEN_WIDTH;

import static game.aviator.GameConstants.SCREEN_HEIGHT;
import game.frameworks.impl.Texture;
import game.sprites.Sprite;


public class GameObjects {
	
	public Texture texture;
	public float x;
	public float y;
	public int width;
	public int height;
	
	
	public GameObjects(){
		
	}
	public GameObjects(float x,float y){
		this.x=x;
		this.y=y;
	}//gameObjects
	
	public GameObjects(float x2, float y2, Texture image) {
		this(x2,y2);
		texture=image;
		
	}

	
	public GameObjects(float x, float y,int width,int height,Texture texture) {
		this(x, y,texture);
		this.width=width;
		this.height=height;
		
	}//constructor
	
	
	/*
	public void setALL(float x, float y,int width,int height,Texture texture){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.texture=texture;
		
	}
	*/
	
	/** returns true if the point resides int the texture rectangle**/
	public boolean contains(float x,float y){
		
		
		return (x>=this.x-(width>>1) && x<=this.x+(width>>1) //this.x and this.y is the middle
				&& y>=this.y-(height>>1) && y<=this.y+(height>>1));
		
		
	}//contains
	

	/** if the shape rectangle of s intersects invoking sprite shape returns true**/
	public boolean intersect(Sprite s){

		float r1x=x-(width>>1);
		float r1y=y-(height>>1);
		
		float r2x=s.x-(s.width>>1);
		float r2y=s.y-(s.height>>1);
		
		
		
		return  r1x<r2x+s.width && 
				r1x+width>r2x  &&
			r1y<r2y+s.height &&
				r1y+height>r2y	;
	}//

	/** determines if the specified sprite is out of the screen
	 * @return true if not visible false if any part of this sprite is visible
	 */
	public boolean isOutOfScreen(){
		float topX=x-(width>>1);
		float topY=y+(height>>1);
		
		return !(topX+width>0 && topX<SCREEN_WIDTH
				&& topY-height<SCREEN_HEIGHT && topY>0);
	}
}
