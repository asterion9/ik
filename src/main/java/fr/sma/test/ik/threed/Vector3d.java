package fr.sma.test.ik.threed;

public class Vector3d {
	private double x;
	private double y;
	private double z;

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double xyLength() {
		return Math.sqrt(x * x + y * y);
	}

	public double xzLength() {
		return Math.sqrt(x * x + z * z);
	}

	public double zyLength() {
		return Math.sqrt(z * z + y * y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector3d rotateAroundY(double yAngle, Vector3d rotationCenter) {
		double cosA = Math.cos(yAngle);
		double sinA = Math.sin(yAngle);
		return new Vector3d(
				rotationCenter.getX() + cosA * (x- rotationCenter.getX()) + sinA * (z- rotationCenter.getZ()),
				y,
				rotationCenter.getZ() + -sinA * (x - rotationCenter.getX()) + cosA * (z - rotationCenter.getZ())
		);
	}

	public Vector3d multiply(double scalar) {
		return new Vector3d(x * scalar, y * scalar, z * scalar);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vector3d)) return false;

		Vector3d vector3d = (Vector3d) o;

		if (Double.compare(vector3d.x, x) != 0) return false;
		if (Double.compare(vector3d.y, y) != 0) return false;
		return Double.compare(vector3d.z, z) == 0;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
