package fr.sma.test.ik;

class TwoLegPlanar {
	private double l1, l2;
	private double angle1, angle2;

	public TwoLegPlanar(double l1, double l2, double angle1, double angle2) {
		this.l1 = l1;
		this.l2 = l2;
		this.angle1 = angle1;
		this.angle2 = angle2;
	}

	public void moveIk2Seg(Vector2d target) {
		double length = target.length();

		if (length > l1 + l2) {
			throw new IllegalArgumentException("position is too far");
		} else if (length < l1 - l2) {
			throw new IllegalArgumentException("position is too close");
		}

		this.angle1 = (target.getY() > 0 ? 1 : -1) * Math.acos(target.getX() / (length)) +
				Math.acos((l1 * l1 + target.length() * target.length() - l2 * l2) / (2 * l1 * length));
		this.angle2 = -Math.acos((target.length() * target.length() - l1 * l1 - l2 * l2) / (2 * l1 * l2));
	}

	public void moveFk(double a1, double a2) {
		this.angle1 += a1;
		this.angle2 += a2;
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

	public double getAngle1() {
		return angle1;
	}

	public void setAngle1(double angle1) {
		this.angle1 = angle1;
	}

	public double getAngle2() {
		return angle2;
	}

	public void setAngle2(double angle2) {
		this.angle2 = angle2;
	}
}
