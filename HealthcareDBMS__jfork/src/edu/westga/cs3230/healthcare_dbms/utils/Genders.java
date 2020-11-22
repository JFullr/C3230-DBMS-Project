package edu.westga.cs3230.healthcare_dbms.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Genders.
 */
public class Genders {

    /** The Constant ALL_GENDERS. */
    public static final List<String> ALL_GENDERS;

    static {
        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");

        ALL_GENDERS = Collections.unmodifiableList(genders);
    }
}
