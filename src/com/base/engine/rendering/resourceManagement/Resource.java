package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Resource extends MappedValues {	
	private HashMap<String, ArrayList<String>> strings;
	private HashMap<String, ArrayList<Integer>> ints;
	private int refCount;
	
	public Resource() {
		super();
		strings = new HashMap<String, ArrayList<String>>();
		ints = new HashMap<String, ArrayList<Integer>>();
		refCount = 1;
	}
	
	@Override
	protected void finalize() { 
		while(getInt("vbo") != 0) {	glDeleteBuffers(getInt("vbo")); }
		while(getInt("ibo") != 0) { glDeleteBuffers(getInt("ibo")); }
		while(getInt("program") != 0) { glDeleteProgram(getInt("program")); }
	}
	
	public void addStringArray(String name) { strings.put(name, new ArrayList<String>()); }
	public void addIntArray(String name) { ints.put(name, new ArrayList<Integer>());}
	
	public ArrayList<String> getStringArray(String name) { return strings.get(name); }
	public ArrayList<Integer> getIntArray(String name) { return ints.get(name); }
	
	public void addReference() { refCount++; }
	public boolean removeReference() { refCount--; return refCount == 0; }
}
