package com.base.engine.rendering.resourceManagement;

import java.util.HashMap;

import com.base.engine.core.Vector3f;

public abstract class MappedValues {
	private HashMap<String, Vector3f> vector3fMap;
	private HashMap<String, Float> floatMap;
	private HashMap<String, Integer> intMap;
	private HashMap<String, String> stringMap;
	
	public MappedValues() {
		vector3fMap = new HashMap<String, Vector3f>();
		floatMap = new HashMap<String, Float>();
		intMap = new HashMap<String, Integer>();
		stringMap = new HashMap<String, String>();
	}
	
	public void addVector3f(String name, Vector3f v) { vector3fMap.put(name, v); }
	public void addFloat(String name, float f) { floatMap.put(name, f); }
	public void addInt(String name, int f) { intMap.put(name, f); }
	public void addString(String name, String value) { stringMap.put(name, value); }
	
	public Vector3f getVector3f(String name) { 
		Vector3f result = vector3fMap.get(name);
		if(result != null) return result;
		return new Vector3f(0,0,0);
	}
	
	public float getFloat(String name) { 
		Float result = floatMap.get(name);
		if(result != null) return result;
		return 0;
	}
	
	public int getInt(String name) { 
		Integer result = intMap.get(name);
		if(result != null) return result;
		return 0;
	}
	
	public String getString(String name) { 
		String result = stringMap.get(name);
		if(result != "") return result;
		return "";
	}
}
