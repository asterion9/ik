package fr.sma.test.ik.sequence;

import fr.sma.test.ik.Vector2d;

public interface Sequence2d {
	/**
	 * get the perimeter of the sequence
	 * @return the length of the sequence
	 */
	double length();

	/**
	 * get a point from the sequece at step t
	 * @param t the step, between 0 and 1
	 * @return the point corresponding to this step
	 */
	Vector2d getPoint(double t);
}
