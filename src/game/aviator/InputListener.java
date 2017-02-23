package game.aviator;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static game.aviator.Aviator.sprites;
import static game.aviator.GameConstants.SCREEN_HEIGHT;
import game.frameworks.impl.Game;
import game.sprites.Player;
import game.weapons.Bomb;
import game.weapons.Missile;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class InputListener implements OnTouchListener/*,SensorEventListener*/ {
	Player player;
	GLSurfaceView glView;
	Activity activity;
	
	
	public InputListener(Game activity,Player player){
		this.player=player;
		this.glView=activity.glView;
		this.activity=activity;
		/*
		SensorManager sm=(SensorManager)activity.getSystemService(Context.SENSOR_SERVICE);
		if(sm.getSensorList(Sensor.TYPE_ACCELEROMETER).size()==0 )//no accelerometer
			Log.d("accel","No accelerometer installed");
		else
		{
			Sensor accelerometer=sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			if(!sm.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME))
				Log.d("accel","Couldn't register sensor listener");
		}*/
	}//constructor
	
	boolean bottomDown;
	boolean upperTouched;
	@Override
	public boolean onTouch(View v, MotionEvent e) {
		
		float x=e.getX()*320.0f/glView.getWidth();
		float y=e.getY()*480.0f/glView.getHeight();
		
	
	
		y=480-y;
		synchronized(this){
		
			switch(e.getAction()){
			
			case ACTION_UP:
			
				
				
				planeTouched=false;
				
				if(!player.contains(x,y) && y<=SCREEN_HEIGHT>>1 && !player.bombs.isEmpty() && bottomDown)
				{
					Bomb b=player.bombs.removeFirst();
					b.x=player.x;
					b.y=player.y+(player.height>>1);
					
					sprites.add(b);
					bottomDown=false;
					Log.d("action up", "condition true");
				}
				
				
/***********check if user press pause button*******/				
				else if(x>=140 && x <=180 && y>=450){
					synchronized(Game.currentGameState){
					Game.currentGameState=Game.GameState.Paused;//prevent glview from drawing
					glView.onPause();

					}//state
					activity.setContentView(R.layout.pause);
					
				}//if
				
				
				else if(y>=240 && player.missiles>0)
				{
					//if(!Aviator.enemies.isEmpty())
						Aviator.sprites.add(new Missile(player.x,player.y,20,40,GameResource.projectileTextures[0],Aviator.enemies.peekFirst()));
						player.missiles--;
			//		upperTouched=true;
				}
				break;
			
			case ACTION_DOWN:
				if(player.contains(x,y))  //**check if user touches the plane
					planeTouched=true;
				
				else	if(y<=SCREEN_HEIGHT>>1)  /**if user dont touch the plane then check if the touchEvent Occurs in the bottom half**/
				{
					bottomDown=true;
				}
				Log.d("input", "touch x "+x+" y "+y+" inside "+player.contains(x, y));
				break;
				
			case ACTION_MOVE:
				
				if(planeTouched==true){//touchevent occurs inside the plane
		
					player.x=x;
					player.y=y+20;
				}//if
				break;
				
		}//switch
		
		
		}//synch
		return true;
	}//ontouch

boolean planeTouched;
boolean firePressed;

	void processInput(float dt){
		/*
		if(upperTouched && player.missiles>0)
		{
			upperTouched=false;
			if(!Aviator.enemies.isEmpty())
			Aviator.sprites.add(new Missile(player.x,player.y,10,20,GameResource.projectileTextures[0],Aviator.enemies.peekFirst()));
			player.missiles--;
		}*/
	}//process inout

/*
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent e) {
		
		float x=e.values[0];
		
		if(x<0 && player.x>0)
			player.x+=x;
		
		
		if(x>=0 && player.x<320)
		player.x+=x;
		//player.y=y;

	}*/
	
}
