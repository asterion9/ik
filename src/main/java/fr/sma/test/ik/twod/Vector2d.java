package fr.sma.test.ik.twod;

public class Vector2d {
	private double x;
	private double y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double length() {
		return Math.pow(x * x + y * y, 0.5d);
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
}
