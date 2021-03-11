package fr.sma.test.ik.threed.sequence;

import fr.sma.test.ik.threed.Vector3d;

public class TurningSequence3d extends Sequence3d {
	private Vector3d center;
	private double radius;
	private double radStart;
	private double radLength;
	private double height;

	public TurningSequence3d(Vector3d center, double radius, double radStart, double radLength, double height) {
		this.center = center;
		this.radius = radius;
		this.radStart = radStart;
		this.radLength = radLength;
		this.height = height;
	}

	@Override
	public Vector3d getPoint(double t) {
		double ratioWalk = 0.8;
		if (t < ratioWalk) {
			double tg = t / ratioWalk;
			return new Vector3d(
					center.getX() + radius * Math.cos(radStart + (radLength * (tg))),
					center.getY(),
					center.getZ() + radius * Math.sin(radStart + (radLength * (tg))));
		}
		else {
			double tv = 0.5 * (t - ratioWalk) / (1 - ratioWalk);
			final double th = (1 + Math.cos(tv * Math.PI * 2)) / 2;
			return new Vector3d(
					center.getX() + radius * Math.cos(radStart + (radLength * th)),
					center.getY() + Math.sin(tv * Math.PI * 2) * height,
					center.getZ() + radius * Math.sin(radStart + (radLength * th)));
		}
	}

	public Vector3d getCenter() {
		return center;
	}

	public void setCenter(Vector3d center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getRadStart() {
		return radStart;
	}

	public void setRadStart(double radStart) {
		this.radStart = radStart;
	}

	public double getRadLength() {
		return radLength;
	}

	public void setRadLength(double radLength) {
		this.radLength = radLength;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}
