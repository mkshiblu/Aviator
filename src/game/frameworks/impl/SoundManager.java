package game.frameworks.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	public  int RAILGUN;
	Activity activity;
	SoundPool soundPool;
	AssetManager am;
	
	
	public SoundManager(Activity activity){
		this.activity=activity;
		am=activity.getAssets();
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);//now + - button control the sounds
		
		soundPool=new SoundPool(32,AudioManager.STREAM_MUSIC,1);//how many sounds can play simultaneously ,where to ouput the sounds
		
	}
	
	public void loadAll(){
		RAILGUN=loadSound("railgun.mp3");
	}
	
	public int loadSound(String name){
		int id=0;
		try {
			id= soundPool.load(am.openFd("sounds/"+name),1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//assetfiledescriptor,priroty =1 for future
	
		return id;
	}//load
	
	public void play(final int soundID){
		new Thread(new Runnable(){

			@Override
			public void run() {
				soundPool.play(soundID, 1, 1, 0, 0, 1);
			}
			
		}).start();
	}
	
	public void dispose(){
		soundPool.release();
	}
}
