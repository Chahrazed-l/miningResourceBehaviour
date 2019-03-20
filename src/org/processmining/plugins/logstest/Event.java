package org.processmining.plugins.logstest;

public class Event {

	String dateStart;
	String dateFin;
	String dateSchedule;
	String eventId;
	String resource;
	String eventName;
	String state;
	long duration;

	public Event(String d1, String d2, String d3, long d4) {
		this.dateStart = d1;
		this.dateFin = d2;
		this.dateSchedule = d3;
		this.duration = d4;

	}
	public Event(String ev,String d1, String d2, String d3, String resource) {
		this.dateStart = d1;
		this.dateFin = d2;
		this.dateSchedule = d3;
		this.eventId= ev;
		this.resource=resource;

	}
	
	public Event(String ev,String d1, String d2, String d3, String resource, long duration) {
		this.dateStart = d1;
		this.dateFin = d2;
		this.dateSchedule = d3;
		this.eventId= ev;
		this.resource=resource;
		this.duration=duration;
	}
	public Event(String ev,String taskname,String d1, String d2, String d3, String resource, long duration, String state) {
		this.dateStart = d1;
		this.dateFin = d2;
		this.dateSchedule = d3;
		this.eventId= ev;
		this.eventName=taskname;
		this.resource=resource;
		this.duration=duration;
		this.state=state;
	}
	public Event(String d1, String d2, String d3) {
		this.dateStart = d1;
		this.dateFin = d2;
		this.dateSchedule = d3;

	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getDateSchedule() {
		return dateSchedule;
	}

	public void setDateSchedule(String dateSchedule) {
		this.dateSchedule = dateSchedule;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

}
