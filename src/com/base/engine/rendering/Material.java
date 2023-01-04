package com.base.engine.rendering;

import java.util.HashMap;

import com.base.engine.rendering.resourceManagement.MappedValues;

public class Material extends MappedValues {
	private static final Texture defaultTexture = new Texture("defaultTexture.png");
	private static final Texture defaultNormalMap = new Texture("default_normal.jpg");
	private static final Texture defaultDispMap = new Texture("default_disp.png");
	private HashMap<String, Texture> textures;

	public Material() {
		this(null);
	}
	
	public Material(Texture diffuse) {
		this(diffuse, 1, 8);
	}
	
	public Material(Texture diffuse, float specIntensity, float specExponent) {
		this(diffuse, specIntensity, specExponent, null);
	}
	
	public Material(Texture diffuse, float specIntensity, float specExponent, Texture normalMap) {
		this(diffuse, specIntensity, specExponent, normalMap, null);
	}
	
	public Material(Texture diffuse, float specIntensity, float specExponent, Texture normalMap, Texture dispMap) {
		this(diffuse, specIntensity, specExponent, normalMap, dispMap, 0.0f, 0.0f);
	}
	
	public Material(Texture diffuse, float specIntensity, float specExponent, Texture normalMap, Texture dispMap, float dispMapScl, float dispMapOffset) {
		textures = new HashMap<String, Texture>();
		if(diffuse == null)
			diffuse = defaultTexture;
		if(normalMap == null)
			normalMap = defaultNormalMap;
		if(dispMap == null)
			dispMap = defaultDispMap;
		addTexture("diffuse", diffuse);
		addFloat("specIntensity", specIntensity);
		addFloat("specExponent", specExponent);
		addTexture("normalMap", normalMap);
		addTexture("dispMap", dispMap);
		
		float defaultBias = dispMapScl/2.0f;
		addFloat("dispMapScl", dispMapScl);
		addFloat("dispMapBias", -defaultBias + (defaultBias * dispMapOffset));
	}

	public void addTexture(String name, Texture t) { textures.put(name, t); }
	
	public Texture getTexture(String name) { 
		Texture result = textures.get(name);
		if(result != null) return result;
		return defaultTexture;
	}	
}
