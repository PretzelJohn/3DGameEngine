package com.base.engine.components;

import com.base.engine.physics.PhysicsObject;

public class PhysicsObjectComponent extends GameComponent{
	private PhysicsObject object;
	
	public PhysicsObjectComponent(PhysicsObject object) {
		this.object = object;
	}
	
	@Override
	public void update(float delta) {
		getTransform().setPos(object.getPos());
	}
}
