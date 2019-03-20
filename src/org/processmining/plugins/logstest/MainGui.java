package org.processmining.plugins.logstest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.deckfour.xes.model.XLog;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import com.fluxicon.slickerbox.components.SlickerButton;

public class MainGui {
	public JPanel panel;
	//public JDialog dialog;
	public Vector<String> nresource = new Vector<>();

	public JPanel displayMainGui(final Connection con, final XLog log, final String host, final String user,
			final String pass, final String db) {
		//Define all the Swing components 
		panel = new JPanel();
		final SlickerButton jButton1 = new SlickerButton();
		final SlickerButton jButton2 = new SlickerButton();
		final SlickerButton jButton3 = new SlickerButton();
		final SlickerButton jButton4 = new SlickerButton();
		JLabel jLabel1 = new JLabel();
		JLabel jLabel2 = new JLabel();
		JLabel jLabel3 = new JLabel();
		JLabel jLabel4 = new JLabel();
		JLabel jLabel5 = new JLabel();

		//Set the proprieties of the components

		panel.setBackground(new java.awt.Color(204, 204, 204));

		jLabel1.setText("<html><h1>Extract, Detect, Analyze Resource Behaviour</h1></html>");
		jLabel2.setForeground(new java.awt.Color(0, 51, 255));
		jLabel2.setText("<html><h1>Import Log file Into Database</h1></html>");
		jButton1.setText("Import LogToDB");
		jLabel3.setForeground(new java.awt.Color(0, 51, 255));
		jLabel3.setText("<html><h1>Check The availability of Resources</h1></html>");
		jLabel4.setForeground(new java.awt.Color(0, 51, 255));
		jLabel4.setText("<html><h1>Queuing behaviour of Resources</h1></html>");
		jButton3.setText("Pereferences Analysis");
		jLabel5.setText("<html><h1>Skills of the Resources</h1></html>");
		jLabel5.setForeground(new java.awt.Color(0, 51, 255));
		jButton4.setText("Skills Analysis");

		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					ProcessLogData procLog = new ProcessLogData();
					InputParam ip = new InputParam();
					ip.setDb(host, user, pass, db);
					System.out.println("database Param: " + host + " " + user + " " + pass + "  " + db);
					long start = System.nanoTime();
					System.out.println("start: " + start);
					procLog.createDb(con, log, ip);
					long end = System.nanoTime();
					long dur = (end - start) / 1000000000;
					System.out.println("dur: " + dur);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				;
			}

		});

		jButton2.setText("Availability Analysis");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ProcessLogData procLog = new ProcessLogData();
				TimeLog time = new TimeLog();
				ChartVisualizer visulize = new ChartVisualizer();
				DefineTimeLog timep = new DefineTimeLog();
				Vector<String> resource = new Vector<String>();
				ArrayList<Long> data = new ArrayList<Long>();
				ArrayList<Double> freedata = new ArrayList<Double>();
				ArrayList<Double> avaidata = new ArrayList<Double>();
				ArrayList<Integer> instructionperday = new ArrayList<Integer>();
				AvailabilityStructure availabilitystru = new AvailabilityStructure();
				XYSeriesCollection dataset = new XYSeriesCollection();
				XYSeriesCollection dataset1 = new XYSeriesCollection();
				DefaultPieDataset dataset2 = new DefaultPieDataset();
				XYSeriesCollection dataset3 = new XYSeriesCollection();
				Map<String, Event> events = new HashMap<String, Event>();
				ArrayList<Double> batchlist = new ArrayList<>();
				ArrayList<Double> nonbatchlist = new ArrayList<>();
				ArrayList<Double> meanav = new ArrayList<>();
				ArrayList<Integer> workingdays = new ArrayList<>();
				ArrayList<Integer> workingbehavdays = new ArrayList<>();
				ArrayList<Integer> totinstruction = new ArrayList<>();
				ArrayList<Double> avaiwork = new ArrayList<>();
				ArrayList<Double> totavaiwork = new ArrayList<>();
				DefaultPieDataset dataset5 = new DefaultPieDataset();
				XYSeriesCollection dataset4 = new XYSeriesCollection();

				double batch = 0;
				double nonbatch = 0;
				try {
					events = procLog.getallevents2(con);
					resource = procLog.getResource(con);
					time.timeInterval = procLog.getStartEndDate(events);
					//System.out.println(time.timeInterval.elementAt(0) + "     " + time.timeInterval.elementAt(1));
					time = timep.getTimeInterval(time.timeInterval, resource);

				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (time.resource.equals("All")) {
					//System.out.println("Start availability for all resources " + time.vect.size());

					for (int i = 0; i < resource.size(); i++) {
						if (time.slot.equals("Day")) {

							try {
								availabilitystru = procLog.availability(con, resource.get(i), time.vect, events);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (time.slot.equals("Weeks")) {
							try {
								availabilitystru = procLog.weekAvaialbility(con, resource.get(i), time.vect, events);
							} catch (ParseException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (availabilitystru.numberofworkfloaterval.size() == 0) {
							System.out.println("The resource"+resource.get(i)+" does not have enough working time to be judged!! ");
						} else {

							nresource.addElement(resource.get(i));
							batch = availabilitystru.getBatchRate();
							nonbatch = availabilitystru.getNonBatchRate();
							avaidata = availabilitystru.getAvailabilityRate();
							batchlist.add(batch);
							nonbatchlist.add(nonbatch);
							workingdays.add(availabilitystru.getWorkingdays());
							workingbehavdays.add(availabilitystru.getWorkdaysBehav());
							totinstruction.add(availabilitystru.getInstructionNb());
							meanav.add(availabilitystru.meanAvailability);
							int nb = 24 * availabilitystru.workingdays;
							int nb1 = 24 * availabilitystru.getTotperiod();
							avaiwork.add(availabilitystru.getMeanAvailability() * 100 / nb);
							totavaiwork.add(availabilitystru.getMeanAvailability() * 100 / nb1);
						}

					}

					procLog.writeResults("workingOrganization.csv", nresource, batchlist, nonbatchlist, meanav, workingdays,
							workingbehavdays, totinstruction, avaiwork, totavaiwork);
					Vector<ChartPanel> chartqueue = new Vector<>();
					ArrayList<ClustredObj> datacluster = procLog.kmeans2vars(nresource, 6, "Results.csv");
					dataset4 = procLog.createclusteDataset(datacluster);
					ChartPanel chartcluster = visulize.panelclusterbatch(dataset4, "Batch Vs non Batch behaviour");
					chartqueue.add(chartcluster);
					dataset5 = (DefaultPieDataset) procLog.createqueuepie(datacluster, nresource.size());
					ChartPanel chartquebeh = visulize.createPanelPie(dataset5, "Rate of Resources Per Cluster");
					chartqueue.add(chartquebeh);
					visulize.clusterQueue(chartqueue, datacluster, nresource,
							"Batch VS non Batch behaviour: Clustering", "Availability");
					System.out.println("End availability for all resources");
					// Write in the CSV file
					try {
						Map<Integer, Vector<Case>> c=procLog.cases(con, time.vect);
						procLog.casecsv("caseinfo.csv",c);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					// Just one given Resource
					try {
						System.out.println("Start availability for one resource" + time.vect.size());
						if (time.slot.equals("Day")) {
							availabilitystru = procLog.availability(con, time.resource, time.vect, events);
						} else if (time.slot.equals("Week")) {
							availabilitystru = procLog.weekAvaialbility(con, time.resource, time.vect, events);
						}

						data = availabilitystru.getTotalDuration();
						instructionperday = availabilitystru.getInstructionday();
						freedata = availabilitystru.getNumberofworkfloaterval();
						batch = availabilitystru.getBatchRate();
						nonbatch = availabilitystru.getNonBatchRate();
						avaidata = availabilitystru.getAvailabilityRate();
						procLog.resourcecaracteristics(time.resource+".csv", avaidata, data,instructionperday);
						System.out.println("End availability " + time.resource);
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Vector<ChartPanel> chart = new Vector<>();
					dataset = procLog.createDataset(data);
					chart.add(visulize.createPanel(dataset,
							"Total working hours per " + time.slot + " For the resource " + time.resource));
					dataset1 = procLog.createDatasetfree(freedata, "Breaks per time slot");
					chart.add(visulize.createPanel1(dataset1,
							"Breaks per time slot per " + time.slot + " For the resource " + time.resource));
					Map<String, Double> map = new HashMap<>();
					map.put("batch "+batch, batch);
					map.put("no-batch "+nonbatch, nonbatch);
					dataset2 = (DefaultPieDataset) procLog.createPieDataset(map);
					chart.add(visulize.createPanelPie(dataset2, "Batch and No-batch behaviour For the resource " + time.resource));
					dataset3 = procLog.createDatasetfree(avaidata, "Avaialiby Rate per " + time.slot);
					chart.add(visulize.createPanelavai(dataset3,
							"Availability Rates per " + time.slot + " For the resource " + time.resource));
					visulize.charts(chart, "Availability and Work organization Behaviour", availabilitystru);
				}

			}
		});

		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Working on Preference !");
				ProcessLogData procLog = new ProcessLogData();
				TimeLog time = new TimeLog();
				DefineTimeLog timep = new DefineTimeLog();
				Vector<String> resource = new Vector<String>();
				Vector<Integer> perf = new Vector<>();
				ChartVisualizer visulize = new ChartVisualizer();
				DefaultPieDataset dataset2 = new DefaultPieDataset();
				DefaultPieDataset dataset3 = new DefaultPieDataset();
				XYSeriesCollection dataset1 = new XYSeriesCollection();
				DefaultPieDataset dataset5 = new DefaultPieDataset();
				XYSeriesCollection dataset4 = new XYSeriesCollection();
				ArrayList<Map<String, Integer>> listtasks = new ArrayList<>();
				Map<String, Event> allevents = new HashMap<String, Event>();
				try {

					allevents = procLog.getallevents2(con);
					resource = procLog.getResource(con);
					time.timeInterval = procLog.getStartEndDate(allevents);
					time = timep.getTimeInterval(time.timeInterval, resource);

				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double fifo = 0.0;
				double lifo = 0.0;
				double random = 0.0;
				ArrayList<Double> fifolist = new ArrayList<>();
				ArrayList<Double> lifolist = new ArrayList<>();
				ArrayList<Double> randomlist = new ArrayList<>();
				Vector<String> nnresource = new Vector<>();
				if (time.resource.equals("All")) {
					for (int i = 0; i < resource.size(); i++) {
						try {
							perf = procLog.processPreference(con, resource.get(i), time.vect, allevents);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						DecimalFormat df = new DecimalFormat("#.##");
						//System.out.println(
								//"For the resource " + nresource.get(i) + "  executed tasks" + perf.elementAt(2));
						if (perf.elementAt(2) > 1) {
							fifo = perf.elementAt(3) * 100.0 / perf.elementAt(2);
							lifo = perf.elementAt(4) * 100.0 / perf.elementAt(2);
							random = perf.elementAt(5) * 100.0 / perf.elementAt(2);
							fifolist.add(Double.parseDouble(df.format(fifo)));
							lifolist.add(Double.parseDouble(df.format(lifo)));
							randomlist.add(Double.parseDouble(df.format(random)));
							nnresource.add(resource.get(i));
						}
					}
					//System.out.println(nresource.size() + " " + fifolist.size());
					procLog.writeResultperf("Resultperf.csv", nnresource, fifolist, lifolist, randomlist);
					Vector<ChartPanel> chartqueue = new Vector<>();
					ArrayList<ClustredObj> datacluster = procLog.kmeans(nnresource, 4, "prefrences.csv");
					dataset4 = procLog.createclusteDataset(datacluster);
					ChartPanel chartcluster = visulize.panelcluster(dataset4,
							"Clusters of the Resource for LIFO, FIFO and Random");
					chartqueue.add(chartcluster);
					dataset5 = (DefaultPieDataset) procLog.createqueuepie(datacluster, nnresource.size());
					ChartPanel chartquebeh = visulize.createPanelPie(dataset5, "Rate of Resources Per Cluster");
					chartqueue.add(chartquebeh);
					visulize.clusterQueue(chartqueue, datacluster, nnresource,
							"Queuing Behaviour For All the Resources", "Queue");

				} else {
						
							try {
								perf = procLog.processPreference(con, time.resource, time.vect, allevents);
							} catch (SQLException | ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
					
					DecimalFormat df = new DecimalFormat("#.##");
					fifo = perf.elementAt(3) * 100.0 / perf.elementAt(2);
					lifo = perf.elementAt(4) * 100.0 / perf.elementAt(2);
					random = perf.elementAt(5) * 100.0 / perf.elementAt(2);
					fifolist.add(Double.parseDouble(df.format(fifo)));
					lifolist.add(Double.parseDouble(df.format(lifo)));
					randomlist.add(Double.parseDouble(df.format(random)));
					Map<String, Double> map = new HashMap<>();
					map.put("FIFO", fifo);
					map.put("LIFO", lifo);
					map.put("Random", random);
					Vector<ChartPanel> chartvect = new Vector<>();
					dataset2 = (DefaultPieDataset) procLog.createPieDataset(map);
					try {
						listtasks = procLog.multiTasking(con, time.resource, time.vect, allevents);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dataset1 = procLog.datasettasks(listtasks);
					dataset3 = (DefaultPieDataset) procLog.createPieDataset1(listtasks);
					ChartPanel chart = visulize.createPanelPie(dataset2,
							"Queuing BEHAVIOUR  " + " For the resource " + time.resource);
					ChartPanel chart1 = visulize.createPanel3(dataset1,
							"Number of Diffrent performed Tasks  " + " For the resource " + time.resource + " per " + time.slot);
					ChartPanel chart2 = visulize.createPanelPie(dataset3,
							"Rate of Task types Execution " + " For the resource " + time.resource);
					chartvect.addElement(chart);
					chartvect.addElement(chart1);
					chartvect.addElement(chart2);
					visulize.chartsprefrence(time.resource, perf, chartvect, "Preference");

				}

			}
		});
		jButton4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Skills Analysis !");
				ProcessLogData procLog = new ProcessLogData();
				Map<String, Event> events = new HashMap<String, Event>();
				Map<String, Event> eventslist = new HashMap<String, Event>();
				Vector<ChartPanel> charts = new Vector<>();
				XYSeriesCollection dataset1 = new XYSeriesCollection();
				XYSeriesCollection dataset2 = new XYSeriesCollection();
				DefaultPieDataset dataset3 = new DefaultPieDataset();
				DefaultPieDataset dataset4 = new DefaultPieDataset();
				TimeLog time = new TimeLog();
				ChartVisualizer visulize = new ChartVisualizer();
				DefineTimeLog timep = new DefineTimeLog();
				Vector<String> resource = new Vector<String>();
				Map<Integer, ArrayList<Event>> eventres = new HashMap<Integer, ArrayList<Event>>();
				ArrayList<String> listofevents = new ArrayList<String>();
				Vector<Integer> perf = new Vector<>();
				Map<Event, Integer> processlist = new HashMap<>();
				ArrayList<XYDataset> listdataset = new ArrayList<>();
				ArrayList<Map<String, Integer>> listtasks = new ArrayList<>();
				try {
					events = procLog.getallevents2(con);

					resource = procLog.getResource(con);
					time.timeInterval = procLog.getStartEndDate(events);
					listofevents = procLog.eventNameslist(events);
					time = timep.getSkills(time.timeInterval, resource, listofevents);
					eventslist = procLog.durations(events);

					if (!time.resource.equals("All") && !time.taskName.equals("All")) {

						procLog.resourcetaskTime(listofevents, resource, eventslist);

						eventres = procLog.resourceToTask(eventslist, time.resource, time.taskName, time.vect);
						dataset1 = procLog.createDatasetresourcetoTask(eventres);
						dataset2 = procLog.createDatasetresourcetoTask1(eventres);
						dataset3 = (DefaultPieDataset) procLog.createPieDatasetrestotask(events, time.resource,
								time.taskName);
						ChartPanel chart = visulize.resourceToTask(dataset1, dataset2,
								"Durations and number of times of executing the task  " + time.taskName
										+ " by the resource " + time.resource);
						ChartPanel chart1 = visulize.createPanelPie(dataset3, "Rate of excuting the Task ( "
								+ time.taskName + " )" + " by the Resource " + time.resource);
						charts.addElement(chart);
						charts.addElement(chart1);
						Vector<Long> durinfo = new Vector<>();
						durinfo = procLog.restaskdurations(eventslist, time.resource, time.taskName);
						visulize.resourceToTask(time.resource, time.taskName, charts, durinfo);
					}
					if (!time.resource.equals("All") && time.taskName.equals("All")) {

						perf = procLog.processPreference(con, time.resource, time.vect, events);
						listtasks = procLog.multiTasking(con, time.resource, time.vect, events);
						Vector<ChartPanel> chartspanel = new Vector<>();
						dataset4 = (DefaultPieDataset) procLog.createPieDataset1(listtasks);
						ChartPanel chart1 = visulize.createPanelPie(dataset4,
								"Rate of Task Execution " + " For the resource " + time.resource);
						chartspanel.add(chart1);
						procLog.processingSpeed(events, time.resource);
						processlist = procLog.processingSpeed(eventslist, time.resource);
						listdataset = procLog.processingdataset(processlist);
						ChartPanel chart2 = visulize.processvsQueue(
								"Execution duration of each event vs number of scheduled events For the resource "
										+ time.resource,
								listdataset);
						chartspanel.add(chart2);
						visulize.resourceAllTasks(time.resource, perf, chartspanel);

					}
					if (time.resource.equals("All") && !time.taskName.equals("All")) {
						DefaultPieDataset dataset11 = new DefaultPieDataset();
						DefaultCategoryDataset dataset22 = new DefaultCategoryDataset();
						XYSeriesCollection listdata = new XYSeriesCollection();
						Vector<ChartPanel> chartpan = new Vector<>();
						//System.out.println("W wre here ");
						Map<String, Double> map = procLog.AllresourcetoTask(eventslist, time.taskName, resource);
						double k = procLog.resexectask(map, resource);
						dataset11 = (DefaultPieDataset) procLog.createPieDataset(map);
						ChartPanel chart = visulize.createPanelPie(dataset11,
								"Rate of each resource executing " + time.taskName);
						chartpan.add(chart);
						Map<String, Map<Integer, ArrayList<Event>>> listofall = procLog.AllresourcetoTaskdur(eventslist,
								time.taskName, resource, time.vect);
						listdata = procLog.createDatasetAllrestoTask(listofall);
						ChartPanel chartt = visulize.allresourcetask("", listdata);
						chartpan.add(chartt);
						Map<String, Long> res = procLog.allresourceTotaskMeanDuration(listofall);
						dataset22 = (DefaultCategoryDataset) procLog.barsetAllrestotaskDura(res);
						ChartPanel chartp = visulize.createbar(dataset22, "");
						chartpan.add(chartp);
						visulize.aLLresourcesToTask(time.taskName, k, chartpan);
						procLog.AllResourceToTaskCSV(time.taskName, resource, map, res);
					}

				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//procLog.skillsAnalysis(events);

			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
		panel.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(195, 195, 195).addComponent(jLabel1))
								.addGroup(layout.createSequentialGroup().addGap(22, 22, 22)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jButton1).addComponent(jLabel2).addComponent(jLabel3)
												.addComponent(jButton2).addComponent(jLabel4).addComponent(jButton3)
												.addComponent(jLabel5).addComponent(jButton4))))
						.addContainerGap(207, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(39, 39, 39)
						.addComponent(jLabel2).addGap(18, 18, 18).addComponent(jButton1).addGap(18, 18, 18)
						.addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jButton2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jButton3).addGap(18, 18, 18).addComponent(jLabel5)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton4)
						.addContainerGap(82, Short.MAX_VALUE)));

		panel.setVisible(true);
		return panel;
	}

}
