package fr.sma.test.ik.threed.sequence;

import fr.sma.test.ik.threed.Vector3d;

public class WalkingSequence3d extends Sequence3d {
	private Vector3d position;
	private double yAngle;
	private double height;
	private double width;

	public WalkingSequence3d(double x0, double y0, double z0, double yAngle, double height, double width) {
		this.position = new Vector3d(x0, y0, z0);
		this.yAngle = yAngle;
		this.height = height;
		this.width = width;
	}

	@Override
	public Vector3d getPoint(double t) {
		double ratioWalk = 0.4;
		Vector3d target;
		if (t < ratioWalk) {
			target = new Vector3d(
					position.getX() + width / 2 - (t / ratioWalk) * width,
					position.getY(),
					position.getZ());
		} else {
			double te = 0.5 - 0.5 * (t - ratioWalk) / (1 - ratioWalk);
			target = new Vector3d(
					position.getX() + Math.cos(te * Math.PI * 2) * (width / 2),
					position.getY() + Math.sin(te * Math.PI * 2) * height,
					position.getZ());
		}
		return target.rotateAroundY(yAngle, position);
	}

	public Vector3d getPosition() {
		return position;
	}

	public void setPosition(Vector3d position) {
		this.position = position;
	}

	public double getyAngle() {
		return yAngle;
	}

	public void setyAngle(double yAngle) {
		this.yAngle = yAngle;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
