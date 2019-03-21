package org.processmining.plugins.logtest;

public class Task {
	public String name;
	public long meanTime;

	public Task(String name, long meanTime) {
		this.name = name;
		this.meanTime = meanTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMeanTime() {
		return meanTime;
	}

	public void setMeanTime(long meanTime) {
		this.meanTime = meanTime;
	}

}
