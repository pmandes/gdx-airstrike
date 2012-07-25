package pl.madsoft.airstrike.model;

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

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;		
	}    
    
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Player(Vector2 position) {
	
		this.position = position;
		this.bounds.height = HEIGHT;
        this.bounds.width = WIDTH;
	}
	
	public void update() {
		
		position = position.add(velocity);
	}


}
