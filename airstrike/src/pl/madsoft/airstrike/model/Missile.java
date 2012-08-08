package pl.madsoft.airstrike.model;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Missile {

	public static final float SPEED = 10f;
	
	Vector2 position = new Vector2();
	Vector2 velocity = new Vector2(0, SPEED);
	
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

	public void updatePosition() {

		Vector2 newPosition = new Vector2(position);

		newPosition.add(velocity);
		position.set(newPosition);
	}
	
}
