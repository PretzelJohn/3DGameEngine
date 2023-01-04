package com.base.engine.physics;

import java.util.ArrayList;

public class PhysicsEngine {
	private ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	
	public PhysicsEngine() {}
	
	public void addObject(PhysicsObject object) {
		objects.add(object);
	}
	
	public void simulate(float delta) {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).integrate(delta);
		}
	}
	
	public void handleCollisions() {
		for(int i = 0; i < objects.size(); i++) {
			for(int j = i + 1; j < objects.size(); j++) {
				IntersectData intersectData = objects.get(i).getBoundingSphere().intersectsBoundingSphere(objects.get(j).getBoundingSphere());
				
				if(intersectData.intersects()) {
					objects.get(i).setVelocity(objects.get(i).getVel().mul(-1));
					objects.get(j).setVelocity(objects.get(i).getVel().mul(-1));
					System.out.println("Intersecting!");
				}
			}
		}
	}
	
	public PhysicsObject getObject(int index) { return objects.get(index); }
	public int getNumObjects() { return objects.size(); }
}
