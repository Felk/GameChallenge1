package de.felk.gamepp1;

import de.felk.gamepp1.entities.EntityTest;
import de.felk.gamepp1.entities.Vector;
import de.felk.gamepp1.entities.Space;
import de.felk.gamepp1.entities.Sprite;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Game {

	private boolean running = true;

	public static void main(String[] args) {
		new Game().start();
	}

	public Game() {
		// Konstruktor.
		// Zur Initialisierung wird die start()-Methode verwendet
	}

	public void start() {
		// Hier beginnt das Programm

		EntityTest entity = new EntityTest(new Space(new Vector(0, 0), 1f, 0f, false), new Sprite());

		try {
			RenderEngine.init(800, 600);
		} catch (LWJGLException e1) {
			stop();
			e1.printStackTrace();
		}
		
		RenderEngine.setOrtho();
		
		// Hauptschleife
		while (running) {

			entity.render();
			
			Display.update();
			if (Display.isCloseRequested()) stop();
			
		}

	}
	
	public void stop() {
		running = false;
	}

}
