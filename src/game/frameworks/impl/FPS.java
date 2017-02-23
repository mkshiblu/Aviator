package game.frameworks.impl;

import android.util.Log;

public class FPS {
	  int frames;
	 long startTime=System.nanoTime();
	 public int currentFPS;
	public  void logFrameRate(){
		frames++;
		if(System.nanoTime()-startTime>=1000000000){
			Log.d("FPS",""+frames);
			currentFPS=frames;
			frames=0;
			startTime=System.nanoTime();
		}//if
	}
}
