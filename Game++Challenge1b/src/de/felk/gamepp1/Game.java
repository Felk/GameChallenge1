package de.felk.gamepp1;

import java.util.ArrayList;
import java.util.Random;

import de.felk.gamepp1.entities.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class Game {

	private boolean running = true;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	public static final int AAABBC = 50;
	private static final Vector GRAVITY = new Vector(0, 0);

	public static void main(String[] args) {
		new Game().start();
	}

	public Game() {
		// Konstruktor.
		// Zur Initialisieren und Starten wird die start()-Methode verwendet
	}

	public void debugInit() {
		//BoundingAABB bounding = new BoundingAABB(new Vector(0, 0), new Vector(10, 10));
		BoundingCircle bounding = new BoundingCircle(AAABBC, new Vector(0, 0));
		/*EntityPhysic pet = new EntityPhysic(new Vector(400, 400), new Sprite(), bounding, 1);
		pet.addForce(new Vector(500, 500));
		EntityPhysic pet2 = new EntityPhysic(new Vector(517, 500), new Sprite(), bounding, 1);
		pet2.addForce(new Vector(-500, -500));
		entities.add(pet);
		entities.add(pet2);*/

		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			EntityPhysic e = new EntityPhysic(new Vector(r.nextInt(WIDTH), r.nextInt(HEIGHT)), new Sprite(), bounding, 1);
			e.addForce(new Vector(r.nextInt(1000), r.nextInt(1000)));
			entities.add(e);
		}

	}

	public void init() {
		debugInit();

		try {
			RenderEngine.init(WIDTH, HEIGHT);
		} catch (LWJGLException e1) {
			stop();
			e1.printStackTrace();
		}

		RenderEngine.setOrtho();
		RenderEngine.enableVSync();
	}

	public void start() {
		// Hier beginnt das Programm

		//EntityTest entity = new EntityTest(new Vector(0, 0), new Sprite());

		init();

		long timePrevNanos = System.nanoTime();
		float timeDelta = 0, lastDelta = 0;

		// Hauptschleife
		while (running) {

			RenderEngine.clearScreen();

			lastDelta = timeDelta;
			timeDelta = Math.min(0.1f, (System.nanoTime() - timePrevNanos) / 1000000000f);
			timePrevNanos = System.nanoTime();

			// Update Entities, baue Physik-Entity-Liste
			Entity e;
			ArrayList<EntityPhysic> entitiesPhysics = new ArrayList<EntityPhysic>();
			for (int i = 0; i < entities.size(); i++) {
				e = entities.get(i);
				e.update();
				if (e instanceof EntityPhysic) {
					// apply gravity
					((EntityPhysic) e).addForce(GRAVITY.multiplied(timeDelta));
					entitiesPhysics.add((EntityPhysic) e);
					((EntityPhysic) e).updatePhysics((float) timeDelta);
				}
			}

			// Kollisionen auflösen
			EntityPhysic ep1, ep2;
			for (int i = 0; i < entitiesPhysics.size(); i++) {
				ep1 = entitiesPhysics.get(i);
				for (int j = i + 1; j < entitiesPhysics.size(); j++) {
					ep2 = entitiesPhysics.get(j);
					if (ep1.isColliding(ep2)) {
						CollisionResolver.resolve(ep1, ep2);
						//ep1.updatePhysics(lastDelta);
						//ep2.updatePhysics(lastDelta);
					}
				}
				// DEBUG
				if (ep1.getPosition().getX() < AAABBC || ep1.getPosition().getX() + AAABBC > WIDTH) {
					ep1.getVelocity().setX(-ep1.getVelocity().getX());
					ep1.updatePhysics(lastDelta);
				}
				if (ep1.getPosition().getY() < AAABBC || ep1.getPosition().getY() + AAABBC > HEIGHT) {
					ep1.getVelocity().setY(-ep1.getVelocity().getY());
					ep1.updatePhysics(lastDelta);
				}
				//
			}

			// Render entities
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).render();
			}

			Display.update();
			if (Display.isCloseRequested()) stop();

		}

	}

	public void stop() {
		running = false;
	}

}
