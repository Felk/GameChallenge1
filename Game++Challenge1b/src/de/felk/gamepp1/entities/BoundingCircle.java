package de.felk.gamepp1.entities;

public class BoundingCircle extends Bounding {

	private float radius;
	private Vector position = new Vector(0,0);
	
	public BoundingCircle(float radius, Vector position) {
		this.radius = radius;
		this.position = position.clone();
	}

	public float getRadius() {
		return radius;
	}
	
	@Override
	public BoundingCircle clone() {
		return new BoundingCircle(radius, position.clone());
	}

	@Override
	public BoundingCircle translated(Vector position) {
		BoundingCircle bounding = clone();
		bounding.position.add(position);
		return bounding;
	}
	
	public Vector getPosition() {
		return position;
	}
	
}
