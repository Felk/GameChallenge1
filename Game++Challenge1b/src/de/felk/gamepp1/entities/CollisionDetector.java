package de.felk.gamepp1.entities;

public class CollisionDetector {

	public static boolean isColliding(Bounding b1, Bounding b2) {
		if (b1 instanceof BoundingCircle && b2 instanceof BoundingCircle) {
			return isColliding((BoundingCircle) b1, (BoundingCircle) b2);
		} else if (b1 instanceof BoundingCircle && b2 instanceof BoundingAABB) {
			return isColliding((BoundingCircle) b1, (BoundingAABB) b2);
		} else if (b1 instanceof BoundingAABB && b2 instanceof BoundingCircle) {
			return isColliding((BoundingCircle) b2, (BoundingAABB) b1);
		} else if (b1 instanceof BoundingAABB && b2 instanceof BoundingAABB) {
			return isColliding((BoundingAABB) b1, (BoundingAABB) b2);
		}
		return false;
	}

	public static boolean isColliding(BoundingCircle b1, BoundingCircle b2) {
		float difference = b1.getPosition().subtracted(b2.getPosition()).getValue();
		return (difference < b1.getRadius() + b2.getRadius());
	}

	public static boolean isColliding(BoundingCircle b1, BoundingAABB b2) {
		if (b2.getA().getX() >= b1.getPosition().getX() + b1.getRadius()) return false;
		if (b2.getA().getY() >= b1.getPosition().getY() + b1.getRadius()) return false;
		if (b2.getB().getX() <= b1.getPosition().getX() - b1.getRadius()) return false;
		if (b2.getB().getY() <= b1.getPosition().getY() - b1.getRadius()) return false;
		return true;
	}

	public static boolean isColliding(BoundingAABB b1, BoundingAABB b2) {
		if (b1.getA().getX() >= b2.getB().getX()) return false;
		if (b1.getA().getY() >= b2.getB().getY()) return false;
		if (b2.getA().getX() >= b1.getB().getX()) return false;
		if (b2.getA().getY() >= b1.getB().getY()) return false;
		return true;
	}

}
