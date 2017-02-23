package game.motion;

import game.sprites.Sprite;

public class GuidedMotion extends Motion {

	float angle;
	int destX;
	int destY;
	protected GuidedMotion(Sprite sprite,int x ,int y) {
		super(sprite);
		destX=x;
		destY=y;
	}

	@Override
	public void move(float dt) {
		
	}

	@Override
	public void recycle() {
		
		
	}

	
}
