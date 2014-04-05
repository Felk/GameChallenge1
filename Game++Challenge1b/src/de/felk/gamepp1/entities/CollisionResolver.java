package de.felk.gamepp1.entities;

public class CollisionResolver {

	public static void resolve(EntityPhysic e1, EntityPhysic e2) {

		System.out.println("Old velocities: " + e1.getVelocity().toString() + " ~ " + e2.getVelocity().toString());

		Vector v1 = e1.getVelocity();
		Vector v2 = e2.getVelocity();

		float sv1 = v1.getSlope();
		float sv2 = v2.getSlope();
		Vector z = e2.getPosition().subtracted(e1.getPosition());
		//Vector t = new Vector(z.getY(), -z.getX());
		float sz = z.getSlope();
		if (e1.getBounding() instanceof BoundingAABB || e2.getBounding() instanceof BoundingAABB) sz = Float.MAX_VALUE;
		float st = -1 / sz;
		//float st = t.getSlope();

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

		Vector[] zs = stoss(e1.getInverseMass(), e2.getInverseMass(), z1, z2);
		Vector zi1 = zs[0];
		Vector zi2 = zs[1];
		zi1.add(t1);
		zi2.add(t2);

		e1.setVelocity(zi1);
		e2.setVelocity(zi2);

		System.out.println("New velocities: " + e1.getVelocity().toString() + " ~ " + e2.getVelocity().toString());

	}

	public static Vector[] stoss(float m1, float m2, Vector v1, Vector v2) {
		// Formel f�r Sto� (1d)
		Vector u = v1.multiplied(m2);
		u.add(v2.multiplied(m1));
		u.multiply(2 / (m1 + m2));

		Vector[] result = new Vector[2];
		result[0] = u.subtracted(v1);
		result[1] = u.subtracted(v2);
		return result;
	}
}
