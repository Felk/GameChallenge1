package de.felk.gamepp1.entities;

import de.felk.gamepp1.RenderHelper;

public class BoundingAABB extends Bounding {

	private Vector a, b;

	public BoundingAABB(Vector a, Vector b) {
		this.a = a.clone();
		this.b = b.clone();
	}

	public Vector getA() {
		return a;
	}

	public void setA(Vector a) {
		this.a = a;
	}

	public Vector getB() {
		return b;
	}

	public void setB(Vector b) {
		this.b = b;
	}

	@Override
	public BoundingAABB clone() {
		return new BoundingAABB(a.clone(), b.clone());
	}

	@Override
	public BoundingAABB translated(Vector position) {
		BoundingAABB bounding = clone();
		bounding.a.add(position);
		bounding.b.add(position);
		return bounding;
	}

	@Override
	public String toString() {
		return "BoundingAABB(" + a.toString() + " - " + b.toString() + ")";
	}

	@Override
	public void render(Vector position, Color color) {
		BoundingAABB translated = translated(position);
		float width = translated.getB().getX() - translated.getA().getX();
		float height = translated.getB().getY() - translated.getA().getY();
		
		RenderHelper.setColor(color);
		RenderHelper.renderQuad(translated.getA().getX(), translated.getA().getY(), width, height);
	}

}
