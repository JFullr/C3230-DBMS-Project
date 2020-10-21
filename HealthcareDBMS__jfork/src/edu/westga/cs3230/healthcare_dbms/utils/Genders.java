package edu.westga.cs3230.healthcare_dbms.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genders {

    public static final List<String> ALL_GENDERS;

    static {
        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");

        ALL_GENDERS = Collections.unmodifiableList(genders);
    }
}
