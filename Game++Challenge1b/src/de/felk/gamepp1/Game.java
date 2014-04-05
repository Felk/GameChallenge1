package de.felk.gamepp1;

import java.util.ArrayList;
import java.util.Random;

import de.felk.gamepp1.entities.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import de.felk.gamepp1.sound.ALSource;
import de.felk.gamepp1.sound.SoundManager;

public class Game implements EntityActionListener {

	private boolean running = true;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	public static final float USER_ACCELERATION = 1000;

	private PhysicThread physics;
	private EntityPhysic playerBar = new EntityPhysic("player", new Vector(WIDTH / 5, HEIGHT / 2), new Sprite(new Color(1, 1, 1)), new BoundingAABB(new Vector(0, 0), new Vector(20, 100)), 1, 0.3f);
	private EntityPhysic ball = new EntityPhysic("circle", new Vector(0, 0), new Sprite(new Color(0.1f, 0, 0)), new BoundingCircle(25, new Vector(0, 0)), 0.7f, 0.8f);

	private Random random = new Random();

	ALSource soundBg, soundHit, soundLose, soundAmbulance;

	private float eventTimer, score;
	private float startTimer;
	private int last_score = 0;

	public static void main(String[] args) {
		new Game().start();
	}

	public Game() {
		// Konstruktor.
		// Zur Initialisieren und Starten wird die start()-Methode verwendet
	}

	public void debugInit() {

		Color red = new Color(1, 0, 0);

		Bounding boundingScreen = new BoundingAABB(new Vector(0, 0), new Vector(WIDTH, HEIGHT));
		EntityPhysic barTop = new EntityPhysic("barTop", new Vector(-1, HEIGHT + 1), new Sprite(red), boundingScreen, 0, 0);
		EntityPhysic barLeft = new EntityPhysic("barLeft", new Vector(-WIDTH + 10, -1), new Sprite(red), boundingScreen, 0, 0);
		EntityPhysic barRight = new EntityPhysic("barRight", new Vector(WIDTH - 10, -1), new Sprite(red), boundingScreen, 0, 0);
		EntityPhysic barBottom = new EntityPhysic("barBottom", new Vector(-1, -HEIGHT - 1), new Sprite(red), boundingScreen, 0, 0);
		barTop.setGravity(false);
		barLeft.setGravity(false);
		barRight.setGravity(false);
		barBottom.setGravity(false);
		entities.add(barTop);
		entities.add(barLeft);
		entities.add(barRight);
		entities.add(barBottom);

		entities.add(playerBar);
		entities.add(ball);
		ball.addActionListener(this);

		try {
			AL.create();
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SoundManager.setListener(new Vector(0, 0), new Vector(0, 0), new Vector(0, 0), new Vector(0, 0));
		SoundManager.instance.preLoadSounds();
		soundBg = SoundManager.instance.create("bg.wav", ALSource.PRIORITY_MODERATE, new Vector(0, 0), new Vector(0, 0), false, false, 0.3f, 1);
		soundHit = SoundManager.instance.create("hit.wav", ALSource.PRIORITY_MODERATE, new Vector(0, 0), new Vector(0, 0), false, false, 1, 1);
		soundLose = SoundManager.instance.create("lose.wav", ALSource.PRIORITY_MODERATE, new Vector(0, 0), new Vector(0, 0), false, false, 1, 1);
		soundAmbulance = SoundManager.instance.create("asd.wav", ALSource.PRIORITY_MODERATE, new Vector(0, 0), new Vector(0, 0), false, false, 0.2f, 1);
	}

	public void spawnNewBall() {

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
		RenderEngine.disableTexture();
		RenderEngine.disableLighting();

		Display.setTitle("Pong?");
		try {
			Display.setFullscreen(true);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		physics = new PhysicThread(entities);
		physics.start();
		resetGame();
	}

	public void start() {
		// Hier beginnt das Programm

		init();
		long prevNanos = System.nanoTime();
		float delta;

		// Hauptschleife
		while (running) {

			ball.getSprite().setColor(new Color(random.nextFloat() / 3, 0, 0));
			playerBar.getSprite().setColor(new Color(random.nextFloat() / 3, 0, 0));

			delta = ((System.nanoTime() - prevNanos) / 1000000000f);
			prevNanos = System.nanoTime();

			RenderEngine.clearScreen();

			// Update Entities, baue Physik-Entity-Liste
			Entity e;
			//ArrayList<EntityPhysic> entitiesPhysics = new ArrayList<EntityPhysic>();
			synchronized (entities) {
				for (int i = 0; i < entities.size(); i++) {
					e = entities.get(i);
					e.update();
				}
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) playerBar.applyAcceleration(new Vector(0, USER_ACCELERATION), delta);
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) playerBar.applyAcceleration(new Vector(-USER_ACCELERATION, 0), delta);
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) playerBar.applyAcceleration(new Vector(0, -USER_ACCELERATION), delta);
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) playerBar.applyAcceleration(new Vector(USER_ACCELERATION, 0), delta);

			// Render entities
			for (int i = 0; i < entities.size(); i++) {
				//entities.get(i).render();
				e = entities.get(i);
				if (e instanceof EntityPhysic) {
					((EntityPhysic) e).getBounding().render(e.getPosition(), e.getSprite().getColor());
				}
			}

			RenderHelper.setColor(new Color(random.nextFloat() / 2, 0, 0));
			RenderHelper.drawString("Score: " + ((int) score), WIDTH / 2, HEIGHT / 2);
			if (startTimer > 0) {
				RenderHelper.drawString("You lasted " + last_score + " seconds", WIDTH / 2, HEIGHT / 2 + 35);
			}

			Display.update();
			if (Display.isCloseRequested()) stop();

			eventTimer -= delta;
			score += delta;
			startTimer -= delta;

			if (eventTimer < 0) {
				float intensity = random.nextFloat();
				soundBg.setPitch(intensity * 2 + 1f);
				soundBg.play();
				eventTimer = random.nextFloat() * 3 + 1;
				ball.applyForce(new Vector(intensity * 300 - 150, intensity * 300 - 150));

				if (random.nextInt(20) == 0) soundAmbulance.play();

			}

		}

	}

	public void resetGame() {
		last_score = (int) score;
		startTimer = 2;
		ball.setVelocity(new Vector(0, 0));
		ball.setPosition(new Vector(WIDTH * 0.8f, HEIGHT * 0.5f));
		playerBar.applyForce(new Vector(random.nextFloat(), random.nextFloat()));
		score = 0;
	}

	public void stop() {
		physics.interrupt();
		running = false;
		SoundManager.instance.clear();
		AL.destroy();
	}

	@Override
	public void onEntityCollide(EntityPhysic e) {
		System.out.println("collision with " + e.getName());
		if (e.getName().equals("barLeft") || e.getName().equals("barRight")) {
			soundLose.play();
			resetGame();
		} else if (e.getName().equals("player")) {
			soundHit.setPitch(1.1f);
			soundHit.play();
		} else {
			soundHit.setPitch(1.0f);
			soundHit.play();
		}
	}
}
