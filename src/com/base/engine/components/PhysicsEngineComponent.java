package com.base.engine.components;

import com.base.engine.physics.PhysicsEngine;

public class PhysicsEngineComponent extends GameComponent {
	private PhysicsEngine engine;
	
	public PhysicsEngineComponent(PhysicsEngine engine) {
		this.engine = engine;
	}
	
	@Override
	public void update(float delta) {
		engine.simulate(delta);
		engine.handleCollisions();
	}
	
	public PhysicsEngine getEngine() { return engine; }
}
