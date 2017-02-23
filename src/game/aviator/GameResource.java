package game.aviator;


import game.frameworks.impl.Game;
import game.frameworks.impl.Texture;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import android.util.Log;

public class GameResource implements GameConstants {

	
	 
	short enemyData[][]={
		//																										<<<<<<<<<<<<>>>WEAPON DATA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>		
		//0			1			2				3				 4					5				6				7			8					9				10	
		//width  | height | 	texture | 		HEALTH|			Motion |			speedX	|	speedY  |		Weapon 		| Rate of Fire | Speed of Projectile  	projectileImage
		{45	,		50,			1,	  			10,			HORIZONTAL_MOTION,		100,		0,				RAILGUN,		160,				300,			1					},
		{32, 	    40,			2,				10,			HORIZONTAL_MOTION,		50,			0,				RAILGUN,		100,				400,			2					},
		{32, 	    38,			3,				15,			DOWN_MOTION,			0,			100,			RAILGUN,		300,				200,			1					},
		{32	,		42,			4,				20,			DOWN_MOTION,			0,			60,	    		RAILGUN,		150,				1010,			2					},
		{32, 	    40,			6,				16,			HORIZONTAL_MOTION,		160,		0,				RAILGUN,		110,				190,			0					},
		{35, 	    45,			7,				17,			HORIZONTAL_MOTION,		0,			250,				0,			0,					0,				0					},
																																										//11x 12				
		{30	,		45,			9,				19,			DIAGONAL_MOTION,		40,			200,    		RAILGUN,		70,					500,			1	,LEFT,DOWN				},
		{40	,		50,			10,				5,			DOWN_MOTION,			0,			50,    			RAILGUN,		70,					500,			1					},
		{35	,		55,			11,				4,			DOWN_MOTION,			0,			50,    			RAILGUN,		70,					500,			1					},
		
//BOSS=10th index	
		{160	,  100,			12,			BOSS_HEALTH,	BOSS_MOTION,			150,		0,    			BOSS_GUN,		70,					500,			1					},
//								?							//?
	};//enemy Data
	
	 
	private int edatafront=0;
	public short[] dequeEnemyData(){
		return enemyData[data[edatafront++]];
	}
	

	/**read enemy data from the data file and save it in enemy data**/
public void readSpriteData(String fileName) {
		try{
			LinkedList<String> num=new LinkedList<String>();
			
			BufferedReader br=(game.fileManager.getBufferedReader(fileName));
			String s;
			while((s=br.readLine()) !=null)
				num.add(s);
			
			data=new int[num.size()];
				
			/**convert the strings into number*/
			for(int i=0;i<data.length;i++){
				data[i]=Integer.parseInt(num.removeFirst());
			}//for
			
		}catch(IOException e){}
		
	}//
	

/**
 * loads various textures from a given file where all the name of a specific texture array
 * @param fileName The .txt file where the names of the textures are written
 * @param folderName if the images are in a folder inside "textures" folder then give it the name and a slash / after the folder name 
 * if the images are directly in the textures then use "" instead
 * @return an array of textures
 */
	public  Texture[] LoadTextureArray(String fileName,String folderName){
		ArrayList<Texture> temp=new ArrayList<Texture>();
		
		try{
			BufferedReader  br=game.fileManager.getBufferedReader(fileName);
			String filePath;
			
			while((filePath=br.readLine())!=null){			 
				temp.add(new Texture(game, "textures/"+folderName+"/"+filePath));
			}
			br.close();
			Log.d("Loadtexturearray","total number of texture loaded : "+temp.size());
			}catch(IOException e){	Log.d("loadTextureArray", "Error in loading texture "+fileName); }
			
		Texture arr[]=new Texture[temp.size()];
		
		 temp.toArray(arr);//for efficiency array list is converted into array
		 
		return arr;
	}
	
	
	public void reloadAllTextures(){
		for(int i=0;i<enemyTextures.length;i++)
			enemyTextures[i].reload();
		for(int i=0;i<backgrounds.length;i++)
			backgrounds[i].reload();
		for(int i=0;i<=9;i++)
			numbers[i].reload();
		for(int i=0;i<bonusTextures.length;i++)
			bonusTextures[i].reload();
		for(int i=0;i<hudTextures.length;i++)
			hudTextures[i].reload();
		for(int i=0;i<projectileTextures.length;i++)
			projectileTextures[i].reload();
		
		
	}//reload
	
	public void loadNumbers(String fileName){
		
		for(int i=0;i<=9;i++)
			numbers[i]=new Texture(game,fileName+i+".png");
		
	}//load numbers
	public GameResource(Game game){
		this.game=game;

		 enemyTextures=null;
		backgrounds=null;
		 projectileTextures=null;
		numbers=null;
		bonusTextures=null;
		hudTextures=null;
		numbers=new Texture[10];
		loadNumbers("textures/numbers/");
	}//res

	
	/**holds all the texture used in this game**/

	public static Texture[] enemyTextures;
	public static Texture backgrounds[];
	public static Texture projectileTextures[];
	public static Texture[] numbers;
	public static Texture[] bonusTextures;
	public static Texture[] hudTextures;
	private int data[];
	
	Game game;
	
	

}//class
