package fr.sma.test.ik.threed.sequence;

import fr.sma.test.ik.threed.Vector3d;

public class SphericalSequence extends Sequence3d {
	private final double x;
	private final double y;
	private final double r;

	public SphericalSequence(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	@Override
	public Vector3d getPoint(double t) {
		double a = t * Math.PI * 2, b = t * Math.PI * 4;
		return new Vector3d(
				r * Math.sin(a) * Math.cos(b),
				r * Math.sin(a) * Math.sin(b),
				r * Math.cos(a)
		);
	}
}
