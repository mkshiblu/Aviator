package game.sprites;

import static game.aviator.GameResource.projectileTextures;
import game.frameworks.Weapon;
import game.frameworks.impl.Texture;
import game.weapons.Bomb;
import game.weapons.PentaGun;
import game.weapons.RailGun;
import game.weapons.TripleGun;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

public class Player extends Sprite{

	RailGun railgun=new RailGun(this,PLAYER_FIRE_RATE,PLAYER_BULLET_SPEED,projectileTextures[0]);
	//TripleGun=new RailGun(this,PLAYER_FIRE_RATE,PLAYER_BULLET_SPEED,projectileTextures[0]);
	
	public Player(float x, float y,int width,int height,Texture texture,byte life,short health,Texture shield) {
		super(x, y,width,height,texture);
		bombs=new LinkedList<Bomb>();
		this.life=life;
		this.health=health;
		weapon=railgun;
	//	weapon=new BossGun(this,PLAYER_FIRE_RATE,PLAYER_BULLET_SPEED,projectileTextures[0],(short)100);
		this.shieldTexture=shield;
	}//constructor

	public void update(float dt){

		
		if(weapon!=null &&weapon.isReadyToShoot(dt)){
			weapon.shoot(UP);
		//	sound.play(sound.RAILGUN);
		}
		
		if(shielded==true){
			elapsedTime+=dt;
			if(elapsedTime>=PLAYER_SHIELDED_TIME)
			{
				shielded=false;
				elapsedTime=0;
				angle=0;
			}//if
			else {
			angle+=400*dt;
			if(angle>=360)
				angle-=360;
			}//else
		}//shielded
	}//update

	@Override
	public void draw(GL10 gl){
		shape.bind(gl);
		
		if(shielded){
		shieldTexture.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 0,-1);
		gl.glScalef(1.5f, 1.5f, 0);
		shape.draw(gl);
		shieldTexture.unbind();
		}
		
		texture.bind();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		
		shape.draw(gl);
		texture.unbind();
		
		//shape.unbind(gl);
	}
	
	public void downGradeWeapon(){
		if(weapon instanceof PentaGun)
			weapon=new TripleGun(this,(short) (PLAYER_FIRE_RATE*1.5f),(short) (PLAYER_BULLET_SPEED*1.5),projectileTextures[1],(short)50);
		else if(weapon instanceof TripleGun)
			weapon=railgun;
	}//downgrade
	public  byte life;
	public short health;
	public boolean shielded;
	public Texture shieldTexture;
	public Weapon weapon;
	public LinkedList<Bomb> bombs;
	public float angle;
	public float elapsedTime;
	public int missiles=10;
}
