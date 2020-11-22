package edu.westga.cs3230.healthcare_dbms.sql;

import java.util.function.Predicate;

// TODO: Auto-generated Javadoc
/**
 * The Interface AssociatedHider.
 */
public interface AssociatedHider {
    
    /**
     * Hide function.
     *
     * @return the predicate
     */
    Predicate<String> hideFunction();
}
