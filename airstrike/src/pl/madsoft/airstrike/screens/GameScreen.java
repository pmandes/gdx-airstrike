package pl.madsoft.airstrike.screens;

import pl.madsoft.airstrike.AirStrikeGame;
import pl.madsoft.airstrike.model.Enemy;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.model.enemy.Mig29Enemy;
import pl.madsoft.airstrike.model.enemy.Su27Enemy;
import pl.madsoft.airstrike.view.EnemyImage;
import pl.madsoft.airstrike.view.PlayerImage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends AbstractScreen {

	private static final int BOX_VELOCITY_ITERATIONS = 6;
	private static final int BOX_POSITION_ITERATIONS = 2;
	private PlayerImage playerImage;
	private Player player;

	private Texture cloudsTexture;
 	private Image cloudsImage;
 	
	private TileMapRenderer cloudsMapRenderer;
	private TileMapRenderer tileMapRenderer;
	private BitmapFont font;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private float BOX_TO_WORLD = 75.0f;
	
	
	class PlayerListener implements ContactListener {
		
		public PlayerListener(String str) {
			
			Gdx.app.log(AirStrikeGame.LOG, "PlayerListener: " + str);				
		}

		@Override
		public void beginContact(Contact contact) {
			// TODO Auto-generated method stub
			Gdx.app.log(AirStrikeGame.LOG, "beginContact: " +  contact.toString());
		}

		@Override
		public void endContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public GameScreen(Game game) {
		super(game);
		
		world = new World(new Vector2(0.0f, 0.0f), true);
		debugRenderer = new Box2DDebugRenderer();
		PlayerListener listener = new PlayerListener("hello");
		world.setContactListener(listener);
		world.setContinuousPhysics(true);
		
        font = new BitmapFont();
        font.setColor(Color.WHITE);		
	}

	@Override
	public void render(float delta) {

		stage.act(delta);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		moveCamera();
		//cloudsImage.translate(0, -2f);
		tileMapRenderer.render((OrthographicCamera) stage.getCamera());
		cloudsMapRenderer.render((OrthographicCamera) stage.getCamera());
		
		debugRenderer.render(world, stage.getCamera().combined);
		world.step(1/60f, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		
//		if (world.getContactCount() > 0) {
//			Gdx.app.log(AirStrikeGame.LOG, "CONTACT: " +  world.getContactCount() + " -> " + world.getContactList() );			
//		}
		
		
		stage.draw();
		
		//font.draw(stage.getSpriteBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
	}

	@Override
	public void show() {


		
		Gdx.app.log(AirStrikeGame.LOG, "GameScreen camera position: " +  stage.getCamera().position);
		
		// create player
		
		player = new Player(new Vector2(3f, 0.25f));
		Texture playerTexture = new Texture(Gdx.files.internal("images/sprites.png"));
		TextureRegion playerTextureRegion = new TextureRegion(playerTexture, 0, 0, 68, 100);
		playerImage = PlayerImage.create(player, playerTexture, playerTextureRegion);
		
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(200,400));
		
		Body playerBody = world.createBody(playerBodyDef);
		
		PolygonShape playerBox = new PolygonShape();
		playerBox.setAsBox(34, 50);
		playerBody.createFixture(playerBox, 0.0f);

		playerImage.setBody(playerBody); 
		
		
		// create world tile map
		
		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("data/sample-level.tmx"));
		SimpleTileAtlas tileAtlas = new SimpleTileAtlas(tiledMap, Gdx.files.internal("data"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 7, 45);
		
		for (TiledObject tiledObject : tiledMap.objectGroups.get(0).objects) {
			
			String[] points = tiledObject.polyline.split(" ");

			Vector2[] vertices = new Vector2[points.length];
			
			for (int i = 0; i < points.length; i++) {
				String[] xy = points[i].split(",");
				float x = new Float(xy[0]);
				float y = new Float(xy[1]);
				vertices[i] = new Vector2(x, -y);
			}

			ChainShape cs = new ChainShape();
			cs.createChain(vertices);
			
			Gdx.app.log(AirStrikeGame.LOG, "tiledObject: " +  tiledObject.toString() + " -> " + (tiledMap.height * tiledMap.tileHeight) );
			
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			
			bodyDef.position.set(tiledObject.x ,(tiledMap.height * tiledMap.tileHeight) - tiledObject.y);
			Body body = world.createBody(bodyDef);
			body.createFixture(cs, 0.0f);
		}

		
		// create clouds tile map

		TiledMap cloudsMap = TiledLoader.createMap(Gdx.files.internal("data/clouds.tmx"));
		SimpleTileAtlas cloudsAtlas = new SimpleTileAtlas(cloudsMap, Gdx.files.internal("data"));
		cloudsMapRenderer = new TileMapRenderer(cloudsMap, cloudsAtlas, 2, 10);
		
		
		createEnemies();
		
		//cloudsTexture = new Texture(Gdx.files.internal("images/clouds2.png"));
		//cloudsImage = new Image(cloudsTexture);
		//stage.addActor(cloudsImage);
		
		stage.addActor(playerImage);
	
        //stage.getRoot().getColor().a = 0f;
        //stage.getRoot().addAction(Actions.fadeIn(0.5f));
		

	}

	private void createEnemies() {

		Array<Enemy> enemiesArray = new Array<Enemy>();
		enemiesArray.add(new Mig29Enemy(new Vector2(2f, 10f)));
		enemiesArray.add(new Su27Enemy(new Vector2(3f, 20f)));
		enemiesArray.add(new Mig29Enemy(new Vector2(1f, 30f)));
		enemiesArray.add(new Su27Enemy(new Vector2(4f, 40f)));
		enemiesArray.add(new Su27Enemy(new Vector2(3f, 32f)));
		enemiesArray.add(new Mig29Enemy(new Vector2(2f, 50f)));
		enemiesArray.add(new Su27Enemy(new Vector2(3f, 51f)));
		enemiesArray.add(new Mig29Enemy(new Vector2(1f, 60f)));
		enemiesArray.add(new Su27Enemy(new Vector2(4f, 61f)));
		enemiesArray.add(new Su27Enemy(new Vector2(3f, 62f)));
		enemiesArray.add(new Mig29Enemy(new Vector2(1f, 70f)));
		enemiesArray.add(new Mig29Enemy(new Vector2(2f, 71f)));		
		enemiesArray.add(new Mig29Enemy(new Vector2(3f, 72f)));

		for (Enemy enemy : enemiesArray) {
			Texture enemyTexture = new Texture(Gdx.files.internal("images/sprites.png"));
			TextureRegion enemyTextureRegion = null;
			
			Gdx.app.log(AirStrikeGame.LOG, "Enemy: " + enemy.getType());
			
			if (enemy.getType().equals(Enemy.Type.SU27)) {
				enemyTextureRegion = new TextureRegion(enemyTexture, 0, 128, 67, 100);
			}
			
			if (enemy.getType().equals(Enemy.Type.MIG29)) {
				enemyTextureRegion = new TextureRegion(enemyTexture, 0, 256, 67, 100);
			}			

			EnemyImage enemyImage = new EnemyImage(enemy, enemyTexture, enemyTextureRegion);
			stage.addActor(enemyImage);
		}

	}

	private void moveCamera() {

		//Gdx.app.log(AirStrikeGame.LOG, "Cam pos: " + stage.getCamera().position.toString());
		
		float cx = 0f, cy = 2f;
		
		if (player.getPosition().x > 4f && stage.getCamera().position.x < 280f) {
			cx = 2f;
		}
		if (player.getPosition().x < 3f && stage.getCamera().position.x > 240f) {
			cx = -2f;
		}
		if (stage.getCamera().position.y > 2962) {
			cx = 0;
			cy = 0;
			player.setVelocity(new Vector2(0, 0.06f));
		}
		
		stage.getCamera().translate(cx, cy, 0);
	}
	
}
