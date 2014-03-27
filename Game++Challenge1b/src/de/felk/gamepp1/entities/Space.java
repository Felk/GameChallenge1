package de.felk.gamepp1.entities;

public class Space {

	private Position position;
	private float scaling;
	private float rotation;
	private boolean flipped;

	public Space(Position position, float scaling, float rotation, boolean flipped) {
		this.setPosition(position);
		this.setScaling(scaling);
		this.setRotation(rotation);
		this.setFlipped(flipped);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
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
