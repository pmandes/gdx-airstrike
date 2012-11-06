package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MissileImage extends Image {

	private Missile missile;
	private float ppuX;
	private float ppuY; 
	
	public MissileImage(Missile missile, TextureRegion texture) {
		
		super(texture);
		
		this.missile = missile;

		ppuX = (float) AbstractScreen.GAME_VIEWPORT_WIDTH / AbstractScreen.CAMERA_WIDTH;
		ppuY = (float) AbstractScreen.GAME_VIEWPORT_HEIGHT / AbstractScreen.CAMERA_HEIGHT;
		
		this.setX(missile.getPosition().x * ppuX);
		this.setY(missile.getPosition().y * ppuY);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		moveMissile();
	}

	private void moveMissile() {

		missile.updatePosition();
		
		this.setX(missile.getPosition().x * ppuX);
		this.setY(missile.getPosition().y * ppuY);
		
		Actor a = this.hit(missile.getPosition().x , missile.getPosition().y);
				
		if (a != null) {
			Gdx.app.log(AirStrikeGame.LOG, "hit!!!! " + a.toString());
		}
	}
	

}
