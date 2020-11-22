package edu.westga.cs3230.healthcare_dbms.sql;

import java.util.function.Predicate;

/**
 * Implemented on an object to determine what should be hidden from the user
 * in the UI.
 *
 * @author Andrew Steinborn
 */
public interface AssociatedHider {
    
    /**
     * Hide function.
     *
     * @return the predicate
     */
    Predicate<String> hideFunction();
}
