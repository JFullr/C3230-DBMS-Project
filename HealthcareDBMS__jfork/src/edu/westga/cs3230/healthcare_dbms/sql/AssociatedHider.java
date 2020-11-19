package edu.westga.cs3230.healthcare_dbms.sql;

import java.util.function.Predicate;

public interface AssociatedHider {
    Predicate<String> hideFunction();
}
