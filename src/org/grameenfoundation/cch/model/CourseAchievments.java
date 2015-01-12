package org.grameenfoundation.cch.model;

public class CourseAchievments {

	private String courseName;
	private String courseSection;
	private String type;
	private String score;
	private String percentage;
	private String resultsJSON;
	
	public CourseAchievments()
	{
		
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
			
	public String getCourseSection() {
		return courseSection;
	}
	public void setCourseSection(String courseSection) {
		this.courseSection = courseSection;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
}