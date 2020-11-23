package fr.sma.test.ik.twod.sequence;

import fr.sma.test.ik.twod.Vector2d;

public abstract class Sequence2d {
	/**
	 * get the perimeter of the sequence
	 *
	 * @return the length of the sequence
	 */
	public double length() {
		return 0;  // TODO calculate integral of the getPoint
	}

	/**
	 * get a point from the sequence at step t
	 *
	 * @param t the step, between 0 and 1
	 * @return the point corresponding to this step
	 */
	public abstract Vector2d getPoint(double t);
}
