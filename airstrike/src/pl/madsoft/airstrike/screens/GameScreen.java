package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.World;
import pl.madsoft.airstrike.view.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GameScreen extends AbstractScreen {

	public GameScreen(Game game) {
		super(game);
		
		Gdx.app.log(AirStrikeGame.LOG, "GameScreen");		
	}

	private WorldRenderer renderer;
	private World world;

	@Override
	public void render(float delta) {
		super.render(delta);
	    renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
	}

	@Override
	public void show() {
		world = new World();
        renderer = new WorldRenderer(world, spriteBatch, true);
	}

}
