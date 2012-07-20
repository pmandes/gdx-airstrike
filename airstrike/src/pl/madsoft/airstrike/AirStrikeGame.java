package pl.madsoft.airstrike;

import pl.madsoft.airstrike.screens.GameScreen;

import com.badlogic.gdx.Game;

public class AirStrikeGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
