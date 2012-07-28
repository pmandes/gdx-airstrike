package pl.madsoft.airstrike.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen {

	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	
	private int SPLASH_BORDER_SIZE = 5;
	
	private int splashPosX; 
	private int splashPosY;
	private Image splashImage;
	
	public SplashScreen(Game game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		
		splashTexture = new Texture("images/airstrike.png");
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashTextureRegion = new TextureRegion(splashTexture, 0, 0, splashTexture.getWidth(), splashTexture.getHeight());
		
		splashPosX = SPLASH_BORDER_SIZE;
		splashPosY = Gdx.graphics.getHeight() - splashTexture.getHeight() - SPLASH_BORDER_SIZE;
		
		splashImage = new Image(splashTextureRegion);
		
		splashImage.addAction(sequence(fadeIn(0.75f), delay(1.75f), fadeOut(0.75f),
	        new Action() {
	            @Override
	            public boolean act(float delta) {
	                game.setScreen(new GameScreen(game));
	                return true;
	            }
		}));
		
		splashImage.setPosition(splashPosX,  splashPosY);
		stage.addActor(splashImage);
	}

	@Override
	public void dispose() {
		super.dispose();
		splashTexture.dispose();
	}
	
	

}
