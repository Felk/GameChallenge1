package de.felk.gamepp1.entities;

public abstract class Bounding {

	public boolean isColliding(Bounding b) {
		return CollisionDetector.isColliding2(this, b);
	}
	
	@Override
	public Bounding clone() {
		// Just for the compiler, never used (no instances of abstract)
		return null;
	}
	
	public abstract Bounding translated(Vector position);
	
}
