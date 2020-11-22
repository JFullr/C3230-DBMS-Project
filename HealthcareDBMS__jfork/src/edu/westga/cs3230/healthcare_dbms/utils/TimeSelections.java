package edu.westga.cs3230.healthcare_dbms.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents allowable time values.
 *
 * @author Joseph Fuller
 */
public class TimeSelections {
	
	/** All allowable hour values. */
	public static final List<String> ALL_HOURS;

    static {
        List<String> hours = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
        	hours.add(""+i);
        }
        ALL_HOURS = Collections.unmodifiableList(hours);
    }
    
    /** All allowable minute values. */
    public static final List<String> ALL_MINUTES;

    static {
        List<String> minutes = new ArrayList<>();
        minutes.add("00");
        for(int i = 15; i <= 45; i+=15) {
        	minutes.add(""+i);
        }
        ALL_MINUTES = Collections.unmodifiableList(minutes);
    }
    
    /** All allowable diurnal values. */
    public static final List<String> ALL_DIURNALS;

    static {
        List<String> diurnals = new ArrayList<>();
        diurnals.add("AM");
        diurnals.add("PM");

        ALL_DIURNALS = Collections.unmodifiableList(diurnals);
    }

}
