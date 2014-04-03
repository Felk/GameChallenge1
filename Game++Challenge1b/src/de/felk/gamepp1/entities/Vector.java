package de.felk.gamepp1.entities;

public class Vector {

	private float x;
	private float y;
	private float value;
	private boolean valueDirty = true;

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
		valueDirty = true;
	}

	public void setY(float y) {
		this.y = y;
		valueDirty = true;
	}

	public void addX(float x) {
		setX(this.x + x);
	}

	public void addY(float y) {
		setY(this.y + y);
	}

	public Vector add(Vector v) {
		addX(v.getX());
		addY(v.getY());
		return this;
	}

	public Vector added(Vector v) {
		return clone().add(v);
	}

	public Vector subtract(Vector v) {
		addX(-v.getX());
		addY(-v.getY());
		return this;
	}

	public Vector subtracted(Vector v) {
		return clone().subtract(v);
	}

	public Vector multiply(float r) {
		setX(x * r);
		setY(y * r);
		return this;
	}

	public Vector multiplied(float r) {
		return clone().multiply(r);
	}

	private void recalculateValue() {
		value = (float) Math.sqrt(x * x + y * y);
		valueDirty = false;
	}

	public float getValue() {
		if (valueDirty) recalculateValue();
		return value;
	}

	public float getSlope() {
		return y / x;
	}

	@Override
	public Vector clone() {
		return new Vector(x, y);
	}

	@Override
	public String toString() {
		return "(" + x + "|" + y + ")";
	}

}
