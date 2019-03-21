package org.processmining.plugins.logtest;

public class Centroid {
	private double mX = 0.0;
	private double mY = 0.0;
	private double mZ = 0.0;

	public Centroid() {
		return;
	}

	public Centroid(double newX, double newY) {
		this.mX = newX;
		this.mY = newY;
		return;
	}

	public Centroid(double newX, double newY, double newZ) {
		this.mX = newX;
		this.mY = newY;
		this.mZ = newZ;
		return;
	}

	public void X(double newX) {
		this.mX = newX;
		return;
	}

	public double X() {
		return this.mX;
	}

	public void Z(double newZ) {
		this.mZ = newZ;
		return;
	}

	public double Z() {
		return this.mZ;
	}

	public void Y(double newY) {
		this.mY = newY;
		return;
	}

	public double Y() {
		return this.mY;
	}
}
