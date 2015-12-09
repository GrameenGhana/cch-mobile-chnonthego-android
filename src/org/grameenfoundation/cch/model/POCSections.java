package org.grameenfoundation.cch.model;

public class POCSections {

	public String section_name;
	public String section_shortname;
	public String section_url;
	public String sub_section;
	private String sectionId;
	public POCSections()
	{
		
	}
	
	public POCSections(String section_name,String section_shortname,String section_url,String sub_section)
	{
		this.section_name=section_name;
		this.section_shortname=section_shortname;
		this.section_url=section_url;
		this.sub_section=sub_section;
	}
	
	public String getSectionId() {
		return sectionId;
	}
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	
	
	public String getSectionName() {
		return section_name;
	}
	public void setSectionName(String section_name) {
		this.section_name = section_name;
	}
	public String getSectionShortname() {
		return section_shortname;
	}
	public void setSectionShortname(String section_shortname) {
		this.section_shortname =section_shortname;
	}
	public String getSectionUrl() {
		return section_url;
	}
	public void setSectionUrl(String section_url) {
		this.section_url = section_url;
	}
	public String getSubSection() {
		return sub_section;
	}
	public void setSubSection(String sub_section) {
		this.sub_section =sub_section;
	}
	
}
