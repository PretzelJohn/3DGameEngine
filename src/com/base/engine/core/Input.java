package com.base.engine.core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	public static final int NUM_KEYS = 110;
	public static final int NUM_MBUTTONS = 5;

	private static boolean[] lastKeys = new boolean[NUM_KEYS];
	private static int lastKeyPressed = 0;
	private static boolean[] lastMouse = new boolean[NUM_MBUTTONS];

	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			lastKeys[i] = getKey(i);
			if (getKey(i))
				lastKeyPressed = i;
		}

		for (int i = 0; i < NUM_MBUTTONS; i++)
			lastMouse[i] = getMouse(i);
	}

	public static boolean getKey(int key) { return Keyboard.isKeyDown(key); }
	public static boolean getKeyDown(int key) { return getKey(key) && (lastKeyPressed == key); }
	public static boolean getKeyUp(int key) { return !getKey(key) && (lastKeyPressed == key); }
	
	public static boolean getMouse(int button) { return Mouse.isButtonDown(button); }
	public static boolean getMouseDown(int button) { return getMouse(button) && !lastMouse[button]; }
	public static boolean getMouseUp(int button) { return !getMouse(button) && lastMouse[button]; }
	public static Vector2f getMousePos() { return new Vector2f(Mouse.getX(), Mouse.getY()); }
	
	public static void setMousePos(Vector2f pos) { Mouse.setCursorPosition((int) pos.getX(), (int) pos.getY()); }
	public static void setCursor(boolean enabled) { Mouse.setGrabbed(!enabled); }
}
