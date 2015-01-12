package org.digitalcampus.oppia.model;

import java.util.ArrayList;

public class Scores {

	
	private String maxScore;
	private String score;
	private String title;
	private String lastUpdated;

	public Scores(){
		
	}
	
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
			
	public String getScore() {
		return score;
	}
	public void setScore(String string) {
		this.score = string;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String actTitles) {
		this.title = actTitles;
	}
	
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
