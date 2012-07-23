package pl.madsoft.airstrike;

import pl.madsoft.airstrike.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AirStrikeGame extends Game {

	public static final String LOG = AirStrikeGame.class.getSimpleName();
	
	@Override
	public void create() {
		
		Gdx.app.log(AirStrikeGame.LOG, "Creating game");
		setScreen(new GameScreen(this));
	}
}
