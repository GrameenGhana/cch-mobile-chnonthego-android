package org.grameenfoundation.cch.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.grameenfoundation.cch.model.FacilityTargets;

public class Sorter implements Comparator<FacilityTargets> {

	  private static final List<String> ORDERED_ENTRIES = Arrays.asList(
			  "Not set","Daily", "Weekly","Monthly");

	  @Override
	  public int compare(FacilityTargets o1, FacilityTargets o2) {
	    if (ORDERED_ENTRIES.contains(o1) && ORDERED_ENTRIES.contains(o2)) {
	      // Both objects are in our ordered list. Compare them by
	      // their position in the list
	      return ORDERED_ENTRIES.indexOf(o1) - ORDERED_ENTRIES.indexOf(o2);
	    }

	    if (ORDERED_ENTRIES.contains(o1)) {
	      // o1 is in the ordered list, but o2 isn't. o1 is smaller (i.e. first)
	      return -1;
	    }

	    if (ORDERED_ENTRIES.contains(o2)) {
	      // o2 is in the ordered list, but o1 isn't. o2 is smaller (i.e. first)
	      return 1;
	    }

	    return o1.toString().compareTo(o2.toString());
	  }
	}