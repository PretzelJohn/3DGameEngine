package com.base.engine.rendering;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class Vertex {
	public static final int SIZE = 11;

	private Vector3f pos;
	private Vector2f texCoord;
	private Vector3f normal;
	private Vector3f tangent;
	
	public Vertex(Vector3f pos) {
		this(pos, new Vector2f(0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord) {
		this(pos, texCoord, new Vector3f(0,0,0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal) {
		this(pos, texCoord, normal, new Vector3f(0,0,0));
	}

	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal, Vector3f tangent) {
		this.pos = pos;
		this.texCoord = texCoord;
		this.setNormal(normal);
		this.setTangent(tangent);
	}

	public Vector3f getPos() { return pos; }
	public Vector2f getTexCoord() {	return texCoord; }
	public Vector3f getNormal() { return normal; }
	public Vector3f getTangent() { return tangent; }
	
	public void setPos(Vector3f pos) { this.pos = pos; }	
	public void setTexCoord(Vector2f texCoord) { this.texCoord = texCoord; }
	public void setNormal(Vector3f normal) { this.normal = normal; }
	public void setTangent(Vector3f tangent) { this.tangent = tangent; }
}
