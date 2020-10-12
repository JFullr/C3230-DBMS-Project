package query.utils;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SqlPopulator {
	
	private static HashMap<Class<?>, HashMap<String, Method>> setCache = new HashMap<Class<?>, HashMap<String, Method>>();
	//https://stackoverflow.com/questions/34843327/how-to-generically-populate-an-object-if-the-field-names-are-provided
	
	public static <T> T initFromSql(T store, HashMap<String, SQLData> values) {
		
		getSetters(store);
		
		/* disallow partial initializations
		if(values.keySet().size() != setCache.get(store.getClass()).keySet().size()) {
			return null;
		}
		*/
		
		Class<?> sclass = store.getClass();
		
		HashMap<String, Method> setters = setCache.get(sclass);
		if(setters == null) {
			return null;
		}
		
		if(setters.keySet().size() < 1) {
			return store;
		}
		
		for(String attr : values.keySet()) {
			String attribute = attr.toLowerCase();
			Method func = setters.get(attribute);
			if(func != null) {
				try {
					func.invoke(store, values.get(attr).getValue());
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(setters.get(attribute));
					System.out.println(attribute);
					System.out.println(values.get(attr));
					return null;
				}
			}
		}
		return store;
	}
	
	private static void getSetters(Object of) {
		
		if(setCache.get(of.getClass()) != null) {
			return;
		}
		
		HashMap<String, Method> setters = new HashMap<String, Method>();
		
	    for(Method m : of.getClass().getMethods()) {
	        if (!m.getName().startsWith("set") || m.getParameterCount() != 1 || m.getName().length() == 3) {
	            continue;
	        }
	        setters.put(m.getName().substring(3,m.getName().length()).toLowerCase(), m);
	    }
	    
	    setCache.put(of.getClass(), setters);
	}
}
