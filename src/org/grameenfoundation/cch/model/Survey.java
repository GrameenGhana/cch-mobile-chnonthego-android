package org.grameenfoundation.cch.model;

public class Survey {

	private String surveyDateTaken;
	private String surveyStatus;
	private String surveyId;
	private String surveyReminderDate;
	private String surveyNextReminderDate;
	
	public Survey()
	{
		
	}
	
	public String getSurveyDateTaken() {
		return surveyDateTaken;
	}
	public void setSurveyDateTaken(String surveyDateTaken) {
		this.surveyDateTaken = surveyDateTaken;
	}
	
	public String getSurveyStatus() {
		return surveyStatus;
	}
	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}
	
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	
	public String getSurveyReminderDate() {
		return surveyReminderDate;
	}
	public void setSurveyReminderDate(String surveyReminderDate) {
		this.surveyReminderDate = surveyReminderDate;
	}
	
	
	public String getSurveyNextReminderDate() {
		return surveyNextReminderDate;
	}
	public void setSurveyNextReminderDate(String surveyNextReminderDate) {
		this.surveyNextReminderDate = surveyNextReminderDate;
	}
	
}
