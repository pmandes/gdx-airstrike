package pl.madsoft.airstrike.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	public enum Type {
		GROUND, RIVER
	}	

	public static final float SIZE = 1f;
	public static final int PIX_SIZE = 75;

    Vector2   position = new Vector2();
    Rectangle bounds = new Rectangle();
	int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public Vector2 getPosition() {
		return this.position;
	}
	
	public Tile(Vector2 position, int type) {
		this.position = position; 
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        this.type = type;
	}	

}
