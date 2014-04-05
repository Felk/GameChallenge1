package de.felk.gamepp1.entities;

public class CollisionHandler {

	/**
	 * Procedure, which detects and resolves collisions between two entities
	 * 
	 * @param e1 First entity
	 * @param e2 Second entity
	 * @return true, if a collision occured
	 */
	public static boolean handle(EntityPhysic e1, EntityPhysic e2) {
		Bounding b1 = e1.getBounding();
		Bounding b2 = e2.getBounding();
		if (b1 instanceof BoundingCircle && b2 instanceof BoundingCircle) {
			return handleCC(e1, e2);
		} else if (b1 instanceof BoundingCircle && b2 instanceof BoundingAABB) {
			return handleCA(e1, e2);
		} else if (b1 instanceof BoundingAABB && b2 instanceof BoundingCircle) {
			return handleCA(e2, e1);
		} else if (b1 instanceof BoundingAABB && b2 instanceof BoundingAABB) {
			return handleAA(e1, e2);
		}
		return false;
	}

	/**
	 * Algorithm to detect and resolve collisions between two circles
	 * 
	 * @param e1 First entity
	 * @param e2 Second entity
	 * @return true, if a collision occured
	 */
	public static boolean handleCC(EntityPhysic e1, EntityPhysic e2) {
		BoundingCircle b1 = (BoundingCircle) e1.getBounding().translated(e1.getPosition());
		BoundingCircle b2 = (BoundingCircle) e2.getBounding().translated(e2.getPosition());
		float difference = b1.getPosition().subtracted(b2.getPosition()).getValue();
		if (difference < b1.getRadius() + b2.getRadius()) {
			Vector[] vNew = resolveCollision(e1.getVelocity(), e2.getVelocity(), e1.getInverseMass(), e2.getInverseMass(), b2.getPosition().subtracted(b1.getPosition()));
			e1.setVelocity(vNew[0]);
			e2.setVelocity(vNew[1]);
			return true;
		}
		return false;
	}

	/**
	 * Algorithm to detect and resolve collisions between a circle and a AABB
	 * 
	 * @param e1 First entity
	 * @param e2 Second entity
	 * @return true, if a collision occured
	 */
	public static boolean handleCA(EntityPhysic e1, EntityPhysic e2) {
		BoundingCircle b1 = (BoundingCircle) e1.getBounding().translated(e1.getPosition());
		BoundingAABB b2 = (BoundingAABB) e2.getBounding().translated(e2.getPosition());
		Vector m = b1.getPosition();
		float r = b1.getRadius();
		Vector a = b2.getA();
		Vector b = b2.getB();

		if (a.getX() >= m.getX() + r) return false;
		if (b.getX() <= m.getX() - r) return false;
		if (a.getY() >= m.getY() + r) return false;
		if (b.getY() <= m.getY() - r) return false;

		Vector ab = new Vector(a.getX(), b.getY());
		Vector ba = new Vector(b.getX(), a.getY());

		Vector slope = null;
		Vector help;
		if ((help = a.subtracted(m)).getValue() <= r) {
			slope = help.clone();
		} else if ((help = ab.subtracted(m)).getValue() <= r) {
			slope = help.clone();
		} else if ((help = b.subtracted(m)).getValue() <= r) {
			slope = help.clone();
		} else if ((help = ba.subtracted(m)).getValue() <= r) {
			slope = help.clone();
		}

		if (slope == null) {
			// Eventuell Kantenkollision
			boolean vertical;
			if (m.getX() > a.getX() && m.getX() < b.getX())
				vertical = false;
			else if (m.getY() > a.getY() && m.getY() < b.getY())
				vertical = true;
			else
				return false; // Kugel ist im Eckbereich, berührt aber keine Ecke
			slope = vertical ? new Vector(1, 0) : new Vector(0, 1);
		}

		Vector[] vNew = resolveCollision(e1.getVelocity(), e2.getVelocity(), e1.getInverseMass(), e2.getInverseMass(), slope);
		e1.setVelocity(vNew[0]);
		e2.setVelocity(vNew[1]);
		return true;

	}

