package edu.westga.cs3230.healthcare_dbms.utils;

public class EmptyUtil {
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof Number) {
            return Double.compare(0, ((Number) o).doubleValue()) == -1;
        } else if (o instanceof String) {
            return ((String) o).isEmpty();
        } else {
            return false;
        }
    }
}
