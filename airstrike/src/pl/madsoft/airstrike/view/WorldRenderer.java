package pl.madsoft.airstrike.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.FPSLogger;

import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.model.Tile;
import pl.madsoft.airstrike.model.World;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 6.5f;
	private static final float CAMERA_HEIGHT = 10.67f;

	//private static final float CAMERA_WIDTH = 7f;
	//private static final float CAMERA_HEIGHT = 46f;

	private Stage stage;

	private FPSLogger fpsLogger;
	
	private World world;
	private OrthographicCamera cam;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private Texture playerTexture;
	private Texture tilemapTexture;
	private TextureRegion[] tiles = new TextureRegion[17];
	private SpriteBatch spriteBatch;
	
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis

	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public Stage getStage() {
		return this.stage;
	}

	public WorldRenderer(World world, boolean debug) {

		this.world = world;
		this.debug = debug;
		
		fpsLogger = new FPSLogger();
		
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();

		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		playerTexture = new Texture(Gdx.files.internal("images/f35.png"));
		tilemapTexture = new Texture(Gdx.files.internal("images/bckgrnd.png"));

		for (int i = 0; i < 17; i++) {
			tiles[i] = new TextureRegion(tilemapTexture, 0, Tile.PIX_SIZE * i, Tile.PIX_SIZE, Tile.PIX_SIZE);
		}
	}

	public void render() {
		spriteBatch.begin();
			drawTilemap();
			drawPlayer();
			moveCam();
		spriteBatch.end();
		
		if (debug) {
			fpsLogger.log();
			drawDebug();
		}
	}

	private void moveCam() {
		
		float y = this.cam.position.y + 0.01f;
		this.cam.position.set(CAMERA_WIDTH / 2f, y, 0);
		this.cam.update();
	}

	private void drawPlayer() {
		Player player = world.getPlayer();
		spriteBatch.draw(playerTexture, player.getPosition().x * ppuX, player.getPosition().y * ppuY, Player.WIDTH * ppuX, Player.HEIGHT * ppuY);
	}
	
	private void drawTilemap() {
	
		for (Tile tile : world.getTiles()) {
			spriteBatch.draw(tiles[tile.getType() - 1], tile.getPosition().x * ppuX, (tile.getPosition().y - 0.01f) * ppuY, Tile.SIZE * ppuX, Tile.SIZE * ppuY);		
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