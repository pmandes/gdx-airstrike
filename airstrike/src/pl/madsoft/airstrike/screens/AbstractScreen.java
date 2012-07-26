package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen {

	public static final int GAME_VIEWPORT_WIDTH = 480, GAME_VIEWPORT_HEIGHT = 800;	
	public static final float CAMERA_WIDTH = 6.5f, CAMERA_HEIGHT = 10.67f;	
	//public static final float CAMERA_WIDTH = 26f, CAMERA_HEIGHT = 46f;	
	
	protected final SpriteBatch spriteBatch;
	protected final Game game;
	protected final Stage stage;

	public AbstractScreen(Game game) {
		
		Gdx.app.log(AirStrikeGame.LOG, "Construct " + getName());
		
		this.game = game;

	    int width = GAME_VIEWPORT_WIDTH;
        int height = GAME_VIEWPORT_HEIGHT;

		spriteBatch = new SpriteBatch();
		stage = new Stage(width, height, true);
	}

    protected String getName() {
        return getClass().getSimpleName();
    }	
	
	@Override
	public void show() {
		Gdx.app.log(AirStrikeGame.LOG, "Showing screen: " + getName());
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(AirStrikeGame.LOG, "Resizing screen: " + getName() + " to: " + width + "x" + height);
		stage.setViewport(width, height, true);
	}

	@Override
	public void render(float delta) {
		
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void hide() {
		Gdx.app.log(AirStrikeGame.LOG, "Hiding screen: " + getName());
	}

	@Override
	public void pause() {
		Gdx.app.log(AirStrikeGame.LOG, "Pausing screen: " + getName());
	}

	@Override
	public void resume() {
		Gdx.app.log(AirStrikeGame.LOG, "Resuming screen: " + getName());
	}

	@Override
	public void dispose() {
		Gdx.app.log(AirStrikeGame.LOG, "Disposing screen: " + getName());
		stage.dispose();
		spriteBatch.dispose();
	}

}
