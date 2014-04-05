package de.felk.gamepp1.entities;

public abstract class Bounding {

	public boolean isColliding(Bounding b) {
		return CollisionDetector.isColliding(this, b);
	}
	
	@Override
	public Bounding clone() {
		// Just for the compiler, never used (no instances of abstract)
		return null;
	}
	
	public abstract Bounding translated(Vector position);
	public abstract void render(Vector position, Color color);
	
}
