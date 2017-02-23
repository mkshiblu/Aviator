package game.sprites;

import static game.aviator.GameResource.bonusTextures;
import game.frameworks.impl.Vertices;

import java.util.LinkedList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class Explosion extends Sprite {
	

	
	public Explosion(float x,float y,float duration,int size){
	this.x=x;
	this.y=y;
	this.duration=duration;
	this.size=size;
	/*
	float vertices[]=new float[]{
			-6,-6,	0, 1,
			6,-6		,1,1,
			6,6,			1,0,
			-6,6,			0,0,
	};
	short indices[]= new short[]{
			0,1,2,
			2,3,0
	};

	shape=Vertices.create(vertices,indices,	 4);
	*/
	shape=Vertices.getRectangularShape(10, 10);
	
	
	
	}//const
	
	@Override
		public void update(float dt){
		super.update(dt);
			elapsedTime+=dt;
			if(elapsedTime>=duration)
				alive=false;
			
			/**NOW USE RANDOMIZE TO GIVE IT A DYNAMIC EFFECT**/
			angle+=rand.nextFloat()*100;//-45;//angle will be between -45 to +45
			axisX=rand.nextFloat();
			axisY=rand.nextFloat();
			axisZ=rand.nextFloat();
			//axisZ=-1;
		}//update
	
	
	public void draw(GL10 gl){
		
		shape.bind(gl);
		bonusTextures[6].bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle,axisX, axisY, axisZ);
		gl.glScalef(size, size, 0);
		
		shape.draw(gl);
		
		
	//	shape.unbind(gl);
		bonusTextures[6].unbind();
	}//draw
	
	
	
	public static Explosion create(float x,float y,float duration,int size){
		if(free.isEmpty())
			return new Explosion(x,y,duration,size);
		Explosion e=free.removeFirst();
	//	Log.d("Explosion ", "free "+free.size());
		e.x=x;
		e.y=y;
		e.size=size;
		e.duration=duration;
		/*
		float vertices[]=new float[]{
				-6,-6,	0, 1,
				6,-6		,1,1,
				6,6,			1,0,
				-6,6,			0,0,
		};
		short indices[]= new short[]{
				0,1,2,
				2,3,0
		};

		e.shape=Vertices.create(vertices,indices,	 4);
		*/
		e.shape=Vertices.getRectangularShape(10, 10);
		e.alive=true;
		e.elapsedTime=0;
		return e;
	}
	
	public void recycle(){
		free.addFirst(this);
		shape.recycle();
	}
	
	float duration,elapsedTime;
	Vertices shape;
	int size;
	public static LinkedList<Explosion> free=new LinkedList<Explosion>();
	public static final int SMALL=2;
	public static final int MEDIUM=10;
	public static final int LARGE=15;
	public static final int VERY_LARGE=25;
	
	private float axisX,axisZ,axisY,angle;
	public static final float SHORT=.35f;
	public static final float LONG=1;
	public static Random rand=new Random(90);
}
