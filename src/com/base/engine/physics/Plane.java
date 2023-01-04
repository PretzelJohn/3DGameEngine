package com.base.engine.physics;

import com.base.engine.core.Vector3f;

public class Plane {
	private Vector3f normal;
	private float distance;
	
	public Plane(Vector3f normal, float distance) {
		this.normal = normal;
		this.distance = distance;
	}
	
	public Plane normalized() {
		float length = normal.length();
		return new Plane(normal.div(length), distance/length);
	}
	
	public IntersectData intersectsSphere(BoundingSphere other) {
		float distFromSphereCenter = Math.abs(normal.dot(other.getCenter()) + distance);
		float distFromSphere = distFromSphereCenter - other.getRadius();
		
		return new IntersectData(distFromSphere < 0, distFromSphere);
	}

	public Vector3f getNormal() { return normal; }
	public float getDistance() { return distance; }
}
