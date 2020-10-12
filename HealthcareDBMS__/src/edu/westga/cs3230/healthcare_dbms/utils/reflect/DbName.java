package edu.westga.cs3230.healthcare_dbms.utils.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the name of this field in this bean is different in the database. This annotation is to be put on the
 * getter and setter methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DbName {
    String value();
}
