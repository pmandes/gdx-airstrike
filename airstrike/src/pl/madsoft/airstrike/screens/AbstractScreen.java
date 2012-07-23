package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractScreen implements Screen {

	protected final SpriteBatch spriteBatch;
	protected final Game game;

	public AbstractScreen(Game game) {
		
		Gdx.app.log(AirStrikeGame.LOG, "AbstractScreen");
		
		this.game = game;
		spriteBatch = new SpriteBatch();
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

}
