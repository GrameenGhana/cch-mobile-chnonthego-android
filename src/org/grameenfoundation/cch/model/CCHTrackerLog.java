package org.grameenfoundation.cch.model;

import org.digitalcampus.oppia.application.MobileLearning;
import org.joda.time.DateTime;

public class CCHTrackerLog {

	private long id;
	private String content;
	private DateTime datetime;
	private boolean submitted;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public DateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(DateTime datetime) {
		this.datetime = datetime;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	
	public String getDateTimeString() {
		return MobileLearning.DATETIME_FORMAT.print(datetime);
	}
}
