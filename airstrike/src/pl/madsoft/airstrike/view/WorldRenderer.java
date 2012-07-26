package pl.madsoft.airstrike.view;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.model.Tile;
import pl.madsoft.airstrike.model.World;
import pl.madsoft.airstrike.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;
	private OrthographicCamera cam;
	
	private float camSpeed = 0.0f;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private Texture tilemapTexture;
	private TextureRegion[] tiles = new TextureRegion[17];
	private SpriteBatch spriteBatch;
	
	private boolean debug = false;
	
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	public void setSize(int width, int height) {
		ppuX = (float) AbstractScreen.GAME_VIEWPORT_WIDTH / AbstractScreen.CAMERA_WIDTH;
		ppuY = (float) AbstractScreen.GAME_VIEWPORT_HEIGHT / AbstractScreen.CAMERA_HEIGHT;
	}

	public WorldRenderer(World world, SpriteBatch spriteBatch, boolean debug) {

		Gdx.app.log(AirStrikeGame.LOG, "WorldRenderer");
		
		this.world = world;
		this.spriteBatch = spriteBatch;
		this.debug = debug;
	
		this.cam = new OrthographicCamera(AbstractScreen.CAMERA_WIDTH, AbstractScreen.CAMERA_HEIGHT);
		this.cam.position.set(AbstractScreen.CAMERA_WIDTH / 2f, AbstractScreen.CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		
		world.getPlayer(); 

		loadTextures();
	}

	private void loadTextures() {
		
		tilemapTexture = new Texture(Gdx.files.internal("images/bckgrnd.png"));
		tilemapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		for (int i = 0; i < 17; i++) {
			tiles[i] = new TextureRegion(tilemapTexture, 0, Tile.PIX_SIZE * i, Tile.PIX_SIZE, Tile.PIX_SIZE);
		}
	}

	public void render() {

		spriteBatch.begin();
		//drawTilemap();
		spriteBatch.end();
		
		if (debug) {
			drawDebug();
		}
	}

	private void drawTilemap() {
	
		for (Tile tile : world.getTiles()) {
			spriteBatch.draw(tiles[tile.getType() - 1], tile.getPosition().x * ppuX, (tile.getPosition().y - camSpeed) * ppuY, Tile.SIZE * ppuX, Tile.SIZE * ppuY);		
		} 
	}

	private void drawDebug() {

		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		
			for (Tile tile : world.getTiles()) {
				Rectangle rect = tile.getBounds();
				float x1 = tile.getPosition().x + rect.x;
				float y1 = tile.getPosition().y + rect.y;
				debugRenderer.setColor(new Color(1, 0, 0, 1));
				debugRenderer.rect(x1, y1, rect.width, rect.height);
			}
	
			Player player = world.getPlayer();
			Rectangle rect = player.getBounds();
	
			float x1 = player.getPosition().x + rect.x;
			float y1 = player.getPosition().y + rect.y;
	
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);

		debugRenderer.end();
	}
}