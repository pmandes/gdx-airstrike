package pl.madsoft.airstrike;

import pl.madsoft.airstrike.screens.GameScreen;
import pl.madsoft.airstrike.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

public class AirStrikeGame extends Game {

	public static final String LOG = AirStrikeGame.class.getSimpleName();
	private FPSLogger fpsLogger;
	
	@Override
	public void create() {
		
		Gdx.app.log(AirStrikeGame.LOG, "Creating game");
		
		fpsLogger = new FPSLogger();
		setScreen(getGameScreen());
		//setScreen(getSplashScreen());
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		
        Gdx.app.log(AirStrikeGame.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
		
	}
	
	
	// screens
	
	public SplashScreen getSplashScreen(){
		return new SplashScreen(this);
	}
	
	public GameScreen getGameScreen() {
		return new GameScreen(this);
	}
	
	
}
