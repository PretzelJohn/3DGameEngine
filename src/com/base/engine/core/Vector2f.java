package com.base.engine.core;

public class Vector2f {
	private float x, y;

	public Vector2f(float x, float y) {
		this.x = x; this.y = y;
	}
	
	public String toString() { return ("(" + x + ", " + y + ")"); }
	public boolean equals(Vector2f v) { return x == v.getX() && y == v.getY(); }
	public float max() { return Math.max(x,y); }
	public float min() { return Math.min(x,y); }
	
	public float length() { return (float)Math.sqrt(x * x + y * y); }
	public Vector2f normalized() { return new Vector2f(x / length(), y / length()); }
	
	public float cross(Vector2f v) { return x * v.getY() - y * v.getX(); }
	public float dot(Vector2f v) { return x * v.getX() + y * v.getY(); }
	
	public Vector2f abs() { return new Vector2f(Math.abs(x), Math.abs(y)); }
	public Vector2f add(Vector2f v) { return new Vector2f(x + v.getX(), y + v.getY()); }
	public Vector2f add(float r) { return new Vector2f(x + r, y + r); }
	public Vector2f sub(Vector2f v) { return new Vector2f(x - v.getX(), y - v.getY()); }
	public Vector2f sub(float v) { return new Vector2f(x - v, y - v); }
	public Vector2f mul(Vector2f v) { return new Vector2f(x * v.getX(), y * v.getY()); }
	public Vector2f mul(float v) { return new Vector2f(x * v, y * v); }
	public Vector2f div(Vector2f v) { return new Vector2f(x / v.getX(), y / v.getY()); }
	public Vector2f div(float v) { return new Vector2f(x / v, y / v); }
	public Vector2f lerp(Vector2f v, float lerpFactor) { return v.sub(this).mul(lerpFactor).add(this); }
	
	public Vector2f rotate(float angle) { 
		float cos = (float)Math.cos(angle);
		float sin = (float)Math.sin(angle);
		return new Vector2f(x * cos - y * sin, x * sin + y * cos); 
	}

	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public Vector2f set(float x, float y) { this.x = x; this.y = y; return this; }
	public Vector2f set(Vector2f v) { set(v.getX(), v.getY()); return this; }
}
