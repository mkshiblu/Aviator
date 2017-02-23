package game.aviator;

import game.frameworks.impl.Texture;
import game.frameworks.impl.Vertices;
import game.sprites.Player;
import game.weapons.Bomb;
import static game.aviator.GameResource.hudTextures;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class UIHud implements GameConstants {
	/**
	 * 
	 * @param width widht of the scoreBoard
	 * @param height height o the scoreBoard
	 */
	public UIHud(){ 
		digits=GameResource.numbers;	
		singleDigitShape=Vertices.getRectangularShape(DIGIT_WIDTH, DIGIT_HEIGHT);
		itemShape=Vertices.getRectangularShape(ITEM_WIDTH,ITEM_HEIGHT);
		boundary=GameResource.bonusTextures[2];
		healthBarShape=new Vertices(new float[]{
			0,	0,	0, 1,  //bottom left at 0,0
			1,	0,  1, 1, //bottom right at 1,0 width=1
			1, 10,  1,	0, //top right height =10
			0, 10,  0, 0
		},new short[]{
				0,1,2,
				2,3,0
		}//short
		);//haelth bar
	}
	
	public void update(float dt){
		angle+=100*dt;
		if(angle>=360)
			angle=angle-360;//make sure angle doesn't get too large
	}
	
	public void drawInteger(GL10 gl,int X,int Y,int number,byte numOfDigit){

		X=X-(DIGIT_WIDTH>>1);
		X+=DIGIT_WIDTH*numOfDigit;//score will be printed from right to left so increment x to the last position
		//int y=Y+digitHeight>>1;
		
		int temp=number;
		singleDigitShape.bind(gl);
/**now convert int score into array of ditit i.e. if score is 429 then on screen 000429 will be printed 
 * so we have to separate the digits from right sides**/ 	
		for(int i=1;i<=numOfDigit;i++){                           //6 digit will be printed
			int dig=temp%10;
			
			temp/=10;
			
			digits[dig].bind();
			gl.glLoadIdentity();
			gl.glTranslatef(X,Y , 0);
			singleDigitShape.draw(gl);
			
			//after drawing increment x a width of a single digit
			X-=DIGIT_WIDTH;
		digits[dig].unbind();
		}
		//singleDigitShape.unbind(gl);
	}//draw;
	
	/**
	 * draws player inventory elements like if he has any bombs,or missile etc
	 * @param gl gl instance
	 * @param x top left x
	 * @param y bottom left y
	 */
	public void drawPlayerInventory(GL10 gl,int x,int y,Player player){
		
		y=y+ITEM_HEIGHT>>1;
		LinkedList<Bomb> bombs=player.bombs;
			itemShape.bind(gl);
		for(int i=0;i<bombs.size();i++)
		{
			

		boundary.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 0,1);
		gl.glScalef(1.2f, 1.2f, 0);
		itemShape.draw(gl);
		boundary.unbind();
		
		
	
		Bomb b=bombs.get(i);
			
			b.texture.bind();
			gl.glLoadIdentity();
			gl.glTranslatef(x, y, 0);
			itemShape.draw(gl);
			b.texture.unbind();
			
			x+=ITEM_WIDTH;
		}//for
		
		//itemShape.unbind(gl);
	}//draw inventory
	
	public void drawHealthBar(GL10 gl,int x,int y,Player player){
		/***drawing health bar**/
		hudTextures[0].bind();
		
		healthBarShape.bind(gl);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glScalef(player.health, 1.0f, 0.0f);
		
		
		healthBarShape.draw(gl);
	
		hudTextures[0].unbind();
		//healthBarShape.unbind(gl);
		
		/*DRAWING LIFES***/
		
		
	}//lifeBar
	
	public void drawLifeBar(GL10 gl,int x,int y,Player player){
		/***drawing health bar**/
		hudTextures[1].bind();
		
		itemShape.bind(gl);
		
		for(int i=1;i<=player.life;i++){
			gl.glLoadIdentity();
			gl.glTranslatef(x, y, 0);
			itemShape.draw(gl);
			x+=ITEM_WIDTH+5;
		
		}
		
		hudTextures[1].unbind();
	//	itemShape.unbind(gl);
		
		/*DRAWING LIFES***/
		
		
	}//lifeBar
	
	public void drawTexture(GL10 gl,int x,int y,Texture texture){
		
		itemShape.bind(gl);
		texture.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glScalef(1.5f,1.5f, 0);
		itemShape.draw(gl);
		texture.unbind();
		
			
	}
	
public void drawTexture(GL10 gl,int x,int y,Texture texture,float size){
		
		itemShape.bind(gl);
		texture.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glScalef(size,size, 0);
		itemShape.draw(gl);
		texture.unbind();
		
			
	}

	Texture digits[];
	Vertices singleDigitShape;
	Vertices healthBarShape;
	
	Vertices itemShape;
	float angle;
	Texture boundary;
}//class
