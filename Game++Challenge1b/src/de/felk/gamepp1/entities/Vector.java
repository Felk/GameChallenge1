package de.felk.gamepp1.entities;

public class Vector {

	private float x;
	private float y;

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public Vector add(Vector v) {
		add(v.getX(), v.getY());
		return this;
	}
	
	public Vector add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector added(Vector v) {
		return new Vector(x, y).add(v);
	}

}
