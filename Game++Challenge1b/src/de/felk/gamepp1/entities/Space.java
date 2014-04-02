package de.felk.gamepp1.entities;

public class Space {

	private Vector position;
	private float scaling;
	private float rotation;
	private boolean flipped;

	public Space(Vector position, float scaling, float rotation, boolean flipped) {
		this.setPosition(position);
		this.setScaling(scaling);
		this.setRotation(rotation);
		this.setFlipped(flipped);
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public float getScaling() {
		return scaling;
	}

	public void setScaling(float scaling) {
		this.scaling = scaling;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

}
