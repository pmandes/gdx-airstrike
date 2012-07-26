package pl.madsoft.airstrike.model;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	public enum State {
		INFLIGTH, EXPLODING
	}

	public static final float HEIGHT = 1.33f;
	public static final float WIDTH = 0.907f;
	
	public static final float START_V_SPEED = 0.02f; 

	Vector2   position = new Vector2();
    Vector2   velocity = new Vector2(0, START_V_SPEED);
    Rectangle bounds = new Rectangle();
	State     state = State.INFLIGTH;

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
		
		Gdx.app.log(AirStrikeGame.LOG, "player position: " + getPosition().toString());
		
		position = position.add(velocity);
	}


}
