package org.processmining.plugins.logstest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DefineECLDBmappingGui extends JDialog {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ECLDBmapping getELDBmapping(Vector<String> eventAttribute) {

		// Variables declaration - do not modify                     
		JButton jButton1 = new JButton();
		final JComboBox<String> jComboBox1 = new JComboBox<>();
		final JComboBox<String> jComboBox10 = new JComboBox<>();
		final JComboBox<String> jComboBox11 = new JComboBox<>();
		final JComboBox<String> jComboBox12 = new JComboBox<>();
		final JComboBox<String> jComboBox13 = new JComboBox<>();
		final JComboBox<String> jComboBox14 = new JComboBox<>();
		final JComboBox<String> jComboBox2 = new JComboBox<>();
		final JComboBox<String> jComboBox3 = new JComboBox<>();
		final JComboBox<String> jComboBox4 = new JComboBox<>();
		final JComboBox<String> jComboBox5 = new JComboBox<>();
		final JComboBox<String> jComboBox6 = new JComboBox<>();
		final JComboBox<String> jComboBox7 = new JComboBox<>();
		final JComboBox<String> jComboBox8 = new JComboBox<>();
		final JComboBox<String> jComboBox9 = new JComboBox<>();
		final JLabel jLabel1 = new JLabel();
		final JLabel jLabel11 = new JLabel();
		final JLabel jLabel2 = new JLabel();
		final JLabel jLabel3 = new JLabel();
		final JLabel jLabel4 = new JLabel();
		final JLabel jLabel5 = new JLabel();
		final JLabel jLabel6 = new JLabel();
		final JLabel jLabel7 = new JLabel();
		final JLabel jLabel8 = new JLabel();
		final JLabel jLabel9 = new JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Event Mapping");
		setBackground(new java.awt.Color(255, 255, 255));

		jLabel1.setForeground(new java.awt.Color(102, 0, 0));
		jLabel1.setText("Event Log Attributes");

		jLabel2.setForeground(new java.awt.Color(102, 0, 0));
		jLabel2.setText("DB Attributes");

		jLabel3.setForeground(new java.awt.Color(102, 0, 0));
		jLabel3.setText("Attributes Types");

		int deftask = 0;
		String[] tabea = new String[15];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabea[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("concept:name")) {
				deftask = i;
			}
		}
		jComboBox1.setModel(new DefaultComboBoxModel<>(tabea));
		jComboBox1.setSelectedItem(eventAttribute.elementAt(deftask));

		jLabel4.setForeground(new java.awt.Color(0, 51, 255));
		jLabel4.setText("Task");

		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "varchar(250)", "int", "bigint",
				"double", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int deftask1 = 0;
		String[] tabea1 = new String[15];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabea1[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("dateFinished")) {
				deftask1 = i;
			}
		}
		jComboBox12.setModel(new DefaultComboBoxModel<>(tabea1));
		jComboBox12.setSelectedItem(eventAttribute.elementAt(deftask1));

		jLabel9.setText("DateFinish");

		jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "varchar(250)", "int", "bigint",
				"double", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int defres = 0;
		String[] tabres = new String[15];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabres[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("org:resource")) {
				defres = i;
			}
		}
		jComboBox5.setModel(new DefaultComboBoxModel<>(tabres));
		jComboBox5.setSelectedItem(eventAttribute.elementAt(defres));

		jLabel6.setForeground(new java.awt.Color(0, 51, 255));
		jLabel6.setText("Resource");

		jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int deftime = 0;
		String[] tabtime = new String[15];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabtime[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("time:timestamp")) {
				deftime = i;
			}
		}
		jComboBox7.setModel(new DefaultComboBoxModel<>(tabtime));
		jComboBox7.setSelectedItem(eventAttribute.elementAt(deftime));

		jLabel7.setForeground(new java.awt.Color(0, 0, 255));
		jLabel7.setText("Timestamp");

		jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "varchar(1000)" }));

		int defstate = 0;
		String[] tabstate = new String[15];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabstate[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("lifecycle:transition")) {
				defstate = i;
			}
		}
		jComboBox9.setModel(new DefaultComboBoxModel<>(tabstate));
		jComboBox9.setSelectedItem(eventAttribute.elementAt(defstate));

		jLabel8.setForeground(new java.awt.Color(0, 0, 255));
		jLabel8.setText("State");

		jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int defmont = 0;
		String[] tabmont = new String[15];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabmont[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("monitoringResource")) {
				defmont = i;
			}
		}
		jComboBox13.setModel(new DefaultComboBoxModel<>(tabmont));
		jComboBox13.setSelectedItem(eventAttribute.elementAt(defmont));

		jLabel11.setForeground(new java.awt.Color(0, 51, 204));
		jLabel11.setText("MonitoringResource");

		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Calculate" }));

		jLabel5.setForeground(new java.awt.Color(0, 102, 255));
		jLabel5.setText("EventId");

		jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(227, 227, 227)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel11).addComponent(jLabel5))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1)
								.addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														209, Short.MAX_VALUE)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jButton1)
														.addGroup(layout.createSequentialGroup().addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
																		layout.createSequentialGroup()
																				.addComponent(jLabel4)
																				.addGap(147, 147, 147))
																.addGroup(layout.createSequentialGroup().addGap(6, 6, 6)
																		.addComponent(jLabel2).addGap(46, 46, 46)))
																.addGroup(layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jLabel3)
																		.addGroup(layout.createSequentialGroup()
																				.addGap(6, 6, 6)
																				.addGroup(layout.createParallelGroup(
																						javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(jComboBox2,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(jComboBox11,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(jComboBox14,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addComponent(jComboBox3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jComboBox10,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jComboBox8,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(jComboBox6,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(jLabel9))
												.addGap(48, 48, 48))
										.addGroup(layout.createSequentialGroup().addGap(200, 200, 200).addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel7).addComponent(jLabel8).addComponent(jLabel6))
												.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)))))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(jButton1)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel3))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel4)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(jComboBox14,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addGap(25, 25, 25)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel6))
										.addGap(18, 18, 18))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(26, 26, 26)))
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel7))
										.addGap(45, 45, 45))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(54, 54, 54)))
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(40, 40, 40)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup()
												.addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(39, 39, 39))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(30, 30, 30)))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel5).addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel8).addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addGap(51, 51, 51)));

		pack();

		final ECLDBmapping map = new ECLDBmapping();
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.ELAttribute.addElement((String) jComboBox1.getSelectedItem());
				map.ELAttribute.addElement((String) jComboBox12.getSelectedItem());
				map.ELAttribute.addElement((String) jComboBox5.getSelectedItem());
				map.ELAttribute.addElement((String) jComboBox7.getSelectedItem());
				map.ELAttribute.addElement((String) jComboBox9.getSelectedItem());
				map.ELAttribute.addElement((String) jComboBox13.getSelectedItem());
				map.ELAttribute.addElement((String) jComboBox4.getSelectedItem());

				map.DBELAttribute.addElement(jLabel4.getText());
				map.DBELAttribute.addElement(jLabel9.getText());
				map.DBELAttribute.addElement(jLabel6.getText());
				map.DBELAttribute.addElement(jLabel7.getText());
				map.DBELAttribute.addElement(jLabel8.getText());
				map.DBELAttribute.addElement(jLabel11.getText());
				map.DBELAttribute.addElement(jLabel5.getText());

				map.DBELType.addElement((String) jComboBox2.getSelectedItem());
				map.DBELType.addElement((String) jComboBox14.getSelectedItem());
				map.DBELType.addElement((String) jComboBox6.getSelectedItem());
				map.DBELType.addElement((String) jComboBox8.getSelectedItem());
				map.DBELType.addElement((String) jComboBox10.getSelectedItem());
				map.DBELType.addElement((String) jComboBox3.getSelectedItem());
				map.DBELType.addElement((String) jComboBox11.getSelectedItem());
				//map.DBELType.addElement(jLabel12.getText());

				dispose();

			}
		});
		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ECLDBmapping getCLMapping(final Vector<String> eventAttribute, final ECLDBmapping map) {

		final JButton jButton2 = new JButton();
		final JComboBox<String> jComboBox1 = new JComboBox<>();
		final JComboBox<String> jComboBox10 = new JComboBox<>();
		final JComboBox<String> jComboBox11 = new JComboBox<>();
		final JComboBox<String> jComboBox12 = new JComboBox<>();
		final JComboBox<String> jComboBox13 = new JComboBox<>();
		final JComboBox<String> jComboBox2 = new JComboBox<>();
		final JComboBox<String> jComboBox3 = new JComboBox<>();
		final JComboBox<String> jComboBox4 = new JComboBox<>();
		final JComboBox<String> jComboBox5 = new JComboBox<>();
		final JComboBox<String> jComboBox6 = new JComboBox<>();
		final JComboBox<String> jComboBox7 = new JComboBox<>();
		final JComboBox<String> jComboBox8 = new JComboBox<>();
		final JComboBox<String> jComboBox9 = new JComboBox<>();
		final JLabel jLabel1 = new JLabel();
		final JLabel jLabel10 = new JLabel();
		final JLabel jLabel11 = new JLabel();
		final JLabel jLabel12 = new JLabel();
		final JLabel jLabel2 = new JLabel();
		final JLabel jLabel3 = new JLabel();
		final JLabel jLabel4 = new JLabel();
		final JLabel jLabel5 = new JLabel();
		final JLabel jLabel6 = new JLabel();
		final JLabel jLabel7 = new JLabel();
		final JLabel jLabel8 = new JLabel();
		final JLabel jLabel9 = new JLabel();

		jComboBox12.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel2.setForeground(new java.awt.Color(153, 0, 0));
		jLabel2.setText("Case Attributes");

		jLabel3.setForeground(new java.awt.Color(153, 0, 0));
		jLabel3.setText("Database Attributes");

		jLabel4.setForeground(new java.awt.Color(153, 0, 0));
		jLabel4.setText("Attributes Types");

		jButton2.setText("Case Mapping");

		int defcaseid = 0;
		String[] tabcaseid = new String[25];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabcaseid[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("concept:name")) {
				defcaseid = i;
			}
		}
		jComboBox1.setModel(new DefaultComboBoxModel<>(tabcaseid));
		jComboBox1.setSelectedItem(eventAttribute.elementAt(defcaseid));

		jLabel1.setForeground(new java.awt.Color(0, 51, 204));
		jLabel1.setText("CaseId");

		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int defcasestate = 0;
		String[] tabcasestate = new String[25];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabcasestate[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("caseStatus")) {
				defcasestate = i;
			}
		}

		jComboBox3.setModel(new DefaultComboBoxModel<>(tabcasestate));
		jComboBox3.setSelectedItem(eventAttribute.elementAt(defcasestate));

		jLabel5.setForeground(new java.awt.Color(0, 51, 204));
		jLabel5.setText("CaseState");

		jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int defcasesdate = 0;
		String[] tabcasedate = new String[25];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabcasedate[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("REG_DATE")) {
				defcasesdate = i;
			}
		}
		jComboBox5.setModel(new DefaultComboBoxModel<>(tabcasedate));
		jComboBox5.setSelectedItem(eventAttribute.elementAt(defcasesdate));

		jLabel6.setForeground(new java.awt.Color(0, 51, 204));
		jLabel6.setText("CaseStartDate");

		jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int defcaseend = 0;
		String[] tabcaseend = new String[25];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabcaseend[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("endDate")) {
				defcaseend = i;
			}
		}
		jComboBox7.setModel(new DefaultComboBoxModel<>(tabcaseend));
		jComboBox7.setSelectedItem(eventAttribute.elementAt(defcaseend));

		jLabel7.setForeground(new java.awt.Color(0, 51, 204));
		jLabel7.setText("CaseEndDate");

		jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		int defcasepla = 0;
		String[] tabcasepla = new String[25];
		for (int i = 0; i < eventAttribute.size(); i++) {
			tabcasepla[i] = eventAttribute.elementAt(i);
			if (eventAttribute.elementAt(i).equals("endDatePlanned")) {
				defcasepla = i;
			}
		}
		jComboBox9.setModel(new DefaultComboBoxModel<>(tabcasepla));
		jComboBox9.setSelectedItem(eventAttribute.elementAt(defcasepla));

		jLabel8.setForeground(new java.awt.Color(0, 51, 204));
		jLabel8.setText("PlannedEndDate");

		jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "varchar(250)", "varchar(20)", "varchar(50)", "varchar(100)" }));

		jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Calculate" }));
		jLabel9.setForeground(new java.awt.Color(0, 51, 204));
		jLabel9.setText("ResourceNum");
		jLabel11.setText("int");

		jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Calculate" }));
		jLabel10.setForeground(new java.awt.Color(0, 51, 204));
		jLabel10.setText("Duration");
		jLabel12.setText("bigint");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(127, 127, 127)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel5).addComponent(jLabel7).addComponent(jLabel6)
										.addComponent(jLabel8).addComponent(jLabel1).addComponent(jLabel9)
										.addComponent(jLabel10)))
						.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addComponent(jLabel2)
								.addGap(110, 110, 110)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(jLabel3).addComponent(jButton2))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel4)
								.addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel11).addComponent(jLabel12))
						.addGap(27, 27, 27)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(15, 15, 15).addComponent(jButton2).addGap(30, 30, 30)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel4))
				.addGap(37, 37, 37)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel1).addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(27, 27, 27)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel5).addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(26, 26, 26)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel6).addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel7).addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel8).addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel9)
						.addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel11))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel12).addComponent(jLabel10))
				.addContainerGap(22, Short.MAX_VALUE)));

		pack();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				map.CLAttribute.addElement((String) jComboBox1.getSelectedItem());
				map.CLAttribute.addElement((String) jComboBox3.getSelectedItem());
				map.CLAttribute.addElement((String) jComboBox5.getSelectedItem());
				map.CLAttribute.addElement((String) jComboBox7.getSelectedItem());
				map.CLAttribute.addElement((String) jComboBox9.getSelectedItem());
				map.CLAttribute.addElement((String) jComboBox11.getSelectedItem());
				map.CLAttribute.addElement((String) jComboBox13.getSelectedItem());

				map.DBCLAttribute.addElement(jLabel1.getText());
				map.DBCLAttribute.addElement(jLabel5.getText());
				map.DBCLAttribute.addElement(jLabel6.getText());
				map.DBCLAttribute.addElement(jLabel7.getText());
				map.DBCLAttribute.addElement(jLabel8.getText());
				map.DBCLAttribute.addElement(jLabel9.getText());
				map.DBCLAttribute.addElement(jLabel10.getText());

				map.DBCLType.addElement((String) jComboBox2.getSelectedItem());
				map.DBCLType.addElement((String) jComboBox4.getSelectedItem());
				map.DBCLType.addElement((String) jComboBox6.getSelectedItem());
				map.DBCLType.addElement((String) jComboBox8.getSelectedItem());
				map.DBCLType.addElement((String) jComboBox10.getSelectedItem());
				map.DBCLType.addElement(jLabel11.getText());
				map.DBCLType.addElement(jLabel12.getText());

				dispose();
			}
		});

		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return map;

	}

}
