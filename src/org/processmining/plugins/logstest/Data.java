package org.processmining.plugins.logstest;

public class Data {

	private double mX = 0;
	private double mY = 0;
	private double mZ = 0;
	private String name="";
	
	private int mCluster = 0;

	public Data() {
		return;
	}

	public Data(double x, double y, String name) {
		this.X(x);
		this.Y(y);
		this.name=name;
		return;
	}
	public Data(double x, double y, double z, String name) {
		this.X(x);
		this.Y(y);
		this.Z(z);
		this.name=name;
		return;
	}

	public void X(double x) {
		this.mX = x;
		return;
	}

	public double Z() {
		return this.mZ;
	}
	public void Z(double z) {
		this.mZ = z;
		return;
	}

	public double X() {
		return this.mX;
	}
	public void Name(String name) {
		this.name = name;
		return;
	}

	public String Name() {
		return this.name;
	}

	public void Y(double y) {
		this.mY = y;
		return;
	}

	public double Y() {
		return this.mY;
	}

	public void cluster(int clusterNumber) {
		this.mCluster = clusterNumber;
		return;
	}

	public int cluster() {
		return this.mCluster;
	}
}
