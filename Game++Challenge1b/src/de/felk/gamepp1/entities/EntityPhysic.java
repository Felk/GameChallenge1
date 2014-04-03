package de.felk.gamepp1.entities;

public class EntityPhysic extends Entity {

	private Vector force;
	private Vector velocity;
	private float inverseMass;
	private Bounding bounding;

	public EntityPhysic(Vector position, Sprite sprite, Bounding bounding, float mass) {
		super(position, sprite);
		this.bounding = bounding;
		inverseMass = 1 / mass;
		force = new Vector(0, 0);
		velocity = new Vector(0, 0);
	}

	public void updatePhysics(float timeDelta) {

		force.multiply(inverseMass);
		velocity.add(force);
		force.multiply(0);

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

	public void addForce(Vector f) {
		force.add(f);
	}

}
