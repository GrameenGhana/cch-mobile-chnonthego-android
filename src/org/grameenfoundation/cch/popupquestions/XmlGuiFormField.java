/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;

import org.json.JSONException;
import org.json.JSONObject;




// class to handle each individual form
public class XmlGuiFormField {
	String name;
	String label;
	String type;
	boolean required;
	String options;
	Object obj;			// holds the ui implementation , i.e. the EditText for example
	private String link;
	private String group;
	private String color_code;
	
	
	// getters & setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getColorCode() {
		return color_code;
	}
	public void setColorCode(String color_code) {
		this.color_code = color_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Field Name: " + this.name + "\n");
		sb.append("Field Label: " + this.label + "\n");
		sb.append("Field Type: " + this.type + "\n");
		sb.append("Field Link: " + this.link + "\n");
		sb.append("Field Group: " + this.group + "\n");
		sb.append("Required? : " + this.required + "\n");
		sb.append("Options : " + this.options + "\n");
		sb.append("Value : " + (String) this.getData() + "\n");
		
		return sb.toString();
	}
	public String getFormattedResult()
	{
		return this.name + "= [" + (String) this.getData() + "]";

	}
	public String getResults()
	{
		JSONObject json=new JSONObject();
		try {
			json.put(this.name, (String) this.getData());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String) this.getData();

	}
	public Object getData()
	{
		
		if (type.equals("choice")) {
			if (obj != null) {
				XmlGuiRadioButtonChoice po = (XmlGuiRadioButtonChoice) obj;
				return po.getValue();
			}
		}
		
		// todo, add other UI elements here
		return null;
	}

}