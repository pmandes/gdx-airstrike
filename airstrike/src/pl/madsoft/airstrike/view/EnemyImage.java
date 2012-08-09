package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Enemy;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;


public class EnemyImage extends Image {
	
	private Enemy enemy;
	private Texture texture;
	
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private int shootTimer = 0;
	
	public EnemyImage (Enemy enemy, Texture texture, TextureRegion textureRegion) {
		
		super(textureRegion);

		this.enemy = enemy;
		this.texture = texture;
		
		ppuX = (float) AbstractScreen.GAME_VIEWPORT_WIDTH / AbstractScreen.CAMERA_WIDTH;
		ppuY = (float) AbstractScreen.GAME_VIEWPORT_HEIGHT / AbstractScreen.CAMERA_HEIGHT;
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
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		super.draw(batch, parentAlpha);
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
	}
	
}


