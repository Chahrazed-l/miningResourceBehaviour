package org.processmining.plugins.logstest;

import javax.swing.JPanel;

import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;

public class MiningTheLogVisulizers  {
	@Plugin(name="Identify the Resource Behaviour",
			parameterLabels= {" Log file "},
			returnLabels= {"Identify the Resource Behaviour"},
			returnTypes= {JPanel.class},
			userAccessible=true)
	@Visualizer
	public JPanel LVisulizer(PluginContext context,JPanel res) {
		return res ;
	}
}
