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
}
