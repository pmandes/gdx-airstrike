package pl.madsoft.airstrike;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = "airstrike";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 800;
		//cfg.width = 320;
		//cfg.height = 480;
		
		new LwjglApplication(new AirStrikeGame(), cfg);
	}
}
