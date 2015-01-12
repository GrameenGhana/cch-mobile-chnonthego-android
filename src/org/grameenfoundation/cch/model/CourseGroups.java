package org.grameenfoundation.cch.model;

import java.util.ArrayList;

import org.digitalcampus.oppia.model.CourseChild;

public class CourseGroups {

    private CourseAchievments Name;
    private ArrayList<CourseChild> Items;

    public CourseAchievments getName() {
        return Name;
    }

    public void setName(CourseAchievments name) {
        this.Name = name;
    }

    public ArrayList<CourseChild> getItems() {
        return Items;
    }

    public void setItems(ArrayList<CourseChild> Items) {
        this.Items = Items;
    }

}

