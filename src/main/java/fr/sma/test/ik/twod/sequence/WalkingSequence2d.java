package fr.sma.test.ik.twod.sequence;

import fr.sma.test.ik.twod.Vector2d;

public class WalkingSequence2d extends Sequence2d {
	private final double x0, y0;
	private final double height;
	private final double width;

	public WalkingSequence2d(double x0, double y0, double height, double width) {
		this.x0 = x0;
		this.y0 = y0;
		this.height = height;
		this.width = width;
	}

	@Override
	public Vector2d getPoint(double t) {
		double ratioWalk = 0.4;
		if (t < ratioWalk) {
			return new Vector2d(x0 + width / 2 - (t / ratioWalk) * width, y0);
		} else {
			double te = 0.5 - 0.5 * (t - ratioWalk) / (1 - ratioWalk);
			return new Vector2d(x0 + Math.cos(te * Math.PI * 2) * (width / 2),
					y0 + Math.sin(te * Math.PI * 2) * height);
		}
	}
}
