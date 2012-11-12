package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.GameManager;
import pl.madsoft.airstrike.GameManager.State;
import pl.madsoft.airstrike.model.Player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends AbstractScreen {

	private static final int BOX_VELOCITY_ITERATIONS = 1;
	private static final int BOX_POSITION_ITERATIONS = 1;

	private Player player;

	private TileMapRenderer cloudsMapRenderer;
	private TileMapRenderer levelMapRenderer;
	private BitmapFont font;

	private Box2DDebugRenderer debugRenderer;
	private GameManager gameManager;
	private Array<Actor> enemies;
	private Group lifes;
	
	public GameScreen(Game game) {
		super(game);
		
		this.gameManager = GameManager.instance();
		debugRenderer = new Box2DDebugRenderer();
		
        font = new BitmapFont();
        font.setColor(Color.WHITE);		
	}

	@Override
	public void render(float delta) {

		stage.act(delta);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		moveCamera();

		levelMapRenderer.render((OrthographicCamera) stage.getCamera());
		cloudsMapRenderer.render((OrthographicCamera) stage.getCamera());

		//debugRenderer.render(GameManager.world, stage.getCamera().combined);
		GameManager.world.step(1/60f, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);

		stage.draw();

		//font.draw(stage.getSpriteBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
	}

	@Override
	public void show() {

		Gdx.app.log(AirStrikeGame.LOG, "GameScreen camera position: " +  stage.getCamera().position);

		// create level tile map

		levelMapRenderer = GameManager.createLevelMapRender();

		// create clouds tile map

		cloudsMapRenderer = GameManager.createCloudsMapRender();

		// create enemies

		enemies = GameManager.createEnemies();
		for (Actor enemy : enemies) {
			stage.addActor(enemy);
		}

		// create player

		player = GameManager.createPlayer();
		stage.addActor(player.getActor());
		
		// create player lifes
		
		lifes = GameManager.createPlayerLifes();
		stage.addActor(lifes);
				
	}

	private void moveCamera() {

		float cx = 0f, cy = 2f;
		int levelEnd = 2962;

		if (player.getPosition().x > 4f && stage.getCamera().position.x < 280f) {
			cx = 2f;
		}

		if (player.getPosition().x < 3f && stage.getCamera().position.x > 240f) {
			cx = -2f;
		}

		if (stage.getCamera().position.y > levelEnd) {
			GameManager.state = GameManager.State.LEVEL_END;
			cx = 0;
			cy = 0;
			player.setVelocity(new Vector2(0, 0.06f));
		}

		stage.getCamera().translate(cx, cy, 0);
	}
	
}
