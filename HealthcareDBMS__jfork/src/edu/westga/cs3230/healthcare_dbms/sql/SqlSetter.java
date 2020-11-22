package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Maps an {@link SqlTuple} into a object.
 *
 * @author Joseph Fuller
 */
public class SqlSetter {

	/** The set cache. */
	private static HashMap<Class<?>, HashMap<String, Method>> setCache = new HashMap<Class<?>, HashMap<String, Method>>();

	/**
	 * Fill with.
	 *
	 * @param <T> the generic type
	 * @param store the store
	 * @param values the values
	 * @return the t
	 */
	public static <T> T fillWith(T store, SqlTuple values) {

		return SqlSetter.fillWith(store, values, false);
	}

	/**
	 * Fill with.
	 *
	 * @param <T> the generic type
	 * @param store the store
	 * @param values the values
	 * @param noPartialFills the no partial fills
	 * @return the t
	 */
	public static <T> T fillWith(T store, SqlTuple values, boolean noPartialFills) {
		
		if(values == null) {
			return store;
		}
		
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
					return null;
				}
			}
		}
		return store;
	}

	/**
	 * Gets the setters.
	 *
	 * @param of the of
	 * @return the setters
	 */
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
