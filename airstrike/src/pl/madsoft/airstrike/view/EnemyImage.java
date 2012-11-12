package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Enemy;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class EnemyImage extends Image {
	
	private Enemy enemy;
	private Texture texture;
	
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private int shootTimer = 0;
	private Body body;
	
	private float stateTimeExpl;
	private float stateTimeHit;
	
	private Animation explosion;
	private Animation hit;	

	public TextureRegion damaged;
	private TextureRegion currentFrameExpl;
	private TextureRegion currentFrameHit;
	
	private int dieTime = 100;
	private float rotation = 0;
	private float hitRndX;
	private float hitRndY;
	
	public EnemyImage (Enemy enemy, Texture texture, TextureRegion textureRegion) {
		
		super(textureRegion);

		this.enemy = enemy;
		this.texture = texture;
		
		ppuX = (float) AbstractScreen.GAME_VIEWPORT_WIDTH / AbstractScreen.CAMERA_WIDTH;
		ppuY = (float) AbstractScreen.GAME_VIEWPORT_HEIGHT / AbstractScreen.CAMERA_HEIGHT;
	
		Texture explosionTexture1 = new Texture(Gdx.files.internal("data/expl1.png"));
		Texture explosionTexture2 = new Texture(Gdx.files.internal("data/expl2.png"));		
		
		Array<TextureRegion> keyFrames1 = new Array<TextureRegion>();		
		Array<TextureRegion> keyFrames2 = new Array<TextureRegion>();
		
		for (int i = 0; i < 12; i++) {
			TextureRegion tr = new TextureRegion(explosionTexture1, i * 96, 0, 96, 96);
			keyFrames1.add(tr);
		}

		for (int i = 0; i < 8; i++) {
			TextureRegion tr = new TextureRegion(explosionTexture2, i * 48, 0, 48, 48);
			keyFrames2.add(tr);
		}		

		explosion = new Animation(0.05f, keyFrames1, Animation.NORMAL);
		hit = new Animation(0.04f, keyFrames2, Animation.NORMAL);
		
		stateTimeExpl = 0f;
		stateTimeHit = 0f;
		rotation = MathUtils.random(-0.4f, 0.4f);
		hitRndX = MathUtils.random(5f, 20f);
		hitRndY = MathUtils.random(30f, 50f);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);

		shootTimer++;
		if (shootTimer == 20){
			//fireGun();
			shootTimer = 0;
		}
		
		movePlane(delta);

		if (enemy.getState().equals(Enemy.State.HITTED)) {
			hit();
		}
		
		if (enemy.getState().equals(Enemy.State.EXPLODING)) {
			die();
		}

		if (enemy.getState().equals(Enemy.State.DEAD)) {
			
			rotate(rotation);
			scale(-0.004f);

			if (dieTime <= 0) {
				this.remove();
			}

			dieTime --;
		}
	}

	private void hit() {

		if (hit.isAnimationFinished(stateTimeHit)) {
			stateTimeHit = 0;
			enemy.setState(Enemy.State.INFLIGTH);
		}		
	}
	
	private void die() {

		setDrawable(new TextureRegionDrawable(damaged));

		rotate(rotation);
		scale(-0.004f);

		if (explosion.isAnimationFinished(stateTimeExpl)) {
			Gdx.app.log(AirStrikeGame.LOG, "ENEMY IS DEAD!!!");
			
			if (!enemy.getState().equals(Enemy.State.DEAD)) {
				enemy.setState(Enemy.State.DEAD);
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		super.draw(batch, parentAlpha);
		
		if (enemy.getState().equals(Enemy.State.EXPLODING) || enemy.getState().equals(Enemy.State.DEAD)) {

			stateTimeExpl += Gdx.graphics.getDeltaTime();
			currentFrameExpl = explosion.getKeyFrame(stateTimeExpl, false);
			batch.draw(currentFrameExpl, getX(), getY() + 30);
			batch.draw(currentFrameExpl, getX() - 10, getY() + 20);
		}

		if (enemy.getState().equals(Enemy.State.HITTED)) {

			stateTimeHit += Gdx.graphics.getDeltaTime();
			currentFrameHit = hit.getKeyFrame(stateTimeHit, false);
			batch.draw(currentFrameHit, getX() + hitRndX, getY() + hitRndY);
		}

	}
		
	public void fireGun() {
	
		Gdx.app.log(AirStrikeGame.LOG, "ENEMY SHOT!: " + enemy.getPosition().toString());
		
		Vector2 mslPos = new Vector2(enemy.getPosition());
		float mslX = enemy.getBounds().width / 2 - 0.12f;
		float mslY = enemy.getBounds().height - 0.3f;
		
		mslPos.add(new Vector2(mslX, mslY));
		Missile missile = new Missile(mslPos);
		missile.setVelocity(new Vector2(0, -0.22f));
		TextureRegion missileTextureRegion = new TextureRegion(texture, 76, 2, 4, 10);
		MissileImage missileImage = new MissileImage(missile, missileTextureRegion);
		
		this.getStage().addActor(missileImage);
	}

	private void flightX(float hSpeed) {
		enemy.setVelocity(new Vector2(hSpeed, enemy.getVelocity().y));
	}

	private void movePlane(float delta) {

		enemy.updatePosition();
		
		Rectangle rect = enemy.getBounds();

		float x = enemy.getPosition().x + rect.x;
		float y = enemy.getPosition().y + rect.y;
		
		float px = x * ppuX;
		float py = y * ppuY;

		setScaling(Scaling.stretch);
		setBounds(px, py, enemy.getBounds().width * ppuX, enemy.getBounds().height * ppuY);
		
 		body.setTransform(px + 34, py + 48, 0.0f);
	}

	public void setBody(Body body) {
		this.body = body;
	}
	
}


