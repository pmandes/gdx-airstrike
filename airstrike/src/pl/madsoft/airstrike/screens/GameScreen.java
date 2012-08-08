package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.view.MissileImage;
import pl.madsoft.airstrike.view.PlayerJet2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class GameScreen extends AbstractScreen {

	private PlayerJet2D playerJet;
	private Player player;
	private TextureRegion playerTextureRegion;
	private TileMapRenderer tileMapRenderer;
	
	private InputMultiplexer inputMultiplexer;
	
	private Texture cloudsTexture;
 	private Image cloudsImage;
	
	public GameScreen(Game game) {
		super(game);
		
		 inputMultiplexer = new InputMultiplexer();
		
		Gdx.app.log(AirStrikeGame.LOG, "GameScreen");
		Gdx.app.log(AirStrikeGame.LOG, "Accelerometer > " + Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer));
	}

	@Override
	public void render(float delta) {
		
		//Gdx.app.log(AirStrikeGame.LOG, "delta >" + delta);
		//Gdx.app.log(AirStrikeGame.LOG, "GameScreen camera position: " +  stage.getCamera().position);	
		
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//moveCamera();

		cloudsImage.translate(0, -2f);

		tileMapRenderer.render((OrthographicCamera) stage.getCamera());
		
		stage.draw();
	}


	private void moveCamera() {

		float cx = 0f, cy = 2f;
		
		if (player.getPosition().x > 4f && stage.getCamera().position.x < 280f) {
			cx = 2f;
		}
		if (player.getPosition().x < 3f && stage.getCamera().position.x > 240f) {
			cx = -2f;
		}
		
		stage.getCamera().translate(cx, cy, 0);
	}

	@Override
	public void show() {

		Gdx.app.log(AirStrikeGame.LOG, "GameScreen camera position: " +  stage.getCamera().position);
		
		player = new Player(new Vector2(3f, 0.25f));
		Texture playerTexture = new Texture(Gdx.files.internal("images/f35.png"));
		TextureRegion playerTextureRegion = new TextureRegion(playerTexture, 0, 0, 68, 100);
		playerJet = PlayerJet2D.create(player, playerTexture, playerTextureRegion);
		
		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("data/sample-level.tmx"));
		SimpleTileAtlas tileAtlas = new SimpleTileAtlas(tiledMap, Gdx.files.internal("data"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 7, 45);

		//Gdx.app.log(AirStrikeGame.LOG, "tiled map > " + tiledMap.width + "x" + tiledMap.height + " -> " + tiledMap.tileWidth + "x" + tiledMap.tileHeight);	
		
		cloudsTexture = new Texture(Gdx.files.internal("images/clouds.png"));
		cloudsImage = new Image(cloudsTexture);
		
		//stage.addActor(cloudsImage);
		stage.addActor(playerJet);
		
		stage.act();
		stage.getCamera().translate(50f, 0, 0);
		stage.getCamera().update(true);
		stage.draw();		

		Gdx.app.log(AirStrikeGame.LOG, "GameScreen camera position !!!: " +  stage.getCamera().position);
		
	}

}
