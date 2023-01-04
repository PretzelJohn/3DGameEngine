package com.base.game;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.meshLoading.MeshGenerator;

public class Bone {
	private static Material mat = new Material(new Texture("bone.png"));
	private float l, w, h;
	private Vector3f startPos;
	private Vector3f pos;
	private Vector3f endPos;
	private Bone origin;
	private Mesh mesh;
	
	public Bone(float length, float width, float height, Bone origin, Vector3f eulerRot, int i) {
		this.origin = origin;
		this.startPos = origin.getEndPos();
		this.l = length;
		this.w = width;
		this.h = height;
		this.endPos = new Vector3f(startPos.getX() + l*(float)Math.cos(eulerRot.getX()), startPos.getY() + l*(float)Math.sin(eulerRot.getX()), startPos.getZ() + l*(float)Math.sin(eulerRot.getZ()));
		this.pos = (startPos).add(endPos);
		this.mesh = MeshGenerator.getBox("bone"+i, new Vector3f(l,w,h));
	}
	
	public Bone(float length, float width, float height, Vector3f startPos, Vector3f eulerRot, int i) {
		this.origin = null;
		this.startPos = startPos;
		this.l = length;
		this.w = width;
		this.h = height;
		this.endPos = new Vector3f(startPos.getX() + l*(float)Math.cos(eulerRot.getX()), startPos.getY() + l*(float)Math.sin(eulerRot.getX()), startPos.getZ() + l*(float)Math.sin(eulerRot.getZ()));
		this.pos = (startPos).add(endPos);
		System.out.println(startPos + ", " + pos + ", " + endPos);
		this.mesh = MeshGenerator.getBox("bone"+i, new Vector3f(l,w,h));
	}
	
	public Vector3f getStartPos() { return startPos; }
	public Vector3f getPos() { return pos; }
	public Vector3f getEndPos() { return endPos; }
	public float getLength() { return l; }
	public Bone getOrigin() { return origin; }
	public MeshRenderer getMeshRenderer() { return new MeshRenderer(mesh,mat); }
}
