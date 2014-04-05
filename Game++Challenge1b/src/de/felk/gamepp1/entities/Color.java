package de.felk.gamepp1.entities;

public class Color {

	private float r, g, b;

	public Color(float r, float g, float b) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	@Override
	public Color clone() {
		return new Color(r, g, b);
	}

}
