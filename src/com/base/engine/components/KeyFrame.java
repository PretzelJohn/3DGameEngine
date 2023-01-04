package com.base.engine.components;

import com.base.engine.core.Transform;
import com.base.engine.rendering.Material;

public class KeyFrame {
	private Transform transform;
	private Material material;
	private int millisecond;
	
	public KeyFrame(int millisecond) {
		this.transform = new Transform();
		this.material = new Material();
		this.millisecond = millisecond;
	}
	
	public void setTransform(Transform transform) { this.transform = transform; }
	public void setMaterial(Material material) { this.material = material; }
	
	public Transform getTransform() { return transform; }
	public Material getMaterial() { return material; }
	
	public int getMillisecond() { return millisecond; }
}
