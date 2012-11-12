package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.GameManager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class PlayerLifesGroup extends Group {

	public PlayerLifesGroup() {

		for (int i = 0; i < GameManager.player.getLifes(); i++) {

			TextureRegion tr = new TextureRegion(GameManager.spritesTexture, 99, 4, 24, 32);
			Image image = new Image(tr);
			
			image.setScaling(Scaling.stretch);
			image.setBounds(5 + (i * 25), 5, 24, 32);
			
			addActor(image);
		}
	}


	public void act(float delta) {
		super.act(delta);
		
		if (!GameManager.state.equals(GameManager.State.LEVEL_END)) {
			setY(getY() + 2);
		}

	}

}
