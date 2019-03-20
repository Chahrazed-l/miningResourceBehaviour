package org.processmining.plugins.logstest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XAttributeLiteral;
import org.deckfour.xes.model.XAttributeTimestamp;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ProcessLogData {
	//Method to create database and tables

	public void createDb(final Connection con, XLog log, InputParam ip) throws SQLException, ParseException {
		System.out.println("Start the execution From createDb");
		java.sql.Statement dbStatement = con.createStatement();
		String dbname = ip.dbName;
		dbStatement.executeUpdate("DROP DATABASE IF EXISTS " + dbname);
		dbStatement.executeUpdate("CREATE DATABASE " + dbname);
		dbStatement.executeQuery("use " + dbname);

		Vector<String> eventatt = new Vector<String>();
		Vector<String> caseatt = new Vector<String>();
		List<XAttribute> eventattlog = log.getGlobalEventAttributes();
		List<XAttribute> caseattlog = log.getGlobalTraceAttributes();

		// Add the extracted attributes of event from event log to the vector eventatt
		for (int i = 0; i < eventattlog.size(); i++) {
			eventatt.add(eventattlog.get(i).getKey());
		}

		// Add the extracted attributes of cases from event log to the vector caseatt
		for (int i = 0; i < caseattlog.size(); i++) {
			caseatt.add(caseattlog.get(i).getKey());
		}

		DefineECLDBmappingGui dmap = new DefineECLDBmappingGui();
		ECLDBmapping map = new ECLDBmapping();
		map = dmap.getELDBmapping(eventatt);

		DefineECLDBmappingGui dmapca = new DefineECLDBmappingGui();
		map = dmapca.getCLMapping(caseatt, map);

		String casetype = map.DBCLType.elementAt(0);

		//Create the tables eventlog et caselog

		String eastring = "";
		for (int i = 0; i < map.DBELAttribute.size(); i++) {

			eastring += map.DBELAttribute.elementAt(i) + " " + map.DBELType.elementAt(i) + ", ";
			System.out.println(map.DBELAttribute.elementAt(i) + "  " + map.DBELType.elementAt(i));

		}

		if (eastring.length() > 0) {
			eastring = eastring.substring(0, eastring.length() - 2);
			System.out.println("+ " + eastring);
		}

		String elquery = "CREATE TABLE eventlog(CaseId " + casetype + ", " + eastring + ")";
		dbStatement.executeUpdate(elquery);

		String castring = "";

		for (int i = 0; i < map.DBCLAttribute.size(); i++) {

			castring += map.DBCLAttribute.elementAt(i) + " " + map.DBCLType.elementAt(i) + ", ";

		}

		if (castring.length() > 0) {
			castring = castring.substring(0, castring.length() - 2);
		}

		String clquery = "CREATE TABLE caselog(" + castring + ")";
		dbStatement.executeUpdate(clquery);

		//logToDB2(log, con, map);
		logToDB(log, con, map);
		createSQLViews(con);
		System.out.println("Finish CreateDb");

	}

	public void createSQLViews(Connection con) throws SQLException {
		java.sql.Statement st = con.createStatement();
		String code2 = " Create VIEW eventsch As select distinct EventId as H from eventlog where State='SCHEDULE';";
		String code = "  Create VIEW eventstart As select distinct EventId as S from eventlog where State='START';";
		String code1 = " Create VIEW eventcomplete As select distinct EventId as C from eventlog where State='COMPLETE'and Resource<>'null' ;";
		String code3 = " Create VIEW eventjoint As select S from eventstart as t1 INNER JOIN eventcomplete as t2 on t1.S=t2.C INNER JOIN eventsch as t3 on t1.S=t3.H;";
		String code4 = " Create VIEW eventRes As select * from eventlog where EventId in (select S from eventjoint);";

		st.executeUpdate(code);
		st.executeUpdate(code1);
		st.executeUpdate(code2);
		st.executeUpdate(code3);
		st.executeUpdate(code4);
		//con.close();
		System.out.println(" process importing and creating views successfully achieved!");
	}

	public Vector<String> getStartEndDate(Map<String, Event> events) throws ParseException {
		Vector<String> time = new Vector<String>();
		int i = 0;
		LocalDateTime date1 = null;
		LocalDateTime date2 = null;
		//SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
		//System.out.println("The size of Events " + events.size());
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (i == 0) {
				//System.out.println(
						//"La date est " + entry.getValue().getDateSchedule() + " " + entry.getValue().getDateFin());
				String t1 = entry.getValue().getDateSchedule().substring(0, 21);
				String t2 = entry.getValue().getDateFin().substring(0, 21);
				date1 = LocalDateTime.parse(t1, formatter);
				date2 = LocalDateTime.parse(t2, formatter);

				//System.out.println(date1.toString() + "  " + date2.toString());
				i = i + 1;
			} else {
				//System.out.println(
						//"La date est " + entry.getValue().getDateSchedule() + " " + entry.getValue().getDateFin());
				String t1 = entry.getValue().getDateSchedule().substring(0, 21);
		
				String t2 = entry.getValue().getDateFin().substring(0, 21);
				
				LocalDateTime date11 = LocalDateTime.parse(t1, formatter);
				LocalDateTime date22 = LocalDateTime.parse(t2, formatter);
				if (date1.isAfter(date11)) {

					date1 = date11;
				}
				if (date2.isBefore(date22)) {
					date2 = date22;
				}
				i++;
			}
		}

		//System.out.println(date1.toString() + "     " + date2.toString());
		LocalDateTime aDateTimeStart = LocalDateTime.of(date1.getYear(), date1.getMonth(), date1.getDayOfMonth(), 0,
				01, 01);
		//LocalDateTime aDateTimeStart = LocalDateTime.of(2012, 2, 1, 0,
		//01, 01);
		//LocalDateTime aDateTimeStart = LocalDateTime.of(2011, 11, 02, 01,
		//01, 01);
		//LocalDateTime aDateTimeEnd = LocalDateTime.of(2012, 3, 3, 0,
				//01, 01);
				//LocalDa
		//LocalDateTime aDateTimeEnd = aDateTimeStart.plusMonths(1);
		LocalDateTime aDateTimeEnd = LocalDateTime.of(date2.getYear(), date2.getMonth(), date2.getDayOfMonth(), 23,
				59, 59);
		//System.out.println(aDateTimeStart.toString() + "     " + aDateTimeEnd.toString());
		time.addElement(aDateTimeStart.toString());
		time.addElement(aDateTimeEnd.toString());

		return time;

	}

	public Vector<String> getResource(Connection con) throws SQLException {
		Vector<String> resource = new Vector<String>();
		java.sql.Statement dbStatement1 = con.createStatement();
		String sqlQuery1 = "SELECT distinct Resource from eventres where Resource<>'null'";
		ResultSet rs1 = dbStatement1.executeQuery(sqlQuery1);
		rs1.beforeFirst();

		while (rs1.next()) {
			String resource1 = rs1.getString("Resource");
			resource.add(resource1);
		}
		return resource;

	}

	public XYSeriesCollection createDataset(ArrayList<Long> data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("total duration per time slot");
		for (int i = 0; i < data.size(); i++) {
			series1.add(i, 1.0 * data.get(i) / 3600);
		}
		dataset.addSeries(series1);
		return dataset;
	}

	public XYSeriesCollection createDatasetfree(ArrayList<Double> freedata, String title) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries(title);
		for (int i = 0; i < freedata.size(); i++) {
			series1.add(i, freedata.get(i));
		}
		dataset.addSeries(series1);
		return dataset;
	}

	public PieDataset createPieDataset(Map<String, Double> map) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			dataset.setValue(entry.getKey(), entry.getValue());
		}
		return dataset;
	}

	//tasks frequencies
	public PieDataset createPieDataset1(ArrayList<Map<String, Integer>> list) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		Map<String, Integer> alltasks = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			for (Map.Entry<String, Integer> entry : list.get(i).entrySet()) {
				if (alltasks.containsKey(entry.getKey())) {
					int k = alltasks.get(entry.getKey()).intValue();
					int p = k + 1;
					alltasks.replace(entry.getKey(), k, p);
				} else {
					alltasks.put(entry.getKey(), 1);
				}
			}

		}
		double nbtotaltask = 0;
		for (Map.Entry<String, Integer> entry : alltasks.entrySet()) {
			nbtotaltask = nbtotaltask + entry.getValue();
		}
		for (Map.Entry<String, Integer> entry : alltasks.entrySet()) {
			dataset.setValue(entry.getKey(), entry.getValue() * 100 / nbtotaltask);
		}

		return dataset;
	}
    //cases per day 
	
	public Map<Integer, Vector<Case>> cases(Connection con, Vector<Vector<String>> vect) throws SQLException {
		ArrayList<Case> casess = new ArrayList<>();
		Map<Integer, Vector<Case>> mapcases = new HashMap<>();
		java.sql.Statement dbStatement1 = con.createStatement();
		String sqlQuery1 = "SELECT * from caseLog";
		//System.out.println("The Request is "+sqlQuery1);
		ResultSet rs1 = dbStatement1.executeQuery(sqlQuery1);
		while (rs1.next()) {
			Case case1 = new Case(rs1.getString("CaseId"), rs1.getLong("CaseStartDate"),
					rs1.getString("CaseEndDate"));
			casess.add(case1);

		}
		for (int i = 0; i < vect.size(); i++) {
			Vector<Case> e = new Vector<>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy");
			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			date1 = LocalDateTime.parse(vect.get(i).elementAt(0), formatter1);
			date2 = LocalDateTime.parse(vect.get(i).elementAt(1), formatter1);
			for (int j = 0; j < casess.size(); j++) {
				LocalDateTime date11 = null;
				//LocalDateTime date12 = null;
				date11 = LocalDateTime.ofInstant(Instant.ofEpochMilli(casess.get(j).startdate), ZoneId.systemDefault());
				//date12 = LocalDateTime.parse(casess.get(j).getEnddate().substring(0, 21), formatter);
				if (date11.isAfter(date1) && date11.isBefore(date2)) {
					e.add(casess.get(j));

				}
			}
			mapcases.put(i, e);
		}
		
		return mapcases;
	}
	
	// Availability for the weeks
	public AvailabilityStructure weekAvaialbility(Connection con, String resource, Vector<Vector<String>> vect,
			Map<String, Event> events) throws ParseException, SQLException {
		AvailabilityStructure av = new AvailabilityStructure();
		double tminav = 0;
		double tmeanav = 0;
		double tmaxav = 0;
		double batch = 0;
		double nonbatch = 0;
		for (int i = 0; i < vect.size(); i++) {
			//Determinate the days intervals in the week
			Vector<Vector<String>> daysvector = new Vector<Vector<String>>();
			AvailabilityStructure a = new AvailabilityStructure();
			Vector<String> t = new Vector<String>();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date startdate = sdf1.parse(vect.elementAt(i).elementAt(0));
			Date endDate = sdf1.parse(vect.elementAt(i).elementAt(1));
			cal.setTime(startdate);
			while (cal.getTime().compareTo(endDate) < 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date1 = sdf.parse(cal.getTime().toString());
				//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				t.add(sdf2.format(date1));
				cal.add(Calendar.DATE, 1);
				Date date2 = sdf.parse(cal.getTime().toString());
				t.add(sdf2.format(date2));
				daysvector.add(t);
				t = new Vector<String>();
			}
			a = availability(con, resource, daysvector, events);
			long duration = 0;
			double breaks = 0;
			double availabilityRate = 0;

			double minav = 0;
			double meanav = 0;
			double maxav = 0;

			for (int j = 0; j < a.totalDuration.size(); j++) {
				duration = duration + a.totalDuration.get(j);
				availabilityRate = (availabilityRate + a.availabilityRate.get(j));
				if (a.numberofworkfloaterval.get(j) != -1) {
					breaks = (breaks + a.getNumberofworkfloaterval().get(j));
				}
			}
			for (int j = 0; j < a.availabilityRate.size(); j++) {
				meanav = (int) (meanav + a.availabilityRate.get(j));
				if (a.availabilityRate.get(j) < minav) {
					minav = a.availabilityRate.get(j);
				}
				if (a.availabilityRate.get(j) > maxav) {
					maxav = a.availabilityRate.get(j);
				}
			}
			int k = 0;
			int b = 0;
			int bno = 0;
			for (int j = 0; j < a.numberofworkfloaterval.size(); j++) {
				if (a.numberofworkfloaterval.get(j) != -1) {
					k = k + 1;
					if (a.numberofworkfloaterval.get(j) != 0) {
						b = b + 1;
					}
					if (a.numberofworkfloaterval.get(j) == 0) {
						bno = bno + 1;
					}
				}

			}
			//System.out.println("The k is " + k + " batch is " + b + "the non batch is " + bno);
			meanav = meanav / a.availabilityRate.size();
			availabilityRate = availabilityRate / a.availabilityRate.size();
			av.totalDuration.add(duration);
			av.numberofworkfloaterval.add(breaks);
			av.availabilityRate.add(availabilityRate);
			tminav = tminav + minav;
			tmeanav = tmeanav + meanav;
			tmaxav = tmaxav + maxav;
			if (k != 0) {
				batch = batch + b * 100 / k;
				nonbatch = nonbatch + bno * 100 / k;
			}
		}
		av.maxAvailability = tmaxav / vect.size();
		av.minAvailability = tminav / vect.size();
		av.meanAvailability = tmeanav / vect.size();
		av.batchRate = batch / vect.size();
		av.nonBatchRate = nonbatch / vect.size();
		//System.out.println("Le batch est " + av.batchRate);
		return av;
	}

	// Availability detection for Days slots
	public AvailabilityStructure availability(Connection con, String resource, Vector<Vector<String>> vect,
			Map<String, Event> events) throws ParseException {
		AvailabilityStructure av = new AvailabilityStructure();
		ArrayList<Event> allevents = new ArrayList<>();
		int workingdays = 0;
		int workdaysbehav = 0;
		int totInstruction = 0;
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			allevents.add(entry.getValue());
		}
		// For a given resource
		ArrayList<Event> r_event = new ArrayList<Event>();
		for (int i = 0; i < allevents.size(); i++) {
			if (allevents.get(i).resource.equals(resource)) {
				r_event.add(allevents.get(i));
			}
		}

		for (int i = 0; i < r_event.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			date1 = LocalDateTime.parse(r_event.get(i).getDateStart().substring(0, 21), formatter);
			date2 = LocalDateTime.parse(r_event.get(i).getDateFin().substring(0, 21), formatter);
			long diff = ChronoUnit.SECONDS.between(date1, date2);
			//System.out.println("The duration of the event  " + r_event.get(i).eventId + " is " + diff + " in seconds");
			r_event.get(i).setDuration(diff);
		}

		//calculate durations per time slot(a day...)
		ArrayList<Long> totalDur = new ArrayList<Long>();
		ArrayList<Double> numberofworkInterval = new ArrayList<Double>();
		ArrayList<Vector<Long>> freetime = new ArrayList<Vector<Long>>();
		ArrayList<Double> availabilityRate = new ArrayList<Double>();

		ArrayList<Event> copydurevents = new ArrayList<Event>();
		Map<Integer, ArrayList<Event>> mapevents = new HashMap<Integer, ArrayList<Event>>();
		copydurevents.addAll(r_event);

		for (int i = 0; i < vect.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy");

			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			date1 = LocalDateTime.parse(vect.get(i).elementAt(0), formatter1);
			date2 = LocalDateTime.parse(vect.get(i).elementAt(1), formatter1);
			ArrayList<Event> e = new ArrayList<Event>();
			for (int j = 0; j < r_event.size(); j++) {
				LocalDateTime date11 = null;
				LocalDateTime date12 = null;
				date11 = LocalDateTime.parse(r_event.get(j).dateStart.substring(0, 21), formatter);
				date12 = LocalDateTime.parse(r_event.get(j).dateFin.substring(0, 21), formatter);
				if (date11.isAfter(date1) && date12.isBefore(date2)) {
					e.add(r_event.get(j));
				}
			}
			if (e.size() > 0) {
				workingdays = workingdays + 1;
				totInstruction = totInstruction + e.size();

			}
			if (e.size() > 1) {
				workdaysbehav = workdaysbehav + 1;
			}
			mapevents.put(i, e);
		}
		ArrayList<Integer> nbins = new ArrayList<>();
		for (Map.Entry<Integer, ArrayList<Event>> entry : mapevents.entrySet()) {
			Collections.sort(entry.getValue(), EventDurationComparator);
			boolean finish = false;
			long slotdurevent = 0;
			int nbinstruction = 0;
			ArrayList<Event> copy = new ArrayList<>();
			nbinstruction = entry.getValue().size();
			copy.addAll(entry.getValue());
			while (entry.getValue().size() > 0 && !finish) {
				//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
				LocalDateTime date1 = null;
				LocalDateTime date2 = null;
				date1 = LocalDateTime.parse(entry.getValue().get(0).getDateStart().substring(0, 21), formatter);
				date2 = LocalDateTime.parse(entry.getValue().get(0).getDateFin().substring(0, 21), formatter);
				ArrayList<Integer> indexes = new ArrayList<>();
				for (int j = 1; j < entry.getValue().size(); j++) {
					// date between date 1 and date2 			

					LocalDateTime datestart = LocalDateTime
							.parse(entry.getValue().get(j).getDateStart().substring(0, 21), formatter);
					LocalDateTime dateend = LocalDateTime.parse(entry.getValue().get(j).getDateStart().substring(0, 21),
							formatter);

					if ((((datestart.isAfter(date1) || datestart.isEqual(date1)) && datestart.isBefore(date2))
							&& (dateend.isAfter(date1) && (dateend.isBefore(date2) || dateend.isEqual(date2))))) {
						indexes.add(j);
					}

					// Only start between date 1 and date 2
					if ((((datestart.isAfter(date1) || datestart.isEqual(date1)) && datestart.isBefore(date2))
							&& dateend.isAfter(date2))) {
						entry.getValue().get(j).setDateStart(entry.getValue().get(0).getDateFin());
					}
					//Only end between date 1 and Date 2
					if (datestart.isBefore(date1) && dateend.isAfter(date1)
							&& (dateend.isBefore(date2) || dateend.isEqual(date2))) {

						entry.getValue().get(j).setDateFin(entry.getValue().get(0).getDateStart());

					}
				}
				// Calculate Total duration 
				slotdurevent = slotdurevent + entry.getValue().get(0).duration;
				//remove the indexes
				for (int k = indexes.size() - 1; k >= 0; k--) {
					int c = indexes.get(k);
					entry.getValue().remove(c);
				}
				entry.getValue().remove(0);
				if (entry.getValue().size() == 0) {
					finish = true;
				}

			}
			//System.out.println("Total work duration for the slot " + entry.getKey() + " is " + slotdurevent);
			totalDur.add(slotdurevent);
			nbins.add(nbinstruction);
			availabilityRate.add((double) (slotdurevent * 100.0 / 86400));
			Vector<Long> vectfree = new Vector<Long>();
			Collections.sort(copy, startdateComparator);
			vectfree = freeTimeperslot(copy);
			double batch = 0;
			//System.out.println("The vect free is " + vectfree + " duration " + slotdurevent);
			if (vectfree.size() > 0 && slotdurevent != 0) {
				for (int k = 0; k < vectfree.size(); k++) {
					if (vectfree.elementAt(k) > 60) {
						batch = batch + 1;
					}
				}
				if (batch == 0) {
					batch = -1;
				}

			} else if (vectfree.size() == 0 && slotdurevent != 0) {
				batch = 0;
				//System.out.println("Nothing to say : There is one task to execute it can be both batch and non-batch");
			} else if (slotdurevent == 0) {
				batch = 0;
			}

			numberofworkInterval.add(batch);
			//ALL THE FREETIME VALUES FOR THE slots
			freetime.add(vectfree);
		}

		long totalhours = 0;
		for (int i = 0; i < totalDur.size(); i++) {
			totalhours = totalhours + totalDur.get(i) / 3600;
		}
		av.totalDuration.addAll(totalDur);
		av.instructionday.addAll(nbins);
		av.numberofworkfloaterval.addAll(numberofworkInterval);
		av.availabilityRate.addAll(availabilityRate);
		//Change the meanRate to be the total number of working hours
		long meanRate = 0;
		double minRate = 100;
		double maxRate = 0;
		double batchrate = 0;
		double batnonchrate = 0;
		for (int i = 0; i < av.availabilityRate.size(); i++) {
			//Rajouter cette ligne si tu veux calculer la valeur moyenne de la disponibilite
			//meanRate = meanRate + av.availabilityRate.get(i);
			if (availabilityRate.get(i) < minRate) {
				minRate = availabilityRate.get(i);
			}
			if (availabilityRate.get(i) > maxRate) {
				maxRate = availabilityRate.get(i);
			}

		}
		//Batch and non batch rates
		int k = 0;
		int b = 0;
		int bno = 0;
		for (int i = 0; i < av.numberofworkfloaterval.size(); i++) {
			if (av.numberofworkfloaterval.get(i) != 0) {
				k = k + 1;
				if (av.numberofworkfloaterval.get(i) > 0) {
					b = b + 1;
				}
				if (av.numberofworkfloaterval.get(i) == -1) {
					bno = bno + 1;
				}
			}

		}
		//System.out.println("The k is " + k + " batch is " + b + "the non batch is " + bno);
		if (k != 0) {
			batchrate = b * 100 / k;
			batnonchrate = bno * 100 / k;
		}
		//meanRate = meanRate / av.availabilityRate.size();
		//meanRate = meanRate / workingdays;

		//System.out.println("The size of the avaialbility rate " + av.availabilityRate.size());
		//System.out.println("The size of the working days " + workingdays);
		//System.out.println("The total number of working hours " + meanRate + "    " + meanRate / 3600);
		//System.out.println("The instruction Number  " + totInstruction + "  The working Behav " + workdaysbehav);
		av.setMeanAvailability(totalhours);
		av.setMinAvailability(minRate);
		av.setMaxAvailability(maxRate);
		av.setBatchRate(batchrate);
		av.setNonBatchRate(batnonchrate);
		av.setInstructionNb(totInstruction);
		av.setWorkdaysBehav(workdaysbehav);
		av.setWorkingdays(workingdays);
		av.setTotperiod(mapevents.size());

		return av;

	}

	//GET ALL EVENTS VERSION 2
	//Get all the events and their related informations FOR THE STATE INITILIZING, RESDY AND COMPLETED 
	public Map<String, Event> getallevents2(Connection con) throws SQLException {

		Map<String, Event> events = new HashMap<String, Event>();
		java.sql.Statement dbStatement1 = con.createStatement();
		String sqlQuery1 = "SELECT * from eventres";
		//System.out.println("The Request is "+sqlQuery1);
		ResultSet rs1 = dbStatement1.executeQuery(sqlQuery1);
		while (rs1.next()) {
			// If the event Already exists
			if (events.containsKey(rs1.getString("EventId"))) {
				if (rs1.getString("State").equals("ready") || rs1.getString("State").equals("START")) {
					events.get(rs1.getString("EventId")).setDateStart(rs1.getString("Timestamp"));
					//events.get(rs1.getString("EventId")).setState("START");
				}
				if (rs1.getString("State").equals("completed") || rs1.getString("State").equals("COMPLETE")) {
					events.get(rs1.getString("EventId")).setDateFin(rs1.getString("Timestamp"));
					events.get(rs1.getString("EventId")).setState("COMPLETE");
					events.get(rs1.getString("EventId")).setResource(rs1.getString("Resource"));
					events.get(rs1.getString("EventId")).setEventName(rs1.getString("Task"));
				}
				if (rs1.getString("State").equals("schedule") || rs1.getString("State").equals("SCHEDULE")) {
					events.get(rs1.getString("EventId")).setDateSchedule(rs1.getString("Timestamp"));
					//events.get(rs1.getString("EventId")).setState("SCHEDULE");
				}
			} // Add the event it does not exist yet
			else {
				String eventid = rs1.getString("EventId");
				String startDate = "";
				String endDate = "";
				String scheduleDate = "";
				String eventidname = "";
				String r = "";
				Event event = new Event(eventidname, startDate, endDate, scheduleDate, r);
				event.setEventId(eventid);
				if (rs1.getString("State").equals("schedule") || rs1.getString("State").equals("SCHEDULE")) {
					event.setDateSchedule(rs1.getString("Timestamp"));
					//event.setState("SCHEDULE");
					events.put(eventid, event);
				}
				if (rs1.getString("State").equals("ready") || rs1.getString("State").equals("START")) {
					event.setDateStart(rs1.getString("Timestamp"));
					//event.setState("START");
					events.put(eventid, event);
				}
				if (rs1.getString("State").equals("completed") || rs1.getString("State").equals("COMPLETE")) {
					event.setDateFin(rs1.getString("Timestamp"));
					event.setState("COMPLETE");
					event.setResource(rs1.getString("Resource"));
					event.setEventName(rs1.getString("Task"));
					events.put(eventid, event);
				}
			}

		}

		return events;
	}

	//Get all the events and their related informations 
	public Map<String, Event> getallevents(Connection con) throws SQLException {
		Map<String, Event> events = new HashMap<String, Event>();
		java.sql.Statement dbStatement1 = con.createStatement();
		String sqlQuery1 = "SELECT * from eventRes";
		//System.out.println("The Request is "+sqlQuery1);
		ResultSet rs1 = dbStatement1.executeQuery(sqlQuery1);
		while (rs1.next()) {
			// If the event Already exists
			if (events.containsKey(rs1.getString("EventId"))) {
				if (rs1.getString("State").equals("start")) {
					events.get(rs1.getString("EventId")).setDateStart(rs1.getString("Timestamp"));
					events.get(rs1.getString("EventId")).setResource(rs1.getString("Resource"));
					events.get(rs1.getString("EventId")).setEventName(rs1.getString("Task"));
					events.get(rs1.getString("EventId")).setState("START");
				}
				if (rs1.getString("State").equals("complete")) {
					events.get(rs1.getString("EventId")).setDateFin(rs1.getString("Timestamp"));
					events.get(rs1.getString("EventId")).setState("complete");
				}
			} // Add the event it does not exist yet
			else {
				String eventid = rs1.getString("EventId");
				String startDate = "";
				String endDate = "";
				String scheduleDate = "";
				String eventidname = "";
				String r = "";
				Event event = new Event(eventidname, startDate, endDate, scheduleDate, r);
				event.setEventId(eventid);
				if (rs1.getString("State").equals("schedule")) {
					event.setDateSchedule(rs1.getString("Timestamp"));
					event.setState("schedule");
					events.put(eventid, event);
				}
			}

		}

		return events;
	}

	public Map<String, Event> getallevents1bonita(Connection con) throws SQLException {
		Map<String, Event> events = new HashMap<String, Event>();
		Statement dbStatement1 = con.createStatement();
		String sqlQuery1 = "SELECT * from eventlog";
		//System.out.println("The Request is "+sqlQuery1);
		ResultSet rs1 = dbStatement1.executeQuery(sqlQuery1);
		while (rs1.next()) {
			// If the event Already exists
			if (events.containsKey(rs1.getString("EventId"))) {
				if (rs1.getString("State").equals("completed")) {
					long t1 = Long.parseLong(rs1.getString("Timestamp"));
					Date date = new Date(t1);
					events.get(rs1.getString("EventId")).setDateFin(date.toString());
					events.get(rs1.getString("EventId")).setState("COMPLETE");
					events.get(rs1.getString("EventId")).setResource(rs1.getString("Resource"));
					events.get(rs1.getString("EventId")).setEventName(rs1.getString("Task"));
				}
			} // Add the event it does not exist yet
			else {
				String eventid = rs1.getString("EventId");
				String startDate = "";
				String endDate = "";
				String scheduleDate = "";
				String eventidname = "";
				String r = "";
				Event event = new Event(eventidname, startDate, endDate, scheduleDate, r);
				event.setEventId(eventid);
				if (rs1.getString("State").equals("ready")) {
					long t1 = Long.parseLong(rs1.getString("Timestamp"));
					Date date = new Date(t1);
					//System.out.println(date);
					event.setDateSchedule(date.toString());
					event.setState("SCHEDULE");
					long t11 = Long.parseLong(rs1.getString("DateFinish"));
					//System.out.println(rs1.getString("DateFinish"));
					Date date1 = new Date(t11);
					//System.out.println(date1);
					event.setDateStart(date1.toString());
					events.put(eventid, event);
				}
			}

		}

		return events;
	}

	// Process preference from event log 
	public Vector<Integer> processPreference(Connection con, String resource, Vector<Vector<String>> vect,
			Map<String, Event> events) throws SQLException, ParseException {
		Vector<Integer> perf = new Vector<>();
		ArrayList<Event> allevents = new ArrayList<Event>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			allevents.add(entry.getValue());
		}
		// for a given resource
		ArrayList<Event> r_event = new ArrayList<Event>();
		for (int i = 0; i < allevents.size(); i++) {
			if (allevents.get(i).resource.equals(resource)) {
				r_event.add(allevents.get(i));
			}

		}

		ArrayList<Event> current = new ArrayList<>();
		current.addAll(r_event);
		Collections.sort(current, startdateComparator);
		Map<Integer, ArrayList<Event>> currentevents = new HashMap<Integer, ArrayList<Event>>();
		for (int i = 0; i < vect.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy");
			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			date1 = LocalDateTime.parse(vect.get(i).elementAt(0), formatter1);
			date2 = LocalDateTime.parse(vect.get(i).elementAt(1), formatter1);
			ArrayList<Event> e = new ArrayList<>();
			for (int j = 0; j < current.size(); j++) {
				LocalDateTime date11 = LocalDateTime.parse(current.get(j).dateStart.substring(0, 21), formatter);
				LocalDateTime date12 = LocalDateTime.parse(current.get(j).dateFin.substring(0, 21), formatter);
				if (((date11.isAfter(date1) || date11.equals(date1))
						&& (date12.isBefore(date2) || date12.isEqual(date2)))) {
					e.add(current.get(j));
				}
			}
			currentevents.put(i, e);
		}
		Map<Integer, Vector<Integer>> map = new HashMap<>();
		int totaldays = r_event.size();
		int workingdays = 0;
		for (Map.Entry<Integer, ArrayList<Event>> entry : currentevents.entrySet()) {
			int fifo = 0;
			int lifo = 0;
			int random = 0;
			int notconclusive = 0;
			//System.out.println("The day number " + entry.getKey());
			if (entry.getValue().size() > 0) {
				workingdays = workingdays + 1;
				ArrayList<Event> copyevent = entry.getValue();
				Collections.sort(copyevent, startdateComparator);
				while (copyevent.size() > 0) {
					ArrayList<Event> e = new ArrayList<>();
					ArrayList<Event> efifo = new ArrayList<>();
					ArrayList<Event> elifo = new ArrayList<>();
					Collections.sort(copyevent, enddateComparator);

					e = eventset(copyevent, entry.getValue().get(0).dateStart);
					efifo = eventset(copyevent, entry.getValue().get(0).dateStart);
					elifo = eventset(copyevent, entry.getValue().get(0).dateStart);
					if (e.size() > 1) {
						Collections.sort(e, startdateComparator);
						Collections.sort(efifo, scheduledateComparatoras);
						Collections.sort(elifo, scheduledateComparatords);
						

						if (e.get(0).equals(efifo.get(0))) {
							fifo = fifo + 1;
						} else if (e.get(0).equals(elifo.get(0))) {
							lifo = lifo + 1;
						} else {
							random = random + 1;
						}
					} else {
						notconclusive = notconclusive + 1;
					}
					copyevent.remove(e.get(0));
					//System.out.println("FIF0 "+fifo+"  LIFO"+lifo+" Random "+random+" notconclusive "+notconclusive);
				}
				
			}
			Vector<Integer> vec = new Vector<>();
			vec.add(fifo);
			vec.add(lifo);
			vec.add(random);
			vec.add(notconclusive);
			//add to the map
			map.put(entry.getKey(), vec);
		}
		// Determine Queuing Behaviour
		//System.out.println("Determine the queuing Behaviour");
		//HashMap<Integer, String> queue = new HashMap<>();
		double fi = 0;
		double li = 0;
		double ra = 0;
		int notc = 0;
		double tottask = 0;
		for (Map.Entry<Integer, Vector<Integer>> entry : map.entrySet()) {
			fi = fi + entry.getValue().elementAt(0);
			li = li + entry.getValue().elementAt(1);
			ra = ra + entry.getValue().elementAt(2);
			notc = notc + entry.getValue().elementAt(3);
		}
		tottask = fi + li + ra ;
		int min = 0;
		/*
		 * long rand= Math.round( Math.random()) ; Random random = new Random();
		 * int randomNumber = random.nextInt(notc+1-min)+min; if (rand == 0) {
		 * fi = fi + randomNumber; li= li+(notc-randomNumber); } if(rand==1) {
		 * li = li + randomNumber; fi= fi+(notc-randomNumber); }
		 */

		perf.add(vect.size());
		perf.add(workingdays);

		perf.add((int) tottask);
		perf.add((int) fi);
		perf.add((int) li);
		perf.add((int) ra);
		perf.add(notc);
		//System.out.println("The total days " + vect.size() + " The total working days" + workingdays
				//+ " the total executed tasks " + tottask);
		//System.out.println("The FIFO " + fi + " The LIFO " + li + " the Random " + ra + " Not conclusive " + notc);
		return perf;
	}
	//preference par rapport au finishing time

	// Process preference from event log 
	public Vector<Integer> processPreference1(Connection con, String resource, Vector<Vector<String>> vect,
			Map<String, Event> events) {
		Vector<Integer> perf = new Vector<>();
		ArrayList<Event> allevents = new ArrayList<Event>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			allevents.add(entry.getValue());
		}
		// for a given resource
		ArrayList<Event> r_event = new ArrayList<Event>();
		for (int i = 0; i < allevents.size(); i++) {
			if (allevents.get(i).resource.equals(resource)) {
				r_event.add(allevents.get(i));
			}
		}
		ArrayList<Event> current = new ArrayList<>();
		current.addAll(r_event);
		Collections.sort(current, enddateComparator);
		Map<Integer, ArrayList<Event>> currentevents = new HashMap<Integer, ArrayList<Event>>();
		for (int i = 0; i < vect.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy");
			LocalDateTime date1 = null;
			LocalDateTime date2 = null;
			date1 = LocalDateTime.parse(vect.get(i).elementAt(0), formatter1);
			date2 = LocalDateTime.parse(vect.get(i).elementAt(1), formatter1);
			ArrayList<Event> e = new ArrayList<Event>();
			for (int j = 0; j < current.size(); j++) {
				LocalDateTime date11 = LocalDateTime.parse(current.get(j).dateStart.substring(0, 21), formatter);
				LocalDateTime date12 = LocalDateTime.parse(current.get(j).dateFin.substring(0, 21), formatter);
				if (((date11.isAfter(date1) || date11.equals(date1))
						&& (date12.isBefore(date2) || date12.isEqual(date2)))) {
					e.add(current.get(j));
				}

			}
			currentevents.put(i, e);
		}
		Map<Integer, Vector<Integer>> map = new HashMap<>();
		int totaldays = r_event.size();
		int workingdays = 0;
		for (Map.Entry<Integer, ArrayList<Event>> entry : currentevents.entrySet()) {
			int fifo = 0;
			int lifo = 0;
			int random = 0;
			int notconclusive = 0;
			if (entry.getValue().size() > 0) {
				workingdays = workingdays + 1;
				ArrayList<Event> copyevent = entry.getValue();
				Collections.sort(copyevent, enddateComparator);
				while (copyevent.size() > 0) {
					ArrayList<Event> e = new ArrayList<>();
					ArrayList<Event> efifo = new ArrayList<>();
					ArrayList<Event> elifo = new ArrayList<>();
					Collections.sort(copyevent, enddateComparator);
					e = eventset(copyevent, entry.getValue().get(0).dateFin);
					efifo = eventset(copyevent, entry.getValue().get(0).dateFin);
					elifo = eventset(copyevent, entry.getValue().get(0).dateFin);
					if (e.size() > 1) {
						Collections.sort(e, enddateComparator);
						Collections.sort(efifo, scheduledateComparatoras);
						Collections.sort(elifo, scheduledateComparatords);

						if (e.get(0).equals(efifo.get(0))) {
							fifo = fifo + 1;
						} else if (e.get(0).equals(elifo.get(0))) {
							lifo = lifo + 1;
						} else {
							random = random + 1;
						}
					} else {
						notconclusive = notconclusive + 1;
					}
					copyevent.remove(e.get(0));
				}
			}
			Vector<Integer> vec = new Vector<>();
			vec.add(fifo);
			vec.add(lifo);
			vec.add(random);
			vec.add(notconclusive);
			//add to the map
			map.put(entry.getKey(), vec);
		}
		// Determine Queuing Behaviour
		System.out.println("Determine the queuing Behaviour");
		//HashMap<Integer, String> queue = new HashMap<>();
		double fi = 0;
		double li = 0;
		double ra = 0;
		int notc = 0;
		double tottask = 0;
		for (Map.Entry<Integer, Vector<Integer>> entry : map.entrySet()) {
			fi = fi + entry.getValue().elementAt(0);
			li = li + entry.getValue().elementAt(1);
			ra = ra + entry.getValue().elementAt(2);
			notc = notc + entry.getValue().elementAt(3);
		}
		tottask = fi + li + ra;
		int min = 0;
		/*
		 * long rand= Math.round( Math.random()) ; Random random = new Random();
		 * int randomNumber = random.nextInt(notc+1-min)+min; if (rand == 0) {
		 * fi = fi + randomNumber; li= li+(notc-randomNumber); } if(rand==1) {
		 * li = li + randomNumber; fi= fi+(notc-randomNumber); }
		 */

		perf.add(vect.size());
		perf.add(workingdays);

		perf.add((int) tottask);
		perf.add((int) fi);
		perf.add((int) li);
		perf.add((int) ra);
		perf.add(notc);
	
		return perf;
	}

	//return the set of events that occured (scheduled) before a given time
	public ArrayList<Event> eventset(ArrayList<Event> events, String date) {
		ArrayList<Event> list = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		LocalDateTime date1 = LocalDateTime.parse(date.substring(0, 21), formatter);
		for (int i = 0; i < events.size(); i++) {
			LocalDateTime date2 = LocalDateTime.parse(events.get(i).dateSchedule.substring(0, 21), formatter);
			if (date2.isBefore(date1)) {
				list.add(events.get(i));
			}
		}
		return list;
	}
	// Update List of the events 

	// Determiner si la resource est multitask ou pas:
	public ArrayList<Map<String, Integer>> multiTasking(Connection con, String resource, Vector<Vector<String>> vect,
			Map<String, Event> events) throws ParseException {
		ArrayList<Event> allevents = new ArrayList<Event>();

		for (Map.Entry<String, Event> entry : events.entrySet()) {
			allevents.add(entry.getValue());
		}
		// for a given resource
		
		ArrayList<Event> r_event = new ArrayList<Event>();
		for (int i = 0; i < allevents.size(); i++) {
			if (allevents.get(i).resource.equals(resource) && allevents.get(i).state.equals("COMPLETE")) {
				r_event.add(allevents.get(i));
			}
		}
		//Number of different tasks executed each slot of Time

		Map<Integer, ArrayList<Event>> currentevents = new HashMap<Integer, ArrayList<Event>>();
		for (int i = 0; i < vect.size(); i++) {
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date1 = sdf1.parse(vect.get(i).elementAt(0));
			Date date2 = sdf1.parse(vect.get(i).elementAt(1));
			ArrayList<Event> e = new ArrayList<Event>();
			for (int j = 0; j < r_event.size(); j++) {
				Date date11 = sdf.parse(r_event.get(j).dateStart);
				Date date12 = sdf.parse(r_event.get(j).dateFin);
				if (date11.getTime() >= date1.getTime() && date12.getTime() <= date2.getTime()) {
					e.add(r_event.get(j));
				}
			}
			currentevents.put(i, e);
		}
		// Contains the total of number of tasks executed per slot

		ArrayList<Map<String, Integer>> alltasks = new ArrayList<>();
		for (Map.Entry<Integer, ArrayList<Event>> entry : currentevents.entrySet()) {
			Map<String, Integer> tasks = new HashMap<String, Integer>();
			for (int i = 0; i < entry.getValue().size(); i++) {
				if (tasks.containsKey(entry.getValue().get(i).eventName)) {
					int k = tasks.get(entry.getValue().get(i).eventName).intValue();
					int p = k + 1;
					tasks.replace(entry.getValue().get(i).eventName, k, p);
				} else {
					tasks.put(entry.getValue().get(i).eventName, 1);
				}
			}
			alltasks.add(tasks);
		}

		return alltasks;

	}

	//The dataset creation for the multitasking
	public XYSeriesCollection datasettasks(ArrayList<Map<String, Integer>> alltasks) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Number of different tasks per time slot");
		for (int i = 0; i < alltasks.size(); i++) {
			series1.add(i, alltasks.get(i).size());
		}
		dataset.addSeries(series1);
		return dataset;

	}

	// Calculate freeTime per slot
	public Vector<Long> freeTimeperslot(ArrayList<Event> av) throws ParseException {
		Vector<Long> free = new Vector<Long>();
		//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		if (av.size() > 1) {
			for (int i = 0; i < av.size() - 1; i++) {
				if (!av.get(i).dateFin.equals("") && !av.get(i + 1).dateStart.equals("")) {
					Date date1 = sdf.parse(av.get(i).getDateFin());
					Date date2 = sdf.parse(av.get(i + 1).getDateStart()); //
					if (date2.getTime() >= date1.getTime()) {
						long minutes = TimeUnit.MILLISECONDS.toMinutes(date2.getTime() - date1.getTime());
						free.addElement(minutes);
					}
				}
			}
		}
		return free;
	}

	//Sort an arraylist

	public static Comparator<Event> EventDurationComparator = new Comparator<Event>() {
		public int compare(Event event1, Event event2) {
			Long eventduration1 = event1.getDuration();
			Long eventduration2 = event2.getDuration();
			int compare = eventduration2.compareTo(eventduration1);
			return compare;
		}

	};
	// Sort the list per Start Date
	public static Comparator<Event> startdateComparator = new Comparator<Event>() {
		public int compare(Event event1, Event event2) {
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			int compare = 0;
			if (!event1.dateStart.equals("") && !event2.dateStart.equals("")) {
				try {
					Date date1 = sdf.parse(event1.getDateStart());
					Date date2 = sdf.parse(event2.getDateStart()); //
					Long eventdate1 = date1.getTime();
					Long eventdate2 = date2.getTime();
					compare = eventdate1.compareTo(eventdate2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return compare;
		}

	};
	// Sort the list per Start Date
	public static Comparator<Event> enddateComparator = new Comparator<Event>() {
		public int compare(Event event1, Event event2) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			int compare = 0;
			if (!event1.dateFin.equals("") && !event2.dateFin.equals("")) {
				try {
					Date date1 = sdf.parse(event1.getDateFin());
					Date date2 = sdf.parse(event2.getDateFin()); //
					Long eventdate1 = date1.getTime();
					Long eventdate2 = date2.getTime();
					compare = eventdate1.compareTo(eventdate2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return compare;
		}

	};
	public static Comparator<Event> scheduledateComparatords = new Comparator<Event>() {
		public int compare(Event event1, Event event2) {
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			int compare = 0;
			try {
				Date date1 = sdf.parse(event1.getDateSchedule());
				Date date2 = sdf.parse(event2.getDateSchedule()); //
				Long eventdate1 = date1.getTime();
				Long eventdate2 = date2.getTime();
				compare = eventdate2.compareTo(eventdate1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return compare;
		}

	};
	public static Comparator<Event> scheduledateComparatoras = new Comparator<Event>() {
		public int compare(Event event1, Event event2) {
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			int compare = 0;
			try {
				Date date1 = sdf.parse(event1.getDateSchedule());
				Date date2 = sdf.parse(event2.getDateSchedule()); //
				Long eventdate1 = date1.getTime();
				Long eventdate2 = date2.getTime();
				compare = eventdate1.compareTo(eventdate2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return compare;
		}

	};

	public void logToDB1(XLog log, Connection con, ECLDBmapping map) throws SQLException, ParseException {

		Vector<String> cadb = new Vector<String>();
		Vector<String> cael = new Vector<String>();
		Vector<String> eadb = new Vector<String>();
		Vector<String> eael = new Vector<String>();

		Vector<String> caseDBLines = new Vector<String>();
		caseDBLines.add("CaseId");
		//caseDBLines.add("CaseState");
		//caseDBLines.add("CaseStartDate");
		//caseDBLines.add("CaseEndDate");
		//caseDBLines.add("CPlannedEndDate");
		caseDBLines.add("ResourceNum");
		//caseDBLines.add("Duration");

		Vector<String> eventDBLines = new Vector<String>();
		eventDBLines.add("CaseId");
		eventDBLines.add("Task");
		//eventDBLines.add("DateFinish");
		eventDBLines.add("Resource");
		eventDBLines.add("Timestamp");
		eventDBLines.add("State");
		//eventDBLines.add("MonitoringResource");
		eventDBLines.add("EventId");

		for (int i = 0; i < map.ELAttribute.size(); i++) {
			if (!map.ELAttribute.elementAt(i).equals("Calculate")) {
				eael.add(map.ELAttribute.elementAt(i));
				eadb.add(map.DBELAttribute.elementAt(i));
			}
		}

		for (int i = 0; i < map.CLAttribute.size(); i++) {
			if (!map.CLAttribute.elementAt(i).equals("Calculate")) {
				//System.out.println(map.CLAttribute.elementAt(i)+"   "+map.DBCLAttribute.elementAt(i));
				cael.add(map.CLAttribute.elementAt(i));
				cadb.add(map.DBCLAttribute.elementAt(i));
			}
		}
		String casedbnames = "CaseId,ResourceNum,";
		for (int i = 0; i < cadb.size(); i++) {
			String ca = cadb.elementAt(i);
			//String ca=Integer.toString(i);
			if (!ca.equals("CaseId") && !ca.equals("ResourceNum")) {
				casedbnames += ca + ",";
				caseDBLines.add(ca);
			}
		}
		casedbnames = casedbnames.substring(0, casedbnames.length() - 1);

		String eventdbnames = "CaseId,Task,Resource,Timestamp,State,EventId,";
		for (int i = 0; i < eadb.size(); i++) {
			String ea = eadb.elementAt(i);
			if (!ea.equals("CaseId") && !ea.equals("Task") && !ea.equals("Resource") && !ea.equals("Timestamp")
					&& !ea.equals("State") && !ea.equals("EventId")) {
				eventdbnames += ea + ",";
				eventDBLines.add(ea);
			}
		}
		eventdbnames = eventdbnames.substring(0, eventdbnames.length() - 1);
		System.out.println("The edbNames are " + eventdbnames);
		Vector<Vector<String>> caselog = new Vector<Vector<String>>();
		Vector<Vector<String>> eventlog = new Vector<Vector<String>>();
		System.out.println("The size of cael et cadb " + cael.size() + " " + cadb.size());
		System.out.println("The size of eael et eadb " + eael.size() + " " + eadb.size());
		//System.out.println("________________ "+cael);
		int c = 1;
		for (XTrace t : log) {
			XAttributeLiteral caseidattr = (XAttributeLiteral) t.getAttributes().get(cael.elementAt(0));
			String caseid = caseidattr.getValue() + c;
			c = c + 1;
			Vector<String> caseLine = new Vector<String>();
			for (int i = 0; i < cael.size(); i++) {
				if (cadb.elementAt(i).equals("CaseStartDate") || cadb.elementAt(i).equals("CaseEndDate")
						|| cadb.elementAt(i).equals("CPlannedEndDate")) {
					XAttributeTimestamp timestamp = (XAttributeTimestamp) t.getAttributes().get(cael.elementAt(i));
					Long nextatt = null;
					if (!(timestamp == null)) {
						Date date = timestamp.getValue();
						nextatt = date.getTime();
						caseLine.add(nextatt.toString());
					} else {
						nextatt = null;
						caseLine.add("NULL");
					}

				} else {
					String next = null;
					XAttributeLiteral nextcaseidattr = (XAttributeLiteral) t.getAttributes().get(cael.elementAt(i));
					if (!(nextcaseidattr == null)) {
						next = nextcaseidattr.getValue();
						caseLine.add(next);
					} else {
						next = null;
						caseLine.add("NULL");
					}

				}
			}
			caselog.add(caseLine);
			//System.out.println("Took all neccassary information from Trace pass to event");
			for (XEvent e : t) {
				Vector<String> event = new Vector<String>();
				event.add(caseid);
				//System.out.println("-----------------------------------------------------");
				for (int i = 0; i < eael.size(); i++) {
					//System.out.println("event attribute "+eael.elementAt(i));
					if (eadb.elementAt(i).equals("Timestamp")) {
						XAttributeTimestamp timestamp = (XAttributeTimestamp) e.getAttributes().get(eael.elementAt(i));
						if (!(timestamp == null)) {

							Date date = timestamp.getValue();
							Long nextatt = date.getTime();
							//System.out.println("event attribute " + eael.elementAt(i) + " --------- " + nextatt);
							event.add(nextatt.toString());
						} else {
							event.add("NULL");
						}

					} else {

						XAttributeLiteral nextatt = (XAttributeLiteral) e.getAttributes().get(eael.elementAt(i));
						if (!(nextatt == null)) {
							String nextat = nextatt.getValue();
							//System.out.println("event attribute " + eael.elementAt(i) + " --------- " + nextat);
							event.add(nextat);
						} else {
							event.add(null);
						}

					}
				}
				eventlog.add(event);
			}
			//System.out.println("----------------Finish------------------");
		}

		eadb.add(0, "CaseId");
		//To test
		System.out.println("cadb" + cadb);
		System.out.println("eadb" + eadb);
		System.out.println("caseDBLines" + caseDBLines);
		System.out.println("eventDBLines" + eventDBLines);
		//System.out.println("caselog" + caselog);
		//System.out.println("eventlog" + eventlog);

		String cdbline = "";
		String edbline = "";
		Vector<String> cqueries = new Vector<String>();
		Vector<String> equeries = new Vector<String>();
		String eventlogdata = "";
		String caselogdata = "";

		Map<String, Boolean> caAdd = new HashMap<String, Boolean>();
		Map<String, Boolean> eaAdd = new HashMap<String, Boolean>();

		for (int i = 0; i < caseDBLines.size(); i++) {
			String nextCA = caseDBLines.elementAt(i);
			if (cadb.contains(nextCA)) {
				caAdd.put(nextCA, false);
			} else {
				caAdd.put(nextCA, true);
			}
		}
		for (int i = 0; i < eventDBLines.size(); i++) {
			String nextEA = eventDBLines.elementAt(i);
			if (eadb.contains(nextEA)) {
				eaAdd.put(nextEA, false);
			} else {
				eaAdd.put(nextEA, true);
			}
		}

		System.out.println("Starting calculating case attributes: " + System.nanoTime());

		//Add The case Attributes
		for (int i = 0; i < caselog.size(); i++) {
			caselogdata = "";
			boolean caseFound = false;
			int caseStart = 0;
			Vector<String> caseline = caselog.elementAt(i);
			String caseid = caseline.elementAt(0);
			String numres = "0";

			if (caAdd.get("ResourceNum")) {
				Set<String> resources = new HashSet<String>();
				for (int k = caseStart; k < eventlog.size(); k++) {
					Vector<String> e = eventlog.elementAt(k);
					if (e.elementAt(0).equals(caseid) && !caseFound) {
						caseFound = true;
						caseStart = i;
					} else {
						if (!e.elementAt(0).equals(caseid) && caseFound) {
							caseStart = i;
							caseFound = false;
							break;
						}
					}
					if (e.elementAt(0).equals(caseid)) {
						resources.add(e.elementAt(1));

					}

				}
				//

				Integer res_num = resources.size();
				numres = res_num.toString();

			}
			if (caAdd.get("ResourceNum"))
				caseline.add(1, numres);

			for (int j = 0; j < caseline.size(); j++)
				caselogdata += "'" + caseline.elementAt(j) + "',";

			caselogdata = caselogdata.substring(0, caselogdata.length() - 1);
			cdbline = "insert into caselog(" + casedbnames + ") values(" + caselogdata + ")";
			cqueries.add(cdbline);
		}
		//Save case attributes -----------------------------------------------------------------------------------

		System.out.println("Case attributes calculated: " + System.nanoTime());
		Statement cstatement = con.createStatement();

		for (String cquery : cqueries) {
			cstatement.addBatch(cquery);
		}
		cstatement.executeBatch();
		cstatement.close();

		System.out.println("Case attributes added: " + System.nanoTime());

		//Add the event attributes
		System.out.println("The size of eventLog " + eventlog.size());
		//Calculatin the Task Index
		ArrayList<String> cases = new ArrayList<String>();
		ArrayList<String> tasks = new ArrayList<String>();
		HashMap<Integer, Vector<String>> states = new HashMap<Integer, Vector<String>>();
		for (int i = 0; i < eventlog.size(); i++) {
			Vector<String> event = eventlog.elementAt(i);
			String caseid = event.elementAt(0);
			String taskname = event.elementAt(1);
			String state = event.elementAt(4);
			if (eaAdd.get("EventId")) {
				if (i == 0) {
					Vector<String> s = new Vector<>();
					cases.add(caseid);
					tasks.add(taskname);
					s.add(state);
					states.put(i, s);
					//System.out.println("-------------- "+tasks.indexOf(taskname));
					event.add(5, caseid + tasks.indexOf(taskname) + i);
				} else {
					//same case
					if (cases.contains(caseid)) {
						if (tasks.contains(taskname)) {
							Iterator<Map.Entry<Integer, Vector<String>>> it = states.entrySet().iterator();
							boolean ok = false;
							while (it.hasNext() && !ok) {
								Entry<Integer, Vector<String>> entry = it.next();
								if (entry.getValue().size() == 1 && entry.getValue().elementAt(0).equals("schedule")
										&& state.equals("assign")) {
									entry.getValue().add(state);
									event.add(5, caseid + tasks.indexOf(taskname) + entry.getKey());
									ok = true;

								}
								if (entry.getValue().size() == 2 && entry.getValue().elementAt(0).equals("schedule")
										&& entry.getValue().elementAt(1).equals("assign") && state.equals("start")) {
									entry.getValue().add(state);
									event.add(5, caseid + tasks.indexOf(taskname) + entry.getKey());
									ok = true;

								}
								if (entry.getValue().size() == 3 && entry.getValue().elementAt(0).equals("schedule")
										&& entry.getValue().elementAt(1).equals("assign")
										&& entry.getValue().elementAt(2).equals("start") && state.equals("complete")) {
									entry.getValue().add(state);
									event.add(5, caseid + tasks.indexOf(taskname) + entry.getKey());
									ok = true;

								}

							}
							if (ok == false) {
								Vector<String> s1 = new Vector<>();
								s1.add(state);
								states.put(i, s1);
								event.add(5, caseid + tasks.indexOf(taskname) + i);
							}

						} else {
							tasks.add(taskname);
							Vector<String> s1 = new Vector<>();
							s1.add(state);
							states.put(i, s1);
							event.add(5, caseid + tasks.indexOf(taskname) + i);
						}
					}
					//new case
					else {
						cases.add(caseid);
						tasks = new ArrayList<String>();
						tasks.add(taskname);
						Vector<String> s1 = new Vector<>();
						s1.add(state);
						states.put(i, s1);
						event.add(5, caseid + tasks.indexOf(taskname) + i);

					}
				}

			}
			eventlogdata = "";
			for (int j = 0; j < 3; j++)
				eventlogdata += "'" + event.elementAt(j) + "',";

			Timestamp event_time = new Timestamp(Long.valueOf(event.elementAt(3)));
			String etime = event_time.toString();
			eventlogdata += "'" + etime + "',";

			for (int j = 4; j < event.size(); j++)
				eventlogdata += "'" + event.elementAt(j) + "',";
			eventlogdata = eventlogdata.substring(0, eventlogdata.length() - 1);
			edbline = "insert into eventlog(" + eventdbnames + ") values(" + eventlogdata + ")";
			equeries.add(edbline);

		}

		//Add event attributes -----------------------------------------------------------------------------------
		System.out.println("Event attributes calculated: " + System.nanoTime());
		Statement estatement = con.createStatement();

		for (String equery : equeries) {
			estatement.addBatch(equery);
		}
		System.out.println("Event attributes added: " + System.nanoTime());
		estatement.executeBatch();
		estatement.close();
		System.out.println("Event attributes added: " + System.nanoTime());

	}

	public void logToDB2(XLog log, Connection con, ECLDBmapping map) throws SQLException, ParseException {

		Vector<String> cadb = new Vector<String>();
		Vector<String> cael = new Vector<String>();
		Vector<String> eadb = new Vector<String>();
		Vector<String> eael = new Vector<String>();

		Vector<String> caseDBLines = new Vector<String>();
		caseDBLines.add("CaseId");
		//caseDBLines.add("CaseState");
		//caseDBLines.add("CaseStartDate");
		//caseDBLines.add("CaseEndDate");
		//caseDBLines.add("CPlannedEndDate");
		caseDBLines.add("ResourceNum");
		//caseDBLines.add("Duration");

		Vector<String> eventDBLines = new Vector<String>();
		eventDBLines.add("CaseId");
		eventDBLines.add("Task");
		//eventDBLines.add("DateFinish");
		eventDBLines.add("Resource");
		eventDBLines.add("Timestamp");
		eventDBLines.add("State");
		//eventDBLines.add("MonitoringResource");
		eventDBLines.add("EventId");
		eventDBLines.add("ProcessDefinitionID");

		for (int i = 0; i < map.ELAttribute.size(); i++) {
			if (!map.ELAttribute.elementAt(i).equals("Calculate")) {
				eael.add(map.ELAttribute.elementAt(i));
				eadb.add(map.DBELAttribute.elementAt(i));
			}
		}

		for (int i = 0; i < map.CLAttribute.size(); i++) {
			if (!map.CLAttribute.elementAt(i).equals("Calculate")) {
				//System.out.println(map.CLAttribute.elementAt(i)+"   "+map.DBCLAttribute.elementAt(i));
				cael.add(map.CLAttribute.elementAt(i));
				cadb.add(map.DBCLAttribute.elementAt(i));
			}
		}
		String casedbnames = "CaseId,ResourceNum,";
		for (int i = 0; i < cadb.size(); i++) {
			String ca = cadb.elementAt(i);
			//String ca=Integer.toString(i);
			if (!ca.equals("CaseId") && !ca.equals("ResourceNum")) {
				casedbnames += ca + ",";
				caseDBLines.add(ca);
			}
		}
		casedbnames = casedbnames.substring(0, casedbnames.length() - 1);

		String eventdbnames = "CaseId,Task,Resource,Timestamp,State,EventId,ProcessDefinitionID,";
		for (int i = 0; i < eadb.size(); i++) {
			String ea = eadb.elementAt(i);
			if (!ea.equals("CaseId") && !ea.equals("Task") && !ea.equals("Resource") && !ea.equals("Timestamp")
					&& !ea.equals("State") && !ea.equals("EventId") && !ea.equals("ProcessDefinitionID")) {
				eventdbnames += ea + ",";
				eventDBLines.add(ea);
			}
		}
		eventdbnames = eventdbnames.substring(0, eventdbnames.length() - 1);
		System.out.println("The edbNames are " + eventdbnames);
		Vector<Vector<String>> caselog = new Vector<Vector<String>>();
		Vector<Vector<String>> eventlog = new Vector<Vector<String>>();
		System.out.println("The size of cael et cadb " + cael.size() + " " + cadb.size());
		System.out.println("The size of eael et eadb " + eael.size() + " " + eadb.size());
		//System.out.println("________________ "+cael);

		for (XTrace t : log) {
			XAttributeLiteral caseidattr = (XAttributeLiteral) t.getAttributes().get(cael.elementAt(0));
			String caseid = caseidattr.getValue();

			Vector<String> caseLine = new Vector<String>();
			for (int i = 0; i < cael.size(); i++) {
				if (cadb.elementAt(i).equals("CaseStartDate") || cadb.elementAt(i).equals("CaseEndDate")
						|| cadb.elementAt(i).equals("CPlannedEndDate")) {
					XAttributeTimestamp timestamp = (XAttributeTimestamp) t.getAttributes().get(cael.elementAt(i));
					Long nextatt = null;
					if (!(timestamp == null)) {
						Date date = timestamp.getValue();
						nextatt = date.getTime();
						caseLine.add(nextatt.toString());
					} else {
						nextatt = null;
						caseLine.add("NULL");
					}

				} else {
					String next = null;
					XAttributeLiteral nextcaseidattr = (XAttributeLiteral) t.getAttributes().get(cael.elementAt(i));
					if (!(nextcaseidattr == null)) {
						next = nextcaseidattr.getValue();
						caseLine.add(next);
					} else {
						next = null;
						caseLine.add("NULL");
					}

				}
			}
			caselog.add(caseLine);
			//System.out.println("Took all neccassary information from Trace pass to event");
			for (XEvent e : t) {
				Vector<String> event = new Vector<String>();
				event.add(caseid);
				//System.out.println("-----------------------------------------------------");
				for (int i = 0; i < eael.size(); i++) {
					//System.out.println("event attribute "+eael.elementAt(i));
					if (eadb.elementAt(i).equals("Timestamp")) {
						XAttributeTimestamp timestamp = (XAttributeTimestamp) e.getAttributes().get(eael.elementAt(i));
						if (!(timestamp == null)) {

							Date date = timestamp.getValue();
							Long nextatt = date.getTime();
							//System.out.println("event attribute " + eael.elementAt(i) + " --------- " + nextatt);
							event.add(nextatt.toString());
						} else {
							event.add("NULL");
						}

					} else {

						XAttributeLiteral nextatt = (XAttributeLiteral) e.getAttributes().get(eael.elementAt(i));
						if (!(nextatt == null)) {
							String nextat = nextatt.getValue();
							//System.out.println("event attribute " + eael.elementAt(i) + " --------- " + nextat);
							event.add(nextat);
						} else {
							event.add(null);
						}

					}
				}
				eventlog.add(event);
			}
			//System.out.println("----------------Finish------------------");
		}

		eadb.add(0, "CaseId");
		//To test
		System.out.println("cadb" + cadb);
		System.out.println("eadb" + eadb);
		System.out.println("caseDBLines" + caseDBLines);
		System.out.println("eventDBLines" + eventDBLines);
		//System.out.println("caselog" + caselog);
		//System.out.println("eventlog" + eventlog);

		String cdbline = "";
		String edbline = "";
		Vector<String> cqueries = new Vector<String>();
		Vector<String> equeries = new Vector<String>();
		String eventlogdata = "";
		String caselogdata = "";

		Map<String, Boolean> caAdd = new HashMap<String, Boolean>();
		Map<String, Boolean> eaAdd = new HashMap<String, Boolean>();

		for (int i = 0; i < caseDBLines.size(); i++) {
			String nextCA = caseDBLines.elementAt(i);
			if (cadb.contains(nextCA)) {
				caAdd.put(nextCA, false);
			} else {
				caAdd.put(nextCA, true);
			}
		}
		for (int i = 0; i < eventDBLines.size(); i++) {
			String nextEA = eventDBLines.elementAt(i);
			if (eadb.contains(nextEA)) {
				eaAdd.put(nextEA, false);
			} else {
				eaAdd.put(nextEA, true);
			}
		}

		System.out.println("Starting calculating case attributes: " + System.nanoTime());

		//Add The case Attributes
		for (int i = 0; i < caselog.size(); i++) {
			caselogdata = "";
			boolean caseFound = false;
			int caseStart = 0;
			Vector<String> caseline = caselog.elementAt(i);
			String caseid = caseline.elementAt(0);
			String numres = "0";

			if (caAdd.get("ResourceNum")) {
				Set<String> resources = new HashSet<String>();
				for (int k = caseStart; k < eventlog.size(); k++) {
					Vector<String> e = eventlog.elementAt(k);
					if (e.elementAt(0).equals(caseid) && !caseFound) {
						caseFound = true;
						caseStart = i;
					} else {
						if (!e.elementAt(0).equals(caseid) && caseFound) {
							caseStart = i;
							caseFound = false;
							break;
						}
					}
					if (e.elementAt(0).equals(caseid)) {
						resources.add(e.elementAt(1));

					}

				}
				//

				Integer res_num = resources.size();
				numres = res_num.toString();

			}
			if (caAdd.get("ResourceNum"))
				caseline.add(1, numres);

			for (int j = 0; j < caseline.size(); j++)
				caselogdata += "'" + caseline.elementAt(j) + "',";

			caselogdata = caselogdata.substring(0, caselogdata.length() - 1);
			cdbline = "insert into caselog(" + casedbnames + ") values(" + caselogdata + ")";
			cqueries.add(cdbline);
		}
		//Save case attributes -----------------------------------------------------------------------------------

		System.out.println("Case attributes calculated: " + System.nanoTime());
		Statement cstatement = con.createStatement();

		for (String cquery : cqueries) {
			cstatement.addBatch(cquery);
		}
		cstatement.executeBatch();
		cstatement.close();

		System.out.println("Case attributes added: " + System.nanoTime());

		//Add the event attributes
		System.out.println("The size of eventLog " + eventlog.size());
		//Calculatin the Task Index
		ArrayList<String> cases = new ArrayList<String>();
		ArrayList<String> tasks = new ArrayList<String>();
		HashMap<Integer, Vector<String>> states = new HashMap<Integer, Vector<String>>();
		for (int i = 0; i < eventlog.size(); i++) {
			Vector<String> event = eventlog.elementAt(i);
			eventlogdata = "";
			for (int j = 0; j < 3; j++)
				eventlogdata += "'" + event.elementAt(j) + "',";

			Timestamp event_time = new Timestamp(Long.valueOf(event.elementAt(3)));
			String etime = event_time.toString();
			eventlogdata += "'" + etime + "',";

			for (int j = 4; j < event.size(); j++)
				eventlogdata += "'" + event.elementAt(j) + "',";
			eventlogdata = eventlogdata.substring(0, eventlogdata.length() - 1);
			edbline = "insert into eventlog(" + eventdbnames + ") values(" + eventlogdata + ")";
			equeries.add(edbline);

		}

		//Add event attributes -----------------------------------------------------------------------------------
		System.out.println("Event attributes calculated: " + System.nanoTime());
		Statement estatement = con.createStatement();

		for (String equery : equeries) {
			estatement.addBatch(equery);
		}
		System.out.println("Event attributes added: " + System.nanoTime());
		estatement.executeBatch();
		estatement.close();
		System.out.println("Event attributes added: " + System.nanoTime());

	}

	public void logToDB(XLog log, Connection con, ECLDBmapping map) throws SQLException, ParseException {

		Vector<String> cadb = new Vector<String>();
		Vector<String> cael = new Vector<String>();
		Vector<String> eadb = new Vector<String>();
		Vector<String> eael = new Vector<String>();

		Vector<String> caseDBLines = new Vector<String>();
		caseDBLines.add("CaseId");
		caseDBLines.add("CaseState");
		caseDBLines.add("CaseStartDate");
		caseDBLines.add("CaseEndDate");
		caseDBLines.add("PlannedEndDate");
		caseDBLines.add("ResourceNum");
		caseDBLines.add("Duration");

		Vector<String> eventDBLines = new Vector<String>();
		eventDBLines.add("CaseId");
		eventDBLines.add("Task");
		eventDBLines.add("DateFinish");
		eventDBLines.add("Resource");
		eventDBLines.add("Timestamp");
		eventDBLines.add("State");
		eventDBLines.add("MonitoringResource");
		eventDBLines.add("EventId");

		for (int i = 0; i < map.ELAttribute.size(); i++) {
			if (!map.ELAttribute.elementAt(i).equals("Calculate")) {
				eael.add(map.ELAttribute.elementAt(i));
				eadb.add(map.DBELAttribute.elementAt(i));
			}
		}

		for (int i = 0; i < map.CLAttribute.size(); i++) {
			if (!map.CLAttribute.elementAt(i).equals("Calculate")) {
				//System.out.println(map.CLAttribute.elementAt(i)+"   "+map.DBCLAttribute.elementAt(i));
				cael.add(map.CLAttribute.elementAt(i));
				cadb.add(map.DBCLAttribute.elementAt(i));
			}
		}
		String casedbnames = "CaseId,CaseState,CaseStartDate,CaseEndDate,PlannedEndDate,ResourceNum,Duration,";
		for (int i = 0; i < cadb.size(); i++) {
			String ca = cadb.elementAt(i);
			if (!ca.equals("CaseId") && !ca.equals("CaseState") && !ca.equals("CaseStartDate")
					&& !ca.equals("CaseEndDate") && !ca.equals("PlannedEndDate") && !ca.equals("ResourceNum")
					&& !ca.equals("Duration")) {
				casedbnames += ca + ",";
				caseDBLines.add(ca);
			}
		}
		casedbnames = casedbnames.substring(0, casedbnames.length() - 1);

		String eventdbnames = "CaseId,Task,DateFinish,Resource,Timestamp,State,MonitoringResource,EventId,";
		for (int i = 0; i < eadb.size(); i++) {
			String ea = eadb.elementAt(i);
			if (!ea.equals("CaseId") && !ea.equals("Task") && !ea.equals("DateFinish") && !ea.equals("Resource")
					&& !ea.equals("Timestamp") && !ea.equals("State") && !ea.equals("MonitoringResource")
					&& !ea.equals("EventId")) {
				eventdbnames += ea + ",";
				eventDBLines.add(ea);
			}
		}
		eventdbnames = eventdbnames.substring(0, eventdbnames.length() - 1);
		System.out.println("The edbNames are " + eventdbnames);
		Vector<Vector<String>> caselog = new Vector<Vector<String>>();
		Vector<Vector<String>> eventlog = new Vector<Vector<String>>();
		System.out.println("The size of cael et cadb " + cael.size() + " " + cadb.size());
		System.out.println("The size of eael et eadb " + eael.size() + " " + eadb.size());
		for (XTrace t : log) {
			XAttributeLiteral caseidattr = (XAttributeLiteral) t.getAttributes().get(cael.elementAt(0));
			String caseid = caseidattr.getValue();
			Vector<String> caseLine = new Vector<String>();
			for (int i = 0; i < cael.size(); i++) {
				if (cadb.elementAt(i).equals("CaseStartDate") || cadb.elementAt(i).equals("CaseEndDate")
						|| cadb.elementAt(i).equals("PlannedEndDate")) {
					XAttributeTimestamp timestamp = (XAttributeTimestamp) t.getAttributes().get(cael.elementAt(i));
					Long nextatt = null;
					if (!(timestamp == null)) {
						Date date = timestamp.getValue();
						nextatt = date.getTime();
						caseLine.add(nextatt.toString());
					} else {
						nextatt = null;
						caseLine.add("NULL");
					}

				} else {
					String next = null;
					XAttributeLiteral nextcaseidattr = (XAttributeLiteral) t.getAttributes().get(cael.elementAt(i));
					if (!(nextcaseidattr == null)) {
						next = nextcaseidattr.getValue();
						caseLine.add(next);
					} else {
						next = null;
						caseLine.add("NULL");
					}

				}
			}
			caselog.add(caseLine);
			//System.out.println("Took all neccassary information from Trace pass to event");
			for (XEvent e : t) {
				Vector<String> event = new Vector<String>();
				event.add(caseid);
				//System.out.println("-----------------------------------------------------");
				for (int i = 0; i < eael.size(); i++) {
					//System.out.println("event attribute "+eael.elementAt(i));
					if (eadb.elementAt(i).equals("Timestamp")) {
						XAttributeTimestamp timestamp = (XAttributeTimestamp) e.getAttributes().get(eael.elementAt(i));
						if (!(timestamp == null)) {

							Date date = timestamp.getValue();
							Long nextatt = date.getTime();
							//System.out.println("event attribute " + eael.elementAt(i) + " --------- " + nextatt);
							event.add(nextatt.toString());
						} else {
							event.add("NULL");
						}

					} else {

						XAttributeLiteral nextatt = (XAttributeLiteral) e.getAttributes().get(eael.elementAt(i));
						if (!(nextatt == null)) {
							String nextat = nextatt.getValue();
							//System.out.println("event attribute " + eael.elementAt(i) + " --------- " + nextat);
							event.add(nextat);
						} else {
							event.add(null);
						}

					}
				}
				eventlog.add(event);
			}
			//System.out.println("----------------Finish------------------");
		}

		eadb.add(0, "CaseId");
		//To test
		System.out.println("cadb" + cadb);
		System.out.println("eadb" + eadb);
		System.out.println("caseDBLines" + caseDBLines);
		System.out.println("eventDBLines" + eventDBLines);
		//System.out.println("caselog" + caselog);
		//System.out.println("eventlog" + eventlog);

		String cdbline = "";
		String edbline = "";
		Vector<String> cqueries = new Vector<String>();
		Vector<String> equeries = new Vector<String>();
		String eventlogdata = "";
		String caselogdata = "";

		Map<String, Boolean> caAdd = new HashMap<String, Boolean>();
		Map<String, Boolean> eaAdd = new HashMap<String, Boolean>();

		for (int i = 0; i < caseDBLines.size(); i++) {
			String nextCA = caseDBLines.elementAt(i);
			if (cadb.contains(nextCA)) {
				caAdd.put(nextCA, false);
			} else {
				caAdd.put(nextCA, true);
			}
		}
		for (int i = 0; i < eventDBLines.size(); i++) {
			String nextEA = eventDBLines.elementAt(i);
			if (eadb.contains(nextEA)) {
				eaAdd.put(nextEA, false);
			} else {
				eaAdd.put(nextEA, true);
			}
		}

		System.out.println("Starting calculating case attributes: " + System.nanoTime());

		//Add The case Attributes
		for (int i = 0; i < caselog.size(); i++) {
			caselogdata = "";
			boolean caseFound = false;
			int caseStart = 0;
			Vector<String> caseline = caselog.elementAt(i);
			String caseid = caseline.elementAt(0);
			String numres = "0";
			String caseduration = "0";
			if (caAdd.get("ResourceNum") || caAdd.get("Duration")) {
				Set<String> resources = new HashSet<String>();
				Timestamp firsttime = null;
				Timestamp lasttime = null;
				Timestamp curTime = null;
				for (int k = caseStart; k < eventlog.size(); k++) {
					Vector<String> e = eventlog.elementAt(k);
					if (e.elementAt(0).equals(caseid) && !caseFound) {
						caseFound = true;
						caseStart = i;
					} else {
						if (!e.elementAt(0).equals(caseid) && caseFound) {
							caseStart = i;
							caseFound = false;
							break;
						}
					}
					if (e.elementAt(0).equals(caseid)) {
						resources.add(e.elementAt(3));
						curTime = new Timestamp(Long.valueOf(e.elementAt(4)));

						if (firsttime == null || firsttime.after(curTime))
							firsttime = curTime;

						if (lasttime == null || lasttime.before(curTime))
							lasttime = curTime;

					}

				}
				//

				Integer res_num = resources.size();
				numres = res_num.toString();
				Long case_long_duration = lasttime.getTime() - firsttime.getTime();
				caseduration = case_long_duration.toString();

			}
			if (caAdd.get("ResourceNum"))
				caseline.add(5, numres);
			if (caAdd.get("Duration"))
				caseline.add(6, caseduration);
			for (int j = 0; j < caseline.size(); j++)
				caselogdata += "'" + caseline.elementAt(j) + "',";

			caselogdata = caselogdata.substring(0, caselogdata.length() - 1);
			cdbline = "insert into caselog(" + casedbnames + ") values(" + caselogdata + ")";
			cqueries.add(cdbline);
		}
		//Save case attributes -----------------------------------------------------------------------------------

		System.out.println("Case attributes calculated: " + System.nanoTime());
		Statement cstatement = con.createStatement();

		for (String cquery : cqueries) {
			cstatement.addBatch(cquery);
		}
		cstatement.executeBatch();
		cstatement.close();

		System.out.println("Case attributes added: " + System.nanoTime());

		//Add the event attributes
		System.out.println("The size of eventLog " + eventlog.size());
		//Calculatin the Task Index
		ArrayList<String> cases = new ArrayList<String>();
		ArrayList<String> tasks = new ArrayList<String>();
		HashMap<Integer, Vector<String>> states = new HashMap<Integer, Vector<String>>();
		for (int i = 0; i < eventlog.size(); i++) {
			System.out.println("The iteration number " + i);
			Vector<String> event = eventlog.elementAt(i);
			String caseid = event.elementAt(0);
			String taskname = event.elementAt(1);
			String state = event.elementAt(5);
			if (eaAdd.get("EventId")) {
				if (i == 0) {
					Vector<String> s = new Vector<>();
					cases.add(caseid);
					tasks.add(taskname);
					s.add(state);
					states.put(i, s);
					//System.out.println("-------------- "+tasks.indexOf(taskname));
					event.add(7, caseid + tasks.indexOf(taskname) + i);
				} else {
					//same case
					if (cases.contains(caseid)) {
						if (tasks.contains(taskname)) {
							Iterator<Map.Entry<Integer, Vector<String>>> it = states.entrySet().iterator();
							boolean ok = false;
							while (it.hasNext() && !ok) {
								Entry<Integer, Vector<String>> entry = it.next();
								if (entry.getValue().size() == 1 && entry.getValue().elementAt(0).equals("SCHEDULE")
										&& state.equals("START")) {
									entry.getValue().add(state);
									event.add(7, caseid + tasks.indexOf(taskname) + entry.getKey());
									ok = true;

								}
								if (entry.getValue().size() == 1 && entry.getValue().elementAt(0).equals("START")
										&& state.equals("COMPLETE")) {
									entry.getValue().add(state);
									event.add(7, caseid + tasks.indexOf(taskname) + entry.getKey());
									ok = true;

								}
								if (entry.getValue().size() == 2 && entry.getValue().elementAt(0).equals("SCHEDULE")
										&& entry.getValue().elementAt(1).equals("START") && state.equals("COMPLETE")) {
									entry.getValue().add(state);
									event.add(7, caseid + tasks.indexOf(taskname) + entry.getKey());
									ok = true;

								}

							}
							if (ok == false) {
								Vector<String> s1 = new Vector<>();
								s1.add(state);
								states.put(i, s1);
								event.add(7, caseid + tasks.indexOf(taskname) + i);
							}

						} else {
							tasks.add(taskname);
							Vector<String> s1 = new Vector<>();
							s1.add(state);
							states.put(i, s1);
							event.add(7, caseid + tasks.indexOf(taskname) + i);
						}
					}
					//new case
					else {
						cases.add(caseid);
						tasks = new ArrayList<String>();
						tasks.add(taskname);
						Vector<String> s1 = new Vector<>();
						s1.add(state);
						states.put(i, s1);
						event.add(7, caseid + tasks.indexOf(taskname) + i);

					}
				}

			}
			eventlogdata = "";
			for (int j = 0; j < 4; j++)
				eventlogdata += "'" + event.elementAt(j) + "',";

			Timestamp event_time = new Timestamp(Long.valueOf(event.elementAt(4)));
			String etime = event_time.toString();
			eventlogdata += "'" + etime + "',";

			for (int j = 5; j < event.size(); j++)
				eventlogdata += "'" + event.elementAt(j) + "',";
			eventlogdata = eventlogdata.substring(0, eventlogdata.length() - 1);
			edbline = "insert into eventlog(" + eventdbnames + ") values(" + eventlogdata + ")";
			equeries.add(edbline);

		}

		//Add event attributes -----------------------------------------------------------------------------------
		System.out.println("Event attributes calculated: " + System.nanoTime());
		Statement estatement = con.createStatement();

		for (String equery : equeries) {
			estatement.addBatch(equery);
		}
		System.out.println("Event attributes added: " + System.nanoTime());
		estatement.executeBatch();
		estatement.close();
		System.out.println("Event attributes added: " + System.nanoTime());

	}

	//Create Bar dataset
	public void resourcecaracteristics(String filename, ArrayList<Double> av, ArrayList<Long> duration,
			ArrayList<Integer> nbinst) {
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "Availability%,Duration,NumberOfInstruction";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filename);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			//Write a new student object list to the CSV file
			for (int i = 0; i < av.size(); i++) {
				fileWriter.append(String.valueOf(av.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(duration.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(nbinst.get(i)));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}
	
	public void casecsv(String filename, Map<Integer, Vector<Case>> cases) {
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "Day,caseNumber";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filename);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			//Write a new student object list to the CSV file
			for(Map.Entry<Integer, Vector<Case>> entry:cases.entrySet()) {
				fileWriter.append(String.valueOf(entry.getKey()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(entry.getValue().size()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	// Create and Write in the CSV file
	public void writeResults(String filename, Vector<String> resource, ArrayList<Double> batch,
			ArrayList<Double> nonBatch, ArrayList<Double> avai, ArrayList<Integer> workingdays,
			ArrayList<Integer> workingdaysbehav, ArrayList<Integer> totinstruction, ArrayList<Double> availabilityWork,
			ArrayList<Double> totavai) {
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "Resourceid,Batch%,NonBatch%,AverageAvailability%,workingDays,WorkingBehavdays,NumberOfInstruction,AvailabilityWork, totAvailabilityWork";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filename);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			//Write a new student object list to the CSV file
			for (int i = 0; i < resource.size(); i++) {
				fileWriter.append(resource.elementAt(i));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(batch.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(nonBatch.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(avai.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(workingdays.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(workingdaysbehav.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(totinstruction.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(availabilityWork.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(totavai.get(i)));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	public void writeResultperf(String filename, Vector<String> resource, ArrayList<Double> batch,
			ArrayList<Double> nonBatch, ArrayList<Double> avai) {
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "Resourceid,FIFO%,LIFO%,Random%";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filename);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			//Write a new student object list to the CSV file
			for (int i = 0; i < resource.size(); i++) {
				fileWriter.append(resource.elementAt(i));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(batch.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(nonBatch.get(i)));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(avai.get(i)));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	//Write dans le csv file resources to tasks
	public void AllResourceToTaskCSV(String taskname, Vector<String> resource, Map<String, Double> rates,
			Map<String, Long> meanDur) {
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "Resourceid,taskname,AverageDuration, ExecutionRate";
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("tasks/" + taskname + ".csv");
			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			//Write a new student object list to the CSV file
			for (int i = 0; i < resource.size(); i++) {
				fileWriter.append(resource.elementAt(i));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(taskname);
				fileWriter.append(COMMA_DELIMITER);
				if (meanDur.containsKey(resource.elementAt(i))) {
					fileWriter.append(String.valueOf(meanDur.get(resource.elementAt(i))));
					fileWriter.append(COMMA_DELIMITER);
				} else {
					fileWriter.append("0");
					fileWriter.append(COMMA_DELIMITER);
				}
				if (rates.containsKey(resource.elementAt(i))) {
					fileWriter.append(String.valueOf(rates.get(resource.elementAt(i))));
					fileWriter.append(COMMA_DELIMITER);
				} else {
					fileWriter.append("0");
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);

			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	//Another type of events

	// Clustering the K-means Algorithm for 3 varibales
	public ArrayList<ClustredObj> kmeans(Vector<String> resource, int nbcluser, String file) {
		//Remplir Dtataset with data
		ArrayList<ClustredObj> res = new ArrayList<>();
		String csvFile = file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		double samples[][] = new double[resource.size()][3];
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int i = 0;
			int k = 0;
			while ((line = br.readLine()) != null) {
				if (i != 0) {
					String[] r = line.split(cvsSplitBy);
					//System.out.println("------------------------" + r.length);
					samples[k][0] = Double.parseDouble(r[1]);
					samples[k][1] = Double.parseDouble(r[2]);
					samples[k][2] = Double.parseDouble(r[3]);
					//System.out.println(samples[k][0] + " " + samples[k][1]);
					k++;
					//System.out.println("------------------------");
				}
				i++;
			}
			ArrayList<Data> dataSet = new ArrayList<Data>();
			ArrayList<Centroid> centroids = new ArrayList<Centroid>();
			centroids = initialize(nbcluser, resource, samples);
			kMeanCluster(nbcluser, dataSet, centroids, resource, samples);
			for (int H = 0; H < nbcluser; H++) {
				String clusterName = "Cluster" + H;
				Centroid c = new Centroid();
				c.X(centroids.get(H).X());
				c.Y(centroids.get(H).Y());
				c.Z(centroids.get(H).Z());
				System.out.println("Cluster " + H + " includes:");
				ArrayList<Data> datalist = new ArrayList<>();
				for (int j = 0; j < resource.size(); j++) {
					if (dataSet.get(j).cluster() == H) {
						System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
						datalist.add(dataSet.get(j));
					}
				} // j
				ClustredObj clustobj = new ClustredObj(clusterName, datalist, c);
				res.add(clustobj);
				System.out.println();
			} // i
				// Print out centroid results.
			System.out.println("Centroids finalized at:");
			for (int H = 0; H < nbcluser; H++) {

				System.out.println("     (" + centroids.get(H).X() + ", " + centroids.get(H).Y());
			}
			System.out.print("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("The size is " + res.size());
		return res;

	}

	//Batch et non Batch
	public ArrayList<ClustredObj> kmeans2vars(Vector<String> resource, int nbcluser, String file) {
		//Remplir Dtataset with data
		ArrayList<ClustredObj> res = new ArrayList<>();
		String csvFile = file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		double samples[][] = new double[resource.size()][2];
		try {
			br = new BufferedReader(new FileReader(csvFile));
			int i = 0;
			int k = 0;
			while ((line = br.readLine()) != null) {
				if (i != 0) {
					String[] r = line.split(cvsSplitBy);
					samples[k][0] = Double.parseDouble(r[1]);
					samples[k][1] = Double.parseDouble(r[2]);
					//samples[k][2] = Double.parseDouble(r[3]);
					k++;
					//System.out.println(samples[k][0] + " " + samples[k][1] + " " + samples[k][2]);	
					//System.out.println("------------------------");
				}
				i++;
			}
			ArrayList<Data> dataSet = new ArrayList<Data>();
			ArrayList<Centroid> centroids = new ArrayList<Centroid>();
			//initialize(centroids);
			centroids = initializekmeansplusplus(nbcluser, resource, samples);
			kMeanCluster2var(nbcluser, dataSet, centroids, resource, samples);
			for (int H = 0; H < nbcluser; H++) {
				String clusterName = "Cluster" + H;
				Centroid c = new Centroid();
				c.X(centroids.get(H).X());
				c.Y(centroids.get(H).Y());
				//c.Z(centroids.get(H).Z());
				System.out.println("Cluster " + H + " includes:");
				ArrayList<Data> datalist = new ArrayList<>();
				for (int j = 0; j < resource.size(); j++) {
					if (dataSet.get(j).cluster() == H) {
						System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y());
						datalist.add(dataSet.get(j));
					}
				} // j
				ClustredObj clustobj = new ClustredObj(clusterName, datalist, c);
				res.add(clustobj);
				System.out.println();
			} // i
				// Print out centroid results.
			System.out.println("Centroids finalized at:");
			for (int H = 0; H < nbcluser; H++) {

				System.out.println("     (" + centroids.get(H).X() + ", " + centroids.get(H).Y());
			}
			System.out.print("\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("The size is " + res.size());
		return res;

	}

	//Initialize centroids for batch non batch availability ()
	private ArrayList<Centroid> initialize(int NUM_CLUSTERS, Vector<String> resource, double[][] SAMPLES) {
		System.out.println("Centroids initialized at:");

		ArrayList<Centroid> centroids = new ArrayList<>();
		Map<Centroid, Vector<Double>> list = new HashMap<>();
		// prepare Data 
		int sampleNumber = 0;
		Centroid newData = null;
		int TOTAL_DATA = resource.size();
		Centroid date0 = new Centroid();
		// Add in new data, one at a time, recalculating centroids with each new one.
		while (list.size() < TOTAL_DATA && sampleNumber < TOTAL_DATA) {
			newData = new Centroid(SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1], SAMPLES[sampleNumber][2]);
			Vector<Double> vect = new Vector<>();
			//boucle for to check if the key object exist or not
			boolean found = false;
			for (Map.Entry<Centroid, Vector<Double>> entry : list.entrySet()) {
				if (newData.X() == entry.getKey().X() && newData.Y() == entry.getKey().Y()
						&& newData.Z() == entry.getKey().Z()) {
					System.out.println("The data " + newData.X() + " " + newData.Y() + " " + newData.Z());
					found = true;
				}
			}
			if (!found) {
				list.put(newData, vect);
			}
			sampleNumber++;
		}
		System.out.println(" The size of the list is " + list.size());
		Map.Entry<Centroid, Vector<Double>> entry = list.entrySet().iterator().next();
		date0 = entry.getKey();
		centroids.add(date0);
		list.remove(date0);
		int k = 0;
		Centroid c = new Centroid();
		while (centroids.size() < NUM_CLUSTERS) {

			//Calculate the Distance
			if (k == 0) {

				for (Map.Entry<Centroid, Vector<Double>> entry1 : list.entrySet()) {
					double distance = dist(date0, entry1.getKey());
					//System.out.println("The distance is  " + distance);
					entry1.getValue().addElement(distance);
				}
				// SELECT THE second CENTROID WITH MAX PROB
				c = max(list);
				centroids.add(c);
				list.remove(c);
				k++;
			}

			//Select the rest of the centroids
			else {
				for (Map.Entry<Centroid, Vector<Double>> entry1 : list.entrySet()) {
					double distance = dist(c, entry1.getKey());
					//System.out.println("The distance is  " + distance);
					entry1.getValue().addElement(distance);
				}
				c = max(list);
				centroids.add(c);
				list.remove(c);
			}

		}

		for (int i = 0; i < centroids.size(); i++) {
			System.out.println("The centroids Are initialized at " + centroids.get(i).X() + "  " + centroids.get(i).Y()
					+ "  " + centroids.get(i).Z());
		}
		return centroids;

	}

	//Initialize the centroids with Kmeans++ method
	private ArrayList<Centroid> initializekmeansplusplus(int NUM_CLUSTERS, Vector<String> resource,
			double[][] SAMPLES) {
		System.out.println("Initialize the centroids with the right values by applying Kmeans++ :");
		ArrayList<Centroid> centroids = new ArrayList<>();
		Map<Centroid, Vector<Double>> list = new HashMap<>();
		// prepare Data 
		int sampleNumber = 0;
		Centroid newData = null;
		int TOTAL_DATA = resource.size();
		Centroid date0 = new Centroid();
		// Add in new data, one at a time, recalculating centroids with each new one.
		while (list.size() < TOTAL_DATA && sampleNumber < TOTAL_DATA) {
			newData = new Centroid(SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1]);
			Vector<Double> vect = new Vector<>();
			//boucle for to check if the key object exist or not
			boolean found = false;
			for (Map.Entry<Centroid, Vector<Double>> entry : list.entrySet()) {
				if (newData.X() == entry.getKey().X() && newData.Y() == entry.getKey().Y()) {
					System.out.println("The data " + newData.X() + " " + newData.Y());
					found = true;
				}
			}
			if (!found) {
				list.put(newData, vect);
			}
			sampleNumber++;
		}
		System.out.println(" The size of the list is " + list.size());
		Map.Entry<Centroid, Vector<Double>> entry = list.entrySet().iterator().next();
		date0 = entry.getKey();
		centroids.add(date0);
		list.remove(date0);
		int k = 0;
		Centroid c = new Centroid();
		while (centroids.size() < NUM_CLUSTERS) {

			//Calculate the Distance
			if (k == 0) {

				for (Map.Entry<Centroid, Vector<Double>> entry1 : list.entrySet()) {
					double distance = dist(date0, entry1.getKey());
					//System.out.println("The distance is  " + distance);
					entry1.getValue().addElement(distance);
				}
				// SELECT THE second CENTROID WITH MAX PROB
				c = max(list);
				centroids.add(c);
				list.remove(c);
				k++;
			}

			//Select the rest of the centroids
			else {
				for (Map.Entry<Centroid, Vector<Double>> entry1 : list.entrySet()) {
					double distance = dist(c, entry1.getKey());
					//System.out.println("The distance is  " + distance);
					entry1.getValue().addElement(distance);
				}
				c = max(list);
				centroids.add(c);
				list.remove(c);
			}

		}

		for (int i = 0; i < centroids.size(); i++) {
			System.out
					.println("The centroids Are initialized at " + centroids.get(i).X() + "  " + centroids.get(i).Y());
		}
		return centroids;
	}

	// select the maximum proba within a vector 
	private Centroid max(Map<Centroid, Vector<Double>> list) {
		double max = 0;
		Centroid c = new Centroid();
		for (Map.Entry<Centroid, Vector<Double>> entry1 : list.entrySet()) {
			for (int i = 0; i < entry1.getValue().size(); i++) {
				if (entry1.getValue().get(i) > max) {
					max = entry1.getValue().get(i);
					c = entry1.getKey();
				}
			}
		}
		return c;
	}

	private static void initialize1(ArrayList<Centroid> centroids) {
		System.out.println("Centroids initialized at:");
		centroids.add(new Centroid(100.0, 0.0, 0.0));
		centroids.add(new Centroid(0.0, 100.0, 0.0));
		centroids.add(new Centroid(0.0, 0.0, 100.0));// lowest set.// highest set.
		centroids.add(new Centroid(50.0, 50.0, 0.0));
		centroids.add(new Centroid(50.0, 0.0, 50.0));
		centroids.add(new Centroid(0.0, 50.0, 50.0));

		//centroids.add(new Centroid(0.0, 0.0, 0.0));
		System.out.println(
				"     (" + centroids.get(0).X() + ", " + centroids.get(0).Y() + ", " + centroids.get(0).Z() + ")");
		System.out.println(
				"     (" + centroids.get(1).X() + ", " + centroids.get(1).Y() + ", " + centroids.get(0).Z() + ")");
		System.out.print("\n");
		return;
	}

	// Create Dataset
	public XYSeriesCollection createclusteDataset(ArrayList<ClustredObj> data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (int i = 0; i < data.size(); i++) {
			XYSeries series1 = new XYSeries(data.get(i).clusterName);
			for (int j = 0; j < data.get(i).datalist.size(); j++) {
				series1.add(data.get(i).datalist.get(j).X(), data.get(i).datalist.get(j).Y());
			}
			dataset.addSeries(series1);
		}

		return dataset;
	}

	private static double dist(Centroid d, Centroid d1) {
		//System.out.println(d1.Y() + " " + d.Y() + " " + d1.X() + " " + d.X() + " " + d1.Z() + " " + d.Z());
		return Math.sqrt(Math.pow((d1.Y() - d.Y()), 2) + Math.pow((d1.X() - d.X()), 2) + Math.pow((d1.Z() - d.Z()), 2));
	}

	private static double dist1(Data d, Centroid c) {
		return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2) + Math.pow((c.Z() - d.Z()), 2));
	}

	private static double dist2(Data d, Centroid c) {
		return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2));
	}

	private static void kMeanCluster(int NUM_CLUSTERS, ArrayList<Data> dataSet, ArrayList<Centroid> centroids,
			Vector<String> resource, double[][] SAMPLES) {
		final double bigNumber = Math.pow(100, 100); // some big number that's sure to be larger than our data range.
		double minimum = bigNumber; // The minimum value to beat.
		double distance = 0.0; // The current minimum value.
		int sampleNumber = 0;
		int cluster = 0;
		boolean isStillMoving = true;
		Data newData = null;
		int TOTAL_DATA = resource.size();
		// Add in new data, one at a time, recalculating centroids with each new one.
		while (dataSet.size() < TOTAL_DATA) {
			newData = new Data(SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1], SAMPLES[sampleNumber][2],
					resource.elementAt(sampleNumber));

			dataSet.add(newData);
			minimum = bigNumber;
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				distance = dist1(newData, centroids.get(i));
				if (distance < minimum) {
					minimum = distance;
					cluster = i;
				}
			}
			newData.cluster(cluster);

			// calculate new centroids.
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				int totalX = 0;
				int totalY = 0;
				int totalZ = 0;
				int totalInCluster = 0;
				for (int j = 0; j < dataSet.size(); j++) {
					if (dataSet.get(j).cluster() == i) {
						totalX += dataSet.get(j).X();
						totalY += dataSet.get(j).Y();
						totalZ += dataSet.get(j).Z();
						totalInCluster++;
					}
				}
				if (totalInCluster > 0) {
					centroids.get(i).X(totalX / totalInCluster);
					centroids.get(i).Y(totalY / totalInCluster);
					centroids.get(i).Z(totalZ / totalInCluster);
				}
			}
			sampleNumber++;
		}

		// Now, keep shifting centroids until equilibrium occurs.
		while (isStillMoving) {
			// calculate new centroids.
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				int totalX = 0;
				int totalY = 0;
				int totalZ = 0;
				int totalInCluster = 0;
				for (int j = 0; j < dataSet.size(); j++) {
					if (dataSet.get(j).cluster() == i) {
						totalX += dataSet.get(j).X();
						totalY += dataSet.get(j).Y();
						totalZ += dataSet.get(j).Z();
						totalInCluster++;
					}
				}
				if (totalInCluster > 0) {
					centroids.get(i).X(totalX / totalInCluster);
					centroids.get(i).Y(totalY / totalInCluster);
					centroids.get(i).Z(totalZ / totalInCluster);
				}
			}

			// Assign all data to the new centroids
			isStillMoving = false;

			for (int i = 0; i < dataSet.size(); i++) {
				Data tempData = dataSet.get(i);
				minimum = bigNumber;
				for (int j = 0; j < NUM_CLUSTERS; j++) {
					distance = dist1(tempData, centroids.get(j));
					if (distance < minimum) {
						minimum = distance;
						cluster = j;
					}
				}
				tempData.cluster(cluster);
				if (tempData.cluster() != cluster) {
					tempData.cluster(cluster);
					isStillMoving = true;
				}
			}
		}
		return;
	}

	private static void kMeanCluster2var(int NUM_CLUSTERS, ArrayList<Data> dataSet, ArrayList<Centroid> centroids,
			Vector<String> resource, double[][] SAMPLES) {
		final double bigNumber = Math.pow(100, 100); // some big number that's sure to be larger than our data range.
		double minimum = bigNumber; // The minimum value to beat.
		double distance = 0.0; // The current minimum value.
		int sampleNumber = 0;
		int cluster = 0;
		boolean isStillMoving = true;
		Data newData = null;
		int TOTAL_DATA = resource.size();
		//System.out.println("The lengh is " + TOTAL_DATA);
		// Add in new data, one at a time, recalculating centroids with each new one.
		while (dataSet.size() < TOTAL_DATA) {
			newData = new Data(SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1], resource.elementAt(sampleNumber));

			dataSet.add(newData);
			minimum = bigNumber;
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				distance = dist2(newData, centroids.get(i));
				if (distance < minimum) {
					minimum = distance;
					cluster = i;
				}
			}
			newData.cluster(cluster);

			// calculate new centroids.
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				int totalX = 0;
				int totalY = 0;
				//int totalZ = 0;
				int totalInCluster = 0;
				for (int j = 0; j < dataSet.size(); j++) {
					if (dataSet.get(j).cluster() == i) {
						totalX += dataSet.get(j).X();
						totalY += dataSet.get(j).Y();
						//totalZ += dataSet.get(j).Z();
						totalInCluster++;
					}
				}
				if (totalInCluster > 0) {
					centroids.get(i).X(totalX / totalInCluster);
					centroids.get(i).Y(totalY / totalInCluster);
					//centroids.get(i).Z(totalZ / totalInCluster);
				}
			}
			sampleNumber++;
		}

		// Now, keep shifting centroids until equilibrium occurs.
		while (isStillMoving) {
			// calculate new centroids.
			for (int i = 0; i < NUM_CLUSTERS; i++) {
				int totalX = 0;
				int totalY = 0;
				//int totalZ = 0;
				int totalInCluster = 0;
				for (int j = 0; j < dataSet.size(); j++) {
					if (dataSet.get(j).cluster() == i) {
						totalX += dataSet.get(j).X();
						totalY += dataSet.get(j).Y();
						//totalZ += dataSet.get(j).Z();
						totalInCluster++;
					}
				}
				if (totalInCluster > 0) {
					centroids.get(i).X(totalX / totalInCluster);
					centroids.get(i).Y(totalY / totalInCluster);
					//centroids.get(i).Z(totalZ / totalInCluster);
				}
			}

			// Assign all data to the new centroids
			isStillMoving = false;

			for (int i = 0; i < dataSet.size(); i++) {
				Data tempData = dataSet.get(i);
				minimum = bigNumber;
				for (int j = 0; j < NUM_CLUSTERS; j++) {
					distance = dist2(tempData, centroids.get(j));
					if (distance < minimum) {
						minimum = distance;
						cluster = j;
					}
				}
				tempData.cluster(cluster);
				if (tempData.cluster() != cluster) {
					tempData.cluster(cluster);
					isStillMoving = true;
				}
			}
		}
		return;
	}

	//Pie queue dataset
	public PieDataset createqueuepie(ArrayList<ClustredObj> list, int nbr) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < list.size(); i++) {
			dataset.setValue(list.get(i).clusterName, list.get(i).datalist.size() * 100 / nbr);
		}
		return dataset;
	}

	//List of eventsNames
	public ArrayList<String> eventNameslist(Map<String, Event> events) {
		ArrayList<String> eventNames = new ArrayList<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (!eventNames.contains(entry.getValue().eventName)) {
				eventNames.add(entry.getValue().eventName);
			}
		}
		//System.out.println("The size of the list of eventNames is " + eventNames.size());
		return eventNames;
	}

	// Skills Analysis
	public void skillsAnalysis(Map<String, Event> events) {
		ArrayList<String> eventNames = new ArrayList<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (!eventNames.contains(entry.getValue().eventName)) {
				//System.out.println("The task name is " + entry.getValue().eventName);
				eventNames.add(entry.getValue().eventName);
			}
		}
		//System.out.println("The size is " + eventNames.size());

	}

	// Determine for Each Resource the average Time of execution for each type of tasks
	public Map<String, Vector<Task>> resourcetaskTime(ArrayList<String> eventName, Vector<String> resources,
			Map<String, Event> event) {
		Map<String, Vector<Task>> mapList = new HashMap<>();
		for (int i = 0; i < eventName.size(); i++) {
			for (int j = 0; j < resources.size(); j++) {

				long timeexec = 0;
				int occ = 0;
				for (Map.Entry<String, Event> entry : event.entrySet()) {

					if (entry.getValue().eventName.equals(eventName.get(i))
							&& entry.getValue().resource.equals(resources.get(j))) {
						timeexec = timeexec + entry.getValue().duration;
						occ = occ + 1;

					}
				}
				if (mapList.containsKey(resources.get(j))) {
					if (occ > 0) {
						long meanTime = timeexec / occ;
						Task task = new Task(eventName.get(i), meanTime);
						mapList.get(resources.get(j)).addElement(task);
					} else {
						Task task = new Task(eventName.get(i), -1);
						mapList.get(resources.get(j)).addElement(task);
					}

				} else {

					if (occ > 0) {
						long meanTime = timeexec / occ;
						Task task = new Task(eventName.get(i), meanTime);
						Vector<Task> vect = new Vector<>();
						vect.add(task);
						mapList.put(resources.get(j), vect);

					} else {
						Task task = new Task(eventName.get(i), -1);
						Vector<Task> vect = new Vector<>();
						vect.add(task);
						mapList.put(resources.get(j), vect);
					}

				}
			}
		}
		

		return mapList;

	}

	//Calculate the durations of the events
	public Map<String, Event> durations(Map<String, Event> events) throws ParseException {
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date1 = sdf.parse(entry.getValue().getDateStart());
			Date date2 = sdf.parse(entry.getValue().getDateFin());
			long diff = date2.getTime() - date1.getTime();
			entry.getValue().setDuration(diff / 1000);
		}

		return events;
	}

	// Resource to task behaviour
	public Map<Integer, ArrayList<Event>> resourceToTask(Map<String, Event> events, String resource, String task,
			Vector<Vector<String>> vect) throws ParseException {
		ArrayList<Event> listevnt = new ArrayList<>();
		ArrayList<Event> listevt = new ArrayList<>();
		Map<Integer, ArrayList<Event>> mapevents = new HashMap<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().resource.equals(resource) && entry.getValue().eventName.equals(task)) {
				listevnt.add(entry.getValue());
			}
		}
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().resource.equals(resource)) {
				listevt.add(entry.getValue());
			}
		}
		for (int i = 0; i < vect.size(); i++) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date1 = sdf1.parse(vect.get(i).elementAt(0));
			Date date2 = sdf1.parse(vect.get(i).elementAt(1));
			ArrayList<Event> e = new ArrayList<Event>();
			for (int j = 0; j < listevnt.size(); j++) {
				Date date11 = sdf.parse(listevnt.get(j).dateStart);
				Date date12 = sdf.parse(listevnt.get(j).dateFin);
				if (date11.getTime() >= date1.getTime() && date12.getTime() <= date2.getTime()) {
					e.add(listevnt.get(j));
				}
			}
			mapevents.put(i, e);
		}
		return mapevents;
	}

	//CreateDataset resoutce to task
	public XYSeriesCollection createDatasetresourcetoTask(Map<Integer, ArrayList<Event>> data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Event duration per time slot");
		for (Map.Entry<Integer, ArrayList<Event>> entry : data.entrySet()) {
			for (int i = 0; i < entry.getValue().size(); i++) {
				int slot = entry.getKey();
				int duration = (int) entry.getValue().get(i).duration;
				series1.add(slot, duration);

			}
		}
		dataset.addSeries(series1);
		return dataset;
	}

	public XYSeriesCollection createDatasetresourcetoTask1(Map<Integer, ArrayList<Event>> data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Event duration per time slot");
		for (Map.Entry<Integer, ArrayList<Event>> entry : data.entrySet()) {
			int slot = entry.getKey();
			int nbr = (int) entry.getValue().size();
			series1.add(slot, nbr);

		}
		dataset.addSeries(series1);
		return dataset;
	}

	//Pie dataset % of executing the task per the resource
	public PieDataset createPieDatasetrestotask(Map<String, Event> events, String resource, String task) {
		ArrayList<Event> listevnt = new ArrayList<>();
		ArrayList<Event> listevt = new ArrayList<>();
		Map<Integer, ArrayList<Event>> mapevents = new HashMap<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().resource.equals(resource) && entry.getValue().eventName.equals(task)) {
				listevnt.add(entry.getValue());
			}
		}
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().resource.equals(resource)) {
				listevt.add(entry.getValue());
			}
		}
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (listevt.size() != 0) {
			double rate1 = listevnt.size() * 100.0 / listevt.size();
			double rate2 = (listevt.size() - listevnt.size()) * 100 / listevt.size();
			dataset.setValue(task, rate1);
			dataset.setValue("OtherTasks executed by " + resource, rate2);
		}
		return dataset;
	}

	//MIN mean MAX durations resource to Task 
	public Vector<Long> restaskdurations(Map<String, Event> events, String resource, String task) {
		Vector<Long> durations = new Vector<>();
		ArrayList<Event> listevnt = new ArrayList<>();
		long minduration = 100000000;
		long maxduration = 0;
		long meanduration = 0;
		long total = 0;

		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().resource.equals(resource) && entry.getValue().eventName.equals(task)) {
				listevnt.add(entry.getValue());
			}
		}
		for (int i = 0; i < listevnt.size(); i++) {
			if (listevnt.get(i).duration < minduration) {
				minduration = listevnt.get(i).duration;
			}
			if (listevnt.get(i).duration > maxduration) {
				maxduration = listevnt.get(i).duration;
			}
			total = total + listevnt.get(i).duration;
		}
		if (listevnt.size() != 0) {
			meanduration = total / listevnt.size();
		}
		durations.addElement(minduration);
		durations.addElement(maxduration);
		durations.addElement(meanduration);
		return durations;
	}

	//Resource to All tasks behaviour: Processing speed VS lengh of the queue at a given Time 
	public Map<Event, Integer> processingSpeed(Map<String, Event> events, String resource) throws ParseException {
		ArrayList<Event> listevnt = new ArrayList<>();
		Map<Event, Integer> res = new HashMap<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().resource.equals(resource)) {
				listevnt.add(entry.getValue());
			}
		}
		for (int i = 0; i < listevnt.size(); i++) {
			int k = 0;
			for (int j = 0; j < listevnt.size(); j++) {
				if (!listevnt.get(i).equals(listevnt.get(j))) {
					//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					Date dateSchedule = sdf.parse(listevnt.get(j).dateSchedule);
					Date dateStart = sdf.parse(listevnt.get(j).dateStart);
					Date dateComplete = sdf.parse(listevnt.get(i).dateFin);
					if (dateSchedule.getTime() <= dateComplete.getTime()
							&& dateStart.getTime() >= dateComplete.getTime()) {
						k = k + 1;
					}
				}
			}
			res.put(listevnt.get(i), k);
		}
		
		return res;

	}

	public ArrayList<XYDataset> processingdataset(Map<Event, Integer> res) throws ParseException {
		Map<String, ArrayList<Event>> list = new HashMap<>();
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		ArrayList<XYDataset> listdataset = new ArrayList<>();
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		for (Map.Entry<Event, Integer> entry : res.entrySet()) {
			if (list.containsKey(entry.getKey().eventName)) {
				list.get(entry.getKey().eventName).add(entry.getKey());
			} else {
				ArrayList<Event> eventlist = new ArrayList<>();
				eventlist.add(entry.getKey());
				list.put(entry.getKey().eventName, eventlist);
			}
		}
		//create the dataset
		for (Map.Entry<String, ArrayList<Event>> entry : list.entrySet()) {
			TimeSeries series = new TimeSeries(entry.getKey());
			for (int i = 0; i < entry.getValue().size(); i++) {
				//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date datefin = sdf.parse(entry.getValue().get(i).dateFin);
				//RegularTimePeriod period= new Day(datefin)+new Second(datefin) ;
				series.add(new Second(datefin), entry.getValue().get(i).duration);
			}
			dataset.addSeries(series);
		}
		// second dataset
		TimeSeries series = new TimeSeries(" Queue Length ");
		for (Map.Entry<Event, Integer> entry : res.entrySet()) {
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date datefin = sdf.parse(entry.getKey().dateFin);
			series.add(new Second(datefin), entry.getValue());
		}
		dataset1.addSeries(series);
		listdataset.add(dataset);
		listdataset.add(dataset1);
		return listdataset;

	}

	// Orgonise Data for One Resource All Tasks

	// All resources to one task 
	public Map<String, Double> AllresourcetoTask(Map<String, Event> events, String taskname, Vector<String> resources) {
		ArrayList<Event> listevnt = new ArrayList<>();
		Map<String, Double> res = new HashMap<>();
		Map<String, Double> res1 = new HashMap<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().eventName.equals(taskname)) {
				listevnt.add(entry.getValue());
			}
		}
		for (int i = 0; i < resources.size(); i++) {
			double k = 0;
			for (int j = 0; j < listevnt.size(); j++) {
				if (listevnt.get(j).resource.equals(resources.elementAt(i))) {
					k = k + 1;
				}
			}
			res.put(resources.elementAt(i), k * 100 / listevnt.size());
		}
		for (Map.Entry<String, Double> entry : res.entrySet()) {
			if (entry.getValue() != 0) {
				res1.put(entry.getKey(), entry.getValue());
			}
		}
		return res1;
	}

	//Number of resources who executed a given Task
	public double resexectask(Map<String, Double> map, Vector<String> resources) {
		double k = 0;
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			if (entry.getValue() != 0) {
				k = k + 1;
			}
		}
		k = k * 100 / resources.size();
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(k));
	}

	//
	public Map<String, Map<Integer, ArrayList<Event>>> AllresourcetoTaskdur(Map<String, Event> events, String taskname,
			Vector<String> resources, Vector<Vector<String>> vect) throws ParseException {
		Map<String, Map<Integer, ArrayList<Event>>> eventslist = new HashMap<>();
		ArrayList<Event> listevnt = new ArrayList<>();
		for (Map.Entry<String, Event> entry : events.entrySet()) {
			if (entry.getValue().eventName.equals(taskname)) {
				listevnt.add(entry.getValue());
			}
		}
		for (int i = 0; i < resources.size(); i++) {
			Map<Integer, ArrayList<Event>> map = new HashMap<>();
			for (int j = 0; j < vect.size(); j++) {
				//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				SimpleDateFormat sdf1 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date date1 = sdf1.parse(vect.get(j).elementAt(0));
				Date date2 = sdf1.parse(vect.get(j).elementAt(1));
				ArrayList<Event> event = new ArrayList<>();
				for (int k = 0; k < listevnt.size(); k++) {
					if (listevnt.get(k).resource.equals(resources.elementAt(i))) {
						Date date11 = sdf.parse(listevnt.get(k).dateStart);
						Date date22 = sdf.parse(listevnt.get(k).dateFin);
						if (date11.getTime() >= date1.getTime() && date22.getTime() <= date2.getTime()) {
							event.add(listevnt.get(k));
						}

					}
				}
				if (event.size() == 0) {
					//System.out.println("Do not Add");
				} else {
					map.put(j, event);
				}

			}
			if (map.size() == 0) {
				//System.out.println("Do not Add");
			} else {
				eventslist.put(resources.elementAt(i), map);
			}

		}
		System.out.println(eventslist.size());

		return eventslist;
	}

	public XYSeriesCollection createDatasetAllrestoTask(Map<String, Map<Integer, ArrayList<Event>>> list) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (Map.Entry<String, Map<Integer, ArrayList<Event>>> entry : list.entrySet()) {
			if (entry.getValue().size() != 0) {
				XYSeries serie1 = new XYSeries(entry.getKey());
				for (Map.Entry<Integer, ArrayList<Event>> entry1 : entry.getValue().entrySet()) {
					for (int i = 0; i < entry1.getValue().size(); i++) {
						serie1.add(1.0 * entry1.getKey(), 1.0 * entry1.getValue().get(i).duration);
					}
				}
				dataset.addSeries(serie1);
			}

		}
		return dataset;
	}

	public Map<String, Long> allresourceTotaskMeanDuration(Map<String, Map<Integer, ArrayList<Event>>> list) {

		Map<String, Long> res = new HashMap<>();
		for (Map.Entry<String, Map<Integer, ArrayList<Event>>> entry : list.entrySet()) {
			long meanduration = 0;
			int k = 0;
			for (Map.Entry<Integer, ArrayList<Event>> entry1 : entry.getValue().entrySet()) {
				k = k + entry1.getValue().size();
				for (int j = 0; j < entry1.getValue().size(); j++) {
					meanduration = meanduration + entry1.getValue().get(j).duration;
				}

			}
			meanduration = meanduration / k;
			res.put(entry.getKey(), meanduration);
		}
		return res;
	}

	public CategoryDataset barsetAllrestotaskDura(Map<String, Long> res) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Map.Entry<String, Long> entry : res.entrySet()) {
			dataset.addValue(entry.getValue(), "resources", entry.getKey());
		}
		return dataset;
	}
	
	//The cases per day
	
}