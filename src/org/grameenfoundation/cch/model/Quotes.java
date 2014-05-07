
package org.grameenfoundation.cch.model;

import org.digitalcampus.oppia.application.MobileLearning;
import org.joda.time.DateTime;

public class Quotes {

	private long id;
	private String author;
	private String category;
	private String content;
	private String lastUpdate;
	private String TAG = "QUOTE";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(String datetime) {
		this.lastUpdate = datetime;
	}
		
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
