package org.grameenfoundation.cch.model;

public class FacilityTargets {

	public String targetStartDate;
	public String targetEndDate;
	public String targetLastUpdated;
	public String targetNumberAchieved;
	public String targetNumber;
	public String targetReminder;
	public String targetStatus;
	public String targetId;
	public String targetTotalNumber;
	public String targetNumberRemaining;
	private String targetOldId;
	private String targetType;
	private String targetCategory;
	private String targetDetail;
	private String targetGroup;
	private String targetOverall;
	private String targetGroupMembers;
	private String targetMonth;
	public FacilityTargets()
	{
		
	}
	
	public FacilityTargets(String target_id,String target_type,String target_detail,String target_category,String target_no,String target_achieved,String target_remaining,String start,String due,
							String reminder,String status,String lastUpdated,String overallTarget,String group_members,String month)
	{
		this.targetId=target_id;
		this.targetType=target_type;
		this.targetDetail=target_detail;
		this.targetCategory=target_category;
		this.targetNumber=target_no;
		this.targetNumberAchieved=target_achieved;
		this.targetNumberRemaining=target_remaining;
		this.targetStartDate=start;
		this.targetEndDate=due;
		this.targetReminder=reminder;
		this.targetStatus=status;
		this.targetLastUpdated=lastUpdated;
		this.targetGroupMembers=group_members;
		this.targetMonth=month;
		this.targetOverall=overallTarget;
	}
	
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	
	public String getTargetOldId() {
		return targetOldId;
	}
	public void setTargetOldId(String targetOldId) {
		this.targetOldId = targetOldId;
	}
	public String getTargetStartDate() {
		return targetStartDate;
	}
	public void setTargetStartDate(String TargetStartDate) {
		this.targetStartDate =TargetStartDate;
	}
	public String getTargetEndDate() {
		return targetEndDate;
	}
	public void setTargetEndDate(String targetEndDate) {
		this.targetEndDate = targetEndDate;
	}
	public String getTargetNumberAchieved() {
		return targetNumberAchieved;
	}
	public void setTargetNumberAchieved(String targetNumberAchieved) {
		this.targetNumberAchieved =targetNumberAchieved;
	}
	public String getTargetNumber() {
		return targetNumber;
	}
	public void setTargetNumber(String targetNumber) {
		this.targetNumber = targetNumber;
	}
	
	public String getTargetReminder() {
		return targetReminder;
	}
	public void setTargetReminder(String targetReminder) {
		this.targetReminder = targetReminder;
	}
	
	public String getTargetStatus() {
		return targetStatus;
	}
	public void setTargetStatus(String targetStatus) {
		this.targetStatus = targetStatus;
	}
	
	public String getTargetLastUpdated() {
		return targetLastUpdated;
	}
	public void setTargetLastUpdated(String targetLastUpdated) {
		this.targetLastUpdated = targetLastUpdated;
	}
	
	public String getTargetNumberRemaining() {
		return targetNumberRemaining;
	}
	public void setEventTargetNumberRemaining(String targetNumberRemaining) {
		this.targetNumberRemaining = targetNumberRemaining;
	}
	
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType= targetType;
	}
	
	public String getTargetCategory() {
		return targetCategory;
	}
	public void setTargetCategory(String targetCategory) {
		this.targetCategory= targetCategory;
	}
	
	public String getTargetDetail() {
		return targetDetail;
	}
	public void setTargetDetail(String targetDetail) {
		this.targetDetail= targetDetail;
	}
	
	public String getTargetGroup() {
		return targetGroup;
	}
	public void setTargetGroup(String targetGroup) {
		this.targetGroup= targetGroup;
	}
	public String getTargetOverall() {
		return targetOverall;
	}
	public void setTargetOverall(String targetOverall) {
		this.targetOverall= targetOverall;
	}
	public String getTargetGroupMembers() {
		return targetGroupMembers;
	}
	public void setTargetGroupMembers(String targetGroupMembers) {
		this.targetGroupMembers= targetGroupMembers;
	}
	public String getTargetMonth() {
		return targetMonth;
	}
	public void setTargetMonth(String targetMonth) {
		this.targetMonth= targetMonth;
	}
}
