package edu.westga.cs3230.healthcare_dbms.utils.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultSetObjectReflector {

    private static Map<Class<?>, Map<String, Method>> setCache = new HashMap<Class<?>, Map<String, Method>>();
    //https://stackoverflow.com/questions/34843327/how-to-generically-populate-an-object-if-the-field-names-are-provided

    public static <T> T reflect(T store, ResultSet set) throws SQLException, InvocationTargetException, IllegalAccessException {
        Map<String, Method> setters = getSetters(store.getClass());
        if (setters.keySet().isEmpty()) {
            return store;
        }

        for (Map.Entry<String, Method> entry : setters.entrySet()) {
            String label = entry.getKey();
            DbName nameAnnotation = entry.getValue().getAnnotation(DbName.class);
            if (nameAnnotation != null) {
                label = nameAnnotation.value();
            }

            // TODO: For now we only handle a few select data types
            Class<?> setterParameterType = entry.getValue().getParameterTypes()[0];
            if (setterParameterType == int.class) {
                entry.getValue().invoke(store, set.getInt(label));
            } else if (setterParameterType == char.class) {
                entry.getValue().invoke(store, set.getString(label).charAt(0));
            } else if (setterParameterType == Date.class) {
                entry.getValue().invoke(store, new Date(set.getDate(label).getTime()));
            } else if (setterParameterType == String.class) {
                entry.getValue().invoke(store, set.getString(label));
            }
        }
        return store;
    }

    private static Map<String, Method> getSetters(Object of) {
        Map<String, Method> existing = setCache.get(of.getClass());
        if(existing != null) {
            return existing;
        }
        Map<String, Method> setters = new HashMap<>();

        for(Method m : of.getClass().getMethods()) {
            if (!m.getName().startsWith("set") || m.getParameterCount() != 1 || m.getName().length() == 3) {
                continue;
            }
            setters.put(m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4), m);
        }
        setCache.put(of.getClass(), setters);
        return setters;
    }
}
