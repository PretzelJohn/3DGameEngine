package com.base.engine.rendering;

import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import com.base.engine.core.Vector2f;

public class Window {
	public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int DEFAULT_WIDTH = 1280;
	public static final int DEFAULT_HEIGHT = 720;

	public static void createWindow(String title, int width, int height) {
		Display.setTitle(title);
		Display.setVSyncEnabled(true);
		try {
			if(width == SCREEN_WIDTH && height == SCREEN_HEIGHT) {
				Display.setFullscreen(true);
				Display.setDisplayMode(Display.getDisplayMode());
			}else {
				Display.setFullscreen(false);
				Display.setDisplayMode(new DisplayMode(width, height));
			};
			
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void render() {
		Display.update();
	}
	
	public static void bindAsRenderTarget() {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		glViewport(0, 0, getWidth(), getHeight());
	}

	public static void dispose() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public static String getTitle() { return Display.getTitle(); }
	public static int getWidth() { return Display.getDisplayMode().getWidth(); }
	public static int getHeight() { return Display.getDisplayMode().getHeight(); }
	public static Vector2f getCenter() { return new Vector2f(getWidth()/2,getHeight()/2); }
	
	public static boolean isCloseRequested() { return Display.isCloseRequested(); }	
}
