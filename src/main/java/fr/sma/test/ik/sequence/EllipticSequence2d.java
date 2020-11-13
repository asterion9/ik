package fr.sma.test.ik.sequence;

import fr.sma.test.ik.Vector2d;

public class EllipticSequence2d implements Sequence2d {
	private double x, y;
	private double a, b;

	public EllipticSequence2d(double x, double y, double a, double b) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
	}

	@Override
	public double length() {
		double h = Math.pow(a - b, 2)/Math.pow(a + b, 2);

		return Math.PI * (a + b) * (1 + h/4 + Math.pow(h, 2)/64 + Math.pow(h, 3)/256 + Math.pow(h, 4)/16384);
	}

	@Override
	public Vector2d getPoint(double t) {
		return new Vector2d(
				Math.cos(t * Math.PI * 2) * a + x,
				Math.sin(t * Math.PI * 2) * b + y
		);
	}
}
