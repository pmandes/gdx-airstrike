package pl.madsoft.airstrike;

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

		Gdx.app.log(AirStrikeGame.LOG, "beginContact: " +  fixA.getBody().getUserData().toString() + " <> " + fixB.getBody().getUserData().toString());

		Player player = (Player) fixB.getBody().getUserData();

		if (!player.getState().equals(Player.State.EXPLODING)) {
			player.setState(Player.State.EXPLODING);
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
