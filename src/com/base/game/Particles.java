package com.base.game;

import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.components.MeshRenderer;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Window;
import com.base.engine.rendering.meshLoading.MeshGenerator;

public class Particles extends Game {
	public void init() {
		Material ballMat = new Material(new Texture("white.png", Texture.T_2D, Texture.F_NONE_M_NONE), 1, 16, new Texture("default_normal.jpg"));
		Material floorMat = new Material(new Texture("checker.png", Texture.T_2D, Texture.F_NONE_M_NONE), 1, 8, new Texture("checker_normal.png"));
		
		Mesh ballMesh = new Mesh("ball.obj");
		Mesh floorMesh = MeshGenerator.getBox("ground", new Vector3f(40,0.01f,40));
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1,0.8f,0.8f), 0.6f);
		GameObject directionalLightObject = new GameObject().addComponent(directionalLight);
		directionalLight.getTransform().getRot().set(new Quaternion(Quaternion.X_AXIS, Util.toRadians(-45)));
		
		GameObject floor = new GameObject().addComponent(new MeshRenderer(floorMesh, floorMat));
		addObject(floor);
		
		addObject(directionalLightObject);
		
		for(int i = 0; i < 720; i+=4) {
			GameObject ball = new GameObject().addComponent(new MeshRenderer(ballMesh, ballMat));
			ball.getTransform().setPos(new Vector3f((10 + (10*(float)Math.toRadians(i)))*(float)Math.sin(Math.toRadians(i)), 20*(float)Math.toRadians(i), (10 + (10*(float)Math.toRadians(i)))*(float)Math.cos(Math.toRadians(i))));
			addObject(ball);
		}
		
		for(int i = 0; i < 720; i+=4) {
			GameObject ball = new GameObject().addComponent(new MeshRenderer(ballMesh, ballMat));
			ball.getTransform().setPos(new Vector3f(-(10 + (10*(float)Math.toRadians(i)))*(float)Math.sin(Math.toRadians(i)), 20*(float)Math.toRadians(i), -(10 + (10*(float)Math.toRadians(i)))*(float)Math.cos(Math.toRadians(i))));
			addObject(ball);
		}
		
		GameObject camera = new GameObject().addComponent(new FreeLook(1f)).addComponent(new FreeMove(20)).addComponent(new Camera((float)Window.getWidth()/(float)Window.getHeight()));
		addObject(camera);
	}
}
