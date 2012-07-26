package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class PlayerJet2D extends Image {
	
	private Player player;
	ShapeRenderer shapeRenderer = new ShapeRenderer();	
	
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private PlayerJet2D (Player player, Texture playerTexture) {
		
		super(playerTexture);
		
		this.player = player;
		ppuX = (float) AbstractScreen.GAME_VIEWPORT_WIDTH / AbstractScreen.CAMERA_WIDTH;
		ppuY = (float) AbstractScreen.GAME_VIEWPORT_HEIGHT / AbstractScreen.CAMERA_HEIGHT;
	}
	
	public static PlayerJet2D create(Player player, Texture playerTexture) {

		playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		return new PlayerJet2D(player, playerTexture);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		movePlane(delta);
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

}
