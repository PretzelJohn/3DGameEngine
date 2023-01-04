package com.base.game;

import com.base.engine.core.CoreEngine;
import com.base.game.planets.PlanetsTest;

public class Main {
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(new TestGame(), CoreEngine.DEFAULT_FRAMERATE);
		engine.createWindow("3D Game Engine", 1024,1024);
		engine.start();
	}
}
