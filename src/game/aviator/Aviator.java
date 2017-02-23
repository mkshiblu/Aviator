package game.aviator;


import static game.aviator.GameConstants.ENEMY_BULLET;
import static game.aviator.GameConstants.PLAYER_BULLET;
import static game.aviator.GameConstants.PLAYER_INITIAL_HEALTH;
import static game.aviator.GameResource.backgrounds;
import static game.aviator.GameResource.bonusTextures;
import static game.aviator.GameResource.enemyTextures;
import static game.aviator.GameResource.hudTextures;
import static game.aviator.GameResource.numbers;
import static game.aviator.GameResource.projectileTextures;
import static game.frameworks.Weapon.projectiles;
import game.frameworks.Screen;
import game.frameworks.World;
import game.frameworks.impl.Game;
import game.frameworks.impl.Graphics;
import game.frameworks.impl.Vertices;
import game.frameworks.impl.World2D;
import game.sprites.Enemy;
import game.sprites.Explosion;
import game.sprites.Player;
import game.sprites.Projectile;
import game.sprites.Sprite;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Aviator extends Game {

	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}//onCreate
	
	
	
	public void onNewGameClick(View v){
		synchronized(currentGameState){
			currentGameState=GameState.Initialized;
		}//synch
	
		gameOver=false;
		glView=new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView); //start new game
		graphics=new Graphics(glView);
		
	}
	
	/**called when user click resume button from pause menu**/
	public void onResumeClick(View v){
		
		synchronized(currentGameState){
			currentGameState=GameState.Resumed;
		}//synch
			//screen.dispose();
		
		glView.onResume();
		
		glView=new GLSurfaceView(this);
		glView.setRenderer(this);
		glView.setOnTouchListener(in);
		setContentView(glView);
	graphics=new Graphics(glView);
		
		//setContentView(glView);
		//screen.resume();
		
	}//resume
	
	public void onMainMenuClick(View v){
		setContentView(R.layout.activity_main);
	}
	
	public void onExitClick(View v ){
		if(screen!=null)
		screen.dispose();//and also remove all the resource
		synchronized(currentGameState){
			currentGameState=GameState.Finished;
		}//this will tear down the gl thread
		finish();
	}//exit
	
	
	public void onOptionClick(View v){
		setContentView(R.layout.opt);
	}
	
	public void onBackClick(View v){
		setContentView(R.layout.activity_main);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class GameScreen extends Screen{
			

		
		
		 
		
		@Override
		public void update(float dt) {
		in.processInput(dt);
	
		
		if(World2D.endOfMapReached==false)//scroll background if end of the map is not reached
		world.scroll(dt);//scroll background
		world.update(dt);
		
		player.update(dt);
		
////////////////////		//*****update sprites************////////////////////////
		for(int i=0;i<enemies.size();i++){
			Enemy e=enemies.get(i);
			
			if(e.alive)
				e.update(dt);
			else
			{
				e.recycle();
				enemies.remove(i);
				
			}//if
		}///for
/*****************************UPDATE BOOOOOOOOONUUUUUUUUUSSSSSSSSSSS*************************/
		
		for(int i=0;i<bonus.size();i++){
			Sprite b=bonus.get(i);
			
			
			if(b.alive==true){
			b.update(dt);
			
				if((b.intersect(player))){				
					b.onPlayerCollision(player);
				bonus.remove(i);
				}
			}//if
			else
			{
				bonus.remove(i);
			}
			
		}///for
//***************************BULLETS*******************************************************		
			for(int i=0;i<projectiles.size();i++){
				Projectile bullet=projectiles.get(i);
				
				if(bullet.isDead==false){
					bullet.update(dt);
					
		/** check for collision of bullet***/			
			
					 if (bullet.dirY==ENEMY_BULLET && bullet.intersect(player) ){
					
						 sprites.add(Explosion.create(bullet.x, bullet.y, Explosion.SHORT, Explosion.SMALL));				
							
						 bullet.recycle();
							projectiles.remove(i);// temp projectiles.booomAnimation
							
							if(player.shielded==false){
							player.health-=10;//temporary
							
							if(player.health<=0){
								
								if(player.life<=0 && gameOver==false){
									//do things for gameOver
									gameOver=true;
								//	currentGameState=GameState.Finished;
									player.weapon=null;
									player.alive=false;
									player.shielded=false;
								}
								else{		//player has life
									
									player.life--;
									player.downGradeWeapon();
									player.health=PLAYER_INITIAL_HEALTH;
								}//else
							}//if
							
							}//shielld if
						}//if this bullet intersects player
					else	
					for(int j=0;j<enemies.size();j++){
						Enemy e=enemies.get(j);
					if(bullet.dirY==PLAYER_BULLET && bullet.intersect(e)){
/******************************************************************************************************************/						
						sprites.add(Explosion.create(bullet.x, bullet.y, Explosion.SHORT, Explosion.SMALL));
						
						bullet.recycle();
						projectiles.remove(i);// temp projectiles.booomAnimation
		//animation
						e.health--;// temp projectiles.booomAnimation
						
						if(e.health<=0)
						{
							e.alive=false;
							score=score+e.life;//temp reduce health
							
							Log.d("score",""+score);
						 }
						
						break;
						
						}//if intersect
					}//for enmey
				}//if
				//else if(bullet.i)
			
				else{
				bullet.recycle();
				projectiles.remove(i);
				}//else
			}//for

/*********************************************sprites****************/
			for(int i=0;i<sprites.size();i++)
			{
				Sprite s=sprites.get(i);
				if(s.alive)
				s.update(dt);
				else
				{	s.recycle();
					sprites.remove(i);
				}
			}//for
			
		scorer.update(dt);
	//	Log.d("size enemy", "enmey size"+enemies.size());
		}//update
	
		
		
		
		
		@Override
		public void render(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			world.drawWorld(gl);
			world.drawSprites(gl);
			
/*************DRAWING PLAYER***********************/			
			player.draw(gl);
		
/****************************DRAWING BULLLETS*******************/			//draw projectiles
			bulletModel.bind(gl);
	
			for(int i=0;i<projectiles.size();i++){
				Projectile b=projectiles.get(i);
				b.texture.bind();
				gl.glLoadIdentity();
				gl.glTranslatef(b.x, b.y, 0);
				bulletModel.draw(gl);
				b.texture.unbind();
			}//for
			
			
			//bulletModel.unbind(gl);	
/****************drawing score UI HUD etc****************/
			scorer.drawInteger(gl, 200, 470, score,(byte)6);
			scorer.drawHealthBar(gl,0, 460, player);
			scorer.drawLifeBar(gl, 10, 440, player);
			
			scorer.drawPlayerInventory(gl, 20, 20, player);
			scorer.drawTexture(gl, 160, 460, hudTextures[2]);//draw pause button
			
			if(gameOver)
			scorer.drawTexture(graphics.getGL(),160, 240, bonusTextures[12], 10);//game over pixture
			else if(gameWin)
				scorer.drawTexture(graphics.getGL(),160, 240, bonusTextures[11], 10);//game win pixture
			
/**************SHOW FPS/LOG FPS**********************/			
			scorer.drawInteger(gl, 280, 10, fps.currentFPS, (byte) 2);
			fps.logFrameRate();
			
		}//render
	
		@Override
		public void resume() {
			GL10 gl=graphics.getGL();
			gl.glClearColor(0, 0, 0, 1);
			
			gl.glViewport(0, 0, graphics.getWidth(), graphics.getHeight());
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			world.reloadResources();
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//enable vertex
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			

		}
	
		@Override
		public  void pause() {
			//setCOnventView pauselayout
		
		}
	
		/**all initialization code should be here***/		
		protected GameScreen(Game game) {
			super(game);		
						in=new InputListener(game,player);
						glView.setOnTouchListener(in);
						
		}//constructor





		@Override
		public void dispose() {
		/**disposing texture**/
			
			for(int i=0;i<enemyTextures.length;i++)
				enemyTextures[i].dispose();
			for(int i=0;i<backgrounds.length;i++)
				backgrounds[i].dispose();
			for(int i=0;i<=9;i++)
				numbers[i].dispose();
			for(int i=0;i<bonusTextures.length;i++)
				bonusTextures[i].dispose();
			for(int i=0;i<hudTextures.length;i++)
				hudTextures[i].dispose();
			for(int i=0;i<projectileTextures.length;i++)
				projectileTextures[i].dispose();
			//sprites=null;
			
			/***now dispose static array**/
			sprites.clear();
			enemies.clear();
			
			//soundmanager.dispose();
		}//dispose
	
		
	}//screen

	
	@Override
	public Screen getStartScreen() {
		
		score=0;
		gameWin=false;
		gameOver=false;
		world=new World2D(this,level);
		world.readMap("maps/level"+level+".txt");
		
		world.loadInitialScreen();
		player=world.getPlayer();
		enemies=world.getEnemies();
		bonus=world.getBonus();
		projectiles=new LinkedList<Projectile> ();
		scorer=new UIHud(/*(byte)20,(byte)30,(byte)40,(byte)50*/);
		
		bulletModel=Vertices.getRectangularShape(8, 8);
	//	enemyModel=Vertices.getRectangularShape(64, 64);
		
		
		return new GameScreen(this);
	}//getStart Screen
	
	
	World world;
	Player player;
	Vertices bulletModel;
	//Vertices enemyModel;
	
	UIHud scorer;
	InputListener in;
	public static LinkedList<Enemy> enemies;
	LinkedList<Sprite> bonus;
	public static LinkedList<Sprite> sprites=new LinkedList<Sprite>();
	public static int score;
	public int level=1;
	boolean gameOver;
	public static boolean gameWin;
	//public LinkedList<Missile> missiles=new LinkedList<Missile>();

}//outer
