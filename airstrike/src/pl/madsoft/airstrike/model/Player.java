package pl.madsoft.airstrike.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

	public enum State {
		INFLIGTH, EXPLODING
	}

	public static final float HEIGHT = 1.33f;
	public static final float WIDTH = 0.907f;

	Vector2   position = new Vector2();
    Vector2   velocity = new Vector2();
    Rectangle bounds = new Rectangle();
	State     state = State.INFLIGTH;

    public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public Player(Vector2 position) {
	
		this.position = position;
		this.bounds.height = HEIGHT;
        this.bounds.width = WIDTH;
	}

}
