package game.frameworks.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class Vertices {
	
	FloatBuffer vertexBuffer;
	ShortBuffer indicesBuffer;
	//Graphics graphics;
	final int VERTEX_SIZE=(2+2)*4;//size of a vertex in bytes x,y,s,t * floatsize 
	
	public Vertices(float []vertices,short indices[]){
		
	//	this.graphics=graphics;
		ByteBuffer bb=ByteBuffer.allocateDirect(VERTEX_SIZE*4);//a square is consist of 4 vertex
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer=bb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.flip();//set position  to 0 and limit to capacity
		
		//now indices buffer
		bb=ByteBuffer.allocateDirect(indices.length*2);//2 bytes each element costs
		bb.order(ByteOrder.nativeOrder());
		indicesBuffer=bb.asShortBuffer();
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		
	}//const
	
	/**call this before drawing**/
	public void bind(GL10 gl){
		//gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//enable vertex
		
		vertexBuffer.position(0);//x,y start from 0
		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertexBuffer);
		
		//now enable texture cooord
//		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		vertexBuffer.position(2);//s,t starts from 2 index 
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertexBuffer);//2 variable s,t
		
	}//bind
	/**call this to draw this model**/
	public void draw(GL10 gl){
		//GL10 gl=graphics.getGL();
		
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
	}//draw
	
	
	/**call this after finish drawing**/
	/*
	public void unbind(GL10 gl){
	//	GL10 gl=graphics.getGL();
		
	//	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}//unbind
	*/
	
	/**a convinient method to create a textured shape;**/
	public static Vertices getRectangularShape(int width,int height){
		
		
		float vertices[]=new float[]{
				-width>>1,-height>>1, 0,1,
				width>>1,-height>>1, 1,1,
				width>>1,height>>1,  1,0,
				-width>>1,height>>1,0,0
		};
		short indices[]= new short[]{
				0,1,2,
				2,3,0
		};
		
		if(free.isEmpty())		
		return new Vertices(vertices,indices);
		
		//else
		Vertices newV=free.removeFirst();
		newV.setVertices(vertices);
		
		newV.setIndices(indices);
		
		return newV;
	}
	
	public void setVertices(float []vertices){
		vertexBuffer.clear();
		vertexBuffer.put(vertices);
		vertexBuffer.flip();
	}
	
	public void setIndices(short []indices){
		indicesBuffer.clear();
		indicesBuffer.put(indices);
		indicesBuffer.flip();
	}
	
	public void recycle(){
		free.addFirst(this);
	}//recycle
	
	public Vertices(float []vertices,short indices[],int numberOfVertex){
		
		//	this.graphics=graphics;
			ByteBuffer bb=ByteBuffer.allocateDirect(VERTEX_SIZE*numberOfVertex);//a square is consist of 4 vertex
			bb.order(ByteOrder.nativeOrder());
			vertexBuffer=bb.asFloatBuffer();
			vertexBuffer.put(vertices);
			vertexBuffer.flip();//set position  to 0 and limit to capacity
			
			//now indices buffer
			bb=ByteBuffer.allocateDirect(indices.length*2);//2 bytes each element costs
			bb.order(ByteOrder.nativeOrder());
			indicesBuffer=bb.asShortBuffer();
			indicesBuffer.put(indices);
			indicesBuffer.flip();
			
			
		}//const
	
	
	public static Vertices create(float []vertices,short indices[],int numberOfVertex){
		if(free.isEmpty())		
			return new Vertices(vertices,indices);
			
			//else
			Vertices newV=free.removeFirst();
			newV.setVertices(vertices);
			
			newV.setIndices(indices);
			
			return newV;
	}
	
	private static LinkedList<Vertices> free=new LinkedList<Vertices>();
}//class
