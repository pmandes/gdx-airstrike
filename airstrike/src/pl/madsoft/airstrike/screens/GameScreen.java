package pl.madsoft.airstrike.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.model.World;
import pl.madsoft.airstrike.view.PlayerJet2D;
import pl.madsoft.airstrike.view.WorldRenderer;


public class GameScreen extends AbstractScreen {

	private PlayerJet2D playerJet;
	private Player player;
	private Texture playerTexture;
	
	//private WorldRenderer worldRenderer;
	//private World world;
	
	public GameScreen(Game game) {
		super(game);
		
		Gdx.app.log(AirStrikeGame.LOG, "GameScreen");
	}

	@Override
	public void render(float delta) {
		
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	
		//worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		//worldRenderer.setSize(width, height);
	}

	@Override
	public void show() {
		
		player = new Player(new Vector2(3f, 0f));
		playerTexture = new Texture(Gdx.files.internal("images/f35.png"));		
		playerJet = PlayerJet2D.create(player, playerTexture);

		//world = new World();
		//world.setPlayer(player);
        //worldRenderer = new WorldRenderer(world, spriteBatch, true);
		
		stage.addActor(playerJet);
	}

}
