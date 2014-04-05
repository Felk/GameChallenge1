package de.felk.gamepp1.entities;

import java.util.ArrayList;

public class EntityPhysic extends Entity {

	private Vector force;
	private Vector velocity;
	private float inverseMass;
	private Bounding bounding;
	private boolean gravity = true;
	private ArrayList<EntityActionListener> listeners = new ArrayList<EntityActionListener>();
	private float friction;
	
	public static final float MAX_SPEED = 2000;

	public EntityPhysic(String name, Vector position, Sprite sprite, Bounding bounding, float inverseMass, float friction) {
		super(name, position, sprite);
		this.bounding = bounding.clone();
		this.inverseMass = inverseMass;
		this.friction = friction;
		force = new Vector(0, 0);
		velocity = new Vector(0, 0);
	}

	public void updatePhysics(float timeDelta) {

		force.multiply(inverseMass);
		velocity.add(force);
		force.multiply(0);
		
		if (velocity.getValue() > MAX_SPEED) velocity.setValue(MAX_SPEED);
		velocity.multiply((float) Math.pow(friction, timeDelta));
		
		getPosition().add(velocity.multiplied(timeDelta));

	}

	public boolean isColliding(EntityPhysic e) {
		Bounding b1 = bounding.translated(getPosition());
		Bounding b2 = e.getBounding().translated(e.getPosition());
		return b1.isColliding(b2);
	}

	public Bounding getBounding() {
		return bounding;
	}

	public float getInverseMass() {
		return inverseMass;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector v) {
		velocity = v.clone();
	}

	public void applyForce(Vector f) {
		force.add(f);
	}
	
	public void applyAcceleration(Vector f, float delta) {
		applyForce(f.multiplied(delta));
	}

	@Override
	public void render() {
		getSprite().render2(getPosition(), 1 / inverseMass);
	}
	
	@Override
	public EntityPhysic clone() {
		EntityPhysic e = new EntityPhysic(getName(), getPosition().clone(), getSprite().clone(), bounding.clone(), inverseMass, friction);
		e.applyForce(force);
		e.setVelocity(velocity);
		return e;
	}

	public boolean receivesGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}
	
	public void addActionListener(EntityActionListener listener) {
		if (!listeners.contains(listener)) listeners.add(listener);
	}
	
	public void removeActionListener(EntityActionListener listener) {
		listeners.remove(listener);
	}
	
	public void triggerEventHit(EntityPhysic entity) {
		for (EntityActionListener l : listeners) l.onEntityCollide(entity);
	}

}
