package com.base.game.planets;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.Vector3f;

public class Planet {
	private MeshRenderer meshRenderer;
	private float radius;
	private int dist;
	private float rotSpeed, orbitSpeed;
	private Vector3f rotAxis;
	private float orbitAngle;
	
	public Planet (MeshRenderer meshRenderer, float radius, int dist, Vector3f rotAxis, float rotSpeed, float orbitSpeed) {
		this.meshRenderer = meshRenderer;
		this.radius = radius;
		this.dist = dist;
		this.rotAxis = rotAxis;
		this.rotSpeed = rotSpeed;
		this.orbitSpeed = orbitSpeed;
		orbitAngle = (float)(Math.random() * (Math.PI * 2));
	}
	
	public MeshRenderer getMeshRenderer() { return meshRenderer; }
	public Vector3f getPos() { return new Vector3f(dist*(float)Math.cos(orbitAngle), 0, dist*(float)Math.sin(orbitAngle)); }
	
	public Vector3f getRotAxis() { return rotAxis; }
	public float getRotSpeed() { return rotSpeed; }
}
