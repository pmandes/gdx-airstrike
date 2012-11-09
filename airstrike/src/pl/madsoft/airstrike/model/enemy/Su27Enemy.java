package pl.madsoft.airstrike.model.enemy;

import com.badlogic.gdx.math.Vector2;

import pl.madsoft.airstrike.model.Enemy;

public class Su27Enemy extends Enemy {

	public static final float HEIGHT = 1.33f;
	public static final float WIDTH = 0.907f;
	public static final float START_V_SPEED = -0.05f;
	public static final int HITPOINTS = 5;
	
	public Su27Enemy(Vector2 position) {
		super(position, START_V_SPEED, HITPOINTS);
		
		type = Enemy.Type.SU27;
	}

}
