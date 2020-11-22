package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies that this field should be hidden in the UI.
 *
 * @author Andrew Steinborn
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UiHide {
}
