package fr.sma.test.ik;

import java.util.Deque;

class Segment2d {
	private double len;
	private double angle;
	private Segment2d next;

	public Segment2d(double len, double angle) {
		this(len, angle, null);
	}

	public Segment2d(double len, double angle, Segment2d next) {
		this.len = len;
		this.angle = angle;
		this.next = next;
	}

	public void moveIk2Seg(Vector2d target) {
		if (this.next == null) {
			throw new IllegalArgumentException("no second segment to apply");
		}

		double length = target.length();
		double l1 = this.len;
		double l2 = this.next.len;

		if (length > l1 + l2) {
			throw new IllegalArgumentException("position is too far");
		} else if (length < l1 - l2) {
			throw new IllegalArgumentException("position is too close");
		}

		this.angle = (target.getY() > 0 ? 1 : -1) * Math.acos(target.getX() / (length)) +
				Math.acos((l1 * l1 + target.length() * target.length() - l2 * l2) / (2 * l1 * length));
		this.next.angle = -Math.acos((target.length() * target.length() - l1 * l1 - l2 * l2) / (2 * l1 * l2));
	}

	public void moveFk(Deque<Double> angles) {
		if (!angles.isEmpty()) {
			this.angle += angles.pop();
			if (next != null) {
				next.moveFk(angles);
			}
		}

	}

	public double getLen() {
		return len;
	}

	public Segment2d getNext() {
		return next;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}
