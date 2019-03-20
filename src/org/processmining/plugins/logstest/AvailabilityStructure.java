package org.processmining.plugins.logstest;

import java.util.ArrayList;

public class AvailabilityStructure {
	public ArrayList<Long> totalDuration = new ArrayList<Long>();
	public ArrayList<Double> numberofworkfloaterval = new ArrayList<Double>();
	public ArrayList<Double> availabilityRate = new ArrayList<Double>();
	public double meanAvailability;
	public double minAvailability;
	public double maxAvailability;
	public double batchRate;
	public double nonBatchRate;
	public int instructionNb;
	public int workdaysBehav;
	public int workingdays;
	public int totperiod;
	
	public ArrayList<Integer> instructionday= new ArrayList<>();

	
	
	public ArrayList<Integer> getInstructionday() {
		return instructionday;
	}

	public void setInstructionday(ArrayList<Integer> instructionday) {
		this.instructionday = instructionday;
	}

	public int getTotperiod() {
		return totperiod;
	}

	public void setTotperiod(int totperiod) {
		this.totperiod = totperiod;
	}

	public int getWorkingdays() {
		return workingdays;
	}

	public void setWorkingdays(int workingdays) {
		this.workingdays = workingdays;
	}

	public int getInstructionNb() {
		return instructionNb;
	}

	public void setInstructionNb(int instructionNb) {
		this.instructionNb = instructionNb;
	}

	public int getWorkdaysBehav() {
		return workdaysBehav;
	}

	public void setWorkdaysBehav(int workdaysBehav) {
		this.workdaysBehav = workdaysBehav;
	}

	public ArrayList<Long> getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(ArrayList<Long> totalDuration) {
		this.totalDuration = totalDuration;
	}

	public ArrayList<Double> getNumberofworkfloaterval() {
		return numberofworkfloaterval;
	}

	public void setNumberofworkfloaterval(ArrayList<Double> numberofworkfloaterval) {
		this.numberofworkfloaterval = numberofworkfloaterval;
	}

	public ArrayList<Double> getAvailabilityRate() {
		return availabilityRate;
	}

	public void setAvailabilityRate(ArrayList<Double> availabilityRate) {
		this.availabilityRate = availabilityRate;
	}

	public double getMeanAvailability() {
		return meanAvailability;
	}

	public void setMeanAvailability(double meanAvailability) {
		this.meanAvailability = meanAvailability;
	}

	public double getMinAvailability() {
		return minAvailability;
	}

	public void setMinAvailability(double minAvailability) {
		this.minAvailability = minAvailability;
	}

	public double getMaxAvailability() {
		return maxAvailability;
	}

	public void setMaxAvailability(double maxAvailability) {
		this.maxAvailability = maxAvailability;
	}

	public double getBatchRate() {
		return batchRate;
	}

	public void setBatchRate(double batchRate) {
		this.batchRate = batchRate;
	}

	public double getNonBatchRate() {
		return nonBatchRate;
	}

	public void setNonBatchRate(double nonBatchRate) {
		this.nonBatchRate = nonBatchRate;
	}

}
