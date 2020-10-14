package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SqlGetter {
	
	private static HashMap<Class<?>, HashMap<String, Method>> getCache = new HashMap<Class<?>, HashMap<String, Method>>();
	
	public static SqlTuple getFrom(Object getOf){
		
		HashMap<String, SqlAttribute> attrs = new HashMap<String, SqlAttribute>();

		SqlGetter.getGetters(getOf);
		
		HashMap<String, Method> methods = getCache.get(getOf.getClass());
		
		for(String key : methods.keySet()) {
			
			String attribute = key.toLowerCase();
			Method func = methods.get(key);
			if (func != null) {
				try {
					attrs.put(attribute, new SqlAttribute(attribute,func.invoke(getOf)));
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(methods.get(attribute));
					System.out.println(attribute);
					System.out.println(attrs.get(key));
					return null;
				}
			}
			
		}
		
		return new SqlTuple(attrs);
		
	}
	
	private static void getGetters(Object of) {

		if (SqlGetter.getCache.get(of.getClass()) != null) {
			return;
		}

		HashMap<String, Method> getters = new HashMap<String, Method>();

		for (Method m : of.getClass().getMethods()) {
			if (m.getName().startsWith("get") && m.getParameterCount() == 0 && m.getName().length() > 3) {
				if(!m.getName().equalsIgnoreCase("getClass")) {
					getters.put(m.getName().substring(3, m.getName().length()).toLowerCase(), m);
				}
			}
		}

		SqlGetter.getCache.put(of.getClass(), getters);
	}
	
}
