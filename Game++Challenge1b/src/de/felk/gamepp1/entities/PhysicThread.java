package de.felk.gamepp1.entities;

import java.util.ArrayList;

public class PhysicThread extends Thread {

	private final float DELTA = 0.016f;
	private ArrayList<Entity> entities;
	private static final Vector GRAVITY = new Vector(-100, 0);

	public PhysicThread(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	@Override
	public void run() {

		long timePrevNanos = System.nanoTime();
		float timeDelta = 0;

		// Hauptschleife
		while (!isInterrupted()) {

			timeDelta = (System.nanoTime() - timePrevNanos) / 1000000000f;
			timePrevNanos = System.nanoTime();

			if (timeDelta < DELTA) {
				try {
					Thread.sleep((long) ((DELTA - timeDelta) * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
					this.interrupt();
				}
			}

			synchronized (entities) {

				ArrayList<EntityPhysic> entitiesPhysics = new ArrayList<EntityPhysic>();
				EntityPhysic e;
				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i) instanceof EntityPhysic) {
						e = (EntityPhysic) entities.get(i);
						// apply gravity
						if (e.receivesGravity()) e.applyAcceleration(GRAVITY, DELTA);
						entitiesPhysics.add(e);
						e.updatePhysics(DELTA);
						//System.out.println("Geschwindigkeit: " + e.getVelocity().getValue());
					}
				}
				// Kollisionen auflösen
				EntityPhysic ep1, ep2;
				for (int i = 0; i < entitiesPhysics.size(); i++) {
					ep1 = entitiesPhysics.get(i);
					for (int j = i + 1; j < entitiesPhysics.size(); j++) {
						ep2 = entitiesPhysics.get(j);
						if (CollisionHandler.handle(ep1, ep2)) {
							// Wenn eine Kollision stattgefunden hat
							ep1.triggerEventHit(ep2);
							ep2.triggerEventHit(ep1);
							ep1.updatePhysics(DELTA);
							ep2.updatePhysics(DELTA);
						}
					}
					// DEBUG
					/*if (ep1.getPosition().getX() < Game.DEBUG_SIZE || ep1.getPosition().getX() + Game.DEBUG_SIZE > Game.WIDTH) {
						ep1.getVelocity().setX(-ep1.getVelocity().getX());
						ep1.updatePhysics(DELTA);
					}
					if (ep1.getPosition().getY() < Game.DEBUG_SIZE || ep1.getPosition().getY() + Game.DEBUG_SIZE > Game.HEIGHT) {
						ep1.getVelocity().setY(-ep1.getVelocity().getY());
						ep1.updatePhysics(DELTA);
					}*/
					//
				}
			}
		}

	}

}
