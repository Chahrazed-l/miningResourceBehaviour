package org.processmining.plugins.logstest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DefineTimeLog extends JDialog {

	public TimeLog getTimeInterval(final Vector<String> timeInterval, final Vector<String> resource)
			throws ParseException {
		final JLabel jLabel1 = new JLabel();
		final JLabel jLabel2 = new JLabel();
		final JLabel jLabel3 = new JLabel();
		final JLabel jLabel4 = new JLabel();
		final JTextField jTextField1 = new javax.swing.JTextField();
		final JTextField jTextField2 = new javax.swing.JTextField();
		final JLabel jLabel5 = new JLabel();
		final JTextField jTextField3 = new javax.swing.JTextField();
		final JLabel jLabel6 = new JLabel();
		final JTextField jTextField4 = new javax.swing.JTextField();
		final JLabel jLabel7 = new JLabel();
		final JTextField jTextField5 = new javax.swing.JTextField();
		final JLabel jLabel8 = new JLabel();
		final JTextField jTextField6 = new javax.swing.JTextField();
		final JLabel jLabel9 = new JLabel();
		final JLabel jLabel10 = new JLabel();
		final JTextField jTextField7 = new javax.swing.JTextField();
		final JLabel jLabel11 = new JLabel();
		final JTextField jTextField8 = new javax.swing.JTextField();
		final JLabel jLabel12 = new JLabel();
		final JTextField jTextField9 = new javax.swing.JTextField();
		final JLabel jLabel13 = new JLabel();
		final JTextField jTextField10 = new javax.swing.JTextField();
		final JLabel jLabel14 = new JLabel();
		final JTextField jTextField11 = new javax.swing.JTextField();
		final JLabel jLabel15 = new JLabel();
		final JTextField jTextField12 = new javax.swing.JTextField();
		final JLabel jLabel16 = new JLabel();
		final JComboBox<String> jComboBox1 = new JComboBox<String>();
		final JLabel jLabel17 = new JLabel();
		final JTextField jTextField13 = new JTextField();
		final JLabel jLabel18 = new JLabel();
		final JComboBox<String> jComboBox2 = new JComboBox<>();
		final JButton jButton1 = new JButton();
		final JButton jButton2 = new JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setForeground(new java.awt.Color(0, 0, 204));
		jLabel1.setText("Inputs for Resource Availability checking");

		jLabel2.setText("Starting Date");

		jLabel3.setText("Year");

		jLabel4.setText("Month");

		jTextField1.setText("2001");

		jLabel5.setText("Days");

		jLabel6.setText("Hours");

		jLabel7.setText("Minutes");

		jLabel8.setText("Seconds");

		jLabel9.setText("Ending Date");

		jLabel10.setText("Year");

		jLabel11.setText("Month");

		jLabel12.setText("Days");

		jLabel13.setText("Hours");

		jLabel14.setText("Minutes");
		jComboBox1.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Day", "Week", "Month", "Year" }));
		jLabel15.setText("Seconds");
		String[] tabres = new String[5000];
		tabres[0] = "All";
		for (int i = 0; i < resource.size(); i++) {
			tabres[i + 1] = resource.elementAt(i);

		}
		jLabel18.setText("Resource");
		jComboBox2.setModel(new DefaultComboBoxModel<>(tabres));

		jButton1.setText("Cancel");

		jButton2.setText("Submit");

		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});

		final Vector<Calendar> c1 = dates(timeInterval);
		jTextField1.setText(Integer.toString(c1.elementAt(0).get(Calendar.YEAR)));
		jTextField2.setText(Integer.toString(c1.elementAt(0).get(Calendar.MONTH)+1));
		jTextField3.setText(Integer.toString(c1.elementAt(0).get(Calendar.DAY_OF_MONTH)));
		jTextField4.setText(Integer.toString(c1.elementAt(0).get(Calendar.HOUR_OF_DAY)));
		jTextField5.setText(Integer.toString(c1.elementAt(0).get(Calendar.MINUTE)));
		jTextField6.setText(Integer.toString(c1.elementAt(0).get(Calendar.SECOND)));
		jTextField7.setText(Integer.toString(c1.elementAt(1).get(Calendar.YEAR)));
		jTextField8.setText(Integer.toString(c1.elementAt(1).get(Calendar.MONTH)+1));
		jTextField9.setText(Integer.toString(c1.elementAt(1).get(Calendar.DAY_OF_MONTH)));
		jTextField10.setText(Integer.toString(c1.elementAt(1).get(Calendar.HOUR_OF_DAY)));
		jTextField11.setText(Integer.toString(c1.elementAt(1).get(Calendar.MINUTE)));
		jTextField12.setText(Integer.toString(c1.elementAt(1).get(Calendar.SECOND)));
		jLabel16.setText("Slot Size");
		jLabel17.setText("Number Of time Slots");
		jComboBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jComboBox1.getSelectedItem().equals("Day")) {
					int days = daysBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(days));
				} else if (jComboBox1.getSelectedItem().equals("Week")) {
					int week = weeksBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(week));
				} else if (jComboBox1.getSelectedItem().equals("Month")) {
					int month = monthsBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(month));
				} else {
					int year = yearBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(year));
				}

			}
		});
		timeIntervals(c1.elementAt(0).getTime(), c1.elementAt(1).getTime(), (String) jComboBox1.getSelectedItem());
		final TimeLog time = new TimeLog();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Submit information to process the mining of the availability(busy and free time)
				//System.out.println("The selected resource is " + jComboBox2.getSelectedItem());
				//System.out.println("The time slot is " + jComboBox1.getSelectedItem());

				time.timeInterval.addElement(c1.elementAt(0).getTime().toString());
				time.timeInterval.addElement(c1.elementAt(1).getTime().toString());
				time.resource = (String) jComboBox2.getSelectedItem();
				time.slot = (String) jComboBox1.getSelectedItem();
				try {
					time.vect = timeIntervals(c1.elementAt(0).getTime(), c1.elementAt(1).getTime(), time.slot);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Free Time
				dispose();

			}
		});
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(65, 65, 65).addComponent(jLabel1))
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(144, 144, 144).addComponent(jLabel3))
								.addGroup(layout.createSequentialGroup().addGap(26, 26, 26).addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(layout.createSequentialGroup().addComponent(jLabel9)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jTextField7,
																javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createSequentialGroup().addComponent(jLabel2)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGap(36, 36, 36).addComponent(jLabel10))
																.addGroup(layout.createSequentialGroup()
																		.addGap(20, 20, 20).addComponent(jTextField1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				56,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))))
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
														.createSequentialGroup().addComponent(jLabel18)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jComboBox2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup().addComponent(jLabel16).addGap(49,
																49, 49).addComponent(jComboBox1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(8, 8, 8)
														.addComponent(jLabel4))
												.addGroup(layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jTextField8,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(jLabel11)))
												.addGroup(layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(jTextField2,
																javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jLabel5)
																		.addComponent(jTextField3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				53,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jTextField9,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				53,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(layout.createSequentialGroup().addGap(14, 14, 14)
																.addComponent(jLabel12)))
												.addGap(41, 41, 41)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		false)
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(jLabel13)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(jLabel14))
																		.addGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				layout.createSequentialGroup()
																						.addComponent(jLabel6)
																						.addGap(26, 26, 26)
																						.addComponent(jLabel7))
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(jTextField4,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						46,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(
																						javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(jTextField5,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						41,
																						javax.swing.GroupLayout.PREFERRED_SIZE)))
																.addGroup(layout.createSequentialGroup()
																		.addComponent(jTextField10,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				46,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18, 18, 18).addComponent(jTextField11,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				41,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
																.addGap(18, 18, 18)
																.addGroup(layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jTextField12,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				42,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jLabel8)
																		.addComponent(jTextField6,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				42,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jLabel15)))
														.addGroup(layout.createSequentialGroup().addGap(64, 64, 64)
																.addComponent(jTextField13,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 81,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGroup(layout.createSequentialGroup().addGap(59, 59, 59)
												.addComponent(jLabel17))))
						.addGroup(layout.createSequentialGroup().addGap(139, 139, 139).addComponent(jButton1)
								.addGap(158, 158, 158).addComponent(jButton2)))
				.addContainerGap(69, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(17, 17, 17).addComponent(jLabel1).addGap(29, 29, 29)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3)
						.addComponent(jLabel4).addComponent(jLabel5).addComponent(jLabel6).addComponent(jLabel7)
						.addComponent(jLabel8))
				.addGap(17, 17, 17)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel10)
						.addComponent(jLabel11).addComponent(jLabel12).addComponent(jLabel13).addComponent(jLabel14)
						.addComponent(jLabel15))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel9)
						.addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(24, 24, 24)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel16)
						.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel17).addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(36, 36, 36)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel18)
						.addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1)
						.addComponent(jButton2))
				.addGap(19, 19, 19)));
		pack();

		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		return time;

	}

	public TimeLog getSkills(final Vector<String> timeInterval, final Vector<String> resource,
			final ArrayList<String> eventlist) throws ParseException {
		JLabel jLabel1 = new javax.swing.JLabel();
		JLabel jLabel2 = new javax.swing.JLabel();
		JLabel jLabel3 = new javax.swing.JLabel();
		JLabel jLabel4 = new javax.swing.JLabel();
		final JTextField jTextField1 = new javax.swing.JTextField();
		final JTextField jTextField2 = new javax.swing.JTextField();
		JLabel jLabel5 = new javax.swing.JLabel();
		final JTextField jTextField3 = new javax.swing.JTextField();
		JLabel jLabel6 = new javax.swing.JLabel();
		final JTextField jTextField4 = new javax.swing.JTextField();
		JLabel jLabel7 = new javax.swing.JLabel();
		final JTextField jTextField5 = new javax.swing.JTextField();
		JLabel jLabel8 = new javax.swing.JLabel();
		final JTextField jTextField6 = new javax.swing.JTextField();
		JLabel jLabel9 = new javax.swing.JLabel();
		JLabel jLabel10 = new javax.swing.JLabel();
		final JTextField jTextField7 = new javax.swing.JTextField();
		JLabel jLabel11 = new javax.swing.JLabel();
		final JTextField jTextField8 = new javax.swing.JTextField();
		JLabel jLabel12 = new javax.swing.JLabel();
		final JTextField jTextField9 = new javax.swing.JTextField();
		JLabel jLabel13 = new javax.swing.JLabel();
		final JTextField jTextField10 = new javax.swing.JTextField();
		JLabel jLabel14 = new javax.swing.JLabel();
		final JTextField jTextField11 = new javax.swing.JTextField();
		JLabel jLabel15 = new javax.swing.JLabel();
		final JTextField jTextField12 = new javax.swing.JTextField();
		JLabel jLabel16 = new javax.swing.JLabel();
		final JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
		JLabel jLabel17 = new javax.swing.JLabel();
		final JTextField jTextField13 = new javax.swing.JTextField();
		JLabel jLabel18 = new javax.swing.JLabel();
		final JComboBox<String> jComboBox2 = new javax.swing.JComboBox<>();
		JButton jButton1 = new javax.swing.JButton();
		JButton jButton2 = new javax.swing.JButton();
		JLabel jLabel19 = new javax.swing.JLabel();
		final JComboBox<String> jComboBox3 = new javax.swing.JComboBox<>();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setForeground(new java.awt.Color(0, 0, 204));
		jLabel1.setText("Skills checking");
		jLabel2.setText("Starting Date");

		jLabel3.setText("Year");

		jLabel4.setText("Month");

		jLabel5.setText("Days");

		jLabel6.setText("Hours");

		jLabel7.setText("Minutes");

		jLabel8.setText("Seconds");

		jLabel9.setText("Ending Date");

		jLabel10.setText("Year");

		jLabel11.setText("Month");

		jLabel12.setText("Days");

		jLabel13.setText("Hours");

		jLabel14.setText("Minutes");

		jLabel15.setText("Seconds");

		jLabel16.setText("Slot Size");

		jComboBox1.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Day", "Week", "Month", "Year" }));

		jLabel17.setText("Number Of time Slots");

		jLabel18.setText("Resource");

		String[] tabres = new String[5000];
		tabres[0] = "All";
		for (int i = 0; i < resource.size(); i++) {
			tabres[i + 1] = resource.elementAt(i);

		}
		jComboBox2.setModel(new DefaultComboBoxModel<>(tabres));

		jButton1.setText("Cancel");

		jButton2.setText("Submit");

		jLabel19.setText("Tasks");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});

		final Vector<Calendar> c1 = dates(timeInterval);
		jTextField1.setText(Integer.toString(c1.elementAt(0).get(Calendar.YEAR)));
		jTextField2.setText(Integer.toString(c1.elementAt(0).get(Calendar.MONTH)));
		jTextField3.setText(Integer.toString(c1.elementAt(0).get(Calendar.DAY_OF_MONTH)));
		jTextField4.setText(Integer.toString(c1.elementAt(0).get(Calendar.HOUR_OF_DAY)));
		jTextField5.setText(Integer.toString(c1.elementAt(0).get(Calendar.MINUTE)));
		jTextField6.setText(Integer.toString(c1.elementAt(0).get(Calendar.SECOND)));
		jTextField7.setText(Integer.toString(c1.elementAt(1).get(Calendar.YEAR)));
		jTextField8.setText(Integer.toString(c1.elementAt(1).get(Calendar.MONTH)));
		jTextField9.setText(Integer.toString(c1.elementAt(1).get(Calendar.DAY_OF_MONTH)));
		jTextField10.setText(Integer.toString(c1.elementAt(1).get(Calendar.HOUR_OF_DAY)));
		jTextField11.setText(Integer.toString(c1.elementAt(1).get(Calendar.MINUTE)));
		jTextField12.setText(Integer.toString(c1.elementAt(1).get(Calendar.SECOND)));
		jLabel16.setText("Slot Size");
		jLabel17.setText("Number Of time Slots");
		jComboBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jComboBox1.getSelectedItem().equals("Day")) {
					int days = daysBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(days));
				} else if (jComboBox1.getSelectedItem().equals("Week")) {
					int week = weeksBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(week));
				} else if (jComboBox1.getSelectedItem().equals("Month")) {
					int month = monthsBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(month));
				} else {
					int year = yearBetween(c1.elementAt(0).getTime(), c1.elementAt(1).getTime());
					jTextField13.setText(Integer.toString(year));
				}

			}
		});
		timeIntervals(c1.elementAt(0).getTime(), c1.elementAt(1).getTime(), (String) jComboBox1.getSelectedItem());

		String[] tabres1 = new String[100];
		tabres1[0] = "All";
		for (int i = 0; i < eventlist.size(); i++) {
			tabres1[i + 1] = eventlist.get(i);

		}
		jComboBox3.setModel(new DefaultComboBoxModel<>(tabres1));
		final TimeLog time = new TimeLog();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Submit information to process the mining of the availability(busy and free time)
				//System.out.println("The selected resource is " + jComboBox2.getSelectedItem());
				//System.out.println("The time slot is " + jComboBox1.getSelectedItem());
				//System.out.println("The Task is " + jComboBox3.getSelectedItem());

				time.timeInterval.addElement(c1.elementAt(0).getTime().toString());
				time.timeInterval.addElement(c1.elementAt(1).getTime().toString());
				time.resource = (String) jComboBox2.getSelectedItem();
				time.slot = (String) jComboBox1.getSelectedItem();
				time.taskName = (String) jComboBox3.getSelectedItem();
				try {
					time.vect = timeIntervals(c1.elementAt(0).getTime(), c1.elementAt(1).getTime(), time.slot);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Free Time
				dispose();

			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1)
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(144, 144, 144).addComponent(jLabel3))
								.addGroup(layout.createSequentialGroup().addGap(26, 26, 26).addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(layout.createSequentialGroup().addComponent(jLabel9)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jTextField7,
																javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createSequentialGroup().addComponent(jLabel2)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGap(36, 36, 36).addComponent(jLabel10))
																.addGroup(layout.createSequentialGroup()
																		.addGap(20, 20, 20).addComponent(jTextField1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				56,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))))
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout
														.createSequentialGroup().addComponent(jLabel18)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jComboBox2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup().addComponent(jLabel16)
																.addGap(49, 49, 49).addComponent(jComboBox1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(8, 8, 8)
														.addComponent(jLabel4))
												.addGroup(layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(jTextField8,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(jLabel11)))
												.addGroup(layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(jTextField2,
																javax.swing.GroupLayout.PREFERRED_SIZE, 56,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jLabel5)
																		.addComponent(jTextField3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				53,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jTextField9,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				53,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(layout.createSequentialGroup().addGap(14, 14, 14)
																.addComponent(jLabel12)))
												.addGap(41, 41, 41)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		false)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(jLabel13)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(jLabel14))
																.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
																		layout.createSequentialGroup()
																				.addComponent(jLabel6)
																				.addGap(26, 26, 26)
																				.addComponent(jLabel7))
																.addGroup(layout.createSequentialGroup()
																		.addComponent(jTextField4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				46,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(jTextField5,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				41,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(layout.createSequentialGroup()
																.addComponent(jTextField10,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 46,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18, 18).addComponent(jTextField11,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 41,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(18, 18, 18)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jTextField12,
																javax.swing.GroupLayout.PREFERRED_SIZE, 42,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel8)
														.addComponent(jTextField6,
																javax.swing.GroupLayout.PREFERRED_SIZE, 42,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel15)))
										.addGroup(layout.createSequentialGroup().addGap(59, 59, 59)
												.addComponent(jLabel17))
										.addGroup(layout.createSequentialGroup().addGap(145, 145, 145)
												.addComponent(jLabel19).addGap(50, 50, 50)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jComboBox3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jTextField13,
																javax.swing.GroupLayout.PREFERRED_SIZE, 81,
																javax.swing.GroupLayout.PREFERRED_SIZE)))))
						.addGroup(layout.createSequentialGroup().addGap(139, 139, 139).addComponent(jButton1)
								.addGap(158, 158, 158).addComponent(jButton2)))
				.addContainerGap(69, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(40, 40, 40)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3)
						.addComponent(jLabel4).addComponent(jLabel5).addComponent(jLabel6).addComponent(jLabel7)
						.addComponent(jLabel8))
				.addGap(17, 17, 17)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel10)
						.addComponent(jLabel11).addComponent(jLabel12).addComponent(jLabel13).addComponent(jLabel14)
						.addComponent(jLabel15))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel9)
						.addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(24, 24, 24)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel16)
						.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel17).addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(36, 36, 36)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel18)
						.addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel19).addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1)
						.addComponent(jButton2))
				.addGap(19, 19, 19)));

		pack();
		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return time;
	}

	public int daysBetween(Date d1, Date d2) {

		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public int weeksBetween(Date d1, Date d2) {

		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24 * 7));
	}

	public int monthsBetween(Date d1, Date d2) {
		Calendar cal = Calendar.getInstance();
		if (d1.before(d2)) {
			cal.setTime(d1);
		} else {
			cal.setTime(d2);
			d2 = d1;
		}
		int c = 0;
		while (cal.getTime().before(d2)) {
			cal.add(Calendar.MONTH, 1);
			c++;
		}
		return c - 1;

	}

	public int yearBetween(Date d1, Date d2) {

		Calendar a = getCalendar(d1);
		Calendar b = getCalendar(d2);
		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
			diff--;
		}
		return diff;
	}

	public Vector<Calendar> dates(Vector<String> timeInterval) throws ParseException {
		Vector<Calendar> cal = new Vector<Calendar>();
		for (int i = 0; i < timeInterval.size(); i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			//SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
			Date date1 = sdf.parse(timeInterval.elementAt(i));
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date1);
			cal.add(c1);

		}
		return cal;
	}

	public Vector<Vector<String>> timeIntervals(Date startDate, Date endDate, String slot) throws ParseException {
		Vector<Vector<String>> tvector = new Vector<Vector<String>>();
		if (slot.equals("Day")) {
			Vector<String> t = new Vector<String>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);

			while (cal.getTime().compareTo(endDate) < 0) {

				SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				Date date1 = sdf.parse(cal.getTime().toString());
				SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				t.add(sdf2.format(date1));
				cal.add(Calendar.DATE, 1);
				Date date2 = sdf.parse(cal.getTime().toString());
				t.add(sdf2.format(date2));
				tvector.add(t);
				t = new Vector<String>();
			}
		}
		if (slot.equals("Week")) {
			Vector<String> t = new Vector<String>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);

			while (cal.getTime().compareTo(endDate) < 0) {

				SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				Date date1 = sdf.parse(cal.getTime().toString());
				SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				t.add(sdf2.format(date1));
				cal.add(Calendar.WEEK_OF_YEAR, 1);
				Date date2 = sdf.parse(cal.getTime().toString());
				t.add(sdf2.format(date2));
				tvector.add(t);
				t = new Vector<String>();
			}
		}
		if (slot.equals("Months")) {
			Vector<String> t = new Vector<String>();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);

			while (cal.getTime().compareTo(endDate) < 0) {

				SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				Date date1 = sdf.parse(cal.getTime().toString());
				SimpleDateFormat sdf2 = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
				//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				t.add(sdf2.format(date1));
				cal.add(Calendar.MONTH, 1);
				Date date2 = sdf.parse(cal.getTime().toString());
				t.add(sdf2.format(date2));
				tvector.add(t);
				t = new Vector<String>();
			}
		}
		//System.out.println(tvector);
		return tvector;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
