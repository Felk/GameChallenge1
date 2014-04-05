package de.felk.gamepp1.entities;

import de.felk.gamepp1.RenderHelper;

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

	@Override
	public void render(Vector position, Color color) {
		BoundingCircle translated = translated(position);
		RenderHelper.setColor(color);
		RenderHelper.renderCircle(translated.getPosition().getX(), translated.getPosition().getY(), getRadius(), true);
	}
	
}
