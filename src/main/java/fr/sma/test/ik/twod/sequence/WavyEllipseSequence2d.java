package fr.sma.test.ik.twod.sequence;

import fr.sma.test.ik.twod.Vector2d;

public class WavyEllipseSequence2d extends Sequence2d {
	private final double x;
	private final double y;  // center of the ellipse
	private final double a;
	private final double b;  // size coefficient of the ellipse
	private final double c;
	private final double p;  // size and period of the wave

	public WavyEllipseSequence2d(double x, double y, double a, double b, double c, double p) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
		this.c = c;
		this.p = p;
	}

	@Override
	public double length() {
		return 0;
	}

	@Override
	public Vector2d getPoint(double t) {
		return new Vector2d(
				Math.cos(t * Math.PI * 2) * a + x + c * Math.cos((t * p) * Math.PI * 2),
				Math.sin(t * Math.PI * 2) * b + y + c * Math.sin((t * p) * Math.PI * 2)
		);
	}
}