	/**
	 * Algorithm to detect and handle collisions between two AABBs
	 * 
	 * @param e1 First entity
	 * @param e2 Second entity
	 * @return true, if a collision occured
	 */
	public static boolean handleAA(EntityPhysic e1, EntityPhysic e2) {
		BoundingAABB b1 = (BoundingAABB) e1.getBounding().translated(e1.getPosition());
		BoundingAABB b2 = (BoundingAABB) e2.getBounding().translated(e2.getPosition());

		if (b1.getA().getX() >= b2.getB().getX()) return false;
		if (b1.getA().getY() >= b2.getB().getY()) return false;
		if (b2.getA().getX() >= b1.getB().getX()) return false;
		if (b2.getA().getY() >= b1.getB().getY()) return false;

		Vector relative = e1.getVelocity().subtracted(e2.getVelocity());

		float overlapX = 0, overlapY = 0;
		if (relative.getX() >= 0) overlapX = b1.getB().getX() - b2.getA().getX();
		else overlapX = b1.getA().getX() - b2.getB().getX();
		if (relative.getY() >= 0) overlapY = b1.getB().getY() - b2.getA().getY();
		else overlapY = b1.getA().getY() - b2.getB().getY();
		Vector overlapSlope = new Vector(overlapX, overlapY);

		Vector slope = (Math.abs(relative.getSlope()) < overlapSlope.getSlope()) ? new Vector(1, 0) : new Vector(0, 1);
		Vector[] vNew = resolveCollision(e1.getVelocity(), e2.getVelocity(), e1.getInverseMass(), e2.getInverseMass(), slope);
		e1.setVelocity(vNew[0]);
		e2.setVelocity(vNew[1]);
		return true;
	}

	/**
	 * Algorithm to resolve collisions.
	 * 
	 * @param v1 Velocity of 1st entity
	 * @param v2 Velocity of 2nd entity
	 * @param m1 Inverse mass of 1st entity
	 * @param m2 Inverse mass of 2nd entity 
	 * @param sz Central slope (slope of vector between the centers of the 2 entities)
	 * 
	 * @return Array with the 2 new velocities
	 */
	public static Vector[] resolveCollision(Vector v1, Vector v2, float m1, float m2, Vector slope) {

		float sz = slope.getSlope();
		float st = -1 / sz;

		float sv1 = v1.getSlope();
		float sv2 = v2.getSlope();

		float xt1 = v1.getX() * ((sz - sv1) / (sz - st));
		float yt1 = xt1 * st;
		Vector t1 = new Vector(xt1, yt1);
		float xz1 = v1.getX() * ((st - sv1) / (st - sz));
		float yz1 = xz1 * sz;
		Vector z1 = new Vector(xz1, yz1);

		float xt2 = v2.getX() * ((sz - sv2) / (sz - st));
		float yt2 = xt2 * st;
		Vector t2 = new Vector(xt2, yt2);
		float xz2 = v2.getX() * ((st - sv2) / (st - sz));
		float yz2 = xz2 * sz;
		Vector z2 = new Vector(xz2, yz2);

		Vector[] zs = resolve1dCollision(z1, z2, m1, m2);
		Vector zi1 = zs[0];
		Vector zi2 = zs[1];
		zi1.add(t1);
		zi2.add(t2);

		Vector[] result = new Vector[2];
		result[0] = zi1;
		result[1] = zi2;
		return result;
	}

	/**
	 * Algorithm to resolve 1-dimensional collisions
	 * 
	 * @param v1 Velocity of 1st entity
	 * @param v2 Velocity of 2nd entity
	 * @param m1 Inverse mass of 1st entity
	 * @param m2 Inverse mass of 2nd entity 
	 * 
	 * @return Array with the 2 new velocities
	 */
	public static Vector[] resolve1dCollision(Vector v1, Vector v2, float m1, float m2) {
		// Formel für Stoß (1d)
		Vector u = v1.multiplied(m2);
		u.add(v2.multiplied(m1));
		u.multiply(2 / (m1 + m2));

		Vector[] result = new Vector[2];
		result[0] = u.subtracted(v1);
		result[1] = u.subtracted(v2);
		return result;
	}

	public static Vector getPenalityForce(float deep, float slope) {

		return new Vector(1, slope).setValue(100f);

	}

}
