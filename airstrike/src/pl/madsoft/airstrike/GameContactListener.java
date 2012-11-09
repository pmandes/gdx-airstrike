package pl.madsoft.airstrike;

import pl.madsoft.airstrike.model.Enemy;
import pl.madsoft.airstrike.model.Missile;
import pl.madsoft.airstrike.model.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener  implements ContactListener {

	
	public GameContactListener(String str) {
		
		Gdx.app.log(AirStrikeGame.LOG, "PlayerListener: " + str);
	}

	@Override
	public void beginContact(Contact contact) {

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		String objAClassName = fixA.getBody().getUserData().getClass().getSimpleName();
		String objBClassName = fixB.getBody().getUserData().getClass().getSimpleName();
		
		//Gdx.app.log(AirStrikeGame.LOG, "beginContact: " +  objAClassName + " <> " + objBClassName);
		
		if (objAClassName.equals("TiledObject") && objBClassName.equals("Player")) {

			Player player = (Player) fixB.getBody().getUserData();
	
			if (!player.getState().equals(Player.State.EXPLODING) && !player.getState().equals(Player.State.DEAD)) {
				player.setState(Player.State.EXPLODING);
			}
		}
		
		if (objAClassName.endsWith("Enemy") && objBClassName.equals("Player")) {

			Enemy enemy = (Enemy) fixA.getBody().getUserData();
			Player player = (Player) fixB.getBody().getUserData();

			if (!enemy.getState().equals(Enemy.State.EXPLODING) && !enemy.getState().equals(Enemy.State.DEAD)) {

				if (!player.getState().equals(Player.State.EXPLODING) && !player.getState().equals(Player.State.DEAD)) {
				
					enemy.setState(Enemy.State.EXPLODING);				
					player.setState(Player.State.EXPLODING);
				}
			}
		}

		if (objAClassName.endsWith("Enemy") && objBClassName.equals("Missile")) {

			Enemy enemy = (Enemy) fixA.getBody().getUserData();
			Missile missile = (Missile) fixB.getBody().getUserData();
			
			if (!enemy.getState().equals(Enemy.State.EXPLODING) && !enemy.getState().equals(Enemy.State.DEAD)) {
				enemy.hit(missile.getHitPower());
				missile.getActor().remove();
			}
		}
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

}
