package pl.madsoft.airstrike.model;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player {

    private Actor actor;
	
	public enum State {
		INFLIGTH, FIRINGGUN, EXPLODING, DEAD
	}

	public static final float HEIGHT = 1.33f;
	public static final float WIDTH = 0.907f;
	
	public static final float MIN_POS_X = 0f;  
	public static final float MAX_POS_X = 7f - WIDTH;

	//public static final float START_V_SPEED = 0f;
	public static final float START_V_SPEED = 0.02667f; 
	//public static final float START_V_SPEED = 0.04f;
	
	public static final float START_POSITION_X = 0;
	public static final float START_POSITION_Y = 0;
	
	Vector2 position = new Vector2();
    Vector2 velocity = new Vector2(0, START_V_SPEED);

	Rectangle bounds = new Rectangle();
	State      state = State.INFLIGTH;
	
	private int lifes = 5; 
    
    public Vector2 getVelocity() {
		return velocity;
	}

    public Vector2 getPosition() {
		return position;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
    
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Player(Vector2 position) {

		this.bounds.height = HEIGHT;
        this.bounds.width = WIDTH;

		this.position = position;
	}
	
	public void updatePosition() {
		
		//Gdx.app.log(AirStrikeGame.LOG, "player position: " + getPosition().toString() + " -> velocity: " + velocity.toString());
		
		Vector2 newPosition = new Vector2(position);
		newPosition.add(velocity);

		if (newPosition.x <= MIN_POS_X) {
			newPosition.set(MIN_POS_X, newPosition.y);
		}
		
		if (newPosition.x >= MAX_POS_X) {
			newPosition.set(MAX_POS_X, newPosition.y);
		}
			
		position.set(newPosition);
	}

	public void setState(State state) {
		
		Gdx.app.log(AirStrikeGame.LOG, "Player.setState() " + getState().toString() + " -> " + state.toString());		
		
		this.state = state;
	}

	public Object getState() {
		return this.state;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
}
