package pl.madsoft.airstrike;

import pl.madsoft.airstrike.model.Enemy;
import pl.madsoft.airstrike.model.Player;
import pl.madsoft.airstrike.model.enemy.Mig29Enemy;
import pl.madsoft.airstrike.model.enemy.Su27Enemy;
import pl.madsoft.airstrike.view.EnemyImage;
import pl.madsoft.airstrike.view.PlayerImage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class GameManager {

	public static World world;
	public Player player;
	
	public GameManager() {
		
		GameContactListener listener = new GameContactListener("hello");
		
		world = new World(new Vector2(0.0f, 0.0f), true);
		world.setContactListener(listener);
		world.setContinuousPhysics(true);
	}
	
	public static Player createPlayer() {
		
		Player player = new Player(new Vector2(3f, 0.25f));
		
		Texture playerTexture = new Texture(Gdx.files.internal("images/sprites.png"));
		TextureRegion playerTextureRegion = new TextureRegion(playerTexture, 0, 0, 68, 100);
		PlayerImage playerImage = PlayerImage.create(player, playerTexture, playerTextureRegion);
	
		BodyDef playerBodyDef = new BodyDef();
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(200,400));
		
		Body playerBody = world.createBody(playerBodyDef);
		
		PolygonShape playerBox = new PolygonShape();
		playerBox.setAsBox(34, 50);
		playerBody.createFixture(playerBox, 0.0f);

		playerBody.setUserData(player);
		playerImage.setBody(playerBody);
		
		player.setActor(playerImage);
		
		return player;
	}

	public static TileMapRenderer createLevelMapRender() {
		
		TiledMap tiledMap = TiledLoader.createMap(Gdx.files.internal("data/sample-level.tmx"));
		SimpleTileAtlas tileAtlas = new SimpleTileAtlas(tiledMap, Gdx.files.internal("data"));
		
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
			
			body.setUserData(tiledObject);
		}

		TileMapRenderer tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 7, 45);
		
		return tileMapRenderer;
	}

	public static TileMapRenderer createCloudsMapRender() {
		
		TiledMap cloudsMap = TiledLoader.createMap(Gdx.files.internal("data/clouds.tmx"));
		SimpleTileAtlas cloudsAtlas = new SimpleTileAtlas(cloudsMap, Gdx.files.internal("data"));
		TileMapRenderer cloudsMapRenderer = new TileMapRenderer(cloudsMap, cloudsAtlas, 2, 10);
		
		return cloudsMapRenderer;
	}

	public static Array<Actor> createEnemies() {

		Array<Enemy> enemiesArray = new Array<Enemy>();
		Array<Actor> enemies = new Array<Actor>();
		
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
			enemies.add(enemyImage);
		}
		
		return enemies;
	}

}
