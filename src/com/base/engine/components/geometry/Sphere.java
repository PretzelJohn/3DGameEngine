package com.base.engine.components.geometry;

public class Sphere extends Ellipsoid{
	private float a;
	private float b;
	private float f;
	private float radius;
	public Sphere() {
		super();
		this.radius = 1;
	}
	
	public Sphere(float radius) {
		super(radius, radius);
		this.radius = radius;
		System.out.println(a + ", " + b+ ", "+f);
	}
	
	public float getSurfaceArea() {
		return 4*(float)Math.PI*radius*radius;
	}
	public float getVolume() {
		return (4/3)*(float)Math.PI*radius*radius*radius;
	}
	
	public float getCir() {
		return 2*(float)Math.PI*radius;
	}
}
