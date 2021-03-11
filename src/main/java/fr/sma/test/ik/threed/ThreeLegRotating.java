package fr.sma.test.ik.threed;

import fr.sma.test.ik.twod.Vector2d;

public class ThreeLegRotating {
	private double l0, l1, l2;
	private double angle1H, angle1V, angle2;

	public ThreeLegRotating(double l0, double l1, double l2, double angle1H, double angle1V, double angle2) {
		this.l0 = l0;
		this.l1 = l1;
		this.l2 = l2;
		this.angle1H = angle1H;
		this.angle1V = angle1V;
		this.angle2 = angle2;
	}

	public void moveIk(Vector3d target) {
		if (target.length() > l0 + l1 + l2) {
			final double reductionRatio = 0.99 * (l0 + l1 + l2) / (target.length());
			target = target.multiply(reductionRatio);
			System.out.println("target is too far, reduce it by " + reductionRatio);
		} else if (target.length() < l0 + l1 - l2) {
			final double augmentationRatio = 1.01 * (l0 + l1 - l2) / target.length();
			target = target.multiply(augmentationRatio);
			System.out.println("target is too close, augment it by " + augmentationRatio);
		}

		this.angle1H = (target.getZ() > 0 ? -1 : 1) * Math.acos(target.getX() / target.xzLength());

		Vector2d rTarget = new Vector2d(target.getX() / Math.cos(this.angle1H) - l0, target.getY());

		this.angle1V = (rTarget.getY() > 0 ? 1 : -1) * Math.acos(rTarget.getX() / rTarget.length()) +
				Math.acos((l1 * l1 + rTarget.length() * rTarget.length() - l2 * l2) / (2 * l1 * rTarget.length()));

		this.angle2 = -Math.acos((rTarget.length() * rTarget.length() - l1 * l1 - l2 * l2) / (2 * l1 * l2));

		System.out.printf("(%4.2f, %4.2f, %4.2f), a0=%f, a1=%f, a2=%f%n", target.getX()/20, target.getY()/20, target.getZ()/20, angle1H, angle1V, angle2);
	}

	public void moveFk(double aH1, double aV1, double a2) {
		this.angle1H = aH1;
		this.angle1V = aV1;
		this.angle2 = a2;
	}

	public double getL0() {
		return l0;
	}

	public void setL0(double l0) {
		this.l0 = l0;
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
