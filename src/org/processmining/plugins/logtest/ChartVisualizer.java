package org.processmining.plugins.logtest;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartVisualizer {
	public JDialog chartsprefrence(String resource, Vector<Integer> vect, Vector<ChartPanel> chartPanel, String title) {
		JDialog jdialog = new JDialog();
		JLabel jLabel1 = new JLabel();
		JLabel jLabel2 = new JLabel();
		JLabel jLabel3 = new JLabel();
		JLabel jLabel4 = new JLabel();
		JLabel jLabel5 = new JLabel();
		JLabel jLabel6 = new JLabel();
		JLabel jLabel9 = new JLabel();
		JLabel jLabel10 = new JLabel();
		JLabel jLabel11 = new JLabel();
		JLabel jLabel12 = new JLabel();
		JLabel jLabel13 = new JLabel();
		JLabel jLabel14 = new JLabel();
		JLabel jLabel15 = new JLabel();
		JPanel jPanel1 = new JPanel();
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JLabel jLabel7 = new javax.swing.JLabel();
		JTextField jTextField7 = new javax.swing.JTextField();
		JLabel jLabel8 = new javax.swing.JLabel();
		JTextField jTextField8 = new javax.swing.JTextField();
		JTextField jTextField1 = new JTextField();
		JTextField jTextField2 = new JTextField();
		JTextField jTextField3 = new JTextField();
		JTextField jTextField4 = new JTextField();
		JTextField jTextField5 = new JTextField();
		JTextField jTextField6 = new JTextField();
		jdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		jLabel1.setText("Queue Behaviour for the Resource");

		jTextField1.setText(resource);

		jLabel2.setText("Number of Total Days in the Log: ");

		jLabel9.setText(vect.elementAt(0).toString());

		jLabel3.setText("Number of working Days of resource : "+resource);

		jLabel10.setText(vect.elementAt(1).toString());

		jLabel4.setText("Total Executed tasks:");

		jLabel11.setText(vect.elementAt(2).toString());

		jLabel5.setText("Number of tasks executed in FIFO:");

		jLabel12.setText(vect.elementAt(3).toString());

		jLabel6.setText("Number of tasks executed in LIFO:");

		jLabel13.setText(vect.elementAt(4).toString());

		jLabel7.setText("Number of tasks executed in Random:");

		jLabel14.setText(vect.elementAt(5).toString());

		jLabel8.setText("Number of tasks not conclusive:");

		jLabel15.setText(vect.elementAt(6).toString());

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 404, Short.MAX_VALUE).addComponent(chartPanel.elementAt(0)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 207, Short.MAX_VALUE).addComponent(chartPanel.elementAt(0)));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 439, Short.MAX_VALUE).addComponent(chartPanel.elementAt(1)));

		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 258, Short.MAX_VALUE).addComponent(chartPanel.elementAt(1)));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 414, Short.MAX_VALUE).addComponent(chartPanel.elementAt(2)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 243, Short.MAX_VALUE).addComponent(chartPanel.elementAt(2)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jdialog.getContentPane());
		jdialog.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(157, 157, 157).addComponent(jLabel1)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(32, 32, 32).addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4)
												.addComponent(jLabel5).addComponent(jLabel6).addComponent(jLabel7)
												.addComponent(jLabel8)).addGap(41, 41, 41)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel15).addComponent(jLabel14)
														.addComponent(jLabel13).addComponent(jLabel12)
														.addComponent(jLabel11).addComponent(jLabel10)
														.addComponent(jLabel9)))
										.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(41, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(21, 21, 21)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup().addGap(9, 9, 9)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel2).addComponent(jLabel9))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel3).addComponent(jLabel10))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel4).addComponent(jLabel11))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel5).addComponent(jLabel12))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel6).addComponent(jLabel13))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel7).addComponent(jLabel14))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel8).addComponent(jLabel15))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));

		jdialog.pack();
		jdialog.setSize(700, 700);
		jdialog.setLocation(100, 100);
		jdialog.setLocationRelativeTo(null);
		jdialog.setVisible(true);
		return jdialog;
	}

	public JDialog histo(ChartPanel chart) {

		JDialog jd = new JDialog();
		JPanel jPanel1 = new JPanel();
		JLabel jLabel1 = new JLabel();

		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(chart));

		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 326, Short.MAX_VALUE).addComponent(chart));

		jLabel1.setText("All resources Behaviour ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jd.getContentPane());
		jd.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap()
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
				.addGroup(layout.createSequentialGroup().addGap(365, 365, 365).addComponent(jLabel1)
						.addContainerGap(381, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(34, 34, 34)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(141, Short.MAX_VALUE)));

		jd.pack();
		jd.setSize(1000, 1000);
		jd.setLocation(100, 100);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);

		return jd;
	}

	public JDialog clusterchart(ChartPanel chart) {
		JDialog jd = new JDialog();
		JPanel jPanel1 = new JPanel();

		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 603, Short.MAX_VALUE).addComponent(chart));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 361, Short.MAX_VALUE).addComponent(chart));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jd.getContentPane());
		jd.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(67, 67, 67)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(208, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(28, 28, 28)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(37, Short.MAX_VALUE)));

		jd.pack();
		jd.setSize(1000, 1000);
		jd.setLocation(100, 100);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);

		return jd;
	}

	/*
	 * Function for clustring The resources based on their Queuing Behaviour
	 * (FIFO, LIFO, Random(With given priorities))
	 */
	public JDialog clusterQueue(Vector<ChartPanel> chart, ArrayList<ClustredObj> list, Vector<String> resource,
			String title, String type) {
		JDialog jd = new JDialog();
		JPanel jPanel1 = new JPanel();
		JLabel jLabel1 = new javax.swing.JLabel();
		JPanel jPanel2 = new javax.swing.JPanel();
		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTextArea jTextArea1 = new javax.swing.JTextArea();
		JLabel jLabel2 = new javax.swing.JLabel();
		String newLine = "\n";

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 401, Short.MAX_VALUE).addComponent(chart.elementAt(0)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 245, Short.MAX_VALUE).addComponent(chart.elementAt(0)));

		jLabel1.setText("<html><h1>" + title + "</html></h1>");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 420, Short.MAX_VALUE).addComponent(chart.elementAt(1)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(chart.elementAt(1)));

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);
		jTextArea1.setEditable(false);
		if (type.equals("Availability")) {
			jTextArea1.append("The total Number of resources is : " + resource.size());
			jTextArea1.append(newLine);
			for (int i = 0; i < list.size(); i++) {
				jTextArea1.append(list.get(i).clusterName + " Contains " + list.get(i).datalist.size());
				jTextArea1.append("==> Centroid (" + list.get(i).c.X() + " , " + list.get(i).c.Y()+ " , " + list.get(i).c.Z() + " )");
				jTextArea1.append(newLine);
				if (list.get(i).datalist.size() != 0) {
					for (int j = 0; j < list.get(i).datalist.size(); j++) {
						jTextArea1.append(list.get(i).datalist.get(j).Name());
						jTextArea1.append(" , ");
					}
				} else {
					jTextArea1.append("Empty Cluster");
				}
				jTextArea1.append(newLine);
				jTextArea1.append(newLine);

			}
		} else {
			jTextArea1.append("The total Number of resources is : " + resource.size());
			jTextArea1.append(newLine);
			for (int i = 0; i < list.size(); i++) {
				jTextArea1.append(list.get(i).clusterName + " Contains " + list.get(i).datalist.size());
				jTextArea1.append("==> Centroid (" + list.get(i).c.X() + " , " + list.get(i).c.Y() + " , "
						+ list.get(i).c.Z() + " ) :");
				jTextArea1.append(newLine);
				if (list.get(i).datalist.size() != 0) {
					for (int j = 0; j < list.get(i).datalist.size(); j++) {
						jTextArea1.append(list.get(i).datalist.get(j).Name());
						jTextArea1.append(" , ");
					}
				} else {
					jTextArea1.append("Empty Cluster");
				}
				jTextArea1.append(newLine);
				jTextArea1.append(newLine);

			}
		}
		jLabel2.setText("Clusters Compositions");
		jLabel2.setForeground(new java.awt.Color(0, 51, 255));
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jd.getContentPane());
		jd.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 843,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
								.createSequentialGroup().addContainerGap()
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGap(305, 305, 305).addComponent(jLabel1))))
						.addGroup(layout.createSequentialGroup().addGap(343, 343, 343).addComponent(jLabel2,
								javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(42, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(26, 26, 26).addComponent(jLabel1).addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
						.addGap(12, 12, 12)));
		jd.pack();
		//jd.setSize(1000, 1000);
		jd.setLocation(100, 100);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);
		return jd;
	}

	public JDialog aLLresourcesToTask(String taskname, double rate, Vector<ChartPanel> charts) {
		JDialog jd = new JDialog();

		JLabel jLabel2 = new javax.swing.JLabel();
		JLabel jLabel1 = new javax.swing.JLabel();
		JLabel jLabel3 = new javax.swing.JLabel();
		JTextField jTextField1 = new javax.swing.JTextField();
		JPanel jPanel1 = new javax.swing.JPanel();
		JPanel jPanel2 = new javax.swing.JPanel();
		JPanel jPanel3 = new javax.swing.JPanel();

		jLabel2.setText("jLabel2");

		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		jLabel1.setText("All the Resource VS the task " + taskname);

		jLabel3.setText("Rate of The resources who exucuted the Task " + taskname);

		jTextField1.setText(Double.toString(rate));
		jTextField1.setEditable(false);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(charts.elementAt(0)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 203, Short.MAX_VALUE).addComponent(charts.elementAt(0)));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(charts.elementAt(1)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 213, Short.MAX_VALUE).addComponent(charts.elementAt(1)));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 832, Short.MAX_VALUE).addComponent(charts.elementAt(2)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 218, Short.MAX_VALUE).addComponent(charts.elementAt(2)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jd.getContentPane());
		jd.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(360, 360, 360).addComponent(jLabel1).addGap(0,
								0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 17, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(layout.createSequentialGroup().addComponent(jLabel3)
														.addGap(48, 48, 48).addComponent(jTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(18, 18, 18).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(19, 19, 19).addComponent(jLabel1)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(52, 52, 52).addComponent(jPanel2,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addGap(18, 18, 18).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(16, Short.MAX_VALUE)));

		jd.pack();
		jd.setLocation(100, 100);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);
		return jd;
	}

	public JDialog resourceToTask(String resource, String task, Vector<ChartPanel> charts, Vector<Long> vect) {
		JDialog jd = new JDialog();
		JLabel jLabel1 = new javax.swing.JLabel();
		JPanel jPanel1 = new javax.swing.JPanel();
		JPanel jPanel2 = new javax.swing.JPanel();
		JLabel jLabel2 = new javax.swing.JLabel();
		JTextField jTextField1 = new javax.swing.JTextField();
		JLabel jLabel3 = new javax.swing.JLabel();
		JTextField jTextField2 = new javax.swing.JTextField();
		JLabel jLabel4 = new javax.swing.JLabel();
		JTextField jTextField3 = new javax.swing.JTextField();

		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		jLabel1.setText("Inspecting the Behaviour of the Resource" + resource + " Regarding the Activity " + task);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 406, Short.MAX_VALUE).addComponent(charts.elementAt(0)));

		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 258, Short.MAX_VALUE).addComponent(charts.elementAt(0)));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 413, Short.MAX_VALUE).addComponent(charts.elementAt(1)));

		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(charts.elementAt(1)));

		jLabel2.setText("Min duration for executing the Task ");

		jTextField1.setText(vect.elementAt(0).toString() + " S");
		jTextField1.setEditable(false);
		jLabel3.setText("Max duration for executing the Task");

		jTextField2.setText(vect.elementAt(1).toString() + " S");
		jTextField2.setEditable(false);
		jLabel4.setText("Average duration for executing the Task");

		jTextField3.setText(vect.elementAt(2).toString() + " S");
		jTextField3.setEditable(false);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jd.getContentPane());
		jd.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel1)
						.addGap(243, 243, 243))
				.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(25, 25, 25)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(38, 38, 38).addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel4).addGap(18, 18, 18)
										.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(layout.createSequentialGroup().addComponent(jLabel3)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addComponent(jLabel2)
												.addGap(38, 38, 38).addComponent(jTextField1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))))))
						.addContainerGap(30, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent(jLabel1).addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(21, 21, 21)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jLabel4).addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));

		jd.pack();
		jd.setLocation(100, 100);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);

		return jd;
	}

	public JFrame charts(Vector<ChartPanel> chartPanel, String title, AvailabilityStructure a) {
		JFrame jframe = new JFrame();
		JPanel jPanel1 = new JPanel();
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();
		JLabel jLabel2 = new JLabel();
		;
		JLabel jLabel3 = new JLabel();
		JLabel jLabel4 = new JLabel();
		JTextField jTextField1 = new JTextField();
		JTextField jTextField2 = new JTextField();
		JTextField jTextField3 = new JTextField();

		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.setTitle(title);
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(chartPanel.elementAt(0)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 267, Short.MAX_VALUE).addComponent(chartPanel.elementAt(0)));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 414, Short.MAX_VALUE).addComponent(chartPanel.elementAt(1)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(chartPanel.elementAt(1)));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 382, Short.MAX_VALUE).addComponent(chartPanel.elementAt(2)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 245, Short.MAX_VALUE).addComponent(chartPanel.elementAt(2)));

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(chartPanel.elementAt(3)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE).addComponent(chartPanel.elementAt(3)));
		jLabel2.setText("Min Availability");

		jTextField1.setText(Double.toString(a.getMinAvailability()));

		jLabel3.setText("Mean Availability");

		jTextField2.setText(Double.toString(a.getMeanAvailability()));

		jLabel4.setText("Max Availability");

		jTextField3.setText(Double.toString(a.getMaxAvailability()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jframe.getContentPane());
		jframe.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(17, 17, 17)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				.addGroup(layout.createSequentialGroup().addGap(39, 39, 39).addComponent(jLabel2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(55, 55, 55).addComponent(jLabel3).addGap(18, 18, 18)
						.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(55, 55, 55).addComponent(jLabel4).addGap(18, 18, 18)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(29, 29, 29)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(51, 51, 51)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel2)
										.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel3)
										.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel4).addComponent(jTextField3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(55, Short.MAX_VALUE)));

		jframe.pack();
		jframe.setSize(1000, 1000);
		jframe.setLocation(100, 100);
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		return jframe;
	}

	public JDialog resourceAllTasks(String resource, Vector<Integer> vect, Vector<ChartPanel> charts) {
		JDialog jd = new JDialog();
		JLabel jLabel1 = new javax.swing.JLabel();
		JLabel jLabel2 = new javax.swing.JLabel();
		JTextField jTextField1 = new javax.swing.JTextField();
		JLabel jLabel3 = new javax.swing.JLabel();
		JTextField jTextField2 = new javax.swing.JTextField();
		JPanel jPanel1 = new javax.swing.JPanel();
		JPanel jPanel2 = new javax.swing.JPanel();

		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		jLabel1.setText("The Resource ( " + resource + " ) vs all the Tasks ");

		jLabel2.setText("Total Days");

		jTextField1.setText(vect.elementAt(0).toString());

		jLabel3.setText("Working Days");

		jTextField2.setText(vect.elementAt(1).toString());

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 476, Short.MAX_VALUE).addComponent(charts.elementAt(0)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 222, Short.MAX_VALUE).addComponent(charts.elementAt(0)));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 808, Short.MAX_VALUE).addComponent(charts.elementAt(1)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 245, Short.MAX_VALUE).addComponent(charts.elementAt(1)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jd.getContentPane());
		jd.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(36, 36, 36)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel3).addGap(18, 18, 18)
										.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addComponent(jLabel2).addGap(41, 41, 41)
										.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(106, 106, 106)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(6, 6, 6).addComponent(jPanel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(jLabel1))))
				.addContainerGap(42, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(20, 20, 20).addComponent(jLabel1)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jPanel2,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addContainerGap()));

		jd.pack();
		jd.setLocation(100, 100);
		jd.setLocationRelativeTo(null);
		jd.setVisible(true);

		return jd;
	}

	public JFreeChart barchars(String title, CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart("Resources IDs", "", "meanDuration%", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		return chart;
	}

	public ChartPanel createbar(CategoryDataset dataset, String title) {
		JFreeChart chart = barchars(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		nchart.setPreferredSize(new Dimension(70, 70));
		return nchart;
	}

	public JFreeChart busyPerResource(String title, XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Time Intervals", "Total Duration(hour)", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(0, dataset);
		XYLineAndShapeRenderer rr = new XYLineAndShapeRenderer();
		chart.getXYPlot().setRenderer(0, rr);
		return chart;
	}

	public JFreeChart multipleChart(String title, XYSeriesCollection dataset, int k) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Time Intervals",
				"Number of Execution of differnt tasks", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = chart.getXYPlot();
		for (int i = 0; i < k; i++) {
			xyplot.setDataset(i, dataset);
			XYLineAndShapeRenderer rr = new XYLineAndShapeRenderer();
			chart.getXYPlot().setRenderer(i, rr);
		}
		return chart;
	}

	public JFreeChart availibilityPerResource(String title, XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Time Interval", "AvailabilityRate(%)", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(0, dataset);
		XYLineAndShapeRenderer rr = new XYLineAndShapeRenderer();
		chart.getXYPlot().setRenderer(0, rr);
		return chart;
	}

	public ChartPanel createPanelavai(XYSeriesCollection dataset, String title) {
		JFreeChart chart = availibilityPerResource(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		nchart.setPreferredSize(new Dimension(50, 60));
		return nchart;
	}

	public JFreeChart clusterdata(String title, XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createScatterPlot("FIFO, LIFO, adn Random Queuing", "X-Axis(FIFO Values)",
				"Y-Axis(LIFO Values)", dataset,null,false,false,false);
		XYPlot plot = (XYPlot) chart.getPlot();

		return chart;

	}

	public JFreeChart clusterdatabatch(String title, XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createScatterPlot("Batch vs Non Batch behaviour", "X-Axis(Batch Values)",
				"Y-Axis(Non Batch Values)", dataset,null,false,false,false);
		XYPlot plot = (XYPlot) chart.getPlot();

		return chart;

	}

	public ChartPanel panelcluster(XYSeriesCollection dataset, String title) {
		JFreeChart chart = clusterdata(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		return nchart;
	}

	public ChartPanel panelclusterbatch(XYSeriesCollection dataset, String title) {
		JFreeChart chart = clusterdatabatch(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		return nchart;
	}

	public JFreeChart freePerResource(String title, XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Time Interval",
				"Breaks Number", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(1, dataset);
		XYLineAndShapeRenderer rr = new XYLineAndShapeRenderer();
		chart.getXYPlot().setRenderer(1, rr);
		return chart;
	}

	public JFreeChart resourceToTask(String title, XYSeriesCollection dataset, XYSeriesCollection dataset1) {
		XYPlot plot = new XYPlot();
		XYLineAndShapeRenderer splinerenderer1 = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer splinerenderer2 = new XYLineAndShapeRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		NumberAxis domainAxis = new NumberAxis("Time Slots");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(0, new NumberAxis("Duration of events in Seconds"));

		plot.setDataset(1, dataset1);
		plot.setRenderer(1, splinerenderer2);
		plot.setRangeAxis(1, new NumberAxis("Number of occurence of the event"));

		plot.mapDatasetToRangeAxis(0, 0);//1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 1); //2nd dataset to 2nd y-axis
		final Font titleFont = new Font("Sans-serif", 10, 10);
		JFreeChart chart = new JFreeChart("Durations of events and Number of occurence per slot", titleFont, plot,
				true);

		return chart;
	}

	public JFreeChart processing(String title, ArrayList<XYDataset> list) {
		XYPlot plot = new XYPlot();
		XYLineAndShapeRenderer splinerenderer1 = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer splinerenderer2 = new XYLineAndShapeRenderer();

		plot.setDataset(0, list.get(0));
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Timeline");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(0, new NumberAxis("Duration of events in Seconds"));

		plot.setDataset(1, list.get(1));
		plot.setRenderer(1, splinerenderer2);
		plot.setRangeAxis(1, new NumberAxis("Number of Sceduled events"));

		plot.mapDatasetToRangeAxis(0, 0);//1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 1); //2nd dataset to 2nd y-axis
		final Font titleFont = new Font("Sans-serif", 10, 10);
		JFreeChart chart = new JFreeChart("Duration of executing an event VS the Queue length", titleFont, plot, true);

		return chart;
	}

	public JFreeChart Allrestotaskprocess(String title, XYSeriesCollection list) {
		XYPlot plot = new XYPlot();
		XYLineAndShapeRenderer splinerenderer1 = new XYLineAndShapeRenderer();

		plot.setDataset(0, list);
		plot.setRenderer(0, splinerenderer1);
		NumberAxis domainAxis = new NumberAxis("Slot Numbers");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(0, new NumberAxis("Duration of events in Seconds"));

		plot.mapDatasetToRangeAxis(0, 0);//1st dataset to 1st y-axis

		final Font titleFont = new Font("Sans-serif", 10, 10);
		JFreeChart chart = new JFreeChart("Duration of execution of the task", titleFont, plot, true);

		return chart;
	}

	public ChartPanel processvsQueue(String title, ArrayList<XYDataset> list) {
		JFreeChart chart = processing(title, list);
		ChartPanel nchart = new ChartPanel(chart);
		//nchart.setPreferredSize(new Dimension(50, 60));
		return nchart;
	}

	public ChartPanel allresourcetask(String title, XYSeriesCollection list) {
		JFreeChart chart = Allrestotaskprocess(title, list);
		ChartPanel nchart = new ChartPanel(chart);
		//nchart.setPreferredSize(new Dimension(50, 60));
		return nchart;
	}

	public ChartPanel resourceToTask(XYSeriesCollection dataset, XYSeriesCollection dataset2, String title) {
		JFreeChart chart = resourceToTask(title, dataset, dataset2);
		ChartPanel nchart = new ChartPanel(chart);
		//nchart.setPreferredSize(new Dimension(50, 60));
		return nchart;
	}

	public ChartPanel createPanel1(XYSeriesCollection dataset, String title) {
		JFreeChart chart = freePerResource(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		nchart.setPreferredSize(new Dimension(50, 60));
		return nchart;
	}

	public ChartPanel createPanel(XYSeriesCollection dataset, String title) {
		JFreeChart chart = busyPerResource(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		nchart.setPreferredSize(new Dimension(50, 60));
		return nchart;
	}

	public JFreeChart PieResource(String title, PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(title, // chart title 
				dataset, // data    
				true, // include legend   
				true, false);
		return chart;
	}

	public ChartPanel createPanelPie(PieDataset dataset, String title) {
		JFreeChart chart = PieResource(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		nchart.setPreferredSize(new Dimension(100, 100));
		return nchart;
	}

	public JFreeChart multitask(String title, XYSeriesCollection dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, "Time Interval", "Number of different tasks", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(0, dataset);
		XYLineAndShapeRenderer rr = new XYLineAndShapeRenderer();
		chart.getXYPlot().setRenderer(0, rr);
		return chart;
	}

	public ChartPanel createPanel3(XYSeriesCollection dataset, String title) {
		JFreeChart chart = multitask(title, dataset);
		ChartPanel nchart = new ChartPanel(chart);
		nchart.setPreferredSize(new Dimension(60, 60));
		return nchart;
	}

}
