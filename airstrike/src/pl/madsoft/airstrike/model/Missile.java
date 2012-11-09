package pl.madsoft.airstrike.model;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Missile {

	public static final float SPEED = 0.22f;
	public static final int HITPOWER = 1;
	
	Vector2 position = new Vector2();
	Vector2 velocity = new Vector2(0, SPEED);
	
	private float lifeTime = 45;

	private Actor actor;
	private int hitPower = HITPOWER;
	
	public float getLifeTime() {
		return this.lifeTime;
	}
	
	public Missile(float x, float y) {
		
		this.position.set(x, y);
	}

	public Missile(Vector2 position) {

		this.position.set(position);
		Gdx.app.log(AirStrikeGame.LOG, "missile position: " + getPosition().toString());

	}	
	
    public Vector2 getPosition() {
		return position;
	}
    
    public void setVelocity(Vector2 velocity) {
    	this.velocity = velocity;
    }

	public void updatePosition() {

		Vector2 newPosition = new Vector2(position);

		newPosition.add(velocity);
		position.set(newPosition);
		
		if (lifeTime > 0) {
			lifeTime--;
		}
	}
	
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public int getHitPower() {
		return this.hitPower;
	}	
}
