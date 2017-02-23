package game.frameworks.impl;

import static game.aviator.Util.setFullScreen;
import game.frameworks.Screen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;

public abstract class Game extends Activity implements Renderer {
	
	public  FileManager fileManager;
	public static SoundManager sound;
	public GLSurfaceView glView;
	public Graphics graphics;
	protected Screen screen;
	protected FPS fps;
	
	public static enum GameState{
			Initialized,
			Running,
			Paused,
			Finished,
			Resumed
			}
	
	public static GameState currentGameState;
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
				init();
	}//onCreate
	
	
	private void init(){
		currentGameState=GameState.Initialized;
		setFullScreen(this);
		
		fileManager=new FileManager(this);
		sound=new SoundManager(this);
		sound.loadAll();//load all the sounds
		fps=new FPS();

		
		
	}//init
	
	
	public abstract Screen getStartScreen();
	
	
	
	
	
	@Override
	public void onResume(){
		super.onResume();
		if(glView!=null)
		glView.onResume();
		//currentGameState=GameState.Running;
		//wake loack
	}
	
	
	@Override
	public void onPause(){
		super.onPause();
		if(glView!=null)
		glView.onPause();
		
		finish();//not working properly
	}
	
	
	
	@Override 
	public void onSurfaceCreated(GL10 gl,EGLConfig config){
		graphics.setGL(gl);
		
		synchronized(currentGameState){						
			if(currentGameState==GameState.Initialized)		//callled only once
				screen=getStartScreen();
			
			currentGameState=GameState.Running;
			screen.resume();
			startTime=System.nanoTime();	
			
			/**on Resumed clicked**/
		 if(currentGameState==GameState.Resumed){
				currentGameState=GameState.Running;
				screen.dispose();
				screen.resume();
			}//	
		}//state
		
		Log.d("On surface Created", "state "+currentGameState);
	}//surface create
	
	long startTime;
	@Override
	public void onDrawFrame(GL10 gl){
		
		synchronized(currentGameState){
			
			if(currentGameState==GameState.Running){
				screen.update((System.nanoTime()-startTime)/1000000000.0f);//frame rate independent movement
			
				
				startTime=System.nanoTime();
				screen.render(gl);
			}//if running
			
				
			/**on Resumed clicked**/
			else if(currentGameState==GameState.Resumed){
				currentGameState=GameState.Running;
				screen.resume();
			}//
			
			else if(currentGameState==GameState.Finished){
				screen.dispose();
				glView.onPause();
				currentGameState.notifyAll();
			}
		}//sy nch
		
	
		
	
	}
	
	
	@Override 
	public void onSurfaceChanged(GL10 gl,int width,int height ){
		
	}
	
	
	
	
}
