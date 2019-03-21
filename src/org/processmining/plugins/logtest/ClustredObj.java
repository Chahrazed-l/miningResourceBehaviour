package org.processmining.plugins.logtest;

import java.util.ArrayList;

public class ClustredObj {
	String clusterName;
	ArrayList<Data> datalist;
	Centroid c;

	public ClustredObj(String clusterName, ArrayList<Data> datalist, Centroid c) {
		// TODO Auto-generated constructor stub
		this.clusterName = clusterName;
		this.datalist = datalist;
		this.c = c;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public Centroid getC() {
		return c;
	}

	public void setC(Centroid c) {
		this.c = c;
	}

	public ArrayList<Data> getDatalist() {
		return datalist;
	}

	public void setDatalist(ArrayList<Data> datalist) {
		this.datalist = datalist;
	}

}
