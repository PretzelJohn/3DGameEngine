package com.base.engine.physics;

import com.base.engine.core.Vector3f;

public class BoundingSphere {
	private Vector3f center;
	private float radius;
	
	public BoundingSphere(Vector3f center, float radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public IntersectData intersectsBoundingSphere(BoundingSphere other) {
		float radiusDist = radius + other.radius;
		float centerDist = (other.getCenter().sub(center)).length();
		float dist = centerDist - radiusDist;
		
		return new IntersectData(centerDist < radiusDist, dist);
	}
	
	public Vector3f getCenter() { return center; }
	public float getRadius() { return radius; }
}
