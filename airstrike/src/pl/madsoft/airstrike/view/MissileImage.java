package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.model.Missile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MissileImage extends Image {

	private TextureRegion texture;
	private Missile missile; 
	
	public MissileImage(Missile missile, TextureRegion texture) {
		
		super(texture);
		
		this.texture = texture;
		this.missile = missile;

		this.setX(missile.getPosition().x);
		this.setY(missile.getPosition().y);		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		moveMissile();
	}

	private void moveMissile() {

		missile.updatePosition();

		this.setX(missile.getPosition().x);
		this.setY(missile.getPosition().y);
	}
	

}
