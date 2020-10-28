package edu.westga.cs3230.healthcare_dbms.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeSelections {
	
	public static final List<String> ALL_HOURS;

    static {
        List<String> hours = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
        	hours.add(""+i);
        }
        ALL_HOURS = Collections.unmodifiableList(hours);
    }
    
    public static final List<String> ALL_MINUTES;

    static {
        List<String> minutes = new ArrayList<>();
        minutes.add("00");
        for(int i = 15; i <= 45; i+=15) {
        	minutes.add(""+i);
        }
        ALL_MINUTES = Collections.unmodifiableList(minutes);
    }
    
    public static final List<String> ALL_DIURNALS;

    static {
        List<String> diurnals = new ArrayList<>();
        diurnals.add("AM");
        diurnals.add("PM");

        ALL_DIURNALS = Collections.unmodifiableList(diurnals);
    }

}
