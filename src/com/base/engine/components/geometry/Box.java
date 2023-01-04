package com.base.engine.components.geometry;

import com.base.engine.components.GameComponent;

public class Box extends GameComponent{
	private float w, h, d;
	
	public Box() {
		this(1,1,1);
	}
	
	public Box(float w, float h, float d) {
		this.w = w;
		this.h = h;
		this.d = d;
	}
	
	
	
}
