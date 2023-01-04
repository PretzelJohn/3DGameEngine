package com.base.engine.components;

public class Gravity extends GameComponent {
	private static float G = 0.0000000000667408f;
	
	public static float getAccel(float mass, float distance) { return -G*(mass/distance)*(mass/distance);}
}
