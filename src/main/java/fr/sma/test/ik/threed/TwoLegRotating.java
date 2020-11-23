package fr.sma.test.ik.threed;

import fr.sma.test.ik.twod.Vector2d;

public class TwoLegRotating {
	private double l1, l2;
	private double angle1H, angle1V, angle2;

	public TwoLegRotating(double l1, double l2, double angle1H, double angle1V, double angle2) {
		this.l1 = l1;
		this.l2 = l2;
		this.angle1H = angle1H;
		this.angle1V = angle1V;
		this.angle2 = angle2;
	}

	public void moveIk(Vector3d target) {
		if (target.length() > l1 + l2) {
			throw new IllegalArgumentException("position is too far");
		} else if (target.length() < l1 - l2) {
			throw new IllegalArgumentException("position is too close");
		}

		this.angle1H = (target.getZ() > 0 ? -1 : 1) * Math.acos(target.getX() / target.xzLength());

		Vector2d rTarget = new Vector2d(target.getX() / Math.cos(this.angle1H), target.getY());

		this.angle1V = (rTarget.getY() > 0 ? 1 : -1) * Math.acos(rTarget.getX() / (rTarget.length())) +
				Math.acos((l1 * l1 + rTarget.length() * rTarget.length() - l2 * l2) / (2 * l1 * rTarget.length()));

		this.angle2 = -Math.acos((rTarget.length() * rTarget.length() - l1 * l1 - l2 * l2) / (2 * l1 * l2));
	}

	public void moveFk(double aH1, double aV1, double a2) {
		this.angle1H = aH1;
		this.angle1V = aV1;
		this.angle2 = a2;
	}

	public double getL1() {
		return l1;
	}

	public void setL1(double l1) {
		this.l1 = l1;
	}

	public double getL2() {
		return l2;
	}

	public void setL2(double l2) {
		this.l2 = l2;
	}

	public double getAngle1H() {
		return angle1H;
	}

	public double getAngle1V() {
		return angle1V;
	}

	public double getAngle2() {
		return angle2;
	}
}
