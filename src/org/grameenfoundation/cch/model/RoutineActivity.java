package org.grameenfoundation.cch.model;


public class RoutineActivity {

	private String userid;
	private String year;
	private String month;
	private String day;
	private String timeofday;
	private String profile;
	private String plan;
	private String order;
	private String action;
	private boolean isDone;
	
	public RoutineActivity()
	{
		this.isDone = false;
	}
	
	public String getUUID()
	{
		return userid+"-"+year+"-"+month+"-"+day+"-"+timeofday+"-"+profile+"-"+plan+"-"+order;
	}
			
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTimeofday() {
		return timeofday;
	}
	public void setTimeofday(String timeofday) {
		this.timeofday = timeofday;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setDone(boolean v)
	{
		this.isDone = v;
	}
	public boolean isDone()
	{
		return this.isDone;
	}
}
