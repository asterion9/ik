package fr.sma.test.ik.threed.sequence;

import fr.sma.test.ik.threed.Vector3d;
import fr.sma.test.ik.twod.Vector2d;

import java.util.stream.DoubleStream;

public abstract class Sequence3d {
	/**
	 * get the perimeter of the sequence
	 *
	 * @return the length of the sequence
	 */
	public double length() {
		double length = 0;
		Vector3d prev = this.getPoint(0);
		for(double i=0.01; i<=1; i += 0.01) {
			Vector3d cur = this.getPoint(i);
			length += Math.sqrt(Math.pow(cur.getX() - prev.getX(), 2) + Math.pow(cur.getY() - prev.getY(), 2) + Math.pow(cur.getZ() - prev.getZ(), 2));
			prev = cur;
		}
		return length;
	}

	/**
	 * get a point from the sequence at step t
	 *
	 * @param t the step, between 0 and 1
	 * @return the point corresponding to this step
	 */
	public abstract Vector3d getPoint(double t);
}
