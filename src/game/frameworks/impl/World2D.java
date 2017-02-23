package game.frameworks.impl;

/*
import static game.aviator.GameConstants.SCREEN_WIDTH;
import static game.aviator.GameConstants.SCREEN_HEIGHT;
import static game.aviator.GameConstants.HORIZONTAL_MOTION;
import static game.aviator.GameConstants.RAILGUN;
import static game.aviator.GameConstants.ROWS_IN_A_SINGLE_SCREEN;*/


import static game.aviator.GameResource.backgrounds;
import static game.aviator.GameResource.bonusTextures;
import static game.aviator.GameResource.enemyTextures;
import static game.aviator.GameResource.hudTextures;
import static game.aviator.GameResource.projectileTextures;
import static game.aviator.Util.getX;
import static game.aviator.Util.getY;
import game.aviator.Aviator;
import game.aviator.GameConstants;
import game.aviator.GameResource;
import game.frameworks.World;
import game.motion.BossMotion;
import game.motion.DiagonalMotion;
import game.motion.HorizontalMotion;
import game.motion.VerticalMotion;
import game.sprites.BombBonus;
import game.sprites.DangerBonus;
import game.sprites.Enemy;
import game.sprites.GunUpgrade;
import game.sprites.HealthBonus;
import game.sprites.LifeBonus;
import game.sprites.MissileBonus;
import game.sprites.Player;
import game.sprites.ShieldBonus;
import game.sprites.Sprite;
import game.weapons.BossGun;
import game.weapons.RailGun;
import game.weapons.TripleGun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class World2D implements World,GameConstants {
	
	
	
	public World2D(Game game,int level){
		this.game=game;
		mapLines=new LinkedList<String>();
		enemies=new LinkedList<Enemy>();
		bonus=new LinkedList<Sprite>();
		gameResource=new GameResource(game);
		
		//Log.d("laoding","level "+level);
		/*/**READ TEXTURES*******/
		backgrounds=gameResource.LoadTextureArray("textures/backgrounds"+level+".txt","backgrounds_level"+level);
		enemyTextures=gameResource.LoadTextureArray("textures/planes.txt","planes");
		projectileTextures=gameResource.LoadTextureArray("textures/projectiles.txt","projectiles");
		bonusTextures=gameResource.LoadTextureArray("textures/bonus.txt","bonus");
		hudTextures=gameResource.LoadTextureArray("textures/hud.txt", "hud");
		currentMapLine=0;
		gameResource.readSpriteData("maps/data"+level+".txt");
		backgroundShape=Vertices.getRectangularShape(SCREEN_WIDTH, SCREEN_HEIGHT);
		
	}
	
	@Override
	public void readMap(String fileName) {
		
		FileManager fm=game.fileManager;
		
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader
					(fm.am.open(fileName)));//get access of txt file
			
			
			String line;
			while((line=br.readLine())!=null){
			
				if(!line.startsWith("START") && !line.startsWith("END")){//make sure only the map lines are added
					mapLines.add(line);
					currentMapLine++;
					//Log.d("load Map",line);
					
				}//if
			}//while
			
			Log.d("loadMap","total map Line is "+mapLines.size()+" current is "+currentMapLine);
			br.close();//close the file after finish reading
			
		} catch (IOException e) {	e.printStackTrace();		}//catch
		
	
	}//load map
	
	
	
	
	/**constructs the start screen of the game.also instantiate players and other gameobjects**/
	@Override
	public void loadInitialScreen() {
		
			/***************load player*************************/			
			player=new Player(160,80,50,50,enemyTextures[0],(byte)3,PLAYER_INITIAL_HEALTH,bonusTextures[5]);
			
	/**ITERATE OVER THE MAPLINE TO FIND ENEMIES ETC***/		
			
			for(int j=1;j<=ROWS_IN_A_SINGLE_SCREEN;j++){
		
				parseNextmapLine(j);
			}//for	
			Log.d("loadSprite", enemies.size()+"");
			mapReadTime=System.currentTimeMillis();
	}//load Sprites
	
	
	
	/**read new map line after a certain time and add enemies etc to the screen**/
	
	@Override
	public void update(float dt) {
		
		if(currentMapLine>0 && System.currentTimeMillis()-mapReadTime>=MAP_LINE_UPDATE_TIME){   ///parse map line if there is more lines to be parsed
			parseNextmapLine(ROWS_IN_A_SINGLE_SCREEN);//top most line is the parsing line so y res will be at most
			mapReadTime=System.currentTimeMillis();
		}//if
		else if(currentMapLine<=0 && Aviator.enemies.isEmpty() &&Aviator.gameWin==false){//means boss is dead and end of map
			Aviator.gameWin=true;
			//Aviator.currentGameState=GameState.Finished;
		}
	}//update
	
	long mapReadTime=System.currentTimeMillis();
	
	/**parse a map line **/
	private void  parseNextmapLine(int row){
		
		String line=mapLines.get(--currentMapLine);
		char c;
		for(int i=0;i<line.length() && (c=line.charAt(i))!='|';i++){
			//char c=line.charAt(i);
		
			if(c=='E'){
		
				short[] edata=gameResource.dequeEnemyData();
				Enemy e=Enemy.create(getX(i),//initial drawing x
						getY(row),//initial drawing y
						edata[0],//width is saved in 0 position of edata
						edata[1],//height is saved in 1 positon of edata
						enemyTextures[edata[2]],edata[3]);//texture is saved in 2 index in edata
						
				/***NOW SET MOTION AND WEAPONS***/
				if(edata[4]==HORIZONTAL_MOTION)//motion is saved in 4 idnex
				e.setMotion(HorizontalMotion.create(e,  edata[5]));
				
				
				
				else if(edata[4]==DOWN_MOTION)
					e.setMotion(VerticalMotion.create(e, DOWN,edata[6]));		///speed Y is saved in 6 index
				else if(edata[4]==DIAGONAL_MOTION)
					e.setMotion(DiagonalMotion.create(e, edata[5], edata[6], (byte) edata[11], (byte)edata[12]));
				else if(edata[4]==BOSS_MOTION)//motion is saved in 4 idnex
					e.setMotion(BossMotion.create(e,  edata[5]));
					
		//**************weapon*****/		
				if(edata[7]==RAILGUN)				//weapon is saved in 5
					e.weapon=new RailGun(e,edata[8],edata[9],projectileTextures[edata[10]]);//temprary shud use indexed textures of bullet
				
				else if(edata[7]==TRIPLEGUN)
					e.weapon=new TripleGun(e,edata[8],edata[9],projectileTextures[edata[10]],(short)100);	
				
				else if(edata[7]==BOSS_GUN)
					e.weapon=new BossGun(e,edata[8],edata[9],projectileTextures[edata[10]],(short)50);
				
				
				enemies.add(e);//add it to the enemy list
				
				//temp code
			}//if
			
			else if(c=='U'){		//an upgrade found
				GunUpgrade gu=new GunUpgrade(getX(i), getY(row), (short)40, (short)40, bonusTextures[0],(short)100 );
				gu.setMotion(VerticalMotion.create(gu, DOWN,(short) 70));
				bonus.add(gu);
			}//

			else if(c=='B'){		//an upgrade found
				BombBonus bb=new BombBonus(getX(i), getY(row), (short)40, (short)40, bonusTextures[1],(short)500,bonusTextures[2] );
				bb.setMotion(VerticalMotion.create(bb, DOWN,(short) 10));
				bonus.add(bb);
			}//bomb
			
			else if(c=='S'){		//shield found
				ShieldBonus sb=new ShieldBonus(getX(i), getY(row), (short)40, (short)40, bonusTextures[4],(short)100 );
				sb.setMotion(VerticalMotion.create(sb, DOWN,(short) 70));
				bonus.add(sb);
			}
			else if(c=='M')
			{
				MissileBonus mb=new MissileBonus(getX(i), getY(row), (short)45, (short)40, bonusTextures[10],(short)500 );
				mb.setMotion(VerticalMotion.create(mb, DOWN,(short) 70));
				bonus.add(mb);
			}
			else if(c=='H'){
				HealthBonus hb=new HealthBonus(getX(i), getY(row), (short)40, (short)40, bonusTextures[9],(short)100 );
				hb.setMotion(VerticalMotion.create(hb, DOWN,(short) 70));
				bonus.add(hb);
				
			}// health
			
			else if(c=='L'){
				LifeBonus lb=new LifeBonus(getX(i), getY(row), (short)40, (short)40, bonusTextures[7],(short)100 );
				lb.setMotion(VerticalMotion.create(lb, DOWN,(short) 70));
				bonus.add(lb);
				
			}// life
			
			else if(c=='D'){
				DangerBonus db=new DangerBonus(getX(i), getY(row), (short)40, (short)40, bonusTextures[8],(short)100 );
				db.setMotion(VerticalMotion.create(db, DOWN,(short) 70));
				bonus.add(db);
				
			}// danger
			
		}//for
		
	}//parser
	
	@Override
	public void drawWorld(GL10 gl) {
		//draw 1st background
		backgrounds[background_index].bind();
		backgroundShape.bind(gl);
		
		gl.glLoadIdentity();
		
		gl.glTranslatef(160, backY, 0);
		backgroundShape.draw(gl);
		backgrounds[background_index].unbind();
		
		//draw second background
		backgrounds[background_index+1].bind();
		gl.glLoadIdentity();	
		gl.glTranslatef(160, backY+SCREEN_HEIGHT, 0);
		backgroundShape.draw(gl);
		
		backgrounds[background_index+1].unbind();
		
		
		//backgroundShape.unbind(gl);
	
	}
	public static boolean endOfMapReached;
	@Override
	public void scroll(float dt) {
		
			
		backY=backY-MAP_SCROLL_SPEED*dt;
		
		if(backY+(SCREEN_HEIGHT>>1)<=0)
		{
			if(background_index==backgrounds.length-2){
				endOfMapReached=true;
				return;
			}
			backY+=SCREEN_HEIGHT;
			backgrounds[background_index].dispose();
			background_index++;
			
		}
		
	}//scroll


	@Override
	public void drawSprites(GL10 gl) {
		
		/*****RENDER ENEMIES***********/
					
					//Log.d("size", enemies.size()+"");
					for(int i=0;i<enemies.size();i++){
						Enemy e=enemies.get(i);
						e.draw(gl);
					}//for
					for(int i=0;i<bonus.size();i++)
						bonus.get(i).draw(gl);
					
					for(int i=0;i<Aviator.sprites.size();i++)
					{
						Sprite s=Aviator.sprites.get(i);
						s.draw(gl);
					}//for
	}


	
@Override
public Player getPlayer() {
	return player;
	}

@Override
public LinkedList<Enemy> getEnemies() {
	return enemies;
}

@Override
public LinkedList<Sprite> getBonus() {
	return bonus;
}

/**when resumes from a paused state all states of opengl are gone including textures
 * so reload the textures*/
@Override
public void reloadResources(){
	gameResource.reloadAllTextures();
}

Player player;
public Vertices playerModel;
Game game;
LinkedList<Enemy> enemies;
LinkedList<Sprite> bonus;

LinkedList<String> mapLines;
/**indicates the currently loading map line**/
int currentMapLine;
GameResource gameResource;
Vertices backgroundShape;
float backY=SCREEN_HEIGHT>>1;
byte background_index=0;

}//class
