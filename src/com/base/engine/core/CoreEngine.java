package com.base.engine.core;

import com.base.engine.core.Time;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class CoreEngine {
	public static final double DEFAULT_FRAMERATE = 60.0;
	private boolean isRunning;
	private Game game;
	private RenderingEngine renderingEngine;
	
	private double frameTime;
	
	public CoreEngine(Game game, double fps) {
		this.frameTime = 1.0f / fps;
		this.isRunning = false;
		this.game = game;
		game.setEngine(this);
	}

	public void createWindow(String title, int width, int height) {
		Window.createWindow(title, width, height);
		this.renderingEngine = new RenderingEngine();
	}

	public void start() {
		if(isRunning) return;
		run();
	}

	public void stop() {
		if(!isRunning) return;
		isRunning = false;
	}

	private void run() {
		isRunning = true;
		
		int frames = 0;
		double frameCounter = 0.0;

		game.init();

		double lastTime = Time.getTime();
		double unprocessedTime = 0;

		while (isRunning) {
			boolean render = false;

			double startTime = Time.getTime();
			double elapsedTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += elapsedTime;
			frameCounter += elapsedTime;

			while (unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;
				
				if (Window.isCloseRequested())
					stop();
				
				game.input((float)frameTime);
				Input.update();
				game.update((float)frameTime);
				
				if(frameCounter >= 1.0) {
					System.out.println(frames + " fps");
					frames = 0;
					frameCounter = 0;
				}
			}

			if (render) {
				game.render(renderingEngine);
				Window.render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}

	private void cleanUp() {
		Window.dispose();
	}

	public RenderingEngine getRenderingEngine() { return renderingEngine; }
}
