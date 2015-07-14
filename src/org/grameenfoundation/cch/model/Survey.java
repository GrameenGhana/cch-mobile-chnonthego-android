package org.grameenfoundation.cch.model;

public class Survey {

	private String surveyDateTaken;
	private String surveyStatus;
	private String surveyId;
	private String surveyReminderDate;
	private String surveyNextReminderDate;
	private String surveyReminderFrequency;
	private String surveyReminderFrequencyValue;
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
	
	public String getSurveyReminderFrequency() {
		return surveyReminderFrequency;
	}
	public void setSurveyReminderFrequency(String surveyReminderFrequency) {
		this.surveyReminderFrequency = surveyReminderFrequency;
	}
	
	public String getSurveyReminderFrequencyValue() {
		return surveyReminderFrequencyValue;
	}
	public void setSurveyReminderFrequencyValue(String surveyReminderFrequencyValue) {
		this.surveyReminderFrequencyValue = surveyReminderFrequencyValue;
	}
}
