package org.grameenfoundation.cch.model;

public class CourseAchievments {

	private String courseName;
	private String courseSection;
	private String type;
	private String score;
	private String maxscore;
	private String percentage;
	private String resultsJSON;
	private String courseTitle;
	private String dateTaken;
	private String course_attempts;
	private String ksa_status;
	private String id;
	private String recordid;
	
	public CourseAchievments()
	{
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecordId() {
		return recordid;
	}
	public void setRecordId(String recordid) {
		this.recordid = recordid;
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
	public String getMaxScore() {
		return maxscore;
	}
	public void setMaxScore(String maxscore) {
		this.maxscore = maxscore;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public String getDateTaken() {
		return dateTaken;
	}
	public void setDateTaken(String dateTaken) {
		this.dateTaken = dateTaken;
	}
	
	public String getCourseAttempts() {
		return course_attempts;
	}
	public void setCourseAttempts(String course_attempts) {
		this.course_attempts = course_attempts;
	}
	
	public String getCourseKSAStatus() {
		return ksa_status;
	}
	public void setCourseKSAStatus(String ksa_status) {
		this.ksa_status = ksa_status;
	}


}
