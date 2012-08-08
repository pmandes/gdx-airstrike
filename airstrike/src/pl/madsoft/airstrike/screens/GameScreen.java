package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.view.PlayerImage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameScreen extends AbstractScreen {

	private PlayerImage playerImage;
	private Player player;
	private TileMapRenderer tileMapRenderer;
	private Texture cloudsTexture;
 	private Image cloudsImage;
	
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		
		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		moveCamera();
		cloudsImage.translate(0, -2f);
		tileMapRenderer.render((OrthographicCamera) stage.getCamera());
		
		stage.draw();
	}

	@Override
	public void show() {

		Gdx.app.log(AirStrikeGame.LOG, "GameScreen camera position: " +  stage.getCamera().position);
		
		player = new Player(new Vector2(3f, 0.25f));
		Texture playerTexture = new Texture(Gdx.files.internal("images/f35.png"));
		TextureRegion playerTextureRegion = new TextureRegion(playerTexture, 0, 0, 68, 100);
		playerImage = PlayerImage.create(player, playerTexture, playerTextureRegion);
		
		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("data/sample-level.tmx"));
		SimpleTileAtlas tileAtlas = new SimpleTileAtlas(tiledMap, Gdx.files.internal("data"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 7, 45);

		cloudsTexture = new Texture(Gdx.files.internal("images/clouds.png"));
		cloudsImage = new Image(cloudsTexture);
		
		stage.addActor(cloudsImage);
		stage.addActor(playerImage);
	
        stage.getRoot().getColor().a = 0f;
        stage.getRoot().addAction(Actions.fadeIn(0.5f));		
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
	
}
