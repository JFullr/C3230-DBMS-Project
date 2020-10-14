package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SqlSetter {

	private static HashMap<Class<?>, HashMap<String, Method>> setCache = new HashMap<Class<?>, HashMap<String, Method>>();

	public static <T> T fillWith(T store, SqlTuple values) {

		return SqlSetter.fillWith(store, values, false);
	}

	public static <T> T fillWith(T store, SqlTuple values, boolean noPartialFills) {

		HashMap<String, SqlAttribute> attrs = values.getAttributes();

		SqlSetter.getSetters(store);

		if (noPartialFills && attrs.keySet().size() != SqlSetter.setCache.get(store.getClass()).keySet().size()) {
			return null;
		}

		Class<?> sclass = store.getClass();

		HashMap<String, Method> setters = SqlSetter.setCache.get(sclass);
		if (setters == null) {
			return null;
		}

		if (setters.keySet().size() < 1) {
			return store;
		}

		for (String attr : attrs.keySet()) {
			String attribute = attr.toLowerCase();
			Method func = setters.get(attribute);
			if (func != null) {
				try {
					func.invoke(store, attrs.get(attr).getValue());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(setters.get(attribute));
					System.out.println(attribute);
					System.out.println(attrs.get(attr));
					return null;
				}
			}
		}
		return store;
	}

	private static void getSetters(Object of) {

		if (SqlSetter.setCache.get(of.getClass()) != null) {
			return;
		}

		HashMap<String, Method> setters = new HashMap<String, Method>();

		for (Method m : of.getClass().getMethods()) {
			if (m.getName().startsWith("set") && m.getParameterCount() == 1 && m.getName().length() > 3) {
				setters.put(m.getName().substring(3, m.getName().length()).toLowerCase(), m);
			}
		}

		SqlSetter.setCache.put(of.getClass(), setters);
	}
}
