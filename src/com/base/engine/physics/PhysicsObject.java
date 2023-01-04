package com.base.engine.physics;

import com.base.engine.components.Gravity;
import com.base.engine.core.Vector3f;

public class PhysicsObject {
	private Vector3f pos, velocity;
	private float radius, distance, mass;

	public PhysicsObject(Vector3f pos, Vector3f velocity, float mass, float distance, float radius) {
		this.pos = pos;
		this.velocity = velocity;
		this.radius = radius;
		this.mass = mass;
		this.distance = distance;
	}
	
	public void integrate(float delta) {
		velocity = velocity.add(new Vector3f(0, Gravity.getAccel(mass, distance), 0));
		pos = pos.add(velocity.mul(delta));
	}
	
	public Vector3f getPos() { return pos; }
	public Vector3f getVel() { return velocity; }
	public float getRadius() { return radius; }
	public BoundingSphere getBoundingSphere() {	return new BoundingSphere(pos, radius); }
	
	public void setVelocity(Vector3f velocity) { this.velocity = velocity; }
}
