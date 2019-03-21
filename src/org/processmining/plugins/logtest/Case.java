package org.processmining.plugins.logtest;

public class Case {
	String caseid;
	long startdate;
	String enddate;

	public Case(String caseid, long startdate, String enddate) {
		this.caseid = caseid;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	

	public long getStartdate() {
		return startdate;
	}

	public void setStartdate(long startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}
