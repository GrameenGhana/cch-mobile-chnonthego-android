/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;
import java.util.Vector;
import java.util.ListIterator;
import java.net.URLEncoder;

public class XmlGuiForm {

	private String formNumber;
	private String formName;
	private String submitTo;
	public Vector<XmlGuiFormField> fields;
	private String formType;
	private String color_code;
	private String formTitle;
	private String formSubTitle;
	
	
	public XmlGuiForm()
	{
		this.fields = new Vector<XmlGuiFormField>();
		formNumber = "";
		formName = "";
		submitTo = "loopback"; // ie, do nothing but display the results
	}
	// getters & setters
	public String getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
	
	public String getFormSubTitle() {
		return formSubTitle;
	}

	public void setFormSubTitle(String formSubTitle) {
		this.formSubTitle = formSubTitle;
	}

	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormColor() {
		return color_code;
	}
	public void setFormColor(String color_code) {
		this.color_code = color_code;
	}
	public String getSubmitTo() {
		return submitTo;
	}

	public void setSubmitTo(String submitTo) {
		this.submitTo = submitTo;
	}

	public Vector<XmlGuiFormField> getFields() {
		return fields;
	}

	public void setFields(Vector<XmlGuiFormField> fields) {
		this.fields = fields;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("XmlGuiForm:\n");
		sb.append("Form Number: " + this.formNumber + "\n");
		sb.append("Form Name: " + this.formName + "\n");
		sb.append("Submit To: " + this.submitTo + "\n");
		if (this.fields == null) return sb.toString();
		ListIterator<XmlGuiFormField> li = this.fields.listIterator();
		while (li.hasNext()) {
			sb.append(li.next().toString());
		}
		
		return sb.toString();
	}
	
	public String getFormattedResults()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Results:\n");
		if (this.fields == null) return sb.toString();
		ListIterator<XmlGuiFormField> li = this.fields.listIterator();
		while (li.hasNext()) {
			sb.append(li.next().getFormattedResult() + "\n");
		}
		
		return sb.toString();
	}
	
	public String getResults()
	{
		StringBuilder sb = new StringBuilder();
		if (this.fields == null) return sb.toString();
		ListIterator<XmlGuiFormField> li = this.fields.listIterator();
		while (li.hasNext()) {
			sb.append(li.next().getResults() );
		}
		
		return sb.toString();
	}
	public String getFormEncodedData()
	{
		try {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("Results:\n");
		if (this.fields == null) return sb.toString();
		ListIterator<XmlGuiFormField> li = this.fields.listIterator();
		while (li.hasNext()) {
			if (i != 0) sb.append("&");
			XmlGuiFormField thisField = li.next();
			sb.append(thisField.name + "=");
			String encstring = new String();
			URLEncoder.encode((String) thisField.getData(),encstring);
			sb.append(encstring);
		}

		return sb.toString();
		}
		catch (Exception e) {
			return "ErrorEncoding";
		}
	}
	
	
}
