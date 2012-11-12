package pl.madsoft.airstrike.view;

import java.util.HashMap;
import java.util.Map;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.GameManager;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;


public class PlayerImage extends Image {
	
	private Player player;
	private Texture texture;
	
	ShapeRenderer shapeRenderer = new ShapeRenderer();	
	
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private boolean isAccelerometer;
	private Body body;
	
	private int dieTime = 150;
	
	enum Keys {
        LEFT, RIGHT, FORWARD, BACK, FIRE
    }

    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
	private PlayerGestureListener gestureListener;

	private Animation explosion;
	private TextureRegion currentFrame;
	private float stateTime;

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
		
		gestureListener = new PlayerGestureListener();
		gestureListener.setPlayer(this);
		Gdx.input.setInputProcessor(new GestureDetector(gestureListener));
		
		Texture explosionTexture = new Texture(Gdx.files.internal("images/expl1.png"));
		Array<TextureRegion> keyFrames = new Array<TextureRegion>();		
		
		for (int i = 0; i < 12; i++) {
			TextureRegion tr = new TextureRegion(explosionTexture, i * 96, 0, 96, 96);
			keyFrames.add(tr);
		}
		
		explosion = new Animation(0.05f, keyFrames, Animation.NORMAL);
		stateTime = 0f;
	}
	
	public static PlayerImage create(Player player, Texture texture, TextureRegion textureRegion) {

		return new PlayerImage(player, texture, textureRegion);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (!player.getState().equals(Player.State.DEAD)) {

			if (!player.getState().equals(Player.State.EXPLODING)) {
			
				processInputKeys();
				if (isAccelerometer) {
					processInputAccelerometer();
				}
			}
		}

		movePlane(delta);
		
		if (player.getState().equals(Player.State.EXPLODING)) {
			die();
		}

		if (player.getState().equals(Player.State.DEAD)) {

			rotate(-0.2f);
			scale(-0.003f);
			
			if (dieTime <= 0) {
				this.remove();
			}
			
			dieTime--;
			
			// IF (lifes > 0) respawn, restart level ELSE game over  
		}
	}


	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		super.draw(batch, parentAlpha);
		
		if (player.getState().equals(Player.State.EXPLODING) || player.getState().equals(Player.State.DEAD)) {

			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = explosion.getKeyFrame(stateTime, false);
			batch.draw(currentFrame, getX(), getY());
			
		} else {
			//super.draw(batch, parentAlpha);
		}
	}

	public void die() {

		player.setVelocity(new Vector2(0, 0.023f));
		rotate(-0.2f);
		scale(-0.003f);
	
		if (explosion.isAnimationFinished(stateTime)) {
			
			if (!player.getState().equals(Player.State.DEAD)) {
				Gdx.app.log(AirStrikeGame.LOG, "PLAYER IS DEAD!!!");
				player.setState(Player.State.DEAD);
			}
		}
	}	
	
	public void fireGun() {
	
		Gdx.app.log(AirStrikeGame.LOG, "SHOT!: " + player.getPosition().toString());
		
		Vector2 position = new Vector2(player.getPosition());
		
		Missile missile = GameManager.createMissile(position);
	
		this.getStage().addActor(missile.getActor());
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

		body.setTransform(px + 34, py + 48, 0.0f);
	}

	public void setBody(Body playerBody) {
		this.body = playerBody;
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
			if (player.getState().equals(Player.State.FIRINGGUN)){
				player.setState(Player.State.INFLIGTH);
			}
		}
	}	
	
	private void processInputAccelerometer() {
		if (AirStrikeGame.DEBUG_MODE) {
			//Gdx.app.log(AirStrikeGame.LOG, "accel >" + Gdx.input.getAccelerometerX() + "," + Gdx.input.getAccelerometerY() + "," + Gdx.input.getAccelerometerZ());
		}
		
		float x = 0 - Gdx.input.getAccelerometerX() / 20;
		flightX(x);
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
