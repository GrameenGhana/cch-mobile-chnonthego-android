package org.grameenfoundation.cch.model;

public class MyCalendarEvents {

	private String eventType;
	private String eventDescription;
	private String eventLocation;
	private String eventTime;
	private String eventComments;
	private String eventJustification;
	private String eventEdited;
	private String eventDeleted;
	private String eventStatus;
	private String eventCategory;
	private String eventId;
	private String startDate;
	private String endDate;
	private String numberCompleted;
	private String totalNumberOfEvents;
	private String numberUncompleted;
	
	public MyCalendarEvents()
{
}
	public MyCalendarEvents(String eventType,
							String eventDescription,
							String eventLocation, 
							String eventId, 
							String startDate, 
							String endDate,
							String eventStatus, 
							String eventCategory,
							String eventComments, 
							String eventJustification)
	{
		this.eventType=eventType;
		this.eventDescription=eventDescription;
		this.eventLocation=eventLocation;
		this.eventId=eventId;
		this.startDate=startDate;
		this.endDate=endDate;
		this.eventStatus=eventStatus;
		this.eventCategory=eventCategory;
		this.eventComments=eventComments;
		this.eventJustification=eventJustification;
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
	
	public String getEventComment() {
		return eventComments;
	}
	public void setEventComment(String eventComments) {
		this.eventComments = eventComments;
	}
	
	public String getEventJustification() {
		return eventJustification;
	}
	public void setEventJustification(String eventJustification) {
		this.eventJustification = eventJustification;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	
	public String getEventEdited() {
		return eventEdited;
	}
	public void setEventEdited(String eventEdited) {
		this.eventEdited = eventEdited;
	}
	
	public String getEventDeleted() {
		return eventDeleted;
	}
	public void setEventDeleted(String eventDeleted) {
		this.eventDeleted = eventDeleted;
	}
	
	public String getEventCategory() {
		return eventCategory;
	}
	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}
	
}
