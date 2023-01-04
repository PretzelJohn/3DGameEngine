package com.base.engine.components;

import com.base.engine.core.Vector3f;

public class SkyDome extends GameComponent {
	private Vector3f horizonColor;
	private Vector3f zenithColor;
	
	public SkyDome(Vector3f horizonColor, Vector3f zenithColor) {
		this.horizonColor = horizonColor;
		this.zenithColor = zenithColor;
	}
}
