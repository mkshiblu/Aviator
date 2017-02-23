package game.aviator;

import static game.aviator.GameConstants.PIXEL_PER_COLUMN;
import static game.aviator.GameConstants.PIXEL_PER_ROW;
import android.app.Activity;
import android.util.FloatMath;
import android.view.Window;
import android.view.WindowManager;

public class Util {
	
	public static void setFullScreen(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	/**when parsing textfile sprites's initial x is determined by the column number this method calc that**/
	public static float getX(int column){
		return column*PIXEL_PER_COLUMN;
	}//getX
	
	public static float getY(int row){
		return row*PIXEL_PER_ROW;
	}
	
	public static float distance(float a,float b){
		return FloatMath.sqrt(a*a+b*b);
	}
	public static float distanceSquared(float a,float b){
		return (a*a+b*b);
	}
	
}

