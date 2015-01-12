package org.grameenfoundation.cch.model;

public class MyCalendarEvents {

	private String eventType;
	private String eventDescription;
	private String eventLocation;
	private String eventTime;
	private String eventId;
	private String startDate;
	private String endDate;
	private String numberCompleted;
	private String totalNumberOfEvents;
	private String numberUncompleted;
	
	public MyCalendarEvents()
	{
		
	}
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
			
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public String getEventStartDate() {
		return startDate;
	}
	public void setEventStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEventEndDate() {
		return endDate;
	}
	public void setEventEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getEventNumberCompleted() {
		return numberCompleted;
	}
	public void setEventNumberCompleted(String numberCompleted) {
		this.numberCompleted = numberCompleted;
	}
	
	
	public String getEventNumberUncompleted() {
		return numberUncompleted;
	}
	public void setEventNumberUncompleted(String numberUncompleted) {
		this.numberUncompleted = numberUncompleted;
	}
	
	public String getEventTotalNumber() {
		return totalNumberOfEvents;
	}
	public void setEventTotalNumber(String totalNumberOfEvents) {
		this.totalNumberOfEvents = totalNumberOfEvents;
	}
	
}
