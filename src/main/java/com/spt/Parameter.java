package com.spt;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.StringTokenizer;

import javolution.util.FastMap;


public class Parameter {
	String action,ani,svc,clickid,pubid,provider;
	public String getSvc() {
		return svc;
	}
	public void setSvc(String svc) {
		this.svc = svc;
	}
	public String getClickid() {
		return clickid;
	}
	public void setClickid(String clickid) {
		this.clickid = clickid;
	}
	
	public String getPubid() {
		return pubid;
	}
	public void setPubid(String pubid) {
		this.pubid = pubid;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String errorRepsone = "{\"responseStatus\":\"0\",\"data\":{},\"responseMessage\":\"Something Went wrong\"}";
	public String noActionResponse = "{\"responseStatus\":\"2000\",\"data\":{},\"responseMessage\":\"No action Found\"}";
	public String success = "{\"responseStatus\":1,\"data\":{},\"responseMessage\":\"Success\"}";
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	@SuppressWarnings("unchecked")
	public void setField(String quaryString, Parameter ObjParam) throws Exception {

		String decodedTarget = URLDecoder.decode(quaryString, "UTF-8");
		StringTokenizer st = new StringTokenizer(decodedTarget, "&");
		FastMap params = new FastMap<String, String>();

		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			String kv[] = s.split("=");
			params.put(kv[0].trim(), (kv.length == 2) ? kv[1].trim() : "0");
		}
		Iterator iterator = params.keySet().iterator();
		Class<?> clazz = ObjParam.getClass();

		while (iterator.hasNext()) {
			try {
				String key = (String) iterator.next();
				Field field = clazz.getDeclaredField(key);
				Class type = field.getType();
				String Fieldtype = type.getCanonicalName();
				field.setAccessible(true);
				if ("int".equalsIgnoreCase(Fieldtype) || " java.lang.Integer".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, new Integer((String) params.get(key)));
				else if ("long".equalsIgnoreCase(Fieldtype) || "java.lang.Long".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, new Long((String) params.get(key)));
				else if ("double".equalsIgnoreCase(Fieldtype) || " java.lang.Double".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, new Integer((String) params.get(key)));
				else if ("java.lang.String".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, (String) params.get(key));
				@SuppressWarnings("unused")
				Object value = field.get(ObjParam);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
