package pl.madsoft.airstrike.model;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {

	public enum Type {
		MIG29, SU27
	}	
	
	public enum State {
		INFLIGTH
	}

	public static final float HEIGHT = 1.33f;
	public static final float WIDTH = 0.907f;

	protected Vector2 position;
	protected Vector2 velocity;

	Rectangle bounds = new Rectangle();
	State     state = State.INFLIGTH;
	
	protected Type type;
	
	final public Type getType() {
		return this.type;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return this.state;
	}
	
    public Vector2 getPosition() {
		return position;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
    
	public Vector2 getVelocity() {
		return velocity;
	}    
    
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Enemy(Vector2 position, float initVSpeed) {
	
		this.bounds.height = HEIGHT;
        this.bounds.width = WIDTH;

		this.position = position;
		setVelocity(new Vector2(0, initVSpeed));
		
		Gdx.app.log(AirStrikeGame.LOG, "new enemy: " + getPosition().toString() + " -> velocity: " + this.velocity.toString());		
	}
	
	public void updatePosition() {
		
		//Gdx.app.log(AirStrikeGame.LOG, "enemy position: " + getPosition().toString() + " -> velocity: " + velocity.toString());
		
		Vector2 newPosition = new Vector2(position);
		newPosition.add(velocity);

		position.set(newPosition);
	}

}
