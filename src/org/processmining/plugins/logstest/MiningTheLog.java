package org.processmining.plugins.logstest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;

public class MiningTheLog {
	static ParamInputConection inpConection = new ParamInputConection();
	static MainGui maingu = new MainGui();
	@Plugin(name = "Identify the Resource Behaviour", parameterLabels = { "Log File" }, returnLabels = {
			"Identify the Resource Behaviour" }, returnTypes = {JPanel.class }, userAccessible = true)
	@UITopiaVariant(affiliation = "loria", author = "C.Labba", email = "chahrazed.labba@loria.fr")

	public static JPanel connect(UIPluginContext context, XLog logfile) throws ClassNotFoundException, SQLException {
		InputParam inputparam = new InputParam();
		inputparam = inpConection.DbParams(inputparam);
		System.out.println(inputparam.hostname+inputparam.username+inputparam.password+inputparam.dbName);
		String hostname = inputparam.hostname;
		String username = inputparam.username;
		String password = inputparam.password;
		String dbName = inputparam.dbName;

		DbHandler dbhandler = new DbHandler();
		
		Connection con = dbhandler.getconnection("jdbc:mysql://" + hostname, username, password);
		
		Statement dbStatement = con.createStatement();
		ResultSet rs = dbStatement.executeQuery(
				"SELECT IF('" + dbName + "' IN(SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA), 1, 0) AS found");
		rs.beforeFirst();
		rs.next();
		int dbexists = rs.getInt("found");
		// System.out.println(dbexists);

		if (dbexists == 1) {
			con = dbhandler.getconnection("jdbc:mysql://" + hostname + "/" + dbName, username, password);
		} else {
			dbStatement.executeUpdate("CREATE DATABASE " + dbName);
			con = dbhandler.getconnection("jdbc:mysql://" + hostname + "/" + dbName, username, password);

		}
		return maingu.displayMainGui(con, logfile, hostname, username, password, dbName);

	}
}
