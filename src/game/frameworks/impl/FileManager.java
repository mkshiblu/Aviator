package game.frameworks.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class FileManager {
	public AssetManager am;
	
	public FileManager(Activity gameActivity){
		am=gameActivity.getAssets();
	}//constructor
	
	public Bitmap loadBitmap(String fileName){
		InputStream is=null;//stream of the file
		Bitmap bitmap=null;
		Log.d("loadBitmap", fileName);
		
		try{ 
			is=am.open(fileName);  				 //returns a inputStream of the file hosted in asset
			bitmap=BitmapFactory.decodeStream(is);	//returns a bitmap object
		}catch (IOException e){
			throw new RuntimeException("couldn't load "+fileName);
		}//catch
		finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace(); Log.d("FileManager", "erro in loading "+fileName);
			} 
		}
		
		
		return bitmap;
	}
	/*
	public void writeString(String fileName,String string){
		String state=Environment.getExternalStorageState();
		if(!state.equals(Environment.MEDIA_MOUNTED)){
			Log.d("fm", "no sd card");
			return;
		}
		File sdCard=Environment.getExternalStorageDirectory();
		File textFile=new File(sdCard.getAbsolutePath()+File.separator+fileName);
		//now write
		BufferedWriter writer=null;
		try {
			 writer=new BufferedWriter(new FileWriter(textFile));
			writer.write(string);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{			
			try {
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}//finally
		
		
	}//write*/
	
	public BufferedReader getBufferedReader(String fileName) throws IOException{
		
		return new BufferedReader(new InputStreamReader(am.open(fileName)));
	}
}
