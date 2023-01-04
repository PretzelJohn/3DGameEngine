package com.base.engine.core;

public class Vector3f {
	private float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString() { return ("(" + x + ", " + y + ", " + z + ")"); }
	public boolean equals(Vector3f v) { return x == v.getX() && y == v.getY() && z == v.getZ(); }
	public float max() { return Math.max(x,  Math.max(y, z)); }
	public float min() { return Math.min(x,  Math.min(y, z)); }
	
	public Vector3f max(Vector3f v) { 
		return new Vector3f(Math.max(x, v.getX()), Math.max(y, v.getY()), Math.max(z, v.getZ()));
	}
	
	public float length() { return (float) Math.sqrt(x * x + y * y + z * z); }
	public Vector3f normalized() { return new Vector3f(x/length(), y/length(), z/length()); }
	
	public Vector3f cross(Vector3f v) { return new Vector3f(y*v.getZ() - z*v.getY(), z*v.getX() - x*v.getZ(), x*v.getY() - y*v.getX()); }
	public float dot(Vector3f v) { return x * v.getX() + y * v.getY() + z * v.getZ(); }
	
	public Vector3f abs() { return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z)); }
	public Vector3f add(Vector3f v) { return new Vector3f(x + v.getX(), y + v.getY(), z + v.getZ()); }
	public Vector3f add(float f) { return new Vector3f(x + f, y + f, z + f); }
	public Vector3f sub(Vector3f v) { return new Vector3f(x - v.getX(), y - v.getY(), z - v.getZ()); }
	public Vector3f sub(float f) { return new Vector3f(x - f, y - f, z - f); }
	public Vector3f mul(Vector3f v) { return new Vector3f(x * v.getX(), y * v.getY(), z * v.getZ()); }
	public Vector3f mul(float f) { return new Vector3f(x * f, y * f, z * f); }
	public Vector3f div(Vector3f v) { return new Vector3f(x / v.getX(), y / v.getY(), z / v.getZ()); }
	public Vector3f div(float f) { return new Vector3f(x / f, y / f, z / f); }
	public Vector3f lerp(Vector3f dest, float lerpFactor) { return dest.sub(this).mul(lerpFactor).add(this); }
	
	public Vector3f rotate(Vector3f axis, float angle) { return this.rotate(new Quaternion(axis, angle)); }
	public Vector3f rotate(Quaternion rotation) { 
		Quaternion w = rotation.mul(this).mul(rotation.conjugate()); 
		return new Vector3f(w.getX(), w.getY(), w.getZ()).normalized(); 
	}

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	
	public Vector2f getXY() { return new Vector2f(x, y); }
	public Vector2f getYZ() { return new Vector2f(y, z); }
	public Vector2f getZX() { return new Vector2f(z, x); }
	
	public Vector2f getYX() { return new Vector2f(y, x); }
	public Vector2f getZY() { return new Vector2f(z, y); }
	public Vector2f getXZ() { return new Vector2f(x, z); }
	
	public void setX(float x) {	this.x = x; }
	public void setY(float y) {	this.y = y; }
	public void setZ(float z) { this.z = z; }
	public Vector3f set(float x, float y, float z) { this.x = x; this.y = y; this.z = z; return this; }
	public Vector3f set(Vector3f v) { set(v.getX(), v.getY(), v.getZ()); return this; }
}
