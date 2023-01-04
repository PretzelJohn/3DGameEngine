package com.base.engine.physics;

import com.base.engine.core.Vector3f;

public class BoundingBox {
	private Vector3f minExtents, maxExtents;
	
	public BoundingBox(Vector3f minExtents, Vector3f maxExtents) {
		this.minExtents = minExtents;
		this.maxExtents = maxExtents;
	}
	
	public IntersectData intersectsBoundingBox(BoundingBox other) {
		Vector3f distances1 = other.getMinExtents().sub(maxExtents);
		Vector3f distances2 = minExtents.sub(other.getMaxExtents());
		Vector3f distances = distances1.max(distances2);
		
		float maxDistance = distances.max();
		
		return new IntersectData(maxDistance < 0, maxDistance);
	}

	public Vector3f getMinExtents() { return minExtents; }
	public Vector3f getMaxExtents() { return maxExtents; }
}
