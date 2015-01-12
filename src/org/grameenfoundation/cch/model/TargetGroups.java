package org.grameenfoundation.cch.model;

import java.util.ArrayList;

public class TargetGroups {
	private String Name;
	    private ArrayList<TargetChild> Items;
	     
	    public String getName() {
	        return Name;
	    }
	    public void setName(String name) {
	        this.Name = name;
	    }
	    public ArrayList<TargetChild> getItems() {
	        return Items;
	    }
	    public void setItems(ArrayList<TargetChild> Items) {
	        this.Items = Items;
	    }

}
