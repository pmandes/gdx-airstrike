package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.view.PlayerJet2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class GameScreen extends AbstractScreen {

	private PlayerJet2D playerJet;
	private Player player;
	private Texture playerTexture;
	private TileMapRenderer tileMapRenderer;
	
	private Texture cloudsTexture;
 	private Image cloudsImage;
	
	public GameScreen(Game game) {
		super(game);
		
		Gdx.app.log(AirStrikeGame.LOG, "GameScreen");
		Gdx.app.log(AirStrikeGame.LOG, "Accelerometer > " + Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer));
	}

	@Override
	public void render(float delta) {
		
		//Gdx.app.log(AirStrikeGame.LOG, "delta >" + delta);
		
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.getCamera().translate(0, 2f, 0);
		cloudsImage.translate(0, -2f);

		tileMapRenderer.render((OrthographicCamera) stage.getCamera());
		
		stage.draw();
	}


	@Override
	public void show() {
		
		player = new Player(new Vector2(3f, 0.25f));
		playerTexture = new Texture(Gdx.files.internal("images/f35.png"));		
		playerJet = PlayerJet2D.create(player, playerTexture);

		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("data/sample-level.tmx"));
		SimpleTileAtlas tileAtlas = new SimpleTileAtlas(tiledMap, Gdx.files.internal("data"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 7, 45);
		
		cloudsTexture = new Texture(Gdx.files.internal("images/clouds.png"));
		cloudsImage = new Image(cloudsTexture);
		
		Gdx.app.log(AirStrikeGame.LOG, "tiled map > " + tiledMap.width + "x" + tiledMap.height + " -> " + tiledMap.tileWidth + "x" + tiledMap.tileHeight);	

		stage.addActor(cloudsImage);
		stage.addActor(playerJet);
	}

}
