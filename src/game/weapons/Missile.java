package game.weapons;

import game.aviator.Aviator;
import game.aviator.Util;
import game.frameworks.impl.Texture;
import game.sprites.Enemy;
import game.sprites.Explosion;
import game.sprites.Sprite;

import javax.microedition.khronos.opengles.GL10;

public class Missile extends Sprite {
	
	float angle;
	Enemy target;
		public Missile(float x,float y,int width,int height,Texture texture,Enemy target){
			super(x,y,width,height,texture);
			this.target=target;
		}//misisile
		
		
		public void update(float dt){
			if(target!=null && target.alive==false)//?temp
				target= Aviator.enemies.peekFirst();//?temp
		
			if(target!=null){
			float X=target.x-x;
			float Y=target.y-y;
			float dist=Util.distance(Y,X );
			
			if(this.intersect(target))
			{
				if(target.life<BOSS_HEALTH)
				target.alive=false;
				else
					target.health-=50;
				this.alive=false;
				Aviator.score+=target.life;
				Aviator.sprites.add(Explosion.create(x, y, Explosion.SHORT,Explosion.SMALL));
			}
			else{
				
			//	 float lombo=y-v.y=Y
				// float bhumi=x-v.x=X;
				
				 angle=(float) ( Math.atan2(Y, X)*TO_DEGREE);
				if(angle<0)
					angle+=360;
		
				// otivuj=Util.distance(lombo, bhumi);
					//cosO=vumi/otivuj so vumi=cosO*otivuj or, x-v.x=cosO*otivuj; so v.x=x-cosO*otivuj so x=speedX 
					
					speedX=(float) (Math.cos(angle*TO_RADIAN)*dist);
						speedY=(float) (Math.sin(angle*TO_RADIAN)*dist);
			}
			}
			//x=Math.
			x+=speedX*dt*2;
			y+=speedY*dt*2;
			if(this.isOutOfScreen())
				this.alive=false;
			
		}
		float	speedX;
		float		speedY=100;
		public void Draw(GL10 gl){
			texture.bind();
			shape.bind(gl);
			
			gl.glLoadIdentity();
			gl.glTranslatef(x, y, 0);
			gl.glRotatef(angle, 0, 0, 1);
			shape.draw(gl);
			texture.unbind();
		}
		
		
		public void recycle(){
			
		}
		static final float TO_DEGREE=(float) (Math.PI/180);
		static final float TO_RADIAN=(float) (180/Math.PI);
		
}
