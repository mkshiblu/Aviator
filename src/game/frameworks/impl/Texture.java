package game.frameworks.impl;


import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class Texture {
	
	private FileManager fm;
	private Graphics graphics;
	private String fileName;//bitmap takes a lot of memory instead save the filePath to reload
	
	public int textureId;
	
	public int width;
	public int height;
	
	public Texture(Game game,String bitmapPath){
		
		fm=game.fileManager;
		graphics=game.graphics;
		fileName=bitmapPath;
		
		load();
	}//constructor
	
	private void load(){
		GL10 gl=graphics.getGL();
		
		int textureIds[]=new int[1];//we are creating only one texture
		gl.glGenTextures(1, textureIds,0);//1 texture obj,array in which id generates 0 is the start index
		textureId=textureIds[0];//set offset at 0 causes the generate id at 0 index
		
		Bitmap bitmap=fm.loadBitmap(fileName);
		width=bitmap.getWidth();
		height=bitmap.getHeight();
		
		//now upload the bitmap in this id
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//first we have to bound the id 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		setFilters(GL10.GL_NEAREST,GL10.GL_NEAREST);
		
		//after uploading unbind this texture
		unbind();
	}//load
	 
	//after gl surface is destroyed we have to reload this call it in resume method
	public void reload(){
		load();
		bind();
		setFilters(GL10.GL_NEAREST,GL10.GL_NEAREST);
		unbind();
	}
	public void setFilters(int minFilter,int magFilter){
		
		GL10 gl=graphics.getGL();
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
		
	}//setfilters
	
	public void bind(){
		GL10 gl=graphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	}//bind
	
	public void unbind(){
		GL10 gl=graphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D,0);

	}
	
	public void dispose() {
		GL10 gl = graphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = { textureId };
		gl.glDeleteTextures(1, textureIds, 0);
		}

}//class
