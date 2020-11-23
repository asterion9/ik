package fr.sma.test.ik.threed.sequence;

import fr.sma.test.ik.threed.Vector3d;

public abstract class Sequence3d {
	/**
	 * get the perimeter of the sequence
	 *
	 * @return the length of the sequence
	 */
	double length() {
		return 0d;  // TODO calculate integral of the getPoint
	}

	/**
	 * get a point from the sequence at step t
	 *
	 * @param t the step, between 0 and 1
	 * @return the point corresponding to this step
	 */
	public abstract Vector3d getPoint(double t);
}
