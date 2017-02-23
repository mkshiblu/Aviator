package game.frameworks;

import game.sprites.Enemy;
import game.sprites.Player;
import game.sprites.Sprite;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public interface World {
	void readMap(String fileName);
	void loadInitialScreen();
	void reloadResources();
	

	void drawWorld(GL10 gl);
	void drawSprites(GL10 gl);
	
	
	Player getPlayer();
	LinkedList<Enemy> getEnemies();

	LinkedList<Sprite> getBonus();
	void scroll(float dt);
	void update(float dt);
}
