package org.processmining.plugins.logstest;

public class InputParam {

	public String hostname;
	public String username;
	public String password;
	public String dbName;

	public void setDb(String hostname, String user, String password, String dbName) {

		this.hostname = hostname;
		this.username = user;
		this.password = password;
		this.dbName = dbName;

	}
}
