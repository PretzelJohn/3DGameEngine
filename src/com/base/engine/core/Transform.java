package com.base.engine.core;

public class Transform {
	private Transform parent;	
	private Matrix4f parentMatrix;
	
	private Vector3f pos, posOld;
	private Quaternion rot, rotOld;
	private Vector3f scl, sclOld;

	public Transform() {
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(0, 0, 0, 1);
		scl = new Vector3f(1, 1, 1);
		parentMatrix = new Matrix4f().initIdentity();
	}
	
	public void update() {
		if(posOld != null) {
			posOld.set(pos);
			rotOld.set(rot);
			sclOld.set(scl);
		}else {
			posOld = new Vector3f(0,0,0).set(pos).add(1.0f);
			rotOld = new Quaternion(0,0,0,0).set(rot).mul(0.5f);
			sclOld = new Vector3f(0,0,0).set(scl).add(1.0f);
		}
	}
	
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}
	
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}
	
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up){
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}
	
	public boolean hasChanged() {
		if(parent != null && parent.hasChanged())
			return true;
		if(!pos.equals(posOld))
			return true;
		if(!rot.equals(rotOld))
			return true;
		if(!scl.equals(sclOld))
			return true;
		return false;
	}

	public Matrix4f getTransformation() {
		Matrix4f translation = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotation = rot.getRotationMatrix();
		Matrix4f scale = new Matrix4f().initScale(scl.getX(), scl.getY(), scl.getZ());
		return getParentMatrix().mul(translation.mul(rotation.mul(scale)));
	}
	
	private Matrix4f getParentMatrix() {
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();
		return parentMatrix;
	}
	
	public Vector3f getTransformedPos() { return getParentMatrix().transform(pos); }
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(0,0,0,1);
		if(parent != null)
			parentRotation = parent.getTransformedRot();
		return parentRotation.mul(rot);
	}
	
	public Transform getParent() { return parent; }
	public Vector3f getPos() { return pos; }
	public Quaternion getRot() { return rot; }
	public Vector3f getScl() { return scl; }
	
	public void setParent(Transform parent) { this.parent = parent; }
	public void setPos(Vector3f pos) { this.pos.set(pos); }
	public void setPos(float x, float y, float z) { this.pos.set(x,y,z); }
	public void setRot(Quaternion rot) { this.rot.set(rot); }
	public void setScl(Vector3f scl) { this.scl.set(scl); }
	public void setScl(float x, float y, float z) { this.scl.set(x,y,z); }
}
