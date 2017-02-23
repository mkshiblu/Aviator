package game.frameworks;

import javax.microedition.khronos.opengles.GL10;

import game.frameworks.impl.Game;

public abstract class Screen {
	Game game;
	protected Screen(Game game){
		this.game=game;
	}
	
	public abstract void update(float dt);
	public abstract void render(GL10 gl);
	
	public abstract void resume();
	public abstract void pause();

	public abstract void dispose();
	
}
