package pl.madsoft.airstrike.view;

import java.util.HashMap;
import java.util.Map;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;


public class PlayerImage extends Image {
	
	private Player player;
	private Texture texture;
	
	ShapeRenderer shapeRenderer = new ShapeRenderer();	
	
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private boolean isAccelerometer;
	
	enum Keys {
        LEFT, RIGHT, FORWARD, BACK, FIRE
    }
	
    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.FORWARD, false);
        keys.put(Keys.BACK, false);
        keys.put(Keys.FIRE, false);
    };
	
	private PlayerImage (Player player, Texture texture, TextureRegion textureRegion) {
		
		super(textureRegion);

		this.player = player;
		this.texture = texture;
		
		ppuX = (float) AbstractScreen.GAME_VIEWPORT_WIDTH / AbstractScreen.CAMERA_WIDTH;
		ppuY = (float) AbstractScreen.GAME_VIEWPORT_HEIGHT / AbstractScreen.CAMERA_HEIGHT;
		
		isAccelerometer = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
		Gdx.app.log(AirStrikeGame.LOG, "Player2D Accelerometer: " + isAccelerometer);
		
		PlayerGestureListener gestureListener = new PlayerGestureListener();
		gestureListener.setPlayer(this);
		Gdx.input.setInputProcessor(new GestureDetector(gestureListener));
	}
	
	public static PlayerImage create(Player player, Texture texture, TextureRegion textureRegion) {

		return new PlayerImage(player, texture, textureRegion);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		processInputKeys();
		if (isAccelerometer) {
			processInputAccelerometer();
		}

		movePlane(delta);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		super.draw(batch, parentAlpha);
	}
		
	private void processInputKeys() {
		
		keys.put(Keys.LEFT, Gdx.input.isKeyPressed(Input.Keys.LEFT)); 
		keys.put(Keys.RIGHT, Gdx.input.isKeyPressed(Input.Keys.RIGHT));
		keys.put(Keys.FORWARD, Gdx.input.isKeyPressed(Input.Keys.UP)); 
		keys.put(Keys.BACK, Gdx.input.isKeyPressed(Input.Keys.DOWN));
		keys.put(Keys.FIRE, Gdx.input.isKeyPressed(Input.Keys.SPACE));

		if (keys.get(Keys.LEFT)) {
			flightX(-0.1f);
		}

		if (keys.get(Keys.RIGHT)) {
			flightX(0.1f);
		}
		
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) || (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			flightX(0f);
		}

		if (keys.get(Keys.FIRE)) {
			
			if (!player.getState().equals(Player.State.FIRINGGUN)) {
				player.setState(Player.State.FIRINGGUN);
				fireGun();
			}

		}
		if (!keys.get(Keys.FIRE)) {
			player.setState(Player.State.INFLIGTH);
		}
	}

	public void fireGun() {
	
		Gdx.app.log(AirStrikeGame.LOG, "SHOT!: " + player.getPosition().toString());
		
		Vector2 mslPos = new Vector2(player.getPosition());
		float mslX = player.getBounds().width / 2 - 0.12f;
		float mslY = player.getBounds().height - 0.3f;
		
		mslPos.add(new Vector2(mslX, mslY));
		Missile missile = new Missile(mslPos);
		TextureRegion missileTextureRegion = new TextureRegion(texture, 76, 2, 4, 10);		
		MissileImage missileImage = new MissileImage(missile, missileTextureRegion);
		
		this.getStage().addActor(missileImage);
	}

	private void processInputAccelerometer() {
		if (AirStrikeGame.DEBUG_MODE) {
			//Gdx.app.log(AirStrikeGame.LOG, "accel >" + Gdx.input.getAccelerometerX() + "," + Gdx.input.getAccelerometerY() + "," + Gdx.input.getAccelerometerZ());
		}
		
		float x = 0 - Gdx.input.getAccelerometerX() / 20;
		flightX(x);
	}
	
	private void flightX(float hSpeed) {
		player.setVelocity(new Vector2(hSpeed, player.getVelocity().y));
	}

	private void movePlane(float delta) {

		player.updatePosition();
		
		Rectangle rect = player.getBounds();

		float x = player.getPosition().x + rect.x;
		float y = player.getPosition().y + rect.y;
		
		float px = x * ppuX;
		float py = y * ppuY;

		setScaling(Scaling.stretch);
		setBounds(px, py, player.getBounds().width * ppuX, player.getBounds().height * ppuY);
	}

	public class PlayerGestureListener implements GestureListener {

			private PlayerImage player;
			
			public void setPlayer(PlayerImage player) {
				this.player = player;
			}
    	
			@Override
			public boolean touchDown(float x, float y, int pointer) {
			
				Gdx.app.log(AirStrikeGame.LOG, "touchDown");
				
				player.fireGun();
				return true;
			}

			@Override
			public boolean tap(float x, float y, int count) {
			
				Gdx.app.log(AirStrikeGame.LOG, "tap");
				return true;
			}

			@Override
			public boolean longPress(float x, float y) {
			
				Gdx.app.log(AirStrikeGame.LOG, "long press");
				return true;
			}

			@Override
			public boolean fling(float velocityX, float velocityY) {
			
				Gdx.app.log(AirStrikeGame.LOG, "fling");
				return true;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
			
				Gdx.app.log(AirStrikeGame.LOG, "pan");
				return true;
			}

			@Override
			public boolean zoom(float initialDistance, float distance) {
			
				Gdx.app.log(AirStrikeGame.LOG, "zoom");
				return true;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
			
				Gdx.app.log(AirStrikeGame.LOG, "pinch");
				return true;
			}

		}
	
}

