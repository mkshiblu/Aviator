package game.sprites;

public class Vector2D  {
	
	float x;
	float y;
	static final float TO_DEGREE=(float) (Math.PI/180);
	public Vector2D(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public float angle(){
		float ang=(float) Math.atan2(y, x)*TO_DEGREE;
		if(ang<0)
			ang+=360;
		return ang;
	}
}
