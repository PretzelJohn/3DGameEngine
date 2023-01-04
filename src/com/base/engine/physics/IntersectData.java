package com.base.engine.physics;

public class IntersectData {
	private boolean intersects;
	private float distance;
	
	public IntersectData(boolean intersects, float distance) {
		this.intersects = intersects;
		this.distance = distance;
	}
	public boolean intersects() {
		return intersects;
	}
	public float getDistance() {
		return distance;
	}
}
