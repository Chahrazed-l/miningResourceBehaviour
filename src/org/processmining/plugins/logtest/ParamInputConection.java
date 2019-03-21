package org.processmining.plugins.logtest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;

import org.processmining.framework.util.ui.widgets.ProMTextField;

import com.fluxicon.slickerbox.components.SlickerButton;

public class ParamInputConection extends JDialog {

	JDialog jf;

	public InputParam DbParams(final InputParam inputParam) {

		//setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Database");
		final JLabel jLabel1 = new JLabel("Create and/or Connect to the Database");
		jLabel1.setForeground(new java.awt.Color(255, 51, 102));

		final JLabel jLabel2 = new JLabel("Host Name");
		final ProMTextField hostname = new ProMTextField("localhost");

		final JLabel jLabel3 = new JLabel("UserName");
		final ProMTextField username = new ProMTextField("indicate user");

		final JLabel jLabel4 = new JLabel("Password");
		final ProMTextField password = new ProMTextField("**********");

		final JLabel jLabel5 = new JLabel("DbName");
		final ProMTextField dbName = new ProMTextField("database Name ");

		final SlickerButton jButton1 = new SlickerButton();
		jButton1.setText("Ok");
		final SlickerButton jButton2 = new SlickerButton();
		jButton2.setText("Cancel");

		//The organization of the Gui Interface
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap(24, Short.MAX_VALUE)
								.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(58, 58, 58))
				.addGroup(layout.createSequentialGroup().addGap(35, 35, 35)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4).addComponent(jLabel5)
								.addComponent(jButton1))
						.addGap(79, 79, 79)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jButton2)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(hostname, javax.swing.GroupLayout.DEFAULT_SIZE, 122,
												Short.MAX_VALUE)
										.addComponent(username).addComponent(password).addComponent(dbName)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(14, 14, 14).addComponent(jLabel1).addGap(24, 24, 24)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(hostname, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(2, 2, 2)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(2, 2, 2)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel4).addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(dbName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel5))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton1).addComponent(jButton2))
						.addContainerGap(9, Short.MAX_VALUE)));

		pack();
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputParam.hostname = hostname.getText();
				inputParam.username = username.getText();
				inputParam.password = password.getText();
				inputParam.dbName = dbName.getText();

				dispose();
			}
		});

		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.dispose();

			}
		});
		//setSize(500, 260);
		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        System.out.println("Les parametres sont "+inputParam.hostname+" "+inputParam.password);
		return inputParam;

	}

}